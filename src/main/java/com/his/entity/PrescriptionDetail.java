package com.his.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 处方明细表
 */
@Data
@Entity
@Table(name = "his_prescription_detail")
public class PrescriptionDetail {

    /**
     * 主键ID（自增）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_id")
    private Long mainId;

    /**
     * 处方
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_main_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Prescription prescription;

    /**
     * 药品
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_main_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Medicine medicine;

    /**
     * 药品名称
     */
    @Column(name = "medicine_name", nullable = false, length = 200)
    private String medicineName;

    /**
     * 单价
     */
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 4)
    private BigDecimal unitPrice;

    /**
     * 数量
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /**
     * 小计
     */
    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    /**
     * 软删除标记
     */
    @Column(name = "is_deleted", nullable = false)
    private Short isDeleted = 0;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 规格
     */
    @Column(name = "specification", length = 100)
    private String specification;

    /**
     * 单位
     */
    @Column(name = "unit", length = 20)
    private String unit;

    /**
     * 用药频率
     */
    @Column(name = "frequency", length = 50)
    private String frequency;

    /**
     * 用量
     */
    @Column(name = "dosage", length = 50)
    private String dosage;

    /**
     * 用药途径
     */
    @Column(name = "route", length = 50)
    private String route;

    /**
     * 用药天数
     */
    @Column(name = "days")
    private Integer days;

    /**
     * 用药说明
     */
    @Column(name = "instructions", length = 500)
    private String instructions;

    /**
     * 排序序号
     */
    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    /**
     * 创建人ID
     */
    @Column(name = "created_by")
    private Long createdBy;

    /**
     * 更新人ID
     */
    @Column(name = "updated_by")
    private Long updatedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
