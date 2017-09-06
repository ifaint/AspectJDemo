//
// Created by 张小飞 on 2017/8/7.
//

# include "demo_aspectjdemo_MathKit.h"

 JNIEXPORT jint JNICALL Java_demo_aspectjdemo_MathKit_square(JNIEnv *env, jint num){
    return num*num;
 }