package com.zmt.mykill.utils;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class SignUtil {

    /**
     * 工具类私有构造
     */
    private SignUtil() {
    }

    /**
     * 获取签名
     *
     * @param key    KEY
     * @param params 待加签数据
     * @param type   签名类型
     * @return 签名
     */
    public static final String sign(String key, TreeMap<String, String> params, SignType type) {
        return sign(key, assembleSrc(params), type);
    }

    /**
     * 获取签名
     *
     * @param key  KEY
     * @param src  待加签数据
     * @param type 签名类型
     * @return 签名
     */
    public static final String sign(String key, String src, SignType type) {
        try {
            return ShaUtil.sign(src, key, type.name());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 组装源数据
     *
     * @param param 参数
     * @return 源数据
     */
    private static String assembleSrc(TreeMap<String, String> param) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            Object value = entry.getValue();
            builder.append(entry.getKey()).append("=").append(value).append("&");
        }
        return builder.substring(0, builder.length() - 1);
    }

    /**
     * 签名类型
     */
    public enum SignType {

        HmacSHA1, HmacSHA256
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        TreeMap<String, String> param = new TreeMap<String, String>();
        param.put("partner", "101");
        param.put("signType", "HmacSHA1");
        param.put("user", "xiekang_i");
        System.out.println(sign("7BD2C6BD01E245A4A36821B0A7A1A8FC", param, SignType.HmacSHA256));

    }
}
