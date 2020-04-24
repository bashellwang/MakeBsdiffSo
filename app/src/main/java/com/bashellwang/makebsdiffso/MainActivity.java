package com.bashellwang.makebsdiffso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mMakeDiffBtn;
    private Button mMergeDiffBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMakeDiffBtn = findViewById(R.id.make_diff);
        mMergeDiffBtn = findViewById(R.id.merge_diff);

        mMakeDiffBtn.setOnClickListener(this);
        mMergeDiffBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.make_diff:
                break;
            case R.id.merge_diff:
                break;
            default:
                break;
        }
    }
}
