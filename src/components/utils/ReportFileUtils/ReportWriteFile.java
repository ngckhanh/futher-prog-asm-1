package utils.ReportFileUtils;

import models.entities.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ReportWriteFile {
    // Define ANSI color codes at the top of your class
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_RESET = "\u001B[0m";
    // Generic method to save a report to a file
    private static <T> void saveReportToFile(List<T> items, String reportType) throws IOException {
        // Get the current timestamp for the filename
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Construct the filename without the random number
        String fileName = String.format("src/components/resource/data/reportData/%s/report_%s.txt",
                reportType, timestamp);

        // Ensure the directory exists
        File directory = new File(String.format("src/components/resource/data/reportData/%s", reportType));
        if (!directory.exists()) {
            directory.mkdirs();
        }

        System.out.println("Saving " + reportType + " report to file: " + fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            for (T item : items) {
                writer.write(item.toString() + "\n"); // Write each item's details
            }

            // Success message
            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "      " +   reportType.toUpperCase() + "SAVED SUCCESSFULLY        ║");
            System.out.println("╟────────────────────────────────────────────────────────╢" + ANSI_RESET);
            System.out.println("       File Name: " + fileName);
            System.out.println("       Total Items: " + items.size());
        } catch (IOException e) {
            System.err.println("An error occurred while saving the " + reportType + " report: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for better debugging
            throw e;
        }
    }
    public static void saveTenantReportToFile(List<Tenant> tenants) throws IOException {
        saveReportToFile(tenants, "tenantReport");
    }

    public static void saveHostReportToFile(List<Host> hosts) throws IOException {
        saveReportToFile(hosts, "hostReport");
    }

    public static void saveOwnerReportToFile(List<Owner> owners) throws IOException {
        saveReportToFile(owners, "ownerReport");
    }

    public static void savePaymentReportToFile(List<Payment> payments) throws IOException {
        saveReportToFile(payments, "paymentReport");
    }

    public static void saveRentalAgreementReportToFile(List<RentalAgreement> rentalAgreements) throws IOException {
        saveReportToFile(rentalAgreements, "rentalAgreementReport");
    }

    public static void savePropertyReportToFile(List<Property> properties) throws IOException {
        saveReportToFile(properties, "propertyReport");
    }

    public static void saveResidentialPropertiesReportToFile(List<ResidentialProperty> residentialProperties) throws IOException {
        saveReportToFile(residentialProperties, "residentialReport");
    }

    public static void saveCommercialPropertiesReportToFile(List<CommercialProperty> commercialProperties) throws IOException {
        saveReportToFile(commercialProperties, "commercialPropertyReport");
    }
}