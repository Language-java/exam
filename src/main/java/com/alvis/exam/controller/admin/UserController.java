package com.alvis.exam.controller.admin;

import com.alvis.exam.base.BaseApiController;
import com.alvis.exam.base.RestResponse;
import com.alvis.exam.domain.other.KeyValue;
import com.alvis.exam.domain.User;
import com.alvis.exam.domain.UserEventLog;
import com.alvis.exam.domain.enums.UserStatusEnum;
import com.alvis.exam.service.AuthenticationService;
import com.alvis.exam.service.UserEventLogService;
import com.alvis.exam.service.UserService;
import com.alvis.exam.utility.DateTimeUtil;
import com.alvis.exam.viewmodel.admin.user.*;
import com.alvis.exam.utility.PageInfoHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * @author alvis 用户管理controller
 */
@RestController("AdminUserController")
@RequestMapping(value = "/api/admin/user")
@AllArgsConstructor
public class UserController extends BaseApiController {

    private final UserService userService;
    private final UserEventLogService userEventLogService;
    private final AuthenticationService authenticationService;


    /**
     * 获取用户列表 (老师和学生管理员一块查出来的)
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<UserResponseVM>> pageList(@RequestBody UserPageRequestVM model) {
        PageInfo<User> pageInfo = userService.userPage(model);
        PageInfo<UserResponseVM> page = PageInfoHelper.copyMap(pageInfo, d -> UserResponseVM.from(d));
        return RestResponse.ok(page);
    }


    /**
     * 返回用户日志
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/event/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<UserEventLogVM>> eventPageList(@RequestBody UserEventPageRequestVM model) {  //对请求的参数做的封装
        PageInfo<UserEventLog> pageInfo = userEventLogService.page(model);  //这两个封装的参数都一样
        PageInfo<UserEventLogVM> page = PageInfoHelper.copyMap(pageInfo, d -> {
            UserEventLogVM vm = modelMapper.map(d, UserEventLogVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(d.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }


    /**
     * 根据用户ID返回用户信息  (编辑中查询)
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> select(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        UserResponseVM userVm = UserResponseVM.from(user);
        return RestResponse.ok(userVm);
    }

    /**
     * (好像没有用到)
     *
     * @return
     */
    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> current() {
        User user = getCurrentUser();
        UserResponseVM userVm = UserResponseVM.from(user);
        return RestResponse.ok(userVm);
    }


    /**
     * 编辑用户 存在就修改 不存在就保存
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<User> edit(@RequestBody @Valid UserCreateVM model) {
        if (model.getId() == null) {  //create
            User existUser = userService.getUserByUserName(model.getUserName());
            if (null != existUser) {
                return new RestResponse<>(2, "用户已存在");
            }

            if (StringUtils.isBlank(model.getPassword())) {
                return new RestResponse<>(3, "密码不能为空");
            }
        }
        if (StringUtils.isBlank(model.getBirthDay())) {
            model.setBirthDay(null);
        }
        User user = modelMapper.map(model, User.class); //表达式定义源(User.class)到目标(model)属性和值的映射
        if (model.getId() == null) {
            String encodePwd = authenticationService.pwdEncode(model.getPassword());
            user.setPassword(encodePwd);
            user.setUserUuid(UUID.randomUUID().toString());
            user.setCreateTime(new Date());
            user.setLastActiveTime(new Date());
            user.setDeleted(false);
            userService.insertByFilter(user);
        } else {
            if (!StringUtils.isBlank(model.getPassword())) {
                String encodePwd = authenticationService.pwdEncode(model.getPassword());
                user.setPassword(encodePwd);
            }
            user.setModifyTime(new Date());
            userService.updateByIdFilter(user);
        }
        return RestResponse.ok(user);
    }


    /**
     * 修改用户信息
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse update(@RequestBody @Valid UserUpdateVM model) {
        User user = userService.selectById(getCurrentUser().getId());
        modelMapper.map(model, user);
        user.setModifyTime(new Date());
        userService.updateByIdFilter(user);
        return RestResponse.ok();
    }


    /**
     * 修改用户是否启用（Status）状态
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/changeStatus/{id}", method = RequestMethod.POST)
    public RestResponse<Integer> changeStatus(@PathVariable Integer id) {
        User user = userService.getUserById(id);  //查
        UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(user.getStatus()); //使用枚举获取它的状态标识
        //查看是否启用,如果不是则开启,否则关闭
        Integer newStatus = userStatusEnum == UserStatusEnum.Enable ? UserStatusEnum.Disable.getCode() : UserStatusEnum.Enable.getCode();
        user.setStatus(newStatus);  //设置新的状态
        user.setModifyTime(new Date());
        userService.updateByIdFilter(user);  //去数据库修改它的状态
        return RestResponse.ok(newStatus); //响应回去
    }


    /**
     * 根据用户ID更改用户delete字段进行逻辑删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        user.setDeleted(true);
        userService.updateByIdFilter(user);
        return RestResponse.ok();
    }


    /**
     * 根据用户名模糊查询    查询接收人(消息发送)
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "/selectByUserName", method = RequestMethod.POST)
    public RestResponse<List<KeyValue>> selectByUserName(@RequestBody String userName) {
        List<KeyValue> keyValues = userService.selectByUserName(userName);
        return RestResponse.ok(keyValues);
    }

}
