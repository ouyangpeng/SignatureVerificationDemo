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
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String getSignaturesSha1FromC(Context context);

    public native boolean checkSha1FromC(Context context);

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
