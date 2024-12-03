package controllers.operation;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileUtility {
    String rental_agreementFile = "src/components/resource/data/rentalAgreementData/rental_agreement.txt";
    String hostFile = "src/components/resource/data/hostData/host.txt";
    String ownerFile = "src/components/resource/data/ownerData/owner.txt";
    String tenantFile = "src/components/resource/data/tenantData/tenant.txt";
    String residential_propertyFile = "src/components/resource/data/propertyData/residential_property.txt";
    String commercial_propertyFile = "src/components/resource/data/propertyData/commercial_property.txt";
    String paymentFile = "src/components/resource/data/paymentData/payment.txt";

    // Write to commercial_property.txt
    private void writeToCommercialPropertyFile() throws IOException {
        String content="Commercial Property{propertyID='cp-01', address='0387 Howe Turnpike', pricing='$2499.48', status='Under_maintenance', ownerID='o-15', hostList='h-04', businessType='Airlines / Aviation', parkingSpaces=8, squareFootage=1768.42}\n" +
                "Commercial Property{propertyID='cp-02', address='385 Adah Greens', pricing='$3472.03', status='Under_maintenance', ownerID='o-11', hostList='h-07', businessType='Financial Services', parkingSpaces=13, squareFootage=4575.15}\n" +
                "Commercial Property{propertyID='cp-03', address='407 Daniel Crossroad', pricing='$3599.16', status='Available', ownerID='o-10', hostList='h-03', businessType='Leisure, Travel & Tourism', parkingSpaces=7, squareFootage=2378.09}\n" +
                "Commercial Property{propertyID='cp-04', address='26060 Harrison Hollow', pricing='$2329.32', status='Rented', ownerID='o-09', hostList='h-01', businessType='Real Estate', parkingSpaces=16, squareFootage=2519.91}\n" +
                "Commercial Property{propertyID='cp-05', address='336 Friesen Highway', pricing='$4016.09', status='Available', ownerID='o-07', hostList='h-12', businessType='Consumer Goods', parkingSpaces=16, squareFootage=8732.39}\n" +
                "Commercial Property{propertyID='cp-06', address='1385 Collen Course', pricing='$3489.26', status='Rented', ownerID='o-03', hostList='h-05', businessType='Mining & Metals', parkingSpaces=11, squareFootage=1993.54}\n" +
                "Commercial Property{propertyID='cp-07', address='41849 Pollich River', pricing='$2643.76', status='Available', ownerID='o-02', hostList='h-09', businessType='Military', parkingSpaces=12, squareFootage=8283.57}\n" +
                "Commercial Property{propertyID='cp-08', address='2118 Billy Roads', pricing='$3484.91', status='Under_maintenance', ownerID='o-01', hostList='h-15', businessType='Printing', parkingSpaces=16, squareFootage=1920.74}\n" +
                "Commercial Property{propertyID='cp-09', address='854 Mariella Springs', pricing='$4358.84', status='Available', ownerID='o-14', hostList='null', businessType='Wine and Spirits', parkingSpaces=11, squareFootage=2087.37}\n" +
                "Commercial Property{propertyID='cp-10', address='909 Roosevelt Station', pricing='$1394.99', status='Under_maintenance', ownerID='o-06', hostList='null', businessType='Shipbuilding', parkingSpaces=7, squareFootage=6918.04}\n" +
                "Commercial Property{propertyID='cp-11', address='08983 Nikolaus Skyway', pricing='$1842.24', status='Available', ownerID='o-05', hostList='null', businessType='Warehousing', parkingSpaces=5, squareFootage=4767.24}\n" +
                "Commercial Property{propertyID='cp-12', address='9723 Steuber Landing', pricing='$2412.07', status='Rented', ownerID='o-02', hostList='null', businessType='Retail', parkingSpaces=9, squareFootage=8289.37}\n" +
                "Commercial Property{propertyID='cp-13', address='0756 Jess Field', pricing='$3029.34', status='Under_maintenance', ownerID='o-01', hostList='null', businessType='Warehousing', parkingSpaces=6, squareFootage=1973.13}\n" +
                "Commercial Property{propertyID='cp-14', address='921 King Fork', pricing='$4178.82', status='Available', ownerID='o-13', hostList='null', businessType='Ranching', parkingSpaces=7, squareFootage=5326.07}\n" +
                "Commercial Property{propertyID='cp-15', address='39946 Spencer Mount', pricing='$1369.36', status='Rented', ownerID='o-03', hostList='null', businessType='Fund-Raising', parkingSpaces=12, squareFootage=7543.6}\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(commercial_propertyFile, true))) {
                writer.write(content);
            }
        }

    // Write to residential_property.txt
    private void writeToResidentialPropertyFile() throws IOException {
        String content="Residential Property{propertyID='rp-01', address='946 Marcus Harbors', pricing='$4235.83', status='Rented', ownerID='o-15', hostList='h-02', numberOfBedrooms=1, gardenAvailable=false, petFriendly=true}\n" +
                "Residential Property{propertyID='rp-02', address='891 Heaney Glens', pricing='$4535.94', status='Available', ownerID='o-12', hostList=h-11', numberOfBedrooms=3, gardenAvailable=false, petFriendly=true}\n" +
                "Residential Property{propertyID='rp-03', address='7325 Elfrieda Stream', pricing='$4233.39', status='Available', ownerID='o-10', hostList='h-10', numberOfBedrooms=4, gardenAvailable=true, petFriendly=true}\n" +
                "Residential Property{propertyID='rp-04', address='646 Marco Key', pricing='$2600.09', status='Under_maintenance', ownerID='o-07', hostList='h-14', numberOfBedrooms=4, gardenAvailable=true, petFriendly=true}\n" +
                "Residential Property{propertyID='rp-05', address='96285 Truman Springs', pricing='$1295.54', status='Available', ownerID='o-05', hostList='h-08', numberOfBedrooms=2, gardenAvailable=false, petFriendly=false}\n" +
                "Residential Property{propertyID='rp-06', address='28628 Ward Street', pricing='$4158.42', status='Under_maintenance', ownerID='o-04', hostList='h-13', numberOfBedrooms=3, gardenAvailable=false, petFriendly=true}\n" +
                "Residential Property{propertyID='rp-07', address='63419 Mel Throughway', pricing='$2326.01', status='Under_maintenance', ownerID='o-01', hostList='h-06', numberOfBedrooms=3, gardenAvailable=true, petFriendly=false}\n" +
                "Residential Property{propertyID='rp-08', address='071 Tamekia Mills', pricing='$2057.81', status='Rented', ownerID='o-08', hostList='null', numberOfBedrooms=2, gardenAvailable=true, petFriendly=false}\n" +
                "Residential Property{propertyID='rp-09', address='1759 Melissa Isle', pricing='$4700.25', status='Under_maintenance', ownerID='o-11', hostList='null', numberOfBedrooms=4, gardenAvailable=true, petFriendly=false}\n" +
                "Residential Property{propertyID='rp-10', address='55208 Lindgren Vista', pricing='$4318.45', status='Rented', ownerID='o-13', hostList='null', numberOfBedrooms=4, gardenAvailable=true, petFriendly=false}\n" +
                "Residential Property{propertyID='rp-11', address='196 Stamm Cove', pricing='$2747.57', status='Rented', ownerID='o-14', hostList='null', numberOfBedrooms=1, gardenAvailable=false, petFriendly=false}\n" +
                "Residential Property{propertyID='rp-12', address='92829 Rachel Street', pricing='$2155.61', status='Available', ownerID='o-15', hostList='null', numberOfBedrooms=3, gardenAvailable=false, petFriendly=true}\n" +
                "Residential Property{propertyID='rp-13', address='997 Corene Meadow', pricing='$4353.71', status='Under_maintenance', ownerID='o-09', hostList='null', numberOfBedrooms=4, gardenAvailable=false, petFriendly=false}\n" +
                "Residential Property{propertyID='rp-14', address='70835 Chance Harbor', pricing='$4014.19', status='Rented', ownerID='o-06', hostList='null', numberOfBedrooms=4, gardenAvailable=false, petFriendly=false}\n" +
                "Residential Property{propertyID='rp-15', address='12468 Mary Meadow', pricing='$1737.04', status='Under_maintenance', ownerID='o-03', hostList='null', numberOfBedrooms=2, gardenAvailable=true, petFriendly=false}\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(residential_propertyFile, true))) {
            writer.write(content);
        }
    }

    // Write to owner.txt
    private void writeToOwnerFile() throws IOException {
        String content="Owner{ID='o-01', fullName='Rodrick Paucek', dob='Fri Nov 19 17:36:59 ICT 1976', contactInfo='waltraud.kessler@hotmail.com', ownedPropertyList='rp-07, cp-08, cp-13', hostList='h-06, h-15'}\n" +
                "Owner{ID='o-02', fullName='Modesto Breitenberg DDS', dob='Mon Feb 05 14:17:08 ICT 1973', contactInfo='omar.romaguera@yahoo.com', ownedPropertyList='cp-07, cp-12', hostList='h-09'}\n" +
                "Owner{ID='o-03', fullName='Mrs. Guadalupe Kessler', dob='Sun May 18 19:49:48 ICT 1980', contactInfo='lonnie.king@yahoo.com', ownedPropertyList='cp-06, rp-15, cp-15', hostList='h-05'}\n" +
                "Owner{ID='o-04', fullName='Enrique Gulgowski', dob='Thu Jul 02 09:49:41 ICT 1981', contactInfo='pete.deckow@hotmail.com', ownedPropertyList='rp-06', hostList='h-13'}\n" +
                "Owner{ID='o-05', fullName='Emery Moore', dob='Sun Sep 30 08:44:31 ICT 1973', contactInfo='ernie.shields@hotmail.com', ownedPropertyList='rp-05, cp-11', hostList='h-08'}\n" +
                "Owner{ID='o-06', fullName='Clarence Schmeler', dob='Mon Oct 04 17:17:10 ICT 1993', contactInfo='maryland.quitzon@yahoo.com', ownedPropertyList='rp-14, cp-10', hostList='null'}\n" +
                "Owner{ID='o-07', fullName='Barton Brakus', dob='Fri Dec 27 05:25:55 ICT 1996', contactInfo='chassidy.schoen@yahoo.com', ownedPropertyList='cp-05, rp-04', hostList='h-14, h-12'}\n" +
                "Owner{ID='o-08', fullName='Jefferey Carroll', dob='Mon Sep 03 23:55:31 ICT 1979', contactInfo='joline.grimes@gmail.com', ownedPropertyList='rp-08', hostList='null'}\n" +
                "Owner{ID='o-09', fullName='Cleo Dietrich', dob='Tue Oct 11 00:04:46 ICT 1960', contactInfo='sid.bashirian@yahoo.com', ownedPropertyList='rp-13, cp-04', hostList='h-01'}\n" +
                "Owner{ID='o-10', fullName='Cira Borer', dob='Sat Nov 08 23:51:51 ICT 1980', contactInfo='kelly.nikolaus@yahoo.com', ownedPropertyList='rp-03, cp-03', hostList='h-10, h-03'}\n" +
                "Owner{ID='o-11', fullName='Dustin Reilly', dob='Mon Sep 15 04:50:46 ICT 1997', contactInfo='elbert.aufderhar@hotmail.com', ownedPropertyList='cp-02, rp-09', hostList='h-07'}\n" +
                "Owner{ID='o-12', fullName='Miss Gregorio Wilkinson', dob='Wed Aug 07 02:58:37 ICT 1991', contactInfo='amalia.kiehn@gmail.com', ownedPropertyList='rp-02', hostList='h-11'}\n" +
                "Owner{ID='o-13', fullName='Ellsworth Yundt', dob='Sun Oct 09 11:51:35 ICT 1994', contactInfo='lasonya.littel@yahoo.com', ownedPropertyList='rp-10, cp-14', hostList='null'}\n" +
                "Owner{ID='o-14', fullName='Ezequiel Rodriguez', dob='Mon Aug 30 00:56:25 ICT 2004', contactInfo='suzette.gislason@yahoo.com', ownedPropertyList='rp-11, cp-09', hostList='null'}\n" +
                "Owner{ID='o-15', fullName='Izola Thiel', dob='Sat Apr 12 02:03:03 ICT 1997', contactInfo='maryam.hessel@gmail.com', ownedPropertyList='rp-01, cp-01, rp-12', hostList='h-02, h-04'}\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ownerFile, true))) {
                writer.write(content);
            }
        }


    // Write to host.txt
    private void writeToHostFile() throws IOException {
        String content="Host{ID='h-01', fullName='Josue Russel', dob='Sat Sep 23 11:52:37 ICT 1995', contactInfo='adelina.yundt@hotmail.com', managedPropertyList='cp-04', cooperatingOwnerList='o-09', rentalAgreementList='a-07'}\n" +
                "Host{ID='h-02', fullName='Julia Morissette', dob='Wed Nov 04 13:26:47 ICT 1987', contactInfo='andres.mayert@gmail.com', managedPropertyList='rp-01', cooperatingOwnerList='o-15', rentalAgreementList='a-14'}\n" +
                "Host{ID='h-03', fullName='Lera Walter', dob='Sun Aug 08 10:25:04 ICT 1965', contactInfo='jacquline.kohler@yahoo.com', managedPropertyList='cp-03', cooperatingOwnerList='o-10', rentalAgreementList='a-05'}\n" +
                "Host{ID='h-04', fullName='Martin Robel IV', dob='Mon Nov 17 17:39:19 ICT 1975', contactInfo='jodi.keeling@yahoo.com', managedPropertyList='cp-01', cooperatingOwnerList='o-15', rentalAgreementList='a-02'}\n" +
                "Host{ID='h-05', fullName='Loni Cronin', dob='Mon Jun 23 06:31:45 ICT 1975', contactInfo='warren.denesik@gmail.com', managedPropertyList='cp-06', cooperatingOwnerList='o-03', rentalAgreementList='a-11'}\n" +
                "Host{ID='h-06', fullName='Imelda Ferry', dob='Sun Jun 11 15:30:37 ICT 1961', contactInfo='mickie.ondricka@yahoo.com', managedPropertyList='rp-07', cooperatingOwnerList='o-01', rentalAgreementList='a-01'}\n" +
                "Host{ID='h-07', fullName='Roselia Stroman', dob='Sun Apr 01 04:04:10 ICT 2001', contactInfo='karl.turcotte@gmail.com', managedPropertyList='cp-02', cooperatingOwnerList='o-11', rentalAgreementList='a-03'}\n" +
                "Host{ID='h-08', fullName='Mr. Homer Witting', dob='Mon May 30 10:10:38 ICT 1966', contactInfo='steve.hand@yahoo.com', managedPropertyList='rp-05', cooperatingOwnerList='o-05', rentalAgreementList='a-06'}\n" +
                "Host{ID='h-09', fullName='John Okuneva', dob='Mon Jun 26 02:17:22 ICT 1967', contactInfo='jarvis.altenwerth@hotmail.com', managedPropertyList='cp-07', cooperatingOwnerList='o-02', rentalAgreementList='a-13'}\n" +
                "Host{ID='h-10', fullName='Gerry Vandervort', dob='Wed Jul 05 21:47:34 ICT 1961', contactInfo='norah.collins@hotmail.com', managedPropertyList='rp-03', cooperatingOwnerList='o-10', rentalAgreementList='a-10'}\n" +
                "Host{ID='h-11', fullName='Marcellus Crist', dob='Thu Jan 26 04:04:35 ICT 1967', contactInfo='scot.gulgowski@gmail.com', managedPropertyList='rp-02', cooperatingOwnerList='o-12', rentalAgreementList='a-12'}\n" +
                "Host{ID='h-12', fullName='Cathi McKenzie', dob='Wed Jul 02 04:09:42 ICT 1997', contactInfo='marinda.robel@gmail.com', managedPropertyList='cp-05', cooperatingOwnerList='o-07', rentalAgreementList='a-09'}\n" +
                "Host{ID='h-13', fullName='Courtney Rodriguez', dob='Sat Jun 13 12:41:38 ICT 1981', contactInfo='shirely.ortiz@gmail.com', managedPropertyList='rp-06', cooperatingOwnerList='o-04', rentalAgreementList='a-04'}\n" +
                "Host{ID='h-14', fullName='Miss Ali Emmerich', dob='Tue Feb 29 22:42:15 ICT 1972', contactInfo='paris.murray@yahoo.com', managedPropertyList='rp-04', cooperatingOwnerList='o-07', rentalAgreementList='a-08'}\n" +
                "Host{ID='h-15', fullName='Ricki Bayer', dob='Tue Aug 08 13:29:43 ICT 1972', contactInfo='dalton.mcglynn@hotmail.com', managedPropertyList='cp-08', cooperatingOwnerList='o-01', rentalAgreementList='a-15'}\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(hostFile, true))) {
            writer.write(content);
        }
    }

    // Write to tenant.txt
    private void writeToTenantFile() throws IOException {
        String content="Tenant{ID='t-01', fullName='Edmund Hills V', dob='Mon Aug 03 19:59:55 ICT 1964', contactInfo='america.durgan@gmail.com', rentalAgreementList='a-15', rentedPropertyList='cp-08, cp-06', paymentRecords='p-06'}\n" +
                "Tenant{ID='t-02', fullName='Valentin Willms I', dob='Mon Apr 22 21:54:43 ICT 1974', contactInfo='dewayne.miller@gmail.com', rentalAgreementList='a-05, a-04', rentedPropertyList='cp-03, rp-06', paymentRecords='p-08'}\n" +
                "Tenant{ID='t-03', fullName='Diego Huel', dob='Tue May 06 23:59:26 ICT 1986', contactInfo='stuart.robel@gmail.com', rentalAgreementList='a-13, a-01, a-09, a-10', rentedPropertyList='cp-07, rp-07, cp-05, rp-03', paymentRecords='p-03'}\n" +
                "Tenant{ID='t-04', fullName='Lynette Gleason', dob='Mon Jun 17 05:21:56 ICT 1996', contactInfo='allie.schneider@hotmail.com', rentalAgreementList='a-14, a-01, a-07, a-12', rentedPropertyList='rp-01, rp-07, cp-04, rp-02', paymentRecords='p-05'}\n" +
                "Tenant{ID='t-05', fullName='Mr. Tracy Hodkiewicz', dob='Thu Feb 16 04:12:24 ICT 2006', contactInfo='pasquale.feest@gmail.com', rentalAgreementList='a-12, a-14', rentedPropertyList='rp-02, rp-01', paymentRecords='p-04'}\n" +
                "Tenant{ID='t-06', fullName='Percy Conroy', dob='Thu Nov 03 10:28:51 ICT 1966', contactInfo='gilberto.watsica@hotmail.com', rentalAgreementList='a-08', rentedPropertyList='rp-04', paymentRecords='p-13'}\n" +
                "Tenant{ID='t-07', fullName='Garry Jacobs', dob='Wed Jan 06 04:16:08 ICT 1993', contactInfo='yung.runolfsson@hotmail.com', rentalAgreementList='a-10, a-02, a-12', rentedPropertyList='rp-03, cp-01, rp-02', paymentRecords='p-02'}\n" +
                "Tenant{ID='t-08', fullName='Brandon Lesch', dob='Wed Oct 06 04:25:17 ICT 1971', contactInfo='tamatha.dibbert@gmail.com', rentalAgreementList='a-11, a-03, a-07', rentedPropertyList='cp-06, cp-02, cp-04', paymentRecords='p-10'}\n" +
                "Tenant{ID='t-09', fullName='Carlo Moen', dob='Sun May 11 04:24:28 ICT 1969', contactInfo='marybeth.erdman@gmail.com', rentalAgreementList='a-03, a-01, a-05, a-12', rentedPropertyList='cp-02, rp-07, cp-03, rp-02', paymentRecords='p-12'}\n" +
                "Tenant{ID='t-10', fullName='Kathie Donnelly', dob='Fri Mar 13 19:50:22 ICT 1964', contactInfo='gail.hilll@hotmail.com', rentalAgreementList='a-02, a-11', rentedPropertyList='cp-01, cp-06', paymentRecords='p-14'}\n" +
                "Tenant{ID='t-11', fullName='Miss Christopher Schneider', dob='Sun Mar 19 15:29:43 ICT 1972', contactInfo='lyman.smith@hotmail.com', rentalAgreementList='a-06, a-02', rentedPropertyList='rp-05, cp-01', paymentRecords='p-11'}\n" +
                "Tenant{ID='t-12', fullName='Cliff Kulas', dob='Wed Jul 26 12:52:55 ICT 1978', contactInfo='dan.balistreri@gmail.com', rentalAgreementList='a-09', rentedPropertyList='cp-05', paymentRecords='p-07'}\n" +
                "Tenant{ID='t-13', fullName='Loyd Wiza DVM', dob='Wed Dec 11 03:57:36 ICT 1985', contactInfo='mary.littel@gmail.com', rentalAgreementList='a-01, a-11', rentedPropertyList='rp-07, cp-06', paymentRecords='p-09'}\n" +
                "Tenant{ID='t-14', fullName='Mr. Albertha Kautzer', dob='Wed May 04 08:09:05 ICT 1988', contactInfo='merrilee.kreiger@gmail.com', rentalAgreementList='a-04, a-08', rentedPropertyList='rp-06, rp-04', paymentRecords='p-01'}\n" +
                "Tenant{ID='t-15', fullName='Mrs. Daniel Jaskolski', dob='Tue May 23 09:24:20 ICT 1967', contactInfo='jefferey.hegmann@hotmail.com', rentalAgreementList='a-07, a-10', rentedPropertyList='cp-04, rp-03', paymentRecords='p-15'}\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tenantFile, true))) {
            writer.write(content);
        }
    }

    // Write to payment.txt
    private void writeToPaymentFile() throws IOException {
        String content="Payment{paymentId='p-01', amount='$310.83', paymentDate='Sat Nov 16 22:08:01 ICT 2024', method='Credit_card'}\n" +
                "Payment{paymentId='p-02', amount='$1347.78', paymentDate='Tue Nov 05 12:43:20 ICT 2024', method='Cash'}\n" +
                "Payment{paymentId='p-03', amount='$1819.59', paymentDate='Wed Nov 20 17:11:45 ICT 2024', method='Cash'}\n" +
                "Payment{paymentId='p-04', amount='$20.8', paymentDate='Fri Nov 08 02:50:42 ICT 2024', method='Cash'}\n" +
                "Payment{paymentId='p-05', amount='$950.74', paymentDate='Thu Oct 31 20:56:43 ICT 2024', method='Cash'}\n" +
                "Payment{paymentId='p-06', amount='$510.35', paymentDate='Wed Oct 23 13:45:30 ICT 2024', method='Cash'}\n" +
                "Payment{paymentId='p-07', amount='$1233.77', paymentDate='Tue Nov 12 15:04:33 ICT 2024', method='Cash'}\n" +
                "Payment{paymentId='p-08', amount='$876.15', paymentDate='Thu Nov 07 10:39:16 ICT 2024', method='Cash'}\n" +
                "Payment{paymentId='p-09', amount='$1164.03', paymentDate='Fri Nov 01 00:57:20 ICT 2024', method='Credit_card'}\n" +
                "Payment{paymentId='p-10', amount='$320.42', paymentDate='Thu Oct 24 16:13:34 ICT 2024', method='Credit_card'}\n" +
                "Payment{paymentId='p-11', amount='$974.64', paymentDate='Wed Oct 23 18:23:33 ICT 2024', method='Cash'}\n" +
                "Payment{paymentId='p-12', amount='$126.52', paymentDate='Wed Nov 13 17:18:09 ICT 2024', method='Cash'}\n" +
                "Payment{paymentId='p-13', amount='$1610.88', paymentDate='Sat Nov 16 08:26:07 ICT 2024', method='Credit_card'}\n" +
                "Payment{paymentId='p-14', amount='$1649.65', paymentDate='Fri Nov 01 18:14:04 ICT 2024', method='Credit_card'}\n" +
                "Payment{paymentId='p-15', amount='$185.04', paymentDate='Mon Nov 04 17:57:05 ICT 2024', method='Credit_card'}\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(paymentFile, true))) {
            writer.write(content);
        }
    }

    // Write to rental_agreement.txt
    private void writeToRentalAgreementFile() throws IOException {
        String content="Rental Agreement{agreementID='a-01', hostID='h-06', propertyID='rp-07', mainTenantID='t-13', subTenantIDs='t-03, t-09, t-04', periodType='Monthly', contractDate='Sun Jan 21 22:43:48 ICT 1990', rentalFee='$1738.98', agreementStatus='Completed'}\n" +
                "Rental Agreement{agreementID='a-02', hostID='h-04', propertyID='cp-01', mainTenantID='t-10', subTenantIDs='t-07, t-11', periodType='Monthly', contractDate='Wed Jun 07 10:30:33 ICT 1967', rentalFee='$2126.68', agreementStatus='Completed'}\n" +
                "Rental Agreement{agreementID='a-03', hostID='h-07', propertyID='cp-02', mainTenantID='t-09', subTenantIDs='t-08', periodType='Monthly', contractDate='Thu Dec 28 14:32:19 ICT 1967', rentalFee='$2295.74', agreementStatus='Completed'}\n" +
                "Rental Agreement{agreementID='a-04', hostID='h-13', propertyID='rp-06', mainTenantID='t-14', subTenantIDs='t-02', periodType='Monthly', contractDate='Wed Sep 10 14:53:44 ICT 1975', rentalFee='$2068.10', agreementStatus='Completed'}\n" +
                "Rental Agreement{agreementID='a-05', hostID='h-03', propertyID='cp-03', mainTenantID='t-02', subTenantIDs='t-09', periodType='Daily', contractDate='Fri Jan 11 17:17:51 ICT 1980', rentalFee='$890.81', agreementStatus='Completed'}\n" +
                "Rental Agreement{agreementID='a-06', hostID='h-08', propertyID='rp-05', mainTenantID='t-11', subTenantIDs='None', periodType='Fortnightly', contractDate='Sun May 14 14:48:53 ICT 2006', rentalFee='$901.14', agreementStatus='Completed'}\n" +
                "Rental Agreement{agreementID='a-07', hostID='h-01', propertyID='cp-04', mainTenantID='t-15', subTenantIDs='t-04, t-08', periodType='Fortnightly', contractDate='Fri Jun 23 21:37:46 ICT 1967', rentalFee='$1029.73', agreementStatus='Completed'}\n" +
                "Rental Agreement{agreementID='a-08', hostID='h-14', propertyID='rp-04', mainTenantID='t-06', subTenantIDs='t-14', periodType='Fortnightly', contractDate='Thu Oct 09 12:23:45 ICT 2003', rentalFee='$1171.07', agreementStatus='Completed'}\n" +
                "Rental Agreement{agreementID='a-09', hostID='h-12', propertyID='cp-05', mainTenantID='t-12', subTenantIDs='t-03', periodType='Fortnightly', contractDate='Mon Jul 05 08:27:34 ICT 1971', rentalFee='$2087.17', agreementStatus='Completed'}\n" +
                "Rental Agreement{agreementID='a-10', hostID='h-10', propertyID='rp-03', mainTenantID='t-07', subTenantIDs='t-03, t-15', periodType='Daily', contractDate='Fri Dec 12 18:25:17 ICT 2003', rentalFee='$1500.84', agreementStatus='Active'}\n" +
                "Rental Agreement{agreementID='a-11', hostID='h-05', propertyID='cp-06', mainTenantID='t-08', subTenantIDs='t-13, t-10, t-01', periodType='Weekly', contractDate='Tue Aug 31 19:46:03 ICT 1971', rentalFee='$2075.23', agreementStatus='New'}\n" +
                "Rental Agreement{agreementID='a-12', hostID='h-11', propertyID='rp-02', mainTenantID='t-05', subTenantIDs='t-09, t-04, t-07', periodType='Weekly', contractDate='Fri Sep 10 17:18:09 ICT 1971', rentalFee='$2015.33', agreementStatus='New'}\n" +
                "Rental Agreement{agreementID='a-13', hostID='h-09', propertyID='cp-07', mainTenantID='t-03', subTenantIDs='None', periodType='Daily', contractDate='Thu Oct 23 04:11:14 ICT 1997', rentalFee='$2334.93', agreementStatus='Active'}\n" +
                "Rental Agreement{agreementID='a-14', hostID='h-02', propertyID='rp-01', mainTenantID='t-04', subTenantIDs='t-05', periodType='Weekly', contractDate='Sat Aug 21 20:20:47 ICT 1993', rentalFee='$1969.71', agreementStatus='New'}\n" +
                "Rental Agreement{agreementID='a-15', hostID='h-15', propertyID='cp-08', mainTenantID='t-01', subTenantIDs='None', periodType='Monthly', contractDate='Fri Jun 03 04:03:47 ICT 1966', rentalFee='$1116.21', agreementStatus='Active'}\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rental_agreementFile, true))) {
            writer.write(content);
        }
    }

    // Clears content of a file
    private void clearFileContent(String filename) throws IOException {
        java.io.File file = new java.io.File(filename);
        java.io.File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // Create directories if they don't exist
        }
        if (!file.exists()) {
            file.createNewFile(); // Create file if it doesn't exist
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(""); // Clear content
        }
    }

    public static void deleteDirectory(File directory) {
        // Check if the directory exists
        if (directory.exists() && directory.isDirectory()) {
            // List all files and directories within the directory
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    // Recursively delete files and subdirectories
                    if (file.isDirectory()) {
                        deleteDirectory(file); // Recursion for subdirectory
                    } else {
                        file.delete(); // Delete file
                    }
                }
            }
            // Finally, delete the directory itself
            directory.delete();
        }
        // If the directory does not exist, simply do nothing (ignore)
    }

    public static void clearReportDataDirectory() {
        String reportDataPath = "src/components/resource/data/reportData";
        File reportDataDir = new File(reportDataPath);
        deleteDirectory(reportDataDir);
    }
    public void writeAllData() {
        try {
            // Clear all data before writing new content
            clearFileContent(rental_agreementFile);
            clearFileContent(hostFile);
            clearFileContent(tenantFile);
            clearFileContent(ownerFile);
            clearFileContent(residential_propertyFile);
            clearFileContent(commercial_propertyFile);
            clearFileContent(paymentFile);
            clearReportDataDirectory();

            // Write new data to files
            writeToRentalAgreementFile();
            writeToHostFile();
            writeToOwnerFile();
            writeToTenantFile();
            writeToPaymentFile();
            writeToResidentialPropertyFile();
            writeToCommercialPropertyFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}