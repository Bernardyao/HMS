package com.his.repository;

import com.his.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 挂号记录 Repository
 */
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long>, JpaSpecificationExecutor<Registration> {

    /**
     * 根据挂号流水号查询（未删除）
     */
    Optional<Registration> findByRegNoAndIsDeleted(String regNo, Short isDeleted);

    /**
     * 根据患者ID查询挂号记录
     */
    List<Registration> findByPatient_MainIdAndIsDeleted(Long patientId, Short isDeleted);

    /**
     * 根据医生ID查询挂号记录
     */
    List<Registration> findByDoctor_MainIdAndIsDeleted(Long doctorId, Short isDeleted);

    /**
     * 根据科室ID查询挂号记录
     */
    List<Registration> findByDepartment_MainIdAndIsDeleted(Long departmentId, Short isDeleted);

    /**
     * 根据就诊日期查询
     */
    List<Registration> findByVisitDateAndIsDeleted(LocalDate visitDate, Short isDeleted);

    /**
     * 根据状态查询
     */
    List<Registration> findByStatusAndIsDeleted(Short status, Short isDeleted);

    /**
     * 检查挂号流水号是否存在
     */
    boolean existsByRegNoAndIsDeleted(String regNo, Short isDeleted);

    /**
     * 查询指定日期、科室的挂号列表
     */
    @Query("SELECT r FROM Registration r WHERE r.visitDate = :date AND r.department.mainId = :deptId AND r.isDeleted = 0 ORDER BY r.createdAt")
    List<Registration> findByDateAndDepartment(@Param("date") LocalDate date, @Param("deptId") Long deptId);

    /**
     * 查询指定日期、医生的挂号列表
     */
    @Query("SELECT r FROM Registration r WHERE r.visitDate = :date AND r.doctor.mainId = :doctorId AND r.isDeleted = 0 ORDER BY r.createdAt")
    List<Registration> findByDateAndDoctor(@Param("date") LocalDate date, @Param("doctorId") Long doctorId);

    /**
     * 查询患者待就诊的挂号记录
     */
    @Query("SELECT r FROM Registration r WHERE r.patient.mainId = :patientId AND r.status = 0 AND r.isDeleted = 0")
    List<Registration> findPendingByPatientId(@Param("patientId") Long patientId);

    /**
     * 统计指定日期、科室的挂号数量
     */
    @Query("SELECT COUNT(r) FROM Registration r WHERE r.visitDate = :date AND r.department.mainId = :deptId AND r.isDeleted = 0")
    long countByDateAndDepartment(@Param("date") LocalDate date, @Param("deptId") Long deptId);

    /**
     * 查询日期范围内的挂号记录
     */
    @Query("SELECT r FROM Registration r WHERE r.visitDate BETWEEN :startDate AND :endDate AND r.isDeleted = 0 ORDER BY r.visitDate, r.createdAt")
    List<Registration> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
