package com.his.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 安全启动检查器 - 防止生产环境使用不安全的配置
 *
 * <p>在应用启动时检查环境配置，确保生产环境不会意外使用开发模式配置。
 */
@Slf4j
@Component
public class SecurityStartupChecker implements ApplicationRunner {

    private final Environment environment;

    @Value("${app.security.check-production:true}")
    private boolean enableProductionCheck;

    public SecurityStartupChecker(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!enableProductionCheck) {
            log.info("生产环境安全检查已禁用");
            return;
        }

        String[] activeProfiles = environment.getActiveProfiles();
        String currentProfile = activeProfiles.length > 0 ? activeProfiles[0] : "default";

        // 检查是否在 dev 环境运行
        boolean isDevProfile = "dev".equals(currentProfile);

        // 检查是否在生产环境迹象（通过环境变量或系统属性）
        boolean isProductionEnvironment = detectProductionEnvironment();

        if (isDevProfile && isProductionEnvironment) {
            String errorMsg = """
                    ===============================================
                    🔴 严重安全警告！
                    ===============================================
                    检测到生产环境但使用了 dev profile！

                    当前配置: dev (开发模式)
                    环境特征: 生产环境

                    这会导致：
                    1. 接口可能暴露未授权访问
                    2. 安全策略过于宽松
                    3. 不符合生产安全要求

                    请立即修改 application.yml 中的 spring.profiles.active 为:
                    - test (测试环境) 或
                    - prod (生产环境)

                    应用即将停止运行...
                    ===============================================
                    """;

            log.error(errorMsg);
            throw new IllegalStateException(
                "生产环境不能使用 dev profile！请修改配置文件使用正确的环境配置。"
            );
        }

        if (isDevProfile) {
            log.warn("""
                ===============================================
                ⚠️  开发模式安全提醒
                ===============================================
                当前运行在: dev (开发模式)

                请注意：
                1. 此模式仅用于本地开发
                2. 不要将 dev 模式部署到生产环境
                3. 登录和 Swagger 接口无需认证
                4. 业务接口需要认证和角色验证

                如果需要测试完整的认证流程，请使用 test profile
                ===============================================
                """);
        } else {
            log.info("安全启动检查通过 - 当前环境: {}", currentProfile);
        }
    }

    /**
     * 检测是否在生产环境中运行
     *
     * @return 如果检测到生产环境特征返回 true
     */
    private boolean detectProductionEnvironment() {
        // 检查环境变量
        String env = System.getenv("SPRING_PROFILES_ACTIVE");
        if ("prod".equals(env) || "production".equals(env)) {
            return true;
        }

        // 检查系统属性
        String sysProp = System.getProperty("spring.profiles.active");
        if ("prod".equals(sysProp) || "production".equals(sysProp)) {
            return true;
        }

        // 检查常见的生产环境特征
        // 如果在生产数据库网络段
        String dbUrl = environment.getProperty("spring.datasource.url");
        if (dbUrl != null && (dbUrl.contains("prod-db") || dbUrl.contains("production"))) {
            return true;
        }

        return false;
    }
}
