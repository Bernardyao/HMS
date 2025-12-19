package com.his.service;

import com.his.enums.RegStatusEnum;
import com.his.vo.RegistrationVO;

import java.util.List;

/**
 * 医生工作站服务接口
 */
public interface DoctorService {

    /**
     * 获取今日候诊列表
     *
     * @param deptId 科室ID
     * @return 候诊列表
     */
    List<RegistrationVO> getWaitingList(Long deptId);

    /**
     * 更新挂号状态（接诊或完成就诊）
     *
     * @param regId 挂号记录ID
     * @param newStatus 新状态
     */
    void updateStatus(Long regId, RegStatusEnum newStatus);
}
