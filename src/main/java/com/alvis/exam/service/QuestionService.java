package com.alvis.exam.service;

import com.alvis.exam.domain.Question;
import com.alvis.exam.viewmodel.admin.question.QuestionEditRequestVM;
import com.alvis.exam.viewmodel.admin.question.QuestionPageRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface QuestionService extends BaseService<Question> {

    PageInfo<Question> page(QuestionPageRequestVM requestVM);

    /**
     * 试题的添加
     *
     * @param model
     * @param userId
     * @return
     */
    Question insertFullQuestion(QuestionEditRequestVM model, Integer userId);

    /**
     * 试题的修改
     *
     * @param model
     * @return
     */
    Question updateFullQuestion(QuestionEditRequestVM model);

    QuestionEditRequestVM getQuestionEditRequestVM(Integer questionId);

    QuestionEditRequestVM getQuestionEditRequestVM(Question question);

    Integer selectAllCount();

    List<Integer> selectMothCount();
}
