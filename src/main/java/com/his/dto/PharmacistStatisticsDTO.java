package com.his.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 药师工作统计 DTO
 */
@Data
@Schema(description = "药师今日工作统计信息")
public class PharmacistStatisticsDTO {
    /**
     * 今日发药单数
     */
    @Schema(description = "今日发药单数", example = "10")
    private Long dispensedCount;

    /**
     * 今日发药总金额
     */
    @Schema(description = "今日发药总金额", example = "1234.50")
    private BigDecimal totalAmount;

    /**
     * 今日发药药品总数
     */
    @Schema(description = "今日发药药品总数", example = "25")
    private Long totalItems;

    public PharmacistStatisticsDTO() {
        this.dispensedCount = 0L;
        this.totalAmount = BigDecimal.ZERO;
        this.totalItems = 0L;
    }
    
    public PharmacistStatisticsDTO(Long dispensedCount, BigDecimal totalAmount, Long totalItems) {
        this.dispensedCount = dispensedCount != null ? dispensedCount : 0L;
        this.totalAmount = totalAmount != null ? totalAmount : BigDecimal.ZERO;
        this.totalItems = totalItems != null ? totalItems : 0L;
    }
}
