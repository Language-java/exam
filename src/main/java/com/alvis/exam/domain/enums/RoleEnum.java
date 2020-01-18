package com.alvis.exam.domain.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 用户的枚举封装
 */
public enum RoleEnum {


    STUDENT(1, "STUDENT"),   //学生
    TEACHER(2, "TEACHER"),   //老师
    ADMIN(3, "ADMIN");        //后台管理

    int code;   //代表1，2，3
    String name;

    RoleEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final Map<Integer, RoleEnum> keyMap = new HashMap<>();

    static {
        for (RoleEnum item : RoleEnum.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    public static RoleEnum fromCode(Integer code) {
        return keyMap.get(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleName() {
        return "ROLE_" + name;
    }

}
