/*
 ============================================================================
 Update: Cashier/Charging Module
 Date: 2025-12-27
 ============================================================================
*/

-- 1. Create his_charge_detail table
CREATE TABLE IF NOT EXISTS his_charge_detail (
    main_id             BIGINT          GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    charge_main_id      BIGINT          NOT NULL,
    item_type           VARCHAR(20)     NOT NULL, -- REGISTRATION/PRESCRIPTION
    item_id             BIGINT          NOT NULL,
    item_name           VARCHAR(100)    NOT NULL,
    item_amount         DECIMAL(10, 2)  NOT NULL,
    created_at          TIMESTAMP       DEFAULT now(),
    
    CONSTRAINT fk_charge_detail_charge FOREIGN KEY (charge_main_id) REFERENCES his_charge(main_id)
);

COMMENT ON TABLE his_charge_detail IS '收费明细表';
COMMENT ON COLUMN his_charge_detail.main_id IS '主键ID（自增）';
COMMENT ON COLUMN his_charge_detail.charge_main_id IS '收费主表ID';
COMMENT ON COLUMN his_charge_detail.item_type IS '项目类型（REGISTRATION=挂号费, PRESCRIPTION=处方药费）';
COMMENT ON COLUMN his_charge_detail.item_id IS '项目关联ID';
COMMENT ON COLUMN his_charge_detail.item_name IS '项目名称';
COMMENT ON COLUMN his_charge_detail.item_amount IS '项目金额';
COMMENT ON COLUMN his_charge_detail.created_at IS '创建时间';

-- 2. Update his_prescription status comment
COMMENT ON COLUMN his_prescription.status IS '状态（0=草稿, 1=已开方, 2=已审核, 3=已发药, 4=已退费, 5=已缴费）';

-- 3. Add index for his_charge_detail
CREATE INDEX IF NOT EXISTS idx_his_charge_detail_charge_main_id ON his_charge_detail(charge_main_id);
