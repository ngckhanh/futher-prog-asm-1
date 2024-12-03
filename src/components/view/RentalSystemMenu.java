package view;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import controllers.operation.FileUtility;
import models.interfaces.RentalManager;
import models.managers.Persons;
import models.managers.Properties;
import services.RentalManagerImpl;

import java.io.IOException;
import java.util.*;

import static controllers.RentalAgreementController.*;

import static view.ReportMenu.reportMenu;

public class RentalSystemMenu {
    private static RentalManager rentalManager;
    private static Persons persons;
    private static Properties properties;

    static {
        rentalManager = new RentalManagerImpl();
        persons = new Persons();
        properties = new Properties();
    }

    public static void displayBanner(){
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";
        String blue = "\u001B[34m";
        String purple = "\u001B[35m";
        String cyan = "\u001B[36m";
        String white = "\u001B[37m";
        String reset = "\u001B[0m";

        String[] colors = {red, green, yellow, blue, purple, cyan, white};

        String bannerLine = "===============================================\n";

        for (int i = 0; i < bannerLine.length(); i++) {
            int colorIndex = i % colors.length;
            System.out.print(colors[colorIndex] + bannerLine.charAt(i));
        }

        //                System.out.println(green + "===============================================");
        System.out.println(cyan + " #####   " + purple + " #     #  " + yellow + "  #####   " + reset);
        System.out.println(cyan + "#     #  " + purple + " ##   ##  " + yellow + " #     #  " + reset);
        System.out.println(cyan + "#     #  " + purple + " # # # #  " + yellow + " #        " + reset);
        System.out.println(cyan + " #####   " + purple + " #  #  #  " + yellow + "  #####   " + reset);
        System.out.println(cyan + "#   #    " + purple + " #     #  " + yellow + "       #  " + reset);
        System.out.println(cyan + "#    #   " + purple + " #     #  " + yellow + " #     #  " + reset);
        System.out.println(cyan + "#     #  " + purple + " #     #  " + yellow + "  #####   " + reset);

        String bannerLine1 = "===============================================\n";

        for (int i = 0; i < bannerLine1.length(); i++) {
            int colorIndex = i % colors.length;
            System.out.print(colors[colorIndex] + bannerLine1.charAt(i));
        }

        System.out.println("          TON NU NGOC KHANH | S3932105"+reset);
        String bannerLine3 = "===============================================\n";

        for (int i = 0; i < bannerLine3.length(); i++) {
            int colorIndex = i % colors.length;
            System.out.print(colors[colorIndex] + bannerLine3.charAt(i));
        }
        System.out.println(yellow + "           COSC2440 CONSOLE APP  ");
        System.out.println(yellow + "     RENTAL MANAGEMENT SYSTEM 2024");
        System.out.println(yellow + "     Instructor: Mr. Minh Vu & Mr. Bao Ho  ");
        String bannerLine4 = "===============================================\n";

        for (int i = 0; i < bannerLine4.length(); i++) {
            int colorIndex = i % colors.length;
            System.out.print(colors[colorIndex] + bannerLine4.charAt(i));
        }
        System.out.println(reset);
        System.out.println("Press any key to Start!..");
        Scanner scanner = new Scanner(System.in);
        scanner.next();

    }

    public static void rentalSystemMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Displaying the menu in a separate method for clarity
        printRentalSystemMenu();

        while (true) {
            System.out.print("  Choose an option: ");

            try {
                int choice = scanner.nextInt();  // Get user input

                switch (choice) {
                    case 1:
                        createRentalAgreement();
                        break;
                    case 2:
                        updateRentalAgreement();
                        break;
                    case 3:
                        deleteRentalAgreement();
                        break;
                    case 4:
                        viewAgreementMenu();
                        break;
                    case 5:
                        reportMenu();
                        break;
                    case 6:
                        resetFileMenu();
                        System.out.println("File reset successfully. Let's start now!");
                        break;
                    case 0:
                        System.out.println("Exiting the system... Goodbye!");
                        System.exit(0);  // Exit the system
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();  // Clear the buffer to avoid infinite loop on invalid input
            }

            // Display the menu again after an action is completed
            printRentalSystemMenu();
        }
    }

