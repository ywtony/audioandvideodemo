#include <jni.h>
#include <string>
#include "lame/lame.h"

/**
 * JNIEnv*参数：代表Java环境，通过这环境可以调用Java里面的方法
 * object参数：调用C语言的方法对象，this对象表示当前对象，即调用JNI方法所在的类
 *
 *
 *
 * C中调用Java中的String类型为 jstring;
 */

extern "C" jstring
Java_com_yw_lamedemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    //此处输出lame的版本号
    return env->NewStringUTF(get_lame_version());
}

//此处仅仅是输出了lame的版本号，如果需要其他的新加的功能可以自己添加对应的功能
