package controllers;

/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.entities.*;
import models.entities.Properties;
import models.enums.AgreementStatus;
import models.enums.PeriodType;
import models.interfaces.RentalManager;
import services.RentalManagerImpl;
import utils.RentalAgreementFileUtils.RentalAgreementWriteFile;
import utils.RentalAgreementFileUtils.RentalAgreementReadFile;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


import static utils.HostFileUtils.HostReadFile.readHostsFromFile;
import static utils.TenantFileUtils.TenantReadFile.readTenantsFromFile;
//import static utils.RentalAgreementFileUtils.RentalAgreementReadFile.readRentalAgreementFromFile;
//import static utils.TenantFileUtils.TenantReadFile.readTenantFromFile;

public class RentalAgreementController {
    private RentalAgreementReadFile rentalAgreementReadFile;
    private static RentalManager rentalManager;

    public RentalAgreementController() {
        rentalAgreementReadFile = new RentalAgreementReadFile();
        rentalManager = new RentalManagerImpl();// Khởi tạo đối tượng
    }
    private static final String RENTAL_AGREEMENT_FILE = "src/components/resource/data/rentalAgreementData/rental_agreement.txt";
    private static final String COMMERCIAL_PROPERTY_FILE = "src/components/resource/data/propertyData/commercial_property.txt";
    private static final String RESIDENTIAL_PROPERTY_FILE = "src/components/resource/data/propertyData/residential_property.txt";
    private static final String HOST_FILE = "src/components/resource/data/hostData/host.txt";
    private static final String TENANT_FILE = "src/components/resource/data/tenantData/tenant.txt";

    public static void displayAgreements() {
        List<RentalAgreement> rentalAgreements = rentalManager.getAllRentalAgreements();
        if (rentalAgreements.isEmpty()) {
            System.out.println("No rental agreements found.");
        } else {
            rentalAgreements.forEach(System.out::println);
        }
    }

    public static void showAgreementsForOwner(String ownerName) {
        List<RentalAgreement> agreements = rentalManager.getRentalAgreementsByOwnerName(ownerName);
        if (agreements.isEmpty()) {
            System.out.println("No rental agreements found for owner: " + ownerName);
        } else {
            System.out.println("Rental Agreements for " + ownerName + ":");
            agreements.forEach(System.out::println); // Assuming RentalAgreement has a meaningful toString()
        }
    }

    public static void showAgreementsByStatus(String status) {
        List<RentalAgreement> agreements = rentalManager.getRentalAgreementsByStatus(status);
        if (agreements.isEmpty()) {
            System.out.println("No rental agreements found with status: " + status);
        } else {
            System.out.println("Rental Agreements with status " + status + ":");
            agreements.forEach(System.out::println);
        }
    }

    public static void showAgreementsForProperty(String propertyAddress) {
        List<RentalAgreement> agreements = rentalManager.getRentalAgreementsByPropertyAddress(propertyAddress);
        if (agreements.isEmpty()) {
            System.out.println("No rental agreements found for property: " + propertyAddress);
        } else {
            System.out.println("Rental Agreements for " + propertyAddress + ":");
            agreements.forEach(System.out::println);
        }
    }
    // Get the last used agreement ID
    public String getLastUsedAgreementId() throws IOException {
        List<RentalAgreement> agreements = rentalAgreementReadFile.readRentalAgreementsFromFile(RENTAL_AGREEMENT_FILE);
        if (agreements.isEmpty()) {
            return "a-0"; // No agreements exist, start from "a-0"
        }
        // Find the maximum agreement ID
        return agreements.stream()
                .map(RentalAgreement::getAgreementId) // Get the ID as a String
                .max(Comparator.naturalOrder())
                .orElse("a-0"); // Return the highest ID found or "a-0" if none found
    }

    // Create a new rental agreement
    public void createRentalAgreement() throws IOException {
        Scanner scanner = new Scanner(System.in);
        //List<CommercialProperty> commercialProperties = readCommercialPropertiesFromFile(COMMERCIAL_PROPERTY_FILE);
        //List<ResidentialProperty> residentialProperties = readResidentialPropertiesFromFile(RESIDENTIAL_PROPERTY_FILE);

        String lastId = getLastUsedAgreementId();
        int lastNumericId = Integer.parseInt(lastId.split("-")[1]); // Extract the numeric part
        String agreementId = "a-" + (lastNumericId + 1); // Increment ID by 1

        Host selectedHost = selectHost(scanner);
        Property selectedProperty = selectProperty(scanner);
        Tenant mainTenant = selectMainTenant(scanner);
        List<Tenant> subTenants = selectSubTenants(scanner, mainTenant);
        PeriodType selectedType = selectPeriodType(scanner);
        Date contractDate = new Date();
        double rentalFee = getRentalFee(scanner);
        AgreementStatus selectedStatus = AgreementStatus.NEW;

        RentalAgreement newAgreement = new RentalAgreement(
                agreementId, selectedHost, selectedProperty,
                mainTenant, subTenants, selectedType, contractDate, rentalFee, selectedStatus);

        rentalManager.addRentalAgreement(newAgreement);
        RentalAgreementWriteFile.writeRentalAgreementToFile(rentalManager.getAllRentalAgreements(), RENTAL_AGREEMENT_FILE);
        printAgreementDetails(newAgreement);
    }

