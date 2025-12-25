# Product Guidelines

## 1. Design Philosophy

### 1.1 Core Principle: User-Centric & Accessible
The primary design goal is to minimize cognitive load for hospital staff who often work in high-pressure environments. The interface should prioritize clarity, intuitive navigation, and readability over information density.
*   **Simplicity:** Use plain language where possible, while maintaining necessary medical precision. Avoid cluttered screens.
*   **Forgiveness:** Design workflows that prevent errors (e.g., confirmation dialogs for critical actions) and allow for easy correction of mistakes.
*   **Accessibility:** Adhere to accessibility standards (WCAG) to ensure the system is usable by all staff members, regardless of ability.

## 2. Visual Identity

### 2.1 Color Palette
*   **Primary:** Trustworthy Blues (e.g., `#1890ff` or similar corporate medical blue) to convey professionalism and calm.
*   **Secondary:** Soft Greens (e.g., `#52c41a`) for success states and confirmation.
*   **Alerts:** Distinct Reds (e.g., `#ff4d4f`) for errors and critical warnings (e.g., low stock, allergy alerts).
*   **Backgrounds:** Clean Whites and Light Grays (`#f0f2f5`) to maintain high contrast and reduce eye strain during long shifts.

### 2.2 Typography
*   **Font Family:** San-serif fonts (e.g., Roboto, Inter, or system defaults) for maximum legibility on digital screens.
*   **Hierarchy:** Use clear heading sizes and font weights to distinguish between section titles, field labels, and data values.
*   **Data Presentation:** Ensure tabular data (patient lists, inventory) is legible with sufficient padding and row distinction.

## 3. Interaction Design

### 3.1 Feedback & Responsiveness
*   **Immediate Feedback:** Every action (save, submit, delete) must provide immediate visual feedback (toast notifications, loading spinners).
*   **State Visibility:** The system status (online/offline, saving...) should always be visible.

### 3.2 Navigation & Workflow
*   **Consistent Layout:** Maintain a consistent layout across modules (e.g., sidebar navigation, top header) to reduce the learning curve.
*   **Task-Oriented:** Group related functions logically (e.g., all pharmacy tasks in one section) to streamline daily workflows.
*   **Smart Defaults:** Pre-fill fields where safe and predictable to save time (e.g., default dates, common dosages).

## 4. Communication & Tone

### 4.1 Voice
*   **Professional yet Approachable:** Instructions and error messages should be polite, direct, and helpful. Avoid robotic or overly technical jargon in error descriptions.
*   **Action-Oriented:** Use active voice for buttons and commands (e.g., "Dispense Medicine" instead of "Medicine Dispensing").
