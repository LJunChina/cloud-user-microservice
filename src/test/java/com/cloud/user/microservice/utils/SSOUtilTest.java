package com.cloud.user.microservice.utils;

import org.junit.Test;

import java.util.UUID;
import java.util.stream.Stream;

public class SSOUtilTest {

    @Test
    public void testGeneratorTokenId(){
        StringBuilder sb = new StringBuilder();
        String uuid = UUID.randomUUID().toString();
        String[] strings = uuid.split("-");
        Stream.of(strings).forEach(s -> sb.append(s));
        System.out.println(sb.toString().toUpperCase());
    }
}
