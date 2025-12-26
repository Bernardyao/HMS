package com.his.service.impl;

import com.his.entity.Medicine;
import com.his.repository.MedicineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineServiceImplTest {

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private MedicineServiceImpl medicineService;

    @Test
    void updateStock_increase_success() {
        Long medicineId = 1L;
        Integer quantity = 10; // Increase by 10
        String reason = "Replenishment";

        Medicine medicine = new Medicine();
        medicine.setMainId(medicineId);
        medicine.setStockQuantity(50);
        medicine.setIsDeleted((short) 0);

        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(medicine));
        when(medicineRepository.save(any(Medicine.class))).thenAnswer(inv -> inv.getArgument(0));

        medicineService.updateStock(medicineId, quantity, reason);

        assertThat(medicine.getStockQuantity()).isEqualTo(60);
        verify(medicineRepository).save(medicine);
    }

    @Test
    void updateStock_decrease_success() {
        Long medicineId = 1L;
        Integer quantity = -10; // Decrease by 10
        String reason = "Breakage";

        Medicine medicine = new Medicine();
        medicine.setMainId(medicineId);
        medicine.setStockQuantity(50);
        medicine.setIsDeleted((short) 0);

        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(medicine));
        when(medicineRepository.save(any(Medicine.class))).thenAnswer(inv -> inv.getArgument(0));

        medicineService.updateStock(medicineId, quantity, reason);

        assertThat(medicine.getStockQuantity()).isEqualTo(40);
        verify(medicineRepository).save(medicine);
    }

    @Test
    void updateStock_decrease_insufficient_throwsException() {
        Long medicineId = 1L;
        Integer quantity = -60; // Decrease by 60 (Stock is 50)
        String reason = "Error";

        Medicine medicine = new Medicine();
        medicine.setMainId(medicineId);
        medicine.setStockQuantity(50);
        medicine.setIsDeleted((short) 0);

        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(medicine));

        assertThatThrownBy(() -> medicineService.updateStock(medicineId, quantity, reason))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("库存不足");

        verify(medicineRepository, never()).save(any());
    }

    @Test
    void updateStock_medicineNotFound_throwsException() {
        Long medicineId = 999L;
        
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> medicineService.updateStock(medicineId, 10, "Test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("药品不存在");
    }
}
