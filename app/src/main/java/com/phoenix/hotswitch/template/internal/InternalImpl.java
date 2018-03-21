package com.phoenix.hotswitch.template.internal;


import com.phoenix.hotswitch.CustomInterface;

/**
 * Created by zhenghui on 2018/3/14.
 */

public class InternalImpl implements CustomInterface {

    private static final String MSG = "toast from internal";

    @Override
    public String getText() {
        return MSG;
    }
}
