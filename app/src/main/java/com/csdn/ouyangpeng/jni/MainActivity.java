package com.csdn.ouyangpeng.jni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    protected TextView appSignaturesTv;
    protected TextView jniSignaturesTv;
    protected Button checkBtn;
    protected Button tokenBtn;

    SignatureVerificationUtil signatureVerificationUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        signatureVerificationUtil = new SignatureVerificationUtil();

        initView();

        String sha1Value = signatureVerificationUtil.getSha1ValueFromJava(MainActivity.this);
        appSignaturesTv.setText(sha1Value);
        jniSignaturesTv.setText(signatureVerificationUtil.getSignaturesSha1FromC(MainActivity.this));
        Log.d(TAG, "sha1Value =" + sha1Value);
    }

    private void initView() {
        appSignaturesTv = (TextView) findViewById(R.id.app_signatures_tv);
        jniSignaturesTv = (TextView) findViewById(R.id.jni_signatures_tv);
        checkBtn = (Button) findViewById(R.id.check_btn);
        tokenBtn = (Button) findViewById(R.id.token_btn);

        checkBtn.setOnClickListener(this);
        tokenBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.check_btn) {
            boolean result = signatureVerificationUtil.checkSha1FromC(MainActivity.this);
            if (result) {
                Toast.makeText(getApplicationContext(), "验证通过", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "验证不通过，请检查valid.cpp文件配置的sha1值", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.token_btn) {
            String result = signatureVerificationUtil.getTokenFromC(MainActivity.this, "12345");
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}