    public static void printRentalSystemMenu() {
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";
        String blue = "\u001B[34m";
        String purple = "\u001B[35m";
        String cyan = "\u001B[36m";
        String white = "\u001B[37m";
        String reset = "\u001B[0m";

        System.out.println(purple+ "╔══════════════════════════════════════════╗");
        System.out.println("║            RENTAL SYSTEM MENU            ║");
        System.out.println("╟──────────────────────────────────────────╢");
        System.out.println("║  [1] Create Rental Agreement             ║");
        System.out.println("║  [2] Update Rental Agreement             ║");
        System.out.println("║  [3] Delete Rental Agreement             ║");
        System.out.println("║  [4] View Rental Agreements              ║");
        System.out.println("║  [5] Report Menu                         ║");
        System.out.println("║  [6] Reset and Add Default Data          ║");
        System.out.println("║  [0] Exit                                ║");
        System.out.println("╚══════════════════════════════════════════╝"+ reset);
    }

    public static void viewAgreementMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String yellow = "\u001B[33m";  // ANSI color for yellow
            System.out.println("\n╔══════════════════════════════════════════╗");
            System.out.println("║           VIEW AGREEMENT MENU            ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] View All Rental Agreements          ║");
            System.out.println("║  [2] View Agreements by Owner Name       ║");
            System.out.println("║  [3] View Agreements by Property Address ║");
            System.out.println("║  [4] View Agreements by Status           ║");
            System.out.println("║  [0] Back to Main Menu                   ║");
            System.out.println("╚══════════════════════════════════════════╝");

            System.out.print("  Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();  // Clear the newline character from the buffer

                switch (choice) {
                    case 1:
                        // Display all rental agreements
                        System.out.println("All Rental Agreements:");
                        displayAgreements();
                        break;
                    case 2:
                        // Display list of owners
                        List<String> ownerNames = persons.getOwnerNames();
                        if (ownerNames.isEmpty()) {
                            System.out.println("No owners found.");
                        } else {
                            System.out.println("Select an owner by number:");
                            for (int i = 0; i < ownerNames.size(); i++) {
                                System.out.println("[" + (i + 1) + "] " + ownerNames.get(i));
                            }
                            System.out.print("Enter the number of the owner: ");
                            int ownerChoice = scanner.nextInt();
                            scanner.nextLine();  // Clear the newline character from the buffer

                            if (ownerChoice > 0 && ownerChoice <= ownerNames.size()) {
                                String selectedOwnerName = ownerNames.get(ownerChoice - 1);
                                showAgreementsForOwner(selectedOwnerName);
                            } else {
                                System.out.println("Invalid selection. Please try again.");
                            }
                        }
                        break;
                    case 3:
                        // Display list of property addresses
                        List<String> propertyAddresses = properties.getPropertyAddresses();
                        if (propertyAddresses.isEmpty()) {
                            System.out.println("No properties found.");
                        } else {
                            System.out.println("Select a property by number:");
                            for (int i = 0; i < propertyAddresses.size(); i++) {
                                System.out.println("[" + (i + 1) + "] " + propertyAddresses.get(i));
                            }
                            System.out.print("Enter the number of the property: ");
                            int propertyChoice = scanner.nextInt();
                            scanner.nextLine();  // Clear the newline character from the buffer

                            if (propertyChoice > 0 && propertyChoice <= propertyAddresses.size()) {
                                String selectedPropertyAddress = propertyAddresses.get(propertyChoice - 1);
                                showAgreementsForProperty(selectedPropertyAddress);
                            } else {
                                System.out.println("Invalid selection. Please try again.");
                            }
                        }
                        break;
                    case 4:
                        String status = "";
                        boolean validStatus = false;

                        while (!validStatus) {
                            System.out.print("Enter status (New/Active/Completed): ");
                            status = scanner.nextLine().trim();

                            // Check if the input is valid
                            if (status.equalsIgnoreCase("New") || status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Completed")) {
                                validStatus = true; // Valid input, exit the loop
                            } else {
                                System.out.println("Invalid status. Please enter either 'New', 'Active', or 'Completed'.");
                            }
                        }

                        // Proceed with the valid status
                        showAgreementsByStatus(status);
                        break;

                    case 0:
                        rentalSystemMenu();  // Assuming this method returns to the main menu
                        return;  // Exit the loop
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();  // Clear buffer
            } catch (IOException e) {
                System.out.println("An error occurred while processing your request.");
            }
        }
    }

    public static void resetFileMenu() {
        FileUtility utility = new FileUtility();
        utility.writeAllData();
    }


}