package com.his.enums;

import lombok.Getter;

/**
 * 用户角色枚举
 */
@Getter
public enum UserRole {
    
    /**
     * 管理员 - 可以访问所有科室数据
     */
    ADMIN("ADMIN", "管理员"),
    
    /**
     * 医生 - 只能访问自己所在科室的数据
     */
    DOCTOR("DOCTOR", "医生"),
    
    /**
     * 护士 - 只能访问自己所在科室的数据
     */
    NURSE("NURSE", "护士");
    
    private final String code;
    private final String description;
    
    UserRole(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
