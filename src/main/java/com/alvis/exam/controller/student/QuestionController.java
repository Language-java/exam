package com.alvis.exam.controller.student;

import com.alvis.exam.base.BaseApiController;
import com.alvis.exam.base.RestResponse;
import com.alvis.exam.domain.Question;
import com.alvis.exam.domain.TextContent;
import com.alvis.exam.domain.question.QuestionObject;
import com.alvis.exam.service.QuestionService;
import com.alvis.exam.service.TextContentService;
import com.alvis.exam.utility.*;
import com.alvis.exam.viewmodel.admin.question.QuestionEditRequestVM;
import com.alvis.exam.viewmodel.admin.question.QuestionPageRequestVM;
import com.alvis.exam.viewmodel.admin.question.QuestionResponseVM;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController("StudentQuestionController")
@RequestMapping(value = "/api/student/question")
@AllArgsConstructor
public class QuestionController extends BaseApiController {

    private final QuestionService questionService;
    private final TextContentService textContentService;



}
