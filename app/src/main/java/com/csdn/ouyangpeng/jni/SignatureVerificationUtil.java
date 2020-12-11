package com.csdn.ouyangpeng.jni;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.security.MessageDigest;
import java.util.Locale;


public class SignatureVerificationUtil {
    private static final String TAG = "SignatureVerificationUtil";

    static {
        System.loadLibrary("native-lib");
    }

    /**
     * 从C层拿预置的SHA1码
     */
    public native String getSignaturesSha1FromC(Context context);

    /**
     * 从C层验证签名是否正确
     */
    public native boolean checkSha1FromC(Context context);

    /**
     * 从C层去做具体的业务，做之前要验证签名是否正确，如果不正确，则获取Token失败
     */
    public native String getTokenFromC(Context context, String userId);


    /**
     * 获得APK的签名文件的SHA1码
     */
    public String getSha1ValueFromJava(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (byte b : publicKey) {
                String appendString = Integer.toHexString(0xFF & b)
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
            }
            return hexString.toString();
        } catch (Exception e) {
            Log.d(TAG, "getSha1Value() crashed , e =" + Log.getStackTraceString(e));
        }
        return null;
    }
}
