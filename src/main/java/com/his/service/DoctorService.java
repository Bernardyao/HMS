package com.his.service;

import com.his.enums.RegStatusEnum;
import com.his.vo.RegistrationVO;

import java.util.List;

/**
 * 医生工作站服务接口
 */
public interface DoctorService {

    /**
     * 获取今日候诊列表（支持个人/科室混合视图）
     *
     * @param doctorId 医生ID（个人视图时使用）
     * @param deptId 科室ID（科室视图时使用，或用于验证）
     * @param showAllDept 是否显示科室所有患者（true=科室视图，false=个人视图）
     * @return 候诊列表
     */
    List<RegistrationVO> getWaitingList(Long doctorId, Long deptId, boolean showAllDept);

    /**
     * 更新挂号状态（接诊或完成就诊）
     *
     * @param regId 挂号记录ID
     * @param newStatus 新状态
     */
    void updateStatus(Long regId, RegStatusEnum newStatus);
}
