package com.his.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 挂号请求 DTO
 */
@Schema(description = "挂号请求数据")
@Data
public class RegistrationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // ============ 患者信息（用于建档） ============

    /**
     * 患者姓名
     */
    @Schema(description = "患者姓名", required = true, example = "李明")
    private String patientName;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号", required = true, example = "110101199001011001")
    private String idCard;

    /**
     * 性别（0=女, 1=男, 2=未知）
     */
    @Schema(description = "性别（0=女, 1=男, 2=未知）", required = true, example = "1")
    private Short gender;

    /**
     * 年龄
     */
    @Schema(description = "年龄", required = true, example = "34")
    private Short age;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话", required = true, example = "13912345678")
    private String phone;

    // ============ 挂号信息 ============

    /**
     * 科室 ID
     */
    @Schema(description = "科室ID", required = true, example = "1")
    private Long deptId;

    /**
     * 医生 ID
     */
    @Schema(description = "医生ID", required = true, example = "1")
    private Long doctorId;

    /**
     * 挂号费
     */
    @Schema(description = "挂号费（单位：元）", required = true, example = "20.00")
    private BigDecimal regFee;
}
