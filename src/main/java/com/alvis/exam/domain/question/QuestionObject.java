package com.alvis.exam.domain.question;


import lombok.Data;

import java.util.List;

@Data
public class QuestionObject {

    private String titleContent;  //

    private String analyze;  //分析内容

    private List<QuestionItemObject> questionItemObjects; //填空对应的答案集合

    private String correct; //选则的答案
}
