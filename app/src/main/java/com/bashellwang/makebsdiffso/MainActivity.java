package com.bashellwang.makebsdiffso;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bashellwang.libbsdiff.BsdiffUtils;

import java.io.File;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mMakeDiffBtn;
    private Button mMergeDiffBtn;

    private String mOldPath;

    // diff 用
    private String mNewPathForDiff;
    private String mGeneratePatchPath;

    // merge 用
    private String mPatchPathForMerge;
    private String mGenerateNewPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HandlerManager.get().init();

        mMakeDiffBtn = findViewById(R.id.make_diff);
        mMergeDiffBtn = findViewById(R.id.merge_diff);

        mMakeDiffBtn.setOnClickListener(this);
        mMergeDiffBtn.setOnClickListener(this);

        initAssert();

    }

    private void initAssert() {
        // 1. 拷贝公用基础包
        String appFilesPath = getFilesDir().getPath() + File.separator;
        mOldPath = appFilesPath + "old.apk";

        InputStream inputStream = FileUtils.openAssetInputStream(this, "old.apk");
        FileUtils.copyFile(inputStream, mOldPath);

        // 2. 拷贝做差分 diff 用的资源
        mNewPathForDiff = appFilesPath + "diff" + File.separator + "app2.apk";
        mGeneratePatchPath = appFilesPath + "diff" + File.separator + "diff_patch";

        inputStream = FileUtils.openAssetInputStream(this, "diff/app2.apk");
        FileUtils.copyFile(inputStream, mNewPathForDiff);

        // 3. 拷贝做差分包合并 merge 的资源
        mPatchPathForMerge = appFilesPath + "merge" + File.separator + "patch";
        mGenerateNewPath = appFilesPath + "merge" + File.separator + "new.apk";

        inputStream = FileUtils.openAssetInputStream(this, "merge/patch");
        FileUtils.copyFile(inputStream, mPatchPathForMerge);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.make_diff:
                if (!FileUtils.isFileExists(mOldPath) || !FileUtils.isFileExists(mNewPathForDiff)) {
                    Toast.makeText(MainActivity.this, "对应文件不存在", Toast.LENGTH_SHORT).show();
                    return;
                }

                Thread diffThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final int diff = BsdiffUtils.diff(mOldPath, mNewPathForDiff, mGeneratePatchPath);
                        String diffMd5 = FileUtils.getFileMD5ToString(mGeneratePatchPath);
                        Log.e("MainActivity", "差分结果：" + diff);
                        Log.e("MainActivity", "差分md5：" + diffMd5);
                        HandlerManager.get().mainPost(new Runnable() {
                            @Override
                            public void run() {
                                if (diff == 0) {
                                    Toast.makeText(MainActivity.this, "生成差分包成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "生成差分包失败:" + diff, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
                diffThread.start();
                break;
            case R.id.merge_diff:
                if (!FileUtils.isFileExists(mOldPath) || !FileUtils.isFileExists(mPatchPathForMerge)) {
                    Toast.makeText(MainActivity.this, "对应文件不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                Thread mergeThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final int result = BsdiffUtils.patch(mOldPath, mPatchPathForMerge, mGenerateNewPath);
                            Log.e("MainActivity", "合并结果：" + result);
                            HandlerManager.get().mainPost(new Runnable() {
                                @Override
                                public void run() {
                                    if (result == 0) {
                                        Toast.makeText(MainActivity.this, "合并成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "合并失败:" + result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            HandlerManager.get().mainPost(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "合并失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });
                mergeThread.start();
                break;
            default:
                break;
        }
    }
}
