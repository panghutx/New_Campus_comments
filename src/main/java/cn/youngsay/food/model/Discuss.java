package cn.youngsay.food.model;

import lombok.Data;

@Data
public class Discuss {
    private Integer commentId;
    private String title;
    private Integer score;
    private String content;
    private String createTime;
    private Integer uid;
}
