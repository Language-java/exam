package com.alvis.exam.domain.question;

import lombok.Data;

@Data
public class QuestionItemObject {
    //答案封装
    private String prefix; //选项对应的编号(选项前面的前缀A B C D或者1,2)

    private String content; //单个答案

    private Integer score; //分数
}
