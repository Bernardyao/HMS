/*
 ============================================================================
 HIS System Demo Data (PostgreSQL 14+)
 ============================================================================
 说明：
 1. 本脚本用于生成测试/演示数据。
 2. 包含完整业务闭环：挂号 -> 看诊(病历) -> 处方 -> 缴费。
 3. 使用 OVERRIDING SYSTEM VALUE 强制写入 ID，确保外键关系准确。
 4. 脚本末尾会自动重置序列，确保后续正常插入不报错。
 ============================================================================
*/

-- ============================================
-- 0. 清理旧数据 (开发环境慎用)
-- ============================================
TRUNCATE TABLE his_charge RESTART IDENTITY CASCADE;
TRUNCATE TABLE his_prescription_detail RESTART IDENTITY CASCADE;
TRUNCATE TABLE his_prescription RESTART IDENTITY CASCADE;
TRUNCATE TABLE his_medical_record RESTART IDENTITY CASCADE;
TRUNCATE TABLE his_registration RESTART IDENTITY CASCADE;
TRUNCATE TABLE his_medicine RESTART IDENTITY CASCADE;
TRUNCATE TABLE his_patient RESTART IDENTITY CASCADE;
TRUNCATE TABLE his_doctor RESTART IDENTITY CASCADE;
TRUNCATE TABLE his_department RESTART IDENTITY CASCADE;

-- ============================================
-- 1. 科室数据 (his_department)
-- ============================================
INSERT INTO his_department (main_id, dept_code, name, parent_id, sort_order, description) 
OVERRIDING SYSTEM VALUE VALUES 
(1, 'DEP001', '内科部', NULL, 1, '全院内科总管'),
(2, 'DEP002', '外科部', NULL, 2, '全院外科总管'),
(3, 'DEP003', '药剂科', NULL, 3, '药品管理与发放'),
(4, 'DEP00101', '呼吸内科', 1, 1, '主治呼吸系统疾病'),
(5, 'DEP00102', '消化内科', 1, 2, '主治消化系统疾病'),
(6, 'DEP00201', '普外科', 2, 1, '普通外科手术');

-- ============================================
-- 2. 医生数据 (his_doctor)
-- ============================================
INSERT INTO his_doctor (main_id, department_main_id, doctor_no, name, gender, title, phone, status) 
OVERRIDING SYSTEM VALUE VALUES 
(1, 4, 'DOC001', '张三丰', 1, '主任医师', '13800138001', 1), -- 呼吸内科
(2, 5, 'DOC002', '灭绝师太', 0, '副主任医师', '13800138002', 1), -- 消化内科
(3, 6, 'DOC003', '华佗', 1, '专家', '13800138003', 1); -- 普外科

-- ============================================
-- 3. 患者数据 (his_patient)
-- ============================================
INSERT INTO his_patient (main_id, patient_no, name, gender, age, birth_date, phone, id_card, address) 
OVERRIDING SYSTEM VALUE VALUES 
(1, 'PAT20231212001', '令狐冲', 1, 28, '1995-10-05', '13900000001', '11010119951005001X', '华山派思过崖'),
(2, 'PAT20231212002', '任盈盈', 0, 26, '1997-03-20', '13900000002', '11010119970320002X', '黑木崖1号');

-- ============================================
-- 4. 药品数据 (his_medicine)
-- ============================================
INSERT INTO his_medicine (main_id, medicine_code, name, generic_name, specification, unit, retail_price, stock_quantity, manufacturer, category) 
OVERRIDING SYSTEM VALUE VALUES 
(1, 'MED001', '阿莫西林胶囊', '阿莫西林', '0.25g*24粒', '盒', 15.50, 1000, '白云山制药', '抗生素'),
(2, 'MED002', '布洛芬缓释胶囊', '布洛芬', '0.3g*20粒', '盒', 22.00, 500, '中美史克', '解热镇痛'),
(3, 'MED003', '感冒灵颗粒', '感冒灵', '10g*9袋', '盒', 12.00, 800, '华润三九', '中成药'),
(4, 'MED004', '999皮炎平', '复方醋酸地塞米松乳膏', '20g', '支', 9.50, 200, '华润三九', '皮肤科用药');

-- ============================================
-- 5. 挂号数据 (his_registration)
-- ============================================
-- 场景：令狐冲 挂了 张三丰(呼吸内科) 的号，已就诊
-- 场景：任盈盈 挂了 灭绝师太(消化内科) 的号，待就诊
INSERT INTO his_registration (main_id, patient_main_id, doctor_main_id, department_main_id, reg_no, visit_date, visit_type, registration_fee, status, queue_no) 
OVERRIDING SYSTEM VALUE VALUES 
(1, 1, 1, 4, 'REG20231212001', CURRENT_DATE, 1, 50.00, 1, 'A001'), -- 已就诊
(2, 2, 2, 5, 'REG20231212002', CURRENT_DATE, 1, 30.00, 0, 'B001'); -- 待就诊

