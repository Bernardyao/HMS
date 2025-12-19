package com.his.controller;

import com.his.common.Result;
import com.his.enums.RegStatusEnum;
import com.his.service.DoctorService;
import com.his.vo.RegistrationVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 医生工作站控制器
 */
@Tag(name = "医生工作站", description = "医生工作站相关接口，包括候诊列表查询、接诊、完成就诊等操作")
@Slf4j
@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    /**
     * 查询今日候诊列表
     *
     * @param deptId 科室ID
     * @return 候诊列表
     */
    @Operation(summary = "查询今日候诊列表", description = "查询指定科室今日待就诊的患者列表，按排队号升序排列")
    @GetMapping("/waiting-list")
    public Result<List<RegistrationVO>> getWaitingList(
            @Parameter(description = "科室ID", required = true, example = "1")
            @RequestParam Long deptId) {
        try {
            // 防御性编程: 参数验证（Service层会做更详细的验证）
            if (deptId == null) {
                log.warn("查询候诊列表失败: 科室ID为空");
                return Result.badRequest("科室ID不能为空");
            }
            
            log.info("查询候诊列表请求,科室ID: {}", deptId);
            List<RegistrationVO> waitingList = doctorService.getWaitingList(deptId);
            
            // 返回带有业务说明的响应
            if (waitingList.isEmpty()) {
                log.info("科室ID: {} 今日暂无候诊患者", deptId);
                return Result.success("今日暂无候诊患者", waitingList);
            }
            
            log.info("科室ID: {} 查询到 {} 位候诊患者", deptId, waitingList.size());
            return Result.success(String.format("查询成功，共%d位候诊患者", waitingList.size()), waitingList);
        } catch (IllegalArgumentException e) {
            log.warn("查询候诊列表参数错误: {}", e.getMessage());
            return Result.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("查询候诊列表系统异常", e);
            return Result.error("系统异常，请联系管理员: " + e.getMessage());
        }
    }

    /**
     * 更新挂号状态（接诊或完成就诊）
     *
     * @param id 挂号记录ID
     * @param status 新状态码（1=已就诊/完成就诊）
     * @return 操作结果
     */
    @Operation(summary = "更新就诊状态", description = "医生接诊或完成就诊，将挂号状态从待就诊更新为已就诊")
    @PutMapping("/registrations/{id}/status")
    public Result<Void> updateStatus(
            @Parameter(description = "挂号记录ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "新状态码（1=已就诊）", required = true, example = "1")
            @RequestParam Short status) {
        try {
            // 防御性编程: 参数验证
            if (id == null) {
                log.warn("更新就诊状态失败: 挂号ID为空");
                return Result.badRequest("挂号ID不能为空");
            }
            
            if (status == null) {
                log.warn("更新就诊状态失败: 状态码为空，挂号ID: {}", id);
                return Result.badRequest("状态码不能为空");
            }
            
            log.info("更新就诊状态请求，挂号ID: {}, 状态码: {}", id, status);
            
            // 将状态码转换为枚举（会验证状态码有效性）
            RegStatusEnum newStatus;
            try {
                newStatus = RegStatusEnum.fromCode(status);
            } catch (IllegalArgumentException e) {
                log.warn("更新就诊状态失败: 无效的状态码 - {}", status);
                return Result.badRequest("无效的状态码: " + status + "，有效值: 0=待就诊, 1=已就诊, 2=已取消, 3=已退号");
            }
            
            // 调用服务层更新状态
            doctorService.updateStatus(id, newStatus);
            
            return Result.success(String.format("状态更新成功: %s", newStatus.getDescription()), null);
        } catch (IllegalArgumentException e) {
            log.warn("状态更新参数错误: {}", e.getMessage());
            return Result.badRequest(e.getMessage());
        } catch (IllegalStateException e) {
            log.warn("状态更新业务错误: {}", e.getMessage());
            return Result.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("状态更新系统异常", e);
            return Result.error("系统异常，请联系管理员: " + e.getMessage());
        }
    }
}
