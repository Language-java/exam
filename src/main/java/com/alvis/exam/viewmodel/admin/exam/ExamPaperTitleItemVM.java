package com.alvis.exam.viewmodel.admin.exam;

import com.alvis.exam.viewmodel.admin.question.QuestionEditRequestVM;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ExamPaperTitleItemVM {

    @NotBlank(message = "标题内容不能为空") //只能作用在String上，不能为null，而且调用trim()后，长度必须大于0
    private String name;

    @Size(min = 1,message = "请添加题目")
    @Valid
    private List<QuestionEditRequestVM> questionItems;
}
