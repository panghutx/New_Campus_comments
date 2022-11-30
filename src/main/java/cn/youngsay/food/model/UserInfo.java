package cn.youngsay.food.model;

import lombok.Data;

@Data
public class UserInfo {
    private Integer uid;
    private String loginName;
    private String username;
    private String password;
    private String head;
    private String createTime;
}
