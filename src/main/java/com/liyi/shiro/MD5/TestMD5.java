package com.liyi.shiro.MD5;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author liyi
 * @create 2021 -10 -16 -23:54
 */
public class TestMD5 { // MD5使用测试
    public static void main(String[] args) {
        // MD5
        Md5Hash hash = new Md5Hash("123456");
        System.out.println(hash.toHex());
        // MD5 + Salt
        Md5Hash hash1 = new Md5Hash("123456", "sdifh");
        System.out.println(hash1.toHex());
        // MD5 + Salt + hash散列（次数）
        Md5Hash hash2 = new Md5Hash("123456", "sdifh", 1024);
        System.out.println(hash2.toHex());
    }
}
