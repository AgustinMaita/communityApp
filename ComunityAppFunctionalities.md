# 🏢 Vecino Community Management App

An application designed to manage residential communities such as buildings, neighborhoods, and condominiums, with two main user roles: **Neighbor** and **Administrator**.

---

## User Roles

### 1. Neighbor
- Regular resident of a unit.
- Access to payment and community information.

### 2. Administrator
- Can be an external manager or a resident with an administrative role (e.g., president, treasurer, secretary).
- Manages finances, communication, and access permissions.
- Can assign custom roles within the administration.

---

## Features by Role

### A. Neighbor

- View pending fee payments.
- See detailed breakdown of each fee.
- Make digital payments for dues.
- Access payment history.
- View general balance of the community:
- By month, year, historical, accumulated.
- Access detailed financial records:
- Income, expenses, balances.
- See delinquency index (monthly, annual, historical).
- View monthly and yearly budgets.
- Access individual utility meter readings (water, gas, electricity).
- Support for shared meters with prorated costs.
- View supporting documents (e.g., repair invoices, maintenance receipts).
- Receive community notifications.
- Participate in surveys initiated by the administration.
- Access emergency contact numbers via a fixed button.
- Reserve shared spaces:
- Pool, event hall, BBQ area, gym, etc.

---

### B. Administration

- Create and assign payment quotas to each unit.
- Upload spending evidence (e.g., receipts, invoices).
- Upload payment proof for each neighbor.
- Record expenses.
- Create and manage budgets.
- Send announcements and notifications.
- Create and manage community surveys.
- Review and manage area reservations.
- Define and publish emergency contact numbers.

---

## Additional Logic

- Neighbors can take administrative roles.
- Those with admin roles are visible with their **Junta Directiva** title (e.g., President, Treasurer).
- Administrators can:
  - Configure roles and permissions.
  - Customize visibility, access levels, and notifications.

---

## Use Cases

- Vertical housing (apartment buildings).
- Horizontal housing (gated neighborhoods).
- Condominiums (multiple buildings under one administration).

---

## Future Enhancements (Suggested)

- Push notifications.
- Audit logs for administrative actions.
- Integration with accounting or payment APIs.
- Multilingual support.

