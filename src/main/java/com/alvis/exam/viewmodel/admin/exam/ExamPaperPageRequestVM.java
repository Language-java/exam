package com.alvis.exam.viewmodel.admin.exam;

import com.alvis.exam.base.BasePage;
import lombok.Data;

@Data
public class ExamPaperPageRequestVM extends BasePage {
    //封装的查询条件
    private Integer id;
    private Integer subjectId;//学科id
    private Integer level;    //年级id
    private Integer paperType; //试卷类型 (使用了枚举)
    private Integer taskExamId;  //试卷id
}