-- ============================================
-- 6. 缴费数据 - 挂号费 (his_charge)
-- ============================================
-- 令狐冲支付挂号费
INSERT INTO his_charge (main_id, patient_main_id, registration_main_id, charge_no, charge_type, total_amount, actual_amount, status, payment_method, charge_time) 
OVERRIDING SYSTEM VALUE VALUES 
(1, 1, 1, 'CHG20231212001', 1, 50.00, 50.00, 1, 3, NOW()); -- 微信支付

-- ============================================
-- 7. 病历数据 (his_medical_record)
-- ============================================
-- 医生张三丰给令狐冲写病历
INSERT INTO his_medical_record (main_id, registration_main_id, patient_main_id, doctor_main_id, record_no, status, chief_complaint, present_illness, diagnosis, doctor_advice) 
OVERRIDING SYSTEM VALUE VALUES 
(1, 1, 1, 1, 'MR20231212001', 1, 
 '咽痛、咳嗽2天，伴发热1天', 
 '患者2天前无明显诱因出现咽痛、咳嗽，咳少量白痰。今晨起发热，体温38.5℃。', 
 '急性上呼吸道感染', 
 '1. 注意休息，多饮水；\n2. 清淡饮食；\n3. 如高热不退请及时就诊。');

-- ============================================
-- 8. 处方数据 (his_prescription)
-- ============================================
-- 医生张三丰给令狐冲开药
INSERT INTO his_prescription (main_id, record_main_id, patient_main_id, doctor_main_id, prescription_no, prescription_type, total_amount, item_count, status, validity_days) 
OVERRIDING SYSTEM VALUE VALUES 
(1, 1, 1, 1, 'PRE20231212001', 1, 53.00, 2, 1, 3); -- 草稿/已开立状态，总额 = 15.5*2 + 22*1 = 53

-- ============================================
-- 9. 处方明细数据 (his_prescription_detail)
-- ============================================
INSERT INTO his_prescription_detail (main_id, prescription_main_id, medicine_main_id, medicine_name, unit_price, quantity, subtotal, specification, frequency, dosage, route, days, instructions) 
OVERRIDING SYSTEM VALUE VALUES 
(1, 1, 1, '阿莫西林胶囊', 15.50, 2, 31.00, '0.25g*24粒', '一日三次', '0.5g', '口服', 3, '饭后服用'),
(2, 1, 2, '布洛芬缓释胶囊', 22.00, 1, 22.00, '0.3g*20粒', '必要时', '1粒', '口服', 1, '疼痛或发热大于38.5℃时服用');

-- ============================================
-- 10. 缴费数据 - 药费 (his_charge)
-- ============================================
-- 令狐冲支付药费
INSERT INTO his_charge (main_id, patient_main_id, registration_main_id, charge_no, charge_type, total_amount, actual_amount, status, payment_method, charge_time, remark) 
OVERRIDING SYSTEM VALUE VALUES 
(2, 1, 1, 'CHG20231212002', 2, 53.00, 53.00, 1, 4, NOW(), '处方号: PRE20231212001'); -- 支付宝支付

-- ============================================
-- 11. 关键步骤：重置序列 (Sequence Reset)
-- ============================================
-- 由于手动指定了ID，必须将 Sequence 重置到当前最大ID之后，否则下次自动插入会报错。
SELECT setval(pg_get_serial_sequence('his_department', 'main_id'), (SELECT MAX(main_id) FROM his_department));
SELECT setval(pg_get_serial_sequence('his_doctor', 'main_id'), (SELECT MAX(main_id) FROM his_doctor));
SELECT setval(pg_get_serial_sequence('his_patient', 'main_id'), (SELECT MAX(main_id) FROM his_patient));
SELECT setval(pg_get_serial_sequence('his_medicine', 'main_id'), (SELECT MAX(main_id) FROM his_medicine));
SELECT setval(pg_get_serial_sequence('his_registration', 'main_id'), (SELECT MAX(main_id) FROM his_registration));
SELECT setval(pg_get_serial_sequence('his_medical_record', 'main_id'), (SELECT MAX(main_id) FROM his_medical_record));
SELECT setval(pg_get_serial_sequence('his_prescription', 'main_id'), (SELECT MAX(main_id) FROM his_prescription));
SELECT setval(pg_get_serial_sequence('his_prescription_detail', 'main_id'), (SELECT MAX(main_id) FROM his_prescription_detail));
SELECT setval(pg_get_serial_sequence('his_charge', 'main_id'), (SELECT MAX(main_id) FROM his_charge));

-- ============================================
-- 脚本结束
-- ============================================