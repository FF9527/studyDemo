package com.app.idgenerator;

import java.util.UUID;

/**
 * @author:wuqi
 * @date:2020/3/26
 * @description:com.app.uuid
 * @version:1.0
 */
public class UUIDTest {
    public static void main(String[] args) {
        //uuid生成——随机
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());//5db67001-7d8b-49a0-baa2-215629b00ae9
    }
}
