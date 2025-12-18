package com.his.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 医生信息VO
 */
@Data
@Schema(description = "医生信息")
public class DoctorVO {

    @Schema(description = "医生ID")
    private Long id;

    @Schema(description = "医生工号")
    private String code;

    @Schema(description = "医生姓名")
    private String name;

    @Schema(description = "性别（0=女, 1=男, 2=未知）")
    private Short gender;

    @Schema(description = "职称")
    private String title;

    @Schema(description = "专长")
    private String specialty;

    @Schema(description = "状态（0=停用, 1=启用）")
    private Short status;
}