    // Select a host from the list
    private static Host selectHost(Scanner scanner) throws IOException {
        System.out.println("Select the host from the list below:");
        List<Host> hosts = readHostsFromFile(HOST_FILE);
        for (int i = 0; i < hosts.size(); i++) {
            System.out.println((i + 1) + ". " + hosts.get(i).getId() + " || " + hosts.get(i).getFullName());
        }
        int selectedHostIndex = getValidInput(scanner, hosts.size());
        return hosts.get(selectedHostIndex);
    }

    // Select an available property for the rental agreement
    public static Property selectProperty(Scanner scanner) {
        // Get the list of available properties from the Properties
        Properties properties = new Properties();
        List<Property> availableProperties = properties.getAvailableProperties();

        if (availableProperties.isEmpty()) {
            throw new IllegalStateException("No available properties.");
        }

        System.out.println("Select an available property for this agreement:");
        for (int i = 0; i < availableProperties.size(); i++) {
            System.out.println((i + 1) + ". " + availableProperties.get(i).getPropertyId() + " || " + availableProperties.get(i).getAddress());
        }

        int selectedPropertyIndex = getValidInput(scanner, availableProperties.size());
        return availableProperties.get(selectedPropertyIndex);
    }

    // Select the main tenant
    private static Tenant selectMainTenant(Scanner scanner) throws IOException {
        System.out.println("Select the main tenant from the list below (only one):");
        List<Tenant> tenants = readTenantsFromFile(TENANT_FILE);
        for (int i = 0; i < tenants.size(); i++) {
            System.out.println((i + 1) + ". " + tenants.get(i).getId() + " || " + tenants.get(i).getFullName());
        }
        int selectedTenantIndex = getValidInput(scanner, tenants.size());
        return tenants.get(selectedTenantIndex);
    }

    // Select sub-tenants for the rental agreement
    private static List<Tenant> selectSubTenants(Scanner scanner, Tenant mainTenant) throws IOException {
        // Read the full tenant list and remove the main tenant
        List<Tenant> subTenantList = readTenantsFromFile(TENANT_FILE).stream()
                .filter(tenant -> !tenant.getId().equals(mainTenant.getId()))
                .collect(Collectors.toList());

        if (subTenantList.isEmpty()) {
            System.out.println("No sub-tenants available.");
            return Collections.emptyList();
        }

        // Display the available sub-tenants
        System.out.println("Select the sub-tenant(s) from the list below (comma-separated):");
        for (int i = 0; i < subTenantList.size(); i++) {
            System.out.println((i + 1) + ". " + subTenantList.get(i).getId() + " || " + subTenantList.get(i).getFullName());
        }

        // Allow multiple sub-tenant selection
        System.out.println("Enter the numbers corresponding to the sub-tenant(s) you want to select (comma-separated), or leave blank if no sub-tenants:");
        String subTenantsInput = scanner.nextLine();
        if (subTenantsInput.isBlank()) {
            return Collections.emptyList();
        }

        List<Tenant> subTenants = new ArrayList<>();
        try {
            for (String index : subTenantsInput.split(",")) {
                int subTenantIndex = Integer.parseInt(index.trim()) - 1;
                subTenants.add(subTenantList.get(subTenantIndex));
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Invalid input for sub-tenants. Please restart the process.");
            return Collections.emptyList(); // or handle error gracefully
        }
        return subTenants;
    }

    // Select the period type for the rental agreement
    private static PeriodType selectPeriodType(Scanner scanner) {
        System.out.println("Enter the type of this period:");
        PeriodType[] periodTypes = PeriodType.values();
        for (int i = 0; i < periodTypes.length; i++) {
            System.out.println((i + 1) + ": " + periodTypes[i].getPeriod());
        }
        int typeOption = getValidInput(scanner, periodTypes.length);
        return periodTypes[typeOption];
    }

    // Get the rental fee from the user
    private static double getRentalFee(Scanner scanner) {
        System.out.println("Enter the rental fee (e.g: 16.6):");
        return scanner.nextDouble();
    }

    // Print the details of the created rental agreement
    private static void printAgreementDetails(RentalAgreement newAgreement) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_CYAN = "\u001B[36m";
        String yellow = "\u001B[33m";
        System.out.println(ANSI_CYAN +"╔════════════════════════════════════════════════════════╗");
        System.out.println("╟" + ANSI_CYAN + "      CREATE RENTAL AGREEMENT SUCCESSFULLY              ║");
        System.out.println("╟────────────────────────────────────────────────────────╢" + ANSI_RESET);
        System.out.println("       Agreement ID: " + newAgreement.getAgreementId());
        System.out.println("       Host: " + newAgreement.getHost().getId());
        System.out.println("       Property: " + newAgreement.getProperty().getPropertyId());
        System.out.println("       Main Tenant: " + newAgreement.getMainTenant().getId());
        System.out.println("       Sub Tenant(s): " + newAgreement.getSubTenantIDs());
        System.out.println("       Period Type: " + newAgreement.getPeriod());
        System.out.println("       Contract Date: " + new SimpleDateFormat("yyyy-MM-dd").format(newAgreement.getContractDate()));
        System.out.println("       Rental Fee: " + newAgreement.getRentalFee());
        System.out.println("       Agreement Status: " + newAgreement.getStatus());
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }

