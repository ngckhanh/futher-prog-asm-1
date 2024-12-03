# further-programming-assignment-1-build-a-console-app-ngckhanh

## Project Information

- **GitHub Repository**: [https://github.com/RMIT-Vietnam-Teaching/further-programming-assignment-1-build-a-console-app-ngckhanh.git](https://github.com/RMIT-Vietnam-Teaching/further-programming-assignment-1-build-a-console-app-ngckhanh.git)
- **UML & Use Case & Activity Diagram**: [View Diagrams](https://app.diagrams.net/#G1zgseuxsJw6un7tMW0noldYDAiAji9Xo8#%7B%22pageId%22%3A%22c3pJUl5TQvUnrKMPT37Q%22%7D)
- **Project Report**: [View Report](https://docs.google.com/document/d/1f2m80KaKkkYJusyRn2yrO60vVElRuK8iHLdY_l4Ibz8/edit?tab=t.0)

---

## Project Timeline

- **Project Start Date**: 20/11/2024
- **Project End Date**: 01/12/2024

---

## Project Contributors

| Student Name      | Student ID |
| :---------------- |:----------:|
| Ton Nu Ngoc Khanh | S-3932105  |

---

## Project Overview

### About:

This project is an assignment for **COSC2440 Further Programming**, offered at **RMIT University Vietnam** in **Semester 2024B**.

- **Campus**: Saigon South (SGS), Vietnam
- **Lecturer**: Mr. Minh Thanh Vu

---

### Project Details:

**Rental Management System**

The Rental Management System is an internal tool designed to streamline the management of rental properties, tenants, and leases. It provides an organized way to handle property details, tenant records, payment history, and contract terms, ensuring smooth and efficient tracking of rental transactions. The technology improves property administration by minimising manual tasks, maintaining consistent data, and offering clear information about rental agreements and tenant payments.

---

### Technologies Used:

- **IDE**: IntelliJ IDEA

### Supporting Tools:

- **Diagram Tool**: Draw.io for visual diagrams (UML, Use Case, and Activity Diagrams)

## Build
```
.
└── src/ 
└── components/ 
└── README.md 
├── Main.java
├── controllers/
│ ├── RentalAgreementController.java
│ ├── ReportController.java
│ └── operation/
│ └── FileUtility.java
├── models/
│ ├── entities/
│ │ ├── CommercialProperty.java
│ │ ├── Host.java
│ │ ├── Owner.java
│ │ ├── Payment.java
│ │ ├── Person.java
│ │ ├── Persons.java
│ │ ├── Property.java
│ │ ├── RentalAgreement.java
│ │ ├── ResidentialProperty.java
│ │ └── Tenant.java
│ ├── managers/
│ │ ├── Properties.java
│ │ ├── Persons.java
│ │ ├── Payments.java
│ │ └── RentalAgreements.java
│ ├── enums/
│ │ ├── AgreementStatus.java
│ │ ├── PaymentMethod.java
│ │ ├── PeriodType.java
│ │ └── PropertyStatus.java
│ └── interfaces/
│ └── RentalManager.java
├── resource/
│ └── data/
│ ├── hostData/
│ │ └── host.txt
│ ├── ownerData/
│ │ └── owner.txt
│ ├── paymentData/
│ │ └── payment.txt
│ ├── propertyData/
│ │ ├── commercial_property.txt
│ │ └── residential_property.txt
│ ├── rentalAgreementData/
│ │ └── rental_agreement.txt
│ └── tenantData/
│ └── tenant.txt
├── services/
│ └── RentalManagerImpl.java
├── utils/
│ ├── CommercialPropertyFileUtils/
│ │ ├── CommercialPropertyReadFile.java
│ │ └── CommercialPropertyWriteFile.java
│ ├── HostFileUtils/
│ │ ├── HostReadFile.java
│ │ └── HostWriteFile.java
│ ├── OwnerFileUtils/
│ │ ├── OwnerReadFile.java
│ │ └── OwnerWriteFile.java
│ ├── PaymentFileUtils/
│ │ ├── PaymentReadFile.java
│ │ └── PaymentWriteFile.java
│ ├── PropertyFileUtils/
│ │ ├── PropertyReadFile.java
│ │ └── PropertyWriteFile.java
│ ├── RentalAgreementFileUtils/
│ │ ├── RentalAgreementReadFile.java
│ │ └── RentalAgreementWriteFile.java
│ ├── ReportFileUtils/
│ │ └── ReportWriteFile.java
│ ├── ResidentialPropertyFileUtils/
│ │ ├── ResidentialPropertyReadFile.java
│ │ └── ResidentialPropertyWriteFile.java
│ ├── SortOption.java
│ └── TenantFileUtils/
│ ├── TenantReadFile.java
│ └── TenantWriteFile.java
└── view/
├── RentalSystemMenu.java
└── ReportMenu.java
```


### Overview:

The project structure follows the Model-View-Controller (MVC) design pattern to separate concerns and facilitate maintainability. 


### Directory Breakdown:

1. **`Controller/`**  
   The main controller handling all system operations and logic.
    - **RentalAgreementController**: Manages rental agreement functionality, including creating, updating, and deleting rental agreements.
    - **ReportController**: Manages report generation functionality, pulling relevant data for creating various reports.

2. **`models/`**  
   Contains all the class objects used in the system (Objects, Enums, Interfaces, Manager).

3. **`services/`**  
   Defines the logic and services used to process business operations in the system.

4. **`managers/`**  
   Contains classes that manage the CRUD operations for various entities.
    - **Properties**: Manages CRUD operations for properties (both residential and commercial).
    - **Persons**: Manages CRUD operations for persons (tenants, hosts, owners).
    - **Payments**: Manages payment records and operations.
    - **RentalAgreements**: Manages rental agreement records and operations.

5. **`entities/`**  
   Stores all core entities in the system, such as:
    - **Rental Agreement**
    - **Person**
    - **Host**
    - **Tenant**
    - **Owner**
    - **Property**
    - **Commercial Property**
    - **Residential Property**

6. **`enums/`**  
   Contains enumerated types for various statuses and methods, such as:
    - **AgreementStatus**
    - **PaymentMethod**
    - **PeriodType**
    - **PropertyStatus**

7. **`interfaces/`**  
  Defines contracts for various managers and services.
   
8. **`resource/`**  
   Stores all the `.txt` files (the system’s database).

9. **`utils/`**  
   Contains utility classes for reading and writing to the database files.

10. **`view/`**  
   Handles the menu and user interaction in the console interface, providing a user-friendly way to interact with the system.

---

## Troubleshooting

If the system fails unexpectedly, follow these steps to reset it:

1. Navigate to the **Main Menu**.
2. Select **Option 6: Reset and Add Default Data**.

This will restore the system to its default state.

---

### Future Improvements

- Add validation to ensure rental agreements cannot overlap.
- Implement a more advanced search feature for better data retrieval.
- Improve user interface for smoother navigation.

---
