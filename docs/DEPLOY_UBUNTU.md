# HIS Backend - Ubuntu 部署指南

本指南将指导您如何在 Ubuntu Server (建议 20.04 LTS 或 22.04 LTS) 上部署 HIS (Hospital Information System) 后端服务。

## 1. 环境准备

### 1.1 更新系统
```bash
sudo apt update && sudo apt upgrade -y
```

### 1.2 安装 JDK 17
本项目需要 Java 17 运行环境。

```bash
sudo apt install openjdk-17-jdk -y

# 验证安装
java -version
# 输出应包含: openjdk version "17..."
```

### 1.3 安装 PostgreSQL
本项目使用 PostgreSQL 作为数据库。

```bash
sudo apt install postgresql postgresql-contrib -y

# 启动并设置开机自启
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

## 2. 数据库配置

### 2.1 创建数据库和用户
登录到 Postgres shell 并创建数据库及用户。

```bash
# 切换到 postgres 用户
sudo -i -u postgres

# 进入数据库控制台
psql
```

在 `psql` 控制台中执行以下 SQL 命令：

```sql
-- 创建数据库
CREATE DATABASE his_project;

-- 创建用户 (请将 'strong_password' 替换为您的安全密码)
CREATE USER his_user WITH ENCRYPTED PASSWORD 'strong_password';

-- 授权
GRANT ALL PRIVILEGES ON DATABASE his_project TO his_user;

-- 退出
\q
```

退出 postgres 用户：
```bash
exit
```

### 2.2 导入初始数据
假设您已将项目代码上传至服务器 (例如 `/opt/his-backend`).

```bash
# 导入表结构
sudo -u postgres psql -d his_project -f /opt/his-backend/sql/his_design_bigserial.sql

# 导入测试/初始数据 (可选)
sudo -u postgres psql -d his_project -f /opt/his-backend/sql/test_data_sysuser.sql
```

> **注意**: 如果您的 SQL 脚本中包含 OWNER 设置，可能需要调整权限，或者直接使用超级用户 postgres 导入。

## 3. 构建应用

在项目根目录下执行构建命令。

```bash
cd /opt/his-backend

# 赋予 gradlew 执行权限
chmod +x gradlew

# 编译并打包 (跳过测试以加快速度，生产构建建议运行测试)
./gradlew build -x test
```

构建成功后，生成的 JAR 包位于 `build/libs/` 目录下，通常命名为 `his-application-0.0.1-SNAPSHOT.jar`。

## 4. 运行应用

### 4.1 直接运行 (测试用)
您可以通过命令行直接启动应用来测试配置是否正确。我们需要指定 `prod` 配置文件，并通过环境变量传入数据库连接信息。

```bash
export DB_USERNAME=his_user
export DB_PASSWORD=strong_password

# 假设数据库在本地，覆盖默认的 prod-db-server 主机名
java -jar -Dspring.profiles.active=prod \
  -Dspring.datasource.url=jdbc:postgresql://localhost:5432/his_project \
  build/libs/his-application-0.0.1-SNAPSHOT.jar
```

### 4.2 配置 Systemd 服务 (推荐)
为了让应用在后台运行并在开机时自动启动，建议创建一个 Systemd 服务。

创建服务文件：
```bash
sudo nano /etc/systemd/system/his-backend.service
```

写入以下内容 (请根据实际路径和密码修改)：

```ini
[Unit]
Description=HIS Backend Service
After=syslog.target network.target postgresql.service

[Service]
User=root
# 建议创建一个专门的系统用户运行应用，这里为了演示使用 root
# User=his_app_user

# 环境变量
Environment="DB_USERNAME=his_user"
Environment="DB_PASSWORD=strong_password"

# 启动命令
# 修改 JAR 包路径和数据库 URL
ExecStart=/usr/bin/java -jar -Dspring.profiles.active=prod \
    -Dspring.datasource.url=jdbc:postgresql://localhost:5432/his_project \
    /opt/his-backend/build/libs/his-application-0.0.1-SNAPSHOT.jar

SuccessExitStatus=143
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

保存并退出。

启动服务：
```bash
# 重载配置
sudo systemctl daemon-reload

# 启动服务
sudo systemctl start his-backend

# 设置开机自启
sudo systemctl enable his-backend

# 查看状态
sudo systemctl status his-backend
```

## 5. 常见问题排查

*   **端口冲突**: 默认端口为 8080。如果被占用，可以在启动命令中添加 `-Dserver.port=9090` 修改。
*   **数据库连接失败**: 
    *   检查 PostgreSQL 服务是否运行 (`systemctl status postgresql`).
    *   检查 `pg_hba.conf` 是否允许密码登录 (通常默认允许 md5/scram-sha-256).
    *   确认防火墙设置 (ufw) 允许本机访问 5432 端口 (默认允许 loopback).
*   **日志查看**:
    *   Systemd 日志: `journalctl -u his-backend -f`
    *   应用日志: 默认配置下，应用可能会在运行目录下的 `logs/` 文件夹生成日志文件 (取决于 `application-prod.yml` 中的配置)。

## 6. Nginx 反向代理 (可选)

建议使用 Nginx 作为反向代理服务器，提供 HTTPS 支持和负载均衡。

```bash
sudo apt install nginx -y
```

配置示例 (`/etc/nginx/sites-available/his-backend`):

```nginx
server {
    listen 80;
    server_name your-domain.com;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```
