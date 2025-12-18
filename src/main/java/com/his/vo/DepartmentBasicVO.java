package com.his.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 科室基础信息 VO（用于前端挂号界面下拉选择）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "科室基础信息")
public class DepartmentBasicVO {

    @Schema(description = "科室ID")
    private Long id;

    @Schema(description = "科室代码")
    private String code;

    @Schema(description = "科室名称")
    private String name;

    @Schema(description = "上级科室ID")
    private Long parentId;

    @Schema(description = "上级科室名称")
    private String parentName;
}
