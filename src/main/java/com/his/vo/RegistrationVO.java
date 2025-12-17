package com.his.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 挂号响应 VO
 */
@Data
public class RegistrationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 挂号记录 ID
     */
    private Long id;

    /**
     * 挂号流水号
     */
    private String regNo;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 患者 ID
     */
    private Long patientId;

    /**
     * 科室 ID
     */
    private Long deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 医生 ID
     */
    private Long doctorId;

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 挂号状态（0=待就诊, 1=已就诊, 2=已取消）
     */
    private Short status;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 就诊日期
     */
    private LocalDate visitDate;

    /**
     * 挂号费
     */
    private BigDecimal registrationFee;

    /**
     * 排队号
     */
    private String queueNo;

    /**
     * 预约时间
     */
    private LocalDateTime appointmentTime;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
