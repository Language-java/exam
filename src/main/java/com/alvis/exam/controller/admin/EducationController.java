package com.alvis.exam.controller.admin;


import com.alvis.exam.base.BaseApiController;
import com.alvis.exam.base.RestResponse;
import com.alvis.exam.domain.Subject;
import com.alvis.exam.service.SubjectService;
import com.alvis.exam.utility.PageInfoHelper;
import com.alvis.exam.viewmodel.admin.education.SubjectEditRequestVM;
import com.alvis.exam.viewmodel.admin.education.SubjectPageRequestVM;
import com.alvis.exam.viewmodel.admin.education.SubjectResponseVM;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 学科controller
 */
@RestController("AdminEducationController")
@RequestMapping(value = "/api/admin/education")
@AllArgsConstructor
public class EducationController extends BaseApiController {

    private final SubjectService subjectService;


    /**
     * 返回卷题管理中的所有学科的数据 (所有牵扯到的学科)
     *
     * @return
     */
    @RequestMapping(value = "/subject/list", method = RequestMethod.POST)
    public RestResponse<List<Subject>> list() {
        List<Subject> subjects = subjectService.allSubject();
        return RestResponse.ok(subjects);

    }

    /**
     * 返回教育管理中的学科列表的数据
     *
     * @return
     */
    @RequestMapping(value = "/subject/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<SubjectResponseVM>> pageList(@RequestBody SubjectPageRequestVM model) {
        PageInfo<Subject> pageInfo = subjectService.page(model);
        PageInfo<SubjectResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            SubjectResponseVM vm = modelMapper.map(e, SubjectResponseVM.class);
            return vm;
        });
        return RestResponse.ok(page);
    }

    /**
     * 编辑学科，学科存在就修改信息反则添加学科
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/subject/edit", method = RequestMethod.POST)
    public RestResponse<SubjectEditRequestVM> edit(@RequestBody @Valid SubjectEditRequestVM model) {
        Subject subject = modelMapper.map(model, Subject.class);
        if (model.getId() == null) {
            subjectService.insertByFilter(subject);
        } else {
            subjectService.updateByIdFilter(subject);
        }
        return RestResponse.ok(model);
    }


    /**
     * 根据学科ID查询学科返回学科信息   (没找到用在哪里)
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/subject/select/{id}", method = RequestMethod.POST)
    public RestResponse<SubjectEditRequestVM> select(@PathVariable Integer id) {
        Subject subject = subjectService.selectById(id);
        SubjectEditRequestVM vm = modelMapper.map(subject, SubjectEditRequestVM.class);
        return RestResponse.ok(vm);
    }

}
