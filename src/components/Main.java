/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import controllers.operation.FileUtility;
import models.entities.*;
import models.enums.PropertyStatus;
import utils.HostFileUtils.HostReadFile;
import utils.OwnerFileUtils.OwnerReadFile;
import utils.RentalAgreementFileUtils.RentalAgreementReadFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static controllers.RentalAgreementController.selectProperty;
import static utils.HostFileUtils.HostReadFile.displayHosts;
import static utils.OwnerFileUtils.OwnerReadFile.displayOwners;
import static utils.OwnerFileUtils.OwnerReadFile.readOwnersFromFile;
import static view.RentalSystemMenu.displayBanner;
import static view.RentalSystemMenu.rentalSystemMenu;

public class Main {
//    public static void main(String[] args) throws IOException {
//
//        displayBanner();
//        rentalSystemMenu();
//
//    }

//public static void main(String[] args) {
//
////        FileUtility utility = new FileUtility();
////        utility.writeAllData();
//}

//    public static void main(String[] args) {
//        try {
//            List<RentalAgreement> rentalAgreements = RentalAgreementReadFile.readRentalAgreementsFromFile("src/components/resource/data/rentalAgreementData/rental_agreement.txt");
//            RentalAgreementReadFile.displayRentalAgreements(rentalAgreements);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void main(String[] args) {
//        System.out.println("Starting program...");
//        System.out.println("Loaded :");
//        FileUtility utility = new FileUtility();
//        utility.writeAllData();
//        System.out.println("Finished processing.");
//    }

    public static void main(String[] args) {
        System.out.println("Starting program...");
        System.out.println("Loaded Owner:");
        List<Owner> owners = readOwnersFromFile("src/components/resource/data/ownerData/owner.txt");
        OwnerReadFile.displayOwners(owners);
        System.out.println("Finished processing owners.");
    }


//    public static void main(String[] args) {
//        try {
//            System.out.println("Loaded Hosts:");
//            List<Host> hosts = HostReadFile.readHostsFromFile("src/components/resource/data/hostData/host.txt");
//
//            for (Host host : hosts) {
//                System.out.println(host);
//            }
//            displayHosts(hosts);
//        } catch (IOException e) {
//            System.err.println("Error reading hosts: " + e.getMessage());
//        }
//
//        // Test loading properties
//        Properties properties = new Properties();
//        properties.displayAllProperties(); // Assuming it prints out all properties
//
//        // Test loading persons
//        Persons persons = new Persons();
//        persons.displayAllPersons(); // Assuming it prints out all persons
//
//        // Test loading rental agreements
//        RentalAgreements agreements = new RentalAgreements();
//        agreements.getAllAgreements().forEach(System.out::println); // Print all agreements
//
//    }

}
