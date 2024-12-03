package view;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.ReportController;

import static view.RentalSystemMenu.rentalSystemMenu;

public class ReportMenu {

    public static void reportMenu(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String yellow = "\u001B[33m";  // ANSI color for yellow
            String reset = "\u001B[0m";

            System.out.println(yellow+"\n╔════════════════════════════════════════════════════╗");
            System.out.println("║             🌟 REPORT FUNCTION MENU 🌟             ║");
            System.out.println("╟────────────────────────────────────────────────────╢");
            System.out.println("║  Here you can view, sort, and save various reports!║");
            System.out.println("║  Please choose a report type from the list below:  ║");
            System.out.println("║                                                    ║");
            System.out.println("║  [1] Tenants Report                                ║");
            System.out.println("║  [2] Hosts Report                                  ║");
            System.out.println("║  [3] Owners Report                                 ║");
            System.out.println("║  [4] Payments Report                               ║");
            System.out.println("║  [5] Residential Properties Report                 ║");
            System.out.println("║  [6] Commercial Properties Report                  ║");
            System.out.println("║  [7] Rental Agreements Report                      ║");
            System.out.println("║                                                    ║");
            System.out.println("║  [0] Back to Main Menu                             ║");
            System.out.println("╚════════════════════════════════════════════════════╝"+ reset);

            System.out.print("  Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        ReportController.handleTenantReport();
                        break;
                    case 2:
                        ReportController.handleHostReport();
                        break;
                    case 3:
                        ReportController.handleOwnerReport();
                        break;
                    case 4:
                        ReportController.handlePaymentReport();
                        break;
                    case 5:
                        ReportController.handleResidentialPropertyReport();
                        break;
                    case 6:
                        ReportController.handleCommercialPropertyReport();
                        break;
                    case 7:
                        ReportController.handleRentalAgreementReport();
                        break;
                    case 0:
                        rentalSystemMenu();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException | IOException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();  // Clear buffer
            }
        }
    }

}
