package com.bashellwang.libbsdiff;

/**
 * Created on 2020/4/24.
 */
public class BsdiffUtils {
    static {
        System.loadLibrary("bsdiff");
    }

    //生成差分包
    public static native int diff(String oldFile, String newFile, String patchFile);


    public static native int patch(String oldFile, String patchFile, String newFile);
}
