package com.phoenix.hotswitch;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.phoenix.hotswitch.template.ToastFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    private Context mContext;

    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        mBtn1 = findViewById(R.id.btn_theme_1);
        mBtn2 = findViewById(R.id.btn_theme_2);
        mBtn3 = findViewById(R.id.btn_theme_3);
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v == mBtn1) {
            ToastFactory.getInstance(this).showCustomToast(ToastFactory.Type.INTERNAL);
        } else if(v == mBtn2) {
            ToastFactory.getInstance(this).showCustomToast(ToastFactory.Type.EXTERNAL);
        } else if(v == mBtn3) {
            ToastFactory.getInstance(this).loadClass();
        }
    }
}
