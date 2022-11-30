package cn.youngsay.food.common;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;

public class PasswordUtil {
    public static String encrypt(String password){
        //得到盐值
        String salt = IdUtil.fastSimpleUUID();
        //得到加密后的密码
        String finalPassword = SecureUtil.md5(salt+password);
        return salt+"$"+finalPassword;
    }
    public static boolean decrypt(String password,String dbPassword){
        //校验
        if(password == null || password.equals("") || dbPassword == null || !dbPassword.contains("$")){
            return false;
        }
        //从dbPassword得到盐值
        String[] split = dbPassword.split("\\$");
        String salt = split[0];
        //将盐值与密码结合加密
        String finalPassword = SecureUtil.md5(salt + password);
        //将其与数据库中的加密密码比较
        return finalPassword.equals(split[1]);

    }

    public static void main(String[] args) {
        System.out.println(PasswordUtil.decrypt("zzzzzz", "7737e31010804f77826d63af60ba8523$738a5cc695927417d4c0b88bcb31ae54"));
    }

}
