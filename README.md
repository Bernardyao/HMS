# 🏥 Hospital Information System (HIS) - 医院信息管理系统

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-6DB33F?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-4169E1?style=flat-square&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat-square)](LICENSE)

## 📖 项目简介 (Introduction)

**HIS (Hospital Information System)** 是一套企业级标准的现代化医疗信息管理后端服务。该系统基于 **Spring Boot 3** 构建，提供完整的 RESTful API，旨在数字化并优化复杂的医院业务流程——从患者挂号、门诊就诊、电子病历书写，到处方开具、收费结算及药房发药，实现了医疗服务的全流程闭环管理。

本项目严格遵循 **基于角色的访问控制 (RBAC)** 模型，为医生、护士、药师、收费员和系统管理员提供了安全、独立的API接口服务。

> **注意：** 本项目为HIS系统的后端API服务，采用前后端分离架构。前端客户端可独立开发（如React/Vue等），通过本项目的RESTful API进行数据交互。

---

## 🏗️ 系统架构 (System Architecture)

系统采用前后端分离架构：
- **后端服务**：基于 Spring Boot 3，提供 RESTful API
- **安全认证**：基于 JWT 的无状态认证机制
- **数据存储**：PostgreSQL 关系型数据库
- **前端客户端**：独立开发（可使用 React/Vue 等框架），通过 API 与后端交互

---

## ✨ 核心功能 (Key Features)

### 🔐 安全与权限 (Security & RBAC)
*   **精细化权限控制**：支持 Admin (管理员), Doctor (医生), Nurse (护士), Cashier (收费员), Pharmacist (药师) 五种角色，实现严格的接口级与视图级权限隔离。
*   **安全认证**：基于 **Spring Security 6** 和 **JWT** 实现无状态认证机制。

### 🏥 临床与运营模块

#### 1. 门诊挂号 (护士工作站)
*   **患者建档**：快速建立患者档案。
*   **智能分诊**：支持初诊、复诊及急诊挂号流程。
*   **级联选择**：挂号时支持科室与医生的动态级联选择。

#### 2. 医生工作站 (Doctor Workstation) 👨‍⚕️
*   **混合队列视图**：支持切换 **个人候诊队列** (指定给该医生的患者) 和 **科室候诊队列** (科室公共池)。
*   **电子病历 (EMR)**：标准化的电子病历书写与管理。
*   **智能处方**：开具处方时实时校验药品库存，防止超售。

#### 3. 药师工作站 (Pharmacist Workstation) 💊
*   **处方审核**：药师对医生开具的处方进行专业审核，确保用药安全。
*   **药品发药**：严格的业务逻辑控制，仅当处方完成缴费后方可发药，自动扣减库存。
*   **退药处理**：支持患者退药申请，自动恢复药品库存。
*   **库存管理**：实时库存查询、低库存预警（库存 < 100）、手动库存调整。
*   **工作统计**：药师每日发药统计（处方数、金额、药品数）。

#### 4. 收费管理 (Financials) 💰
*   **交易处理**：收费员专属界面，处理处方缴费与退费业务，生成交易记录。

---

## 🛠️ 技术栈 (Tech Stack)

| 组件 | 技术 | 说明 |
| :--- | :--- | :--- |
| **开发语言** | Java 17+ | 使用 Record, Sealed Classes 等现代 Java 特性 |
| **核心框架** | Spring Boot 3.2.1 | 应用程序核心框架 |
| **安全框架** | Spring Security 6 | 认证与授权 (RBAC) + JWT |
| **持久层** | Spring Data JPA | 数据库抽象与 Repository 模式 |
| **数据库** | PostgreSQL 14+ | 关系型数据库存储 |
| **API 文档** | Knife4j (Swagger 3) | 接口文档与在线调试 |
| **构建工具** | Gradle 8.2 | 依赖管理与构建 |
| **工具库** | Lombok, Jakarta Bean Validation | 简化开发 |

更多技术栈详情请参阅 [conductor/tech-stack.md](conductor/tech-stack.md)

---

