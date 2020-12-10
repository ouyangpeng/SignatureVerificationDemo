#include <jni.h>
#include <string>
#include"valid_sha1.cpp"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_csdn_ouyangpeng_jni_SignatureVerificationUtil_getSignaturesSha1FromC(
        JNIEnv *env,
        jobject,
        jobject contextObject) {

    // 返回存在C层的sha1码
    return env->NewStringUTF(app_sha1);
}


extern "C"
JNIEXPORT jboolean JNICALL
Java_com_csdn_ouyangpeng_jni_SignatureVerificationUtil_checkSha1FromC(
        JNIEnv *env,
        jobject,
        jobject contextObject) {

    // 检验签名是否正确
    char *sha1 = getSha1(env, contextObject);
    jboolean result = checkValidity(env, sha1);
    return result;
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_csdn_ouyangpeng_jni_SignatureVerificationUtil_getTokenFromC(
        JNIEnv *env,
        jobject,
        jobject contextObject,
        jstring userId) {
    // 检验签名是否正确
    char *sha1 = getSha1(env, contextObject);
    jboolean result = checkValidity(env, sha1);

    // 如果签名正确，则返回正确的token
    if (result) {
        return env->NewStringUTF("获取Token成功，token为 ouyangpeng");
    } else {
        // 如果签名不正确，则提示不可用
        return env->NewStringUTF("获取Token失败，请检查valid.cpp文件配置的sha1值");
    }
}