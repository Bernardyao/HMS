package com.his.service.impl;

import com.his.entity.Medicine;
import com.his.entity.Prescription;
import com.his.entity.PrescriptionDetail;
import com.his.repository.MedicineRepository;
import com.his.repository.PrescriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrescriptionServiceImplTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private PrescriptionServiceImpl prescriptionService;

    @Test
    void getPendingDispenseList_returnsOnlyApprovedPrescriptions() {
        Prescription p1 = new Prescription();
        p1.setStatus((short) 2); // Approved
        
        Prescription p2 = new Prescription();
        p2.setStatus((short) 2); // Approved

        when(prescriptionRepository.findByStatusAndIsDeleted((short) 2, (short) 0))
                .thenReturn(Arrays.asList(p1, p2));

        List<Prescription> result = prescriptionService.getPendingDispenseList();

        assertThat(result).hasSize(2);
        verify(prescriptionRepository).findByStatusAndIsDeleted((short) 2, (short) 0);
    }

    @Test
    void dispense_whenStatusApproved_updatesStatusAndReducesStock() {
        Long prescriptionId = 1L;
        Long pharmacistId = 100L;

        Prescription prescription = new Prescription();
        prescription.setMainId(prescriptionId);
        prescription.setStatus((short) 2); // Approved

        Medicine medicine = new Medicine();
        medicine.setMainId(10L);
        medicine.setStockQuantity(100);

        PrescriptionDetail detail = new PrescriptionDetail();
        detail.setMedicine(medicine);
        detail.setQuantity(5);
        
        prescription.setDetails(Collections.singletonList(detail));

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.of(prescription));
        when(medicineRepository.findById(10L)).thenReturn(Optional.of(medicine));
        when(prescriptionRepository.save(any(Prescription.class))).thenAnswer(inv -> inv.getArgument(0));

        prescriptionService.dispense(prescriptionId, pharmacistId);

        assertThat(prescription.getStatus()).isEqualTo((short) 3); // Dispensed
        assertThat(prescription.getDispenseBy()).isEqualTo(pharmacistId);
        assertThat(prescription.getDispenseTime()).isNotNull();
        assertThat(medicine.getStockQuantity()).isEqualTo(95);

        verify(medicineRepository).save(medicine);
        verify(prescriptionRepository).save(prescription);
    }

    @Test
    void dispense_whenInsufficientStock_throwsException() {
        Long prescriptionId = 1L;
        Long pharmacistId = 100L;

        Prescription prescription = new Prescription();
        prescription.setMainId(prescriptionId);
        prescription.setStatus((short) 2);

        Medicine medicine = new Medicine();
        medicine.setMainId(10L);
        medicine.setStockQuantity(3); // Less than 5

        PrescriptionDetail detail = new PrescriptionDetail();
        detail.setMedicine(medicine);
        detail.setQuantity(5);
        
        prescription.setDetails(Collections.singletonList(detail));

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.of(prescription));
        when(medicineRepository.findById(10L)).thenReturn(Optional.of(medicine));

        assertThatThrownBy(() -> prescriptionService.dispense(prescriptionId, pharmacistId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("库存不足");

        verify(medicineRepository, never()).save(any());
        verify(prescriptionRepository, never()).save(any());
    }

    @Test
    void dispense_whenNotApproved_throwsException() {
        Long prescriptionId = 1L;
        Long pharmacistId = 100L;

        Prescription prescription = new Prescription();
        prescription.setMainId(prescriptionId);
        prescription.setStatus((short) 1); // Not yet approved

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.of(prescription));

        assertThatThrownBy(() -> prescriptionService.dispense(prescriptionId, pharmacistId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("审核");
    }
}
