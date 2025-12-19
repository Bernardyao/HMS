package com.his.dto;

import com.his.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 当前登录用户信息 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUser {
    
    /**
     * 用户ID（医生ID）
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 所属科室ID
     */
    private Long deptId;
    
    /**
     * 所属科室名称
     */
    private String deptName;
    
    /**
     * 用户角色
     */
    private UserRole role;
    
    /**
     * 判断当前用户是否为管理员
     */
    public boolean isAdmin() {
        return UserRole.ADMIN.equals(role);
    }
}
