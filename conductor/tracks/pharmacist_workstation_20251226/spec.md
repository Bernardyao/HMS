# Track Specification: Pharmacist Workstation

## 1. Goal
Complete the backend implementation for the Pharmacist Workstation, enabling pharmacists to manage medicine dispensing, handle returns, and monitor inventory effectively. This ensures the loop from prescription to medication delivery is closed and stock levels are accurately maintained.

## 2. User Stories
*   **As a Pharmacist**, I want to see a list of prescriptions waiting to be dispensed, so I can prepare and hand out medications efficiently.
*   **As a Pharmacist**, I want the system to automatically deduct stock when I dispense medications, so that inventory levels are always accurate.
*   **As a Pharmacist**, I want to be able to process medicine returns and have the stock automatically restored, ensuring consistency between physical and system inventory.
*   **As a Pharmacist**, I want to manually adjust stock levels (e.g., for replenishment or breakage) and view today's dispensing statistics.

## 3. Functional Requirements
*   **Pending Dispense List:** Filter prescriptions that are paid and ready for pickup.
*   **Dispensing Workflow:** 
    *   Verify prescription details.
    *   Confirm dispensing, which triggers status change and stock reduction.
*   **Medicine Return:** Handle returns for dispensed medications, including reasoning and stock restoration.
*   **Inventory Management:**
    *   View current stock levels.
    *   Manual stock adjustment (inbound/outbound).
    *   Low stock alerts (threshold-based).
*   **Statistics:** Summarize daily workload (number of prescriptions, total items).

## 4. Technical Constraints
*   **Backend:** Logic resides in `PrescriptionService` and `MedicineService`.
*   **Concurrency:** Use database transactions and appropriate locking (e.g., optimistic locking on stock) to prevent race conditions during inventory updates.
*   **Security:** Ensure only users with `PHARMACIST` or `ADMIN` roles can access these APIs.

## 5. Success Metrics
*   **Accuracy:** 100% accuracy in stock deduction and restoration.
*   **Efficiency:** Pharmacists can process dispensing in a single action.
*   **Reliability:** Transactions roll back correctly if any part of the dispensing/return process fails.