package com.alvis.exam.viewmodel.admin.user;

import com.alvis.exam.domain.User;
import com.alvis.exam.utility.DateTimeUtil;
import com.alvis.exam.viewmodel.BaseVM;
import lombok.Data;

/**
 * @author alvis
 */

@Data
public class UserResponseVM extends BaseVM {

    private Integer id;

    private String userUuid;    //用户的唯一名称id(uuid自动生成)

    private String userName;   //用户名

    private String realName;   //真实名

    private Integer age;    //年龄

    private Integer role;   //角色

    private Integer sex;   //性别

    private String birthDay;  //生日

    private String phone;  //手机号

    private String lastActiveTime;  //最后活跃时间

    private String createTime;   //创建时间

    private String modifyTime;    //修改时间

    private Integer status;  //状态

    private Integer userLevel;  //使用者级别?


    public static UserResponseVM from(User user) {
        UserResponseVM vm = modelMapper.map(user, UserResponseVM.class);
        vm.setBirthDay(DateTimeUtil.dateFormat(user.getBirthDay()));
        vm.setLastActiveTime(DateTimeUtil.dateFormat(user.getLastActiveTime()));
        vm.setCreateTime(DateTimeUtil.dateFormat(user.getCreateTime()));
        vm.setModifyTime(DateTimeUtil.dateFormat(user.getModifyTime()));
        return vm;
    }
}
