package com.cloud.user.microservice.utils;

import com.cloud.user.microservice.enums.RoleEnum;
import org.junit.Test;

import java.net.URLEncoder;
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

    @Test
    public void urlEncoder(){
        try {
            System.out.println(URLEncoder.encode("http://local.joninfo.cn:8020/index.html&appName=user-app","UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testEnum(){
        System.out.println(RoleEnum.ASSESSOR);
    }
}