    // Get valid input from the user
    private static int getValidInput(Scanner scanner, int max) {
        int selectedIndex;
        while (true) {
            System.out.print("Enter the number corresponding to your choice: ");
            if (scanner.hasNextInt()) {
                selectedIndex = scanner.nextInt() - 1; // Convert to zero-based index
                scanner.nextLine(); // Consume the newline
                if (selectedIndex >= 0 && selectedIndex < max) {
                    return selectedIndex;
                } else {
                    System.out.println("Invalid selection. Please choose a number between 1 and " + max + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    // Update an existing rental agreement
    public static void updateRentalAgreement() throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<RentalAgreement> rentalAgreements = rentalManager.getAllRentalAgreements();

        System.out.println("Select a rental agreement to update:");
        if (rentalAgreements.isEmpty()) {
            System.out.println("No rental agreements available.");
            return;
        }
        for (int i = 0; i < rentalAgreements.size(); i++) {
            System.out.println((i + 1) + ". " + rentalAgreements.get(i).getAgreementId());
        }

        System.out.println("Enter the order number of the agreement you want to update:");
        int selectedIndex = getValidInput(scanner, rentalAgreements.size());
        RentalAgreement selectedRentalAgreement = rentalAgreements.get(selectedIndex);
        System.out.println("Current information of this rental agreement: \n" + selectedRentalAgreement);

        // Update the agreement's period type
        PeriodType newSelectedType = updatePeriodType(scanner, selectedRentalAgreement);
        // Update the agreement's contract date
        Date newContractDate = updateContractDate(scanner, selectedRentalAgreement);
        // Update the agreement's rental fee
        double newRentalFee = updateRentalFee(scanner, selectedRentalAgreement);
        // Update the agreement's status
        AgreementStatus newSelectedStatus = updateAgreementStatus(scanner, selectedRentalAgreement);

        // Set the updated values
        selectedRentalAgreement.setPeriod(newSelectedType);
        selectedRentalAgreement.setContractDate(newContractDate);
        selectedRentalAgreement.setRentalFee(newRentalFee);
        selectedRentalAgreement.setStatus(newSelectedStatus);

        // Use the RentalManager to update the rental agreement
        rentalManager.updateRentalAgreement(selectedRentalAgreement);

        // Print updated rental agreement details
        printUpdatedAgreementDetails(selectedRentalAgreement);
    }

    private static PeriodType updatePeriodType(Scanner scanner, RentalAgreement selectedRentalAgreement) {
        System.out.println("Enter new period type for " + selectedRentalAgreement.getAgreementId() + ": (Leave blank to keep the current value)");
        PeriodType[] periodTypes = PeriodType.values();
        for (int i = 0; i < periodTypes.length; i++) {
            System.out.println((i + 1) + ": " + periodTypes[i].getPeriod());
        }
        System.out.println("Choose a period type by number or leave blank if no change: ");
        String typeInput = scanner.nextLine();
        if (typeInput.isBlank()) {
            return selectedRentalAgreement.getPeriod(); // No change
        } else {
            int typeOption = Integer.parseInt(typeInput);
            return periodTypes[typeOption - 1];
        }
    }

    private static Date updateContractDate(Scanner scanner, RentalAgreement selectedRentalAgreement) {
        System.out.println("Enter a new contract date for " + selectedRentalAgreement.getAgreementId() + " (format: Mon Nov 20 15:30:00) or leave blank if no change:");
        String dateInput = scanner.nextLine();
        if (dateInput.isBlank()) {
            return selectedRentalAgreement.getContractDate(); // No change
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss");
            try {
                return formatter.parse(dateInput);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use the correct format (EEE MMM dd HH:mm:ss).");
                return selectedRentalAgreement.getContractDate(); // Return current date on error
            }
        }
    }

    private static double updateRentalFee(Scanner scanner, RentalAgreement selectedRentalAgreement) {
        System.out.println("Enter new rental fee for " + selectedRentalAgreement.getAgreementId() + " (or leave blank if no change):");
        String rentalFeeInput = scanner.nextLine();
        if (rentalFeeInput.isBlank()) {
            return selectedRentalAgreement.getRentalFee(); // No change
        } else {
            return Double.parseDouble(rentalFeeInput);
        }
    }

    private static AgreementStatus updateAgreementStatus(Scanner scanner, RentalAgreement selectedRentalAgreement) {
        System.out.println("Enter a new status for " + selectedRentalAgreement.getAgreementId() + ": (or leave blank if no change)");
        AgreementStatus[] agreementStatuses = AgreementStatus.values();
        for (int i = 0; i < agreementStatuses.length; i++) {
            System.out.println((i + 1) + ": " + agreementStatuses[i].getStatus());
        }
        System.out.println("Choose an agreement status by number or leave blank if no change: ");
        String statusInput = scanner.nextLine();
        if (statusInput.isBlank()) {
            return selectedRentalAgreement.getStatus(); // No change
        } else {
            int statusNumber = Integer.parseInt(statusInput);
            return agreementStatuses[statusNumber - 1];
        }
    }

    private static void printUpdatedAgreementDetails(RentalAgreement updatedAgreement) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("╟          UPDATE RENTAL AGREEMENT SUCCESSFULLY          ║");
        System.out.println("╟ ────────────────────────────────────────────────────────╢");
        System.out.println("          Agreement ID: " + updatedAgreement.getAgreementId());
        System.out.println("          Period Type: " + updatedAgreement.getPeriod());
        System.out.println("          Contract Date: " + new SimpleDateFormat("EEE MMM dd HH:mm:ss").format(updatedAgreement.getContractDate()));
        System.out.println("            Rental Fee: " + updatedAgreement.getRentalFee());
        System.out.println("            Status: " + updatedAgreement.getStatus());
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.print("Press any key to return...\n");
        new Scanner(System.in).next();  // Wait for the user to press Enter
    }

    // Delete a rental agreement by ID
    public static void deleteRentalAgreement() throws IOException {
        String ANSI_RED = "\u001B[31m";
        String reset = "\u001B[0m";
        Scanner scanner = new Scanner(System.in);
        List<RentalAgreement> rentalAgreements = rentalManager.getAllRentalAgreements();

        System.out.println("Select a rental agreement to delete:");
        // Check if the list is empty
        if (rentalAgreements.isEmpty()) {
            System.out.println("No rental agreements available.");
            return;
        }

        for (int i = 0; i < rentalAgreements.size(); i++) {
            System.out.println((i + 1) + ". " + rentalAgreements.get(i).getAgreementId());
        }

        int selectedIndex = getValidInput(scanner, rentalAgreements.size());
        RentalAgreement agreementToDelete = rentalAgreements.get(selectedIndex);

        // Show the selected agreement to the user
        System.out.println("The following rental agreement will be deleted:");
        System.out.println(agreementToDelete);  // Assuming you have overridden the `toString()` method in RentalAgreement

        // Confirm deletion
        System.out.print("Are you sure you want to delete this agreement? (y/n): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y")) {
            // Use the RentalManager to delete the rental agreement
            rentalManager.deleteRentalAgreement(agreementToDelete);

            // Save the updated list back to the file
            RentalAgreementWriteFile.writeRentalAgreementToFile(rentalManager.getAllRentalAgreements(), RENTAL_AGREEMENT_FILE);

            // Display success message
            System.out.println(ANSI_RED + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟     DELETE RENTAL AGREEMENT SUCCESSFULLY               ║");
            System.out.println(ANSI_RED + "╚════════════════════════════════════════════════════════╝"+ reset);
            System.out.print("Press any key to return...\n");
            scanner.next();  // Wait for user to press Enter
        } else {
            System.out.println("Deletion canceled.");
        }
    }
    public static void saveAgreementToFile(List<RentalAgreement> rentalAgreements) throws IOException {
        FileWriter rentalAgreementWriter = new FileWriter("src/components/resource/data/rentalAgreementData/rental_agreement.txt", false);
        for (RentalAgreement rentalAgreement : rentalAgreements) {
            rentalAgreementWriter.write(rentalAgreement.toString() + "\n");
        }
        rentalAgreementWriter.close();
    }

}