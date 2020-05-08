bsdiff 4.3:http://www.daemonology.net/bsdiff/bsdiff-4.3.tar.gz

依赖 bzip2:

https://sourceforge.net/projects/bzip2/files/latest/download

CMakeLists.txt 必须在 gradle 里配置才能自动补全


CMakeLists 里 文件包含类别要写清楚，比如 diffpatch-lib.cpp 就不能写出 diffpatch-lib.c

cpp 和 c 的写法，引用很多不一样的地方

c 写法：声明写在 .h 头文件里，然后 include
cpp: 直接写 cpp 文件里


点击 Build -> Rebuild 即可生成 so 文件，
在 libbsdiff/build/intermediates/cmake 下