## 🚀 快速开始 (Getting Started)

按照以下步骤在本地搭建开发环境。

### 前置要求
*   **JDK 17** 或更高版本
*   **PostgreSQL 14+**

### 1. 数据库初始化
创建一个名为 `his_project` 的 PostgreSQL 数据库。
系统配置了 `ddl-auto: update`，首次运行时会自动创建表结构。若需导入初始数据（如测试用户、基础药品库），请执行：

```bash
# 请确保处于项目根目录
psql -U postgres -d his_project -f sql/test_data_sysuser.sql
psql -U postgres -d his_project -f sql/his_design_bigserial.sql
```

### 2. 启动服务
进入项目根目录并运行：

```bash
# 运行测试确保环境正常
./gradlew test

# 启动 Spring Boot 应用
./gradlew bootRun
```

服务默认运行在 `8080` 端口。

---

## ⚙️ 关键配置 (Configuration)

关键配置项位于 `src/main/resources/application.yml` 或各环境配置文件中。

| 属性配置 | 说明 | 默认值 |
| :--- | :--- | :--- |
| `spring.datasource.url` | 数据库连接 URL | `jdbc:postgresql://...` |
| `jwt.secret` | Token 签名密钥 | *(见 application-common.yml)* |
| `jwt.expiration` | Token 有效期 | `86400` (24 小时) |

---

## 📚 API 文档 (API Documentation)

### API 路径架构

本项目采用模块化的 API 架构，按工作站和功能划分路径：

| API 路径前缀 | 说明 | 使用角色 |
|-------------|------|---------|
| `/api/auth` | 认证接口 | 所有用户 |
| `/api/common` | 公共接口 | 所有认证用户 |
| `/api/doctor` | 医生工作站 | DOCTOR, ADMIN |
| `/api/nurse` | 护士工作站 | NURSE, ADMIN |
| `/api/pharmacist` | 药师工作站 | PHARMACIST, ADMIN |

### 药品相关接口说明

| 功能 | 正确路径 | 说明 |
|------|---------|------|
| 药品搜索/详情 | `GET /api/common/medicines/*` | 所有认证用户可用 |
| 药师库存管理 | `GET /api/pharmacist/medicines/inventory` | 药师专用 |
| 处方发药/退药 | `POST /api/pharmacist/prescriptions/{id}/dispense` | 药师专用 |
| 待发药列表 | `GET /api/pharmacist/prescriptions/pending` | 药师专用 |

⚠️ **注意**: 旧的 `/api/medicine/*` 路径已被移除，请使用上述正确的 API 路径。

### 在线接口文档

后端服务启动后，可访问 Knife4j 在线接口文档进行调试：

**访问地址：** `http://localhost:8080/doc.html`

> 💡 **提示：** 您可以使用 `admin` 账号登录获取 Token，然后在文档页面的 "Authorize" 功能中设置 Token，以便调试受保护的接口。

---

## 📚 项目文档 (Documentation)

### 核心文档
- [项目企划书](./docs/project-proposal.md) - 项目概述与目标
- [技术架构文档](./docs/技术架构文档.md) - 系统架构设计
- [开发路线图](./docs/开发路线图.md) - 开发进度与规划

### 工作站文档
- [医生工作站开发文档](./docs/医生工作站开发文档.md) - 医生工作站功能详解
- [药师工作站开发文档](./docs/药师工作站开发文档.md) - 药师工作站功能详解

### 指南文档
- [认证与医生上下文指南](./docs/认证与医生上下文使用指南.md) - 认证机制说明
- [Ubuntu部署指南](./docs/DEPLOY_UBUNTU.md) - 服务器部署指南

---

## 🤝 贡献指南 (Contributing)

1.  Fork 本仓库。
2.  创建您的特性分支 (`git checkout -b feature/AmazingFeature`)。
3.  提交您的更改 (`git commit -m 'Add some AmazingFeature'`)。
4.  推送到分支 (`git push origin feature/AmazingFeature`)。
5.  提交 Pull Request。

---
© 2025 HIS Development Team. All rights reserved.
