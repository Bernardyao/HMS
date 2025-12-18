package com.his.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 医生基础信息 VO（用于前端挂号界面下拉选择）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "医生基础信息")
public class DoctorBasicVO {

    @Schema(description = "医生ID")
    private Long id;

    @Schema(description = "医生工号")
    private String doctorNo;

    @Schema(description = "医生姓名")
    private String name;

    @Schema(description = "性别（0=女, 1=男, 2=未知）")
    private Short gender;

    @Schema(description = "性别文本")
    private String genderText;

    @Schema(description = "职称")
    private String title;

    @Schema(description = "专长")
    private String specialty;

    @Schema(description = "状态（0=停用, 1=启用）")
    private Short status;

    @Schema(description = "状态文本")
    private String statusText;

    @Schema(description = "所属科室ID")
    private Long departmentId;

    @Schema(description = "所属科室名称")
    private String departmentName;

    @Schema(description = "挂号费（元）", example = "50.00")
    private BigDecimal registrationFee;
}
