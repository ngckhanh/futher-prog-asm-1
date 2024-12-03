package controllers;

/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.entities.*;
import models.managers.Persons;
import models.managers.Properties;
import models.enums.AgreementStatus;
import models.enums.PeriodType;
import models.interfaces.RentalManager;
import services.RentalManagerImpl;
import utils.RentalAgreementFileUtils.RentalAgreementWriteFile;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


import static utils.HostFileUtils.HostReadFile.readHostsFromFile;
import static utils.RentalAgreementFileUtils.RentalAgreementReadFile.readRentalAgreementsFromFile;
import static utils.TenantFileUtils.TenantReadFile.readTenantsFromFile;

public class RentalAgreementController {
    private static final String RENTAL_AGREEMENT_FILE = "src/components/resource/data/rentalAgreementData/rental_agreement.txt";

    private static RentalManager rentalManager;
    private static Persons persons;
    private static Properties properties;

    static {
        rentalManager = new RentalManagerImpl();
        persons = new Persons();
        properties = new Properties();
    }

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
            agreements.forEach(System.out::println);
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
    public static String getLastUsedAgreementId() throws IOException {
        List<RentalAgreement> agreements = readRentalAgreementsFromFile();
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
    public static void createRentalAgreement() throws IOException {
        Scanner scanner = new Scanner(System.in);

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

        // Create new rental agreement
        RentalAgreement newAgreement = new RentalAgreement(
                agreementId, selectedHost, selectedProperty,
                mainTenant, subTenants, selectedType, contractDate, rentalFee, selectedStatus);

        // Add rental agreement to rental manager
        rentalManager.addRentalAgreement(newAgreement);

        printAgreementDetails(newAgreement);
    }

    // Select a host from the list
    private static Host selectHost(Scanner scanner) throws IOException {
        System.out.println("Select the host from the list below:");
        List<Host> hosts = readHostsFromFile();
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
        List<Tenant> tenants = readTenantsFromFile();
        for (int i = 0; i < tenants.size(); i++) {
            System.out.println((i + 1) + ". " + tenants.get(i).getId() + " || " + tenants.get(i).getFullName());
        }
        int selectedTenantIndex = getValidInput(scanner, tenants.size());
        return tenants.get(selectedTenantIndex);
    }

    // Select sub-tenants for the rental agreement
    private static List<Tenant> selectSubTenants(Scanner scanner, Tenant mainTenant) throws IOException {
        // Read the full tenant list and remove the main tenant
        List<Tenant> subTenantList = readTenantsFromFile().stream()
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
        double rentalFee;
        while (true) {
            System.out.print("Enter the rental fee (e.g: 16.6): ");
            if (scanner.hasNextDouble()) {
                rentalFee = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline
                if (rentalFee >= 0) { // Assuming rental fee cannot be negative
                    return rentalFee;
                } else {
                    System.out.println("Invalid input. Rental fee cannot be negative. Please enter a valid number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
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
        String purple = "\u001B[35m";
        String cyan = "\u001B[36m";
        String white = "\u001B[37m";
        String reset = "\u001B[0m";

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

        int selectedIndex = getValidInput(scanner, rentalAgreements.size());
        RentalAgreement selectedRentalAgreement = rentalAgreements.get(selectedIndex);
        System.out.println("Current information of this rental agreement: \n" + purple +selectedRentalAgreement + reset);

        // Create a new RentalAgreement object to hold the updated values
        RentalAgreement updatedRentalAgreement = new RentalAgreement(
                selectedRentalAgreement.getAgreementId(), // Keep the same ID
                selectHostForUpdate(scanner, selectedRentalAgreement), // Update the host
                selectPropertyForUpdate(scanner, selectedRentalAgreement), // Update the property
                selectMainTenantForUpdate(scanner, selectedRentalAgreement), // Update the main tenant
                selectSubTenantsForUpdate(scanner, selectedRentalAgreement), // Update the sub tenants
                updatePeriodType(scanner, selectedRentalAgreement), // Update the period type
                updateContractDate(scanner, selectedRentalAgreement), // Update the contract date
                updateRentalFee(scanner, selectedRentalAgreement), // Update the rental fee
                updateAgreementStatus(scanner, selectedRentalAgreement) // Update the status
        );

        // Use the RentalManager to update the rental agreement
        rentalManager.updateRentalAgreement(selectedRentalAgreement, updatedRentalAgreement);

        // Save the updated rental agreement to the file
        saveAgreementToFile(rentalManager.getAllRentalAgreements());

        // Print updated rental agreement details
        printUpdatedAgreementDetails(updatedRentalAgreement);
    }


    // Method to select a host
    private static Host selectHostForUpdate(Scanner scanner, RentalAgreement selectedRentalAgreement) throws IOException {
        String yellow = "\u001B[33m";  // ANSI color for yellow
        String reset = "\u001B[0m";

        System.out.println("Current Host: " + selectedRentalAgreement.getHost().getId());
        System.out.println("Select the new host from the list below " + yellow + "(or leave blank to keep current):" + reset);

        List<Host> hosts = readHostsFromFile();
        for (int i = 0; i < hosts.size(); i++) {
            System.out.println((i + 1) + ". " + hosts.get(i).getId() + " || " + hosts.get(i).getFullName());
        }

        int hostSelection = -1;
        while (hostSelection < 1 || hostSelection > hosts.size()) {
            System.out.println("Enter the number corresponding to your choice:");

            // Get input from the user
            String hostSelectionInput = scanner.nextLine();

            // Handle blank input (user wants to keep the current host)
            if (hostSelectionInput.isBlank()) {
                return selectedRentalAgreement.getHost(); // No change
            }

            try {
                hostSelection = Integer.parseInt(hostSelectionInput);
                // Check if the input is valid
                if (hostSelection < 1 || hostSelection > hosts.size()) {
                    System.out.println("Invalid selection. Please choose a number between 1 and " + hosts.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return hosts.get(hostSelection - 1); // Return the selected host (adjust for zero-based index)
    }

    // Method to select an available property for the rental agreement
    private static Property selectPropertyForUpdate(Scanner scanner, RentalAgreement selectedRentalAgreement) {
        String yellow = "\u001B[33m";  // ANSI color for yellow
        String reset = "\u001B[0m";
        System.out.println("Current Property: " + selectedRentalAgreement.getProperty().getPropertyId());
        System.out.println("Select the new property from the list below " + yellow + "(or leave blank to keep current):" + reset);

        Properties propertiesManager = new Properties();
        List<Property> availableProperties = propertiesManager.getAvailableProperties();

        if (availableProperties.isEmpty()) {
            throw new IllegalStateException("No available properties.");
        }

        for (int i = 0; i < availableProperties.size(); i++) {
            System.out.println((i + 1) + ". " + availableProperties.get(i).getPropertyId() + " || " + availableProperties.get(i).getAddress());
        }

        int propertySelection = -1;
        while (propertySelection < 1 || propertySelection > availableProperties.size()) {
            System.out.println("Enter the number corresponding to your choice:");

            // Get input from the user
            String propertySelectionInput = scanner.nextLine();

            // Handle blank input (user wants to keep the current property)
            if (propertySelectionInput.isBlank()) {
                return selectedRentalAgreement.getProperty(); // No change
            }

            try {
                propertySelection = Integer.parseInt(propertySelectionInput);
                // Check if the input is valid
                if (propertySelection < 1 || propertySelection > availableProperties.size()) {
                    System.out.println("Invalid selection. Please choose a number between 1 and " + availableProperties.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return availableProperties.get(propertySelection - 1); // Return the selected property (adjust for zero-based index)
    }

    // Method to select the main tenant
    private static Tenant selectMainTenantForUpdate(Scanner scanner, RentalAgreement selectedRentalAgreement) throws IOException {
        String yellow = "\u001B[33m";  // ANSI color for yellow
        String reset = "\u001B[0m";
        System.out.println("Current Main Tenant: " + selectedRentalAgreement.getMainTenant().getId());
        System.out.println("Select the new main tenant from the list below " + yellow + "(or leave blank to keep current):" + reset);

        List<Tenant> tenants = readTenantsFromFile();

        if (tenants.isEmpty()) {
            System.out.println("No tenants available.");
            return selectedRentalAgreement.getMainTenant(); // No change if no tenants are available
        }

        for (int i = 0; i < tenants.size(); i++) {
            System.out.println((i + 1) + ". " + tenants.get(i).getId() + " || " + tenants.get(i).getFullName());
        }

        int mainTenantSelection = -1;
        while (mainTenantSelection < 1 || mainTenantSelection > tenants.size()) {
            System.out.println("Enter the number corresponding to your choice:");

            // Get input from the user
            String mainTenantSelectionInput = scanner.nextLine();

            // Handle blank input (user wants to keep the current main tenant)
            if (mainTenantSelectionInput.isBlank()) {
                return selectedRentalAgreement.getMainTenant(); // No change
            }

            try {
                mainTenantSelection = Integer.parseInt(mainTenantSelectionInput);
                // Check if the input is valid
                if (mainTenantSelection < 1 || mainTenantSelection > tenants.size()) {
                    System.out.println("Invalid selection. Please choose a number between 1 and " + tenants.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return tenants.get(mainTenantSelection - 1); // Return the selected main tenant (adjust for zero-based index)
    }

    private static List<Tenant> selectSubTenantsForUpdate(Scanner scanner, RentalAgreement selectedRentalAgreement) throws IOException {
        String yellow = "\u001B[33m";  // ANSI color for yellow
        String reset = "\u001B[0m";
        // Display current sub-tenants from the agreement
        System.out.println("Current Sub Tenants: " + selectedRentalAgreement.getSubTenants().stream()
                .map(Tenant::getId)
                .collect(Collectors.joining(", ")));

        // Get the main tenant from the rental agreement
        Tenant mainTenant = selectedRentalAgreement.getMainTenant();

        // Read the full tenant list and filter out the main tenant
        List<Tenant> availableSubTenants = readTenantsFromFile().stream()
                .filter(tenant -> !tenant.getId().equals(mainTenant.getId())) // Exclude the main tenant
                .collect(Collectors.toList());

        if (availableSubTenants.isEmpty()) {
            System.out.println("No sub-tenants available.");
            return Collections.emptyList(); // Return an empty list if no sub-tenants are available
        }

        // Display the available sub-tenants
        System.out.println("Select the new sub-tenant(s) from the list below (comma-separated), " + yellow + "(or leave blank to keep current):" + reset);
        for (int i = 0; i < availableSubTenants.size(); i++) {
            System.out.println((i + 1) + ". " + availableSubTenants.get(i).getId() + " || " + availableSubTenants.get(i).getFullName());
        }

        int validSelectionCount = 0;
        List<Tenant> subTenants = new ArrayList<>();
        System.out.println("Enter the number corresponding to your choice:");

        // Get input from the user for sub-tenants (allowing multiple selections)
        String subTenantsInput = scanner.nextLine();

        if (subTenantsInput.isBlank()) {
            return selectedRentalAgreement.getSubTenants(); // No change if input is blank
        }

        String[] tenantIds = subTenantsInput.split(",");
        for (String id : tenantIds) {
            try {
                int subTenantIndex = Integer.parseInt(id.trim()) - 1; // Convert to zero-based index
                if (subTenantIndex >= 0 && subTenantIndex < availableSubTenants.size()) {
                    subTenants.add(availableSubTenants.get(subTenantIndex));
                    validSelectionCount++;
                } else {
                    System.out.println("Invalid tenant ID: " + (subTenantIndex + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for sub-tenants: " + id.trim() + ". Please enter valid tenant IDs.");
            }
        }

        if (validSelectionCount == 0) {
            System.out.println("No valid sub-tenants were selected.");
            return Collections.emptyList(); // Return an empty list if no valid selections were made
        }

        return subTenants; // Return the list of selected sub-tenants
    }

    private static PeriodType updatePeriodType(Scanner scanner, RentalAgreement selectedRentalAgreement) {
        String yellow = "\u001B[33m";  // ANSI color for yellow
        String reset = "\u001B[0m";
        System.out.println("Enter new period type for " + selectedRentalAgreement.getAgreementId());

        PeriodType[] periodTypes = PeriodType.values();

        // Display available period types once
        for (int i = 0; i < periodTypes.length; i++) {
            System.out.println((i + 1) + ": " + periodTypes[i].getPeriod());
        }

        // Handle user input
        while (true) {
            System.out.println("Choose a period type by number" + yellow + " (or leave blank if no change):" + reset);
            String typeInput = scanner.nextLine();

            // Handle blank input to keep current period type
            if (typeInput.isBlank()) {
                return selectedRentalAgreement.getPeriod(); // No change
            }

            try {
                int typeOption = Integer.parseInt(typeInput);

                // Validate range of the period type selection
                if (typeOption < 1 || typeOption > periodTypes.length) {
                    System.out.println("Invalid period type number. Please try again.");
                } else {
                    return periodTypes[typeOption - 1]; // Return selected period type
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static Date updateContractDate(Scanner scanner, RentalAgreement selectedRentalAgreement) {
        String yellow = "\u001B[33m";  // ANSI color for yellow
        String reset = "\u001B[0m";
        System.out.println("Enter a new contract date for " + selectedRentalAgreement.getAgreementId() +
                " (format: Mon Nov 20 15:30:00) " + yellow + "(or leave blank if no change):" + reset);

        String dateInput = scanner.nextLine();

        // Handle blank input to keep current contract date
        if (dateInput.isBlank()) {
            return selectedRentalAgreement.getContractDate(); // No change
        }

        // Try parsing the date input
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss");
        try {
            return formatter.parse(dateInput);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use the correct format (EEE MMM dd HH:mm:ss).");
            return updateContractDate(scanner, selectedRentalAgreement); // Recurse to ask again
        }
    }

    private static double updateRentalFee(Scanner scanner, RentalAgreement selectedRentalAgreement) {
        String yellow = "\u001B[33m";  // ANSI color for yellow
        String reset = "\u001B[0m";
        System.out.println("Enter new rental fee for " + selectedRentalAgreement.getAgreementId() + yellow + " (or leave blank if no change):" + reset);

        String rentalFeeInput = scanner.nextLine();

        // Handle blank input to keep current rental fee
        if (rentalFeeInput.isBlank()) {
            return selectedRentalAgreement.getRentalFee(); // No change
        }

        // Try parsing the rental fee input
        try {
            return Double.parseDouble(rentalFeeInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for the rental fee.");
            return updateRentalFee(scanner, selectedRentalAgreement); // Recurse to ask again
        }
    }

    private static AgreementStatus updateAgreementStatus(Scanner scanner, RentalAgreement selectedRentalAgreement) {
        String yellow = "\u001B[33m";  // ANSI color for yellow
        String reset = "\u001B[0m";
        System.out.println("Enter a new status for " + selectedRentalAgreement.getAgreementId() + ": ");
        AgreementStatus[] agreementStatuses = AgreementStatus.values();

        // Display available statuses once
        for (int i = 0; i < agreementStatuses.length; i++) {
            System.out.println((i + 1) + ": " + agreementStatuses[i].getStatus());
        }

        // Input loop for user to select status
        while (true) {
            System.out.println("Choose an agreement status by number " + yellow + "(or leave blank if no change): " + reset);
            String statusInput = scanner.nextLine();

            // Handle blank input to keep the current status
            if (statusInput.isBlank()) {
                return selectedRentalAgreement.getStatus(); // No change
            }

            try {
                int statusNumber = Integer.parseInt(statusInput);

                // Validate the selected number
                if (statusNumber < 1 || statusNumber > agreementStatuses.length) {
                    System.out.println("Invalid status number. Please try again.");
                } else {
                    // Return the selected status
                    return agreementStatuses[statusNumber - 1];
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }


    private static void printUpdatedAgreementDetails(RentalAgreement updatedAgreement) {
        String cyan = "\u001B[36m";
        String white = "\u001B[37m";
        String reset = "\u001B[0m";
        System.out.println(cyan+"╔════════════════════════════════════════════════════════╗");
        System.out.println("╟          UPDATE RENTAL AGREEMENT SUCCESSFULLY          ║");
        System.out.println("╟ ───────────────────────────────────────────────────────╢");
        System.out.println("          Agreement ID: " + updatedAgreement.getAgreementId());
        System.out.println("          Host: " + updatedAgreement.getHost().getId());
        System.out.println("          Property: " + updatedAgreement.getProperty().getPropertyId());
        System.out.println("          Main Tenant: " + updatedAgreement.getMainTenant().getId());
        System.out.println("          Sub Tenants: " + updatedAgreement.getSubTenantIDs());
        System.out.println("          Period Type: " + updatedAgreement.getPeriod());
        System.out.println("          Contract Date: " + new SimpleDateFormat("EEE MMM dd HH:mm:ss").format(updatedAgreement.getContractDate()));
        System.out.println("            Rental Fee: " + updatedAgreement.getRentalFee());
        System.out.println("            Status: " + updatedAgreement.getStatus());
        System.out.println("╚════════════════════════════════════════════════════════╝"+reset);
        System.out.print("Press any key to return...\n");
        new Scanner(System.in).next();  // Wait for the user to press Enter
    }

    // Delete a rental agreement by ID
    public static void deleteRentalAgreement() throws IOException {
        String ANSI_RED = "\u001B[31m";
        String reset = "\u001B[0m";
        Scanner scanner = new Scanner(System.in);
        List<RentalAgreement> rentalAgreements = rentalManager.getNewRentalAgreements();

        System.out.println(ANSI_RED+"Only can delete rental agreement with NEW status!"+reset);
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

        // Display total agreement's rental fee
        agreementToDelete.displayRentalFee();

        // Display total payments made
        agreementToDelete.displayTotalPayments();

        // Display balance due
        agreementToDelete.displayBalanceDue();

        // Check if the rental fee is fully paid before allowing deletion
        if (agreementToDelete.isRentalFeePaid()) {
            // Show the selected agreement to the user
            System.out.println("The following rental agreement will be deleted:");
            System.out.println(agreementToDelete.getAgreementId());  // Assuming you have overridden the `toString()` method in RentalAgreement

            // Confirm deletion
            System.out.print("Are you sure you want to delete this agreement? (y/n): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("y")) {
                // Use the RentalManager to delete the rental agreement
                rentalManager.deleteRentalAgreement(agreementToDelete.getAgreementId());

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
        } else {
            System.out.println("Cannot delete the agreement until the rental fee is fully paid.");
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