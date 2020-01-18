package com.alvis.exam.viewmodel.admin.question;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
public class QuestionEditRequestVM {

    private Integer id;
    @NotNull
    private Integer questionType;
    @NotNull
    private Integer subjectId;
    @NotBlank
    private String title;

    private Integer gradeLevel;

    @Valid
    private List<QuestionEditItemVM> items;

    @NotBlank
    private String analyze;

    private List<String> correctArray;  //填空题的答案

    private String correct;  //正确的
    @NotBlank
    private String score; //分数

    @Range(min = 1, max = 5, message = "请选择题目难度")
    private Integer difficult;

    private Integer itemOrder;
}
