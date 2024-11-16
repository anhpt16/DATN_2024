package com.nhatanh.centerlearn.common.utils;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;

import java.util.Base64;

@EzySingleton
public class Base64Util
{
    public String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public String decodePassword(String encodedPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
        return new String(decodedBytes);
    }
}
