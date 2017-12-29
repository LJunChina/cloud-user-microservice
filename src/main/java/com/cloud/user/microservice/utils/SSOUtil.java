package com.cloud.user.microservice.utils;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * SSO工具类
 */
public class SSOUtil {

    private SSOUtil(){}

    /**
     * 生成tokenId
     * @return
     */
    public static String generatorTokenId(){
        StringBuilder sb = new StringBuilder();
        String uuid = UUID.randomUUID().toString();
        String[] strings = uuid.split("-");
        Stream.of(strings).forEach(s -> sb.append(s));
        return sb.toString().toUpperCase();
    }
}
