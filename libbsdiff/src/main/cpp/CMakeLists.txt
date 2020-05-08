# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.4.1)

# 查找文件系统中指定模式的路径，如/* 匹配根目录的文件（注意路径）
file(GLOB bzip2 bzip2/*.c)

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

add_library( # Specifies the name of the library.
        bsdiff

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        bsdiff.c
        bspatch.c
        # 这里名字需要完全匹配，不能你是 cpp 类型文件，这里只写 .c 后缀；会有问题
        diffpatch-lib.cpp
        ${bzip2}
        )


include_directories(src/main/cpp)

find_library( # Defines the name of the path variable that stores the
        # location of the NDK library.
        log-lib

        # Specifies the name of the NDK library that
        # CMake needs to locate.
        log)


# Links your native library against one or more other native libraries.
target_link_libraries( # Specifies the target library.
        bsdiff

        # Links the log library to the target library.
        ${log-lib})