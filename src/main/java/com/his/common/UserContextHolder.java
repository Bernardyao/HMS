package com.his.common;

import com.his.dto.CurrentUser;

/**
 * 用户上下文持有者
 * 使用 ThreadLocal 存储当前登录用户信息，保证线程安全
 */
public class UserContextHolder {
    
    private static final ThreadLocal<CurrentUser> CONTEXT = new ThreadLocal<>();
    
    /**
     * 设置当前用户信息
     *
     * @param user 当前用户
     */
    public static void setCurrentUser(CurrentUser user) {
        CONTEXT.set(user);
    }
    
    /**
     * 获取当前用户信息
     *
     * @return 当前用户，如果未登录则返回 null
     */
    public static CurrentUser getCurrentUser() {
        return CONTEXT.get();
    }
    
    /**
     * 清除当前用户信息
     */
    public static void clear() {
        CONTEXT.remove();
    }
    
    /**
     * 获取当前用户ID
     *
     * @return 用户ID，如果未登录则返回 null
     */
    public static Long getCurrentUserId() {
        CurrentUser user = getCurrentUser();
        return user != null ? user.getId() : null;
    }
    
    /**
     * 获取当前用户所属科室ID
     *
     * @return 科室ID，如果未登录则返回 null
     */
    public static Long getCurrentUserDeptId() {
        CurrentUser user = getCurrentUser();
        return user != null ? user.getDeptId() : null;
    }
    
    /**
     * 判断当前用户是否为管理员
     *
     * @return true-是管理员，false-不是管理员
     */
    public static boolean isCurrentUserAdmin() {
        CurrentUser user = getCurrentUser();
        return user != null && user.isAdmin();
    }
}
