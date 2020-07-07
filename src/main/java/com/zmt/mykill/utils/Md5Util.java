package com.zmt.mykill.utils;

import org.springframework.util.DigestUtils;

public class Md5Util {
    private static final String SALT = "zuihaohaishibuyaoyongweiyiyan";

    public static String getMD5(String str) {
        String base = str + "/" + SALT;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public static void main(String[] args) {
        System.out.println(getMD5("123456"));
    }
}
