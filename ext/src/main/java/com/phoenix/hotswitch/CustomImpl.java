package com.phoenix.hotswitch;

/**
 * Created by zhenghui on 2018/3/13.
 */

public class CustomImpl implements CustomInterface {
    private static final String EXT_STRING = "hello from the other side";
    @Override
    public String getText() {
        return EXT_STRING;
    }
}
