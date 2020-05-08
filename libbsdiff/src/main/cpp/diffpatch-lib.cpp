#include <jni.h>
#include <cstdlib>

//
// Created on 2020/4/24.
// 输入 jni，ide 会有提示自动生成 jni 方法
//

extern "C" {
int bsdiff_main(int argc, const char *argv[]);

int bspatch_main(int argc, const char *argv[]);
}


extern "C" JNIEXPORT jint JNICALL
Java_com_bashellwang_libbsdiff_BsdiffUtils_diff(JNIEnv *env, jclass clazz, jstring old_file,
                                                jstring new_file, jstring patch_file) {

    // for cpp
    const char *oldFile = env->GetStringUTFChars(old_file, 0);
    const char *patchFile = env->GetStringUTFChars(patch_file, 0);
    const char *newFile = env->GetStringUTFChars(new_file, 0);

    const char *argv[] = {"", oldFile, newFile, patchFile};

    int result = bsdiff_main(4, argv);

    env->ReleaseStringUTFChars(old_file, oldFile);
    env->ReleaseStringUTFChars(patch_file, patchFile);
    env->ReleaseStringUTFChars(new_file, newFile);

    return result;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_bashellwang_libbsdiff_BsdiffUtils_patch(JNIEnv *env, jclass clazz, jstring old_file,
                                                 jstring patch_file, jstring new_file) {

    // for cpp
    const char *oldFile = env->GetStringUTFChars(old_file, 0);
    const char *patchFile = env->GetStringUTFChars(patch_file, 0);
    const char *newFile = env->GetStringUTFChars(new_file, 0);

    const char *argv[] = {"", oldFile, newFile, patchFile};

    int result = bspatch_main(4, argv);


    env->ReleaseStringUTFChars(old_file, oldFile);
    env->ReleaseStringUTFChars(new_file, newFile);
    env->ReleaseStringUTFChars(patch_file, patchFile);

    return result;
}