package com.his.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 科室信息VO
 */
@Data
@Schema(description = "科室信息")
public class DepartmentVO {

    @Schema(description = "科室ID")
    private Long id;

    @Schema(description = "科室代码")
    private String code;

    @Schema(description = "科室名称")
    private String name;

    @Schema(description = "排序序号")
    private Integer sortOrder;

    @Schema(description = "科室描述")
    private String description;
}
