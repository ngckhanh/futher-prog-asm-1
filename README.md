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

The Rental Management System is designed as an internal tool to streamline the management of rental properties, tenants, and lease agreements. It provides a structured approach to organizing and tracking rental transactions, including:

- **Property Details**
- **Tenant Information**
- **Payment Records**
- **Contract Terms**

This system reduces manual tasks, ensures consistent data, and provides clear insights into rental agreements and tenant payments, simplifying property management.

---

### Technologies Used:

- **IDE**: IntelliJ IDEA

### Supporting Tools:

- **Diagram Tool**: Draw.io for visual diagrams (UML, Use Case, and Activity Diagrams)

## Build
```
. └── src/ 
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
│ │ ├── Properties.java
│ │ ├── Property.java
│ │ ├── RentalAgreement.java
│ │ ├── ResidentialProperty.java
│ │ └── Tenant.java
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
│ ├── EntityDisplay.java
│ ├── EntitySave.java
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

2. **`RentalAgreementController/`**  
   Handles rental agreement functionality, such as creating, updating, and deleting agreements.

3. **`ReportController/`**  
   Manages report generation functionality, pulling relevant data for creating reports.

4. **`models/`**  
   Contains all the class objects used in the system (Objects, Enums, Interfaces).

5. **`services/`**  
   Defines the logic and services used to process business operations in the system.

6. **`entities/`**  
   Stores all core entities in the system, such as:
    - **Rental Agreement**
    - **Person**
    - **Host**
    - **Tenant**
    - **Owner**
    - **Property**
    - **Commercial Property**
    - **Residential Property**

7. **`resource/`**  
   Stores all the `.txt` files (the system’s database).

8. **`utils/`**  
   Contains utility classes for reading and writing to the database files.

9. **`view/`**  
   Handles the menu and user interaction in the console interface.

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
