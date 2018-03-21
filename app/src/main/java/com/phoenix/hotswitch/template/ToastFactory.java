package com.phoenix.hotswitch.template;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.phoenix.hotswitch.CustomInterface;
import com.phoenix.hotswitch.template.internal.InternalImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import dalvik.system.DexClassLoader;

/**
 * Created by zhenghui on 2018/3/14.
 */

public class ToastFactory {
    private static final String TAG = "ToastFactory";

    public enum Type {
        INTERNAL,
        EXTERNAL
    }

    private static ToastFactory mInstance;
    private Context mContext;
    private CustomInterface mExt;

    private ToastFactory(Context context) {
        mContext = context;
    }

    public static ToastFactory getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new ToastFactory(context);
        }

        return mInstance;
    }

    public void showCustomToast(Type type) {
        switch (type){
            case EXTERNAL:
                Toast.makeText(mContext, getMsgFromExt(), Toast.LENGTH_SHORT).show();
                break;

            case INTERNAL:
                Toast.makeText(mContext, getMsgFromInernal(), Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    private String getMsgFromInernal(){
        CustomInterface impl = new InternalImpl();
        String msg = impl.getText();
        return msg;
    }

    private String getMsgFromExt() {
        if(mExt != null) {
            return mExt.getText();
        } else {
            return "ooops";
        }
    }

    public void loadClass() {
        String target = extractJarFromAsset();
        if(TextUtils.isEmpty(target)) {
            return;
        }

        DexClassLoader loader = new DexClassLoader(target, mContext.getExternalCacheDir().getAbsolutePath(), null, mContext.getClassLoader());

        try {
            Class extImpl = loader.loadClass("com.phoenix.hotswitch.CustomImpl");
            mExt = (CustomInterface) extImpl.newInstance();
            Toast.makeText(mContext, "ext class loaded success", Toast.LENGTH_SHORT).show();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private String extractJarFromAsset() {
        try {
            String[] jarFiles = mContext.getAssets().list("jar");
            boolean matched = false;
            String jar = "";
            for(String file : jarFiles) {
                if(file.equals("extdex.jar")) {
                    jar = file;
                    matched = true;
                    break;
                }
            }
            if(!matched) {
                return "";
            }
            InputStream ios = mContext.getAssets().open("jar" + File.separator + jar);
            File dir = new File(mContext.getExternalCacheDir().getAbsoluteFile(), "jar");
            if(!dir.exists()){
                dir.mkdirs();
            }

            File extract = new File(dir.getAbsolutePath() + File.separator + jar);
            FileOutputStream fos = new FileOutputStream(extract);
            int bytecount = 0;
            byte[] buffer = new byte[1024];
            while((bytecount = ios.read(buffer)) != -1) {
                fos.write(buffer, 0, bytecount);
            }
            fos.flush();
            ios.close();
            fos.close();
            Log.d(TAG, "extractJarFromAsset: successed with: " + extract.getAbsolutePath());
            return extract.getAbsolutePath();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
