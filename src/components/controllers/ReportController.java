package controllers;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.*;
import models.managers.Persons;
import models.managers.Properties;
import utils.*;
import utils.CommercialPropertyFileUtils.CommercialPropertyReadFile;
import utils.HostFileUtils.HostReadFile;
import utils.OwnerFileUtils.OwnerReadFile;
import utils.PaymentFileUtils.PaymentReadFile;
import utils.RentalAgreementFileUtils.RentalAgreementReadFile;
import utils.ReportFileUtils.ReportWriteFile;
import utils.ResidentialPropertyFileUtils.ResidentialPropertyReadFile;
import utils.TenantFileUtils.TenantReadFile;

import java.io.IOException;
import java.util.*;
import static view.ReportMenu.reportMenu;

public class ReportController {
    private static final Scanner scanner = new Scanner(System.in);

    @FunctionalInterface
    public interface EntityDisplay<T> {
        // Abstract method to display a list of entities
        void display(List<T> entities);
    }

    @FunctionalInterface
    public interface EntitySave<T> {
        // Abstract method to save a list of entities
        void save(List<T> entities) throws IOException;
    }

    // Generic method to handle report generation
    private static <T> void handleReport(String entityType, List<T> entities, EntityDisplay<T> displayMethod, EntitySave<T> saveMethod, List<SortOption<T>> sortOptions) throws IOException {
        if (entities == null || entities.isEmpty()) {
            System.out.printf("No %s found. Please check the data file.%n", entityType);
            return;
        }

        displayMethod.display(entities);

        // Ask to sort and proceed based on the response
        boolean sort = askToSort(entityType);
        if (sort) {
            sortEntities(entities, sortOptions);
            displayMethod.display(entities);
        } else {
            // If the user does not want to sort, skip to the report menu
            reportMenu();
            return; // End the method early
        }

        // Ask to save the report if sorting was done
        if (askToSave(entityType)) {
            try {
                saveMethod.save(entities);
                System.out.printf("%s report saved successfully.%n", entityType);
            } catch (IOException e) {
                System.err.printf("Failed to save %s report: %s%n", entityType, e.getMessage());
            }
        } else {
            reportMenu();
        }
    }
    private static boolean askToSort(String entityType) {
        String response;
        while (true) {
            System.out.printf("Would you like to sort the %s? (yes/no)%n", entityType);
            response = scanner.nextLine().trim().toLowerCase(); // Normalize input

            if (response.equals("yes")) {
                return true;
            } else if (response.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }

    private static <T> void sortEntities(List<T> entities, List<SortOption<T>> sortOptions) {
        System.out.println("How would you like to sort?");

        // Display sorting options
        for (int i = 0; i < sortOptions.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, sortOptions.get(i).getDescription());
        }

        int sortChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Sort in ascending or descending order? (asc/desc)");
        boolean ascending = scanner.nextLine().equalsIgnoreCase("asc");

        Comparator<T> comparator = sortOptions.get(sortChoice - 1).getComparator();
        entities.sort(ascending ? comparator : comparator.reversed());
    }

    private static boolean askToSave(String entityType) {
        System.out.printf("Would you like to save the %s report to a file? (yes/no)%n", entityType);
        return scanner.nextLine().equalsIgnoreCase("yes");
    }

    // Specific methods for handling different reports
    public static void handleTenantReport() throws IOException {
        Persons persons = new Persons();
        List<Tenant> tenants = persons.getTenants();
        List<SortOption<Tenant>> tenantSortOptions = getTenantSortOptions();
        handleReport("tenants", tenants, TenantReadFile::displayTenants, ReportWriteFile::saveTenantReportToFile, tenantSortOptions);
    }

    public static void handleHostReport() throws IOException {
        Persons persons = new Persons();
        List<Host> hosts = persons.getHosts();
        List<SortOption<Host>> hostSortOptions = getHostSortOptions();
        handleReport("hosts", hosts, HostReadFile::displayHosts, ReportWriteFile::saveHostReportToFile, hostSortOptions);
    }

    public static void handleOwnerReport() throws IOException {
        Persons persons = new Persons();
        List<Owner> owners = persons.getOwners();
        List<SortOption<Owner>> ownerSortOptions = getOwnerSortOptions();
        handleReport("owners", owners, OwnerReadFile::displayOwners, ReportWriteFile::saveOwnerReportToFile, ownerSortOptions);
    }

    public static void handlePaymentReport() throws IOException {
        List<Payment> payments = PaymentReadFile.readPaymentsFromFile();
        List<SortOption<Payment>> paymentSortOptions = getPaymentSortOptions();
        handleReport("payments", payments, PaymentReadFile::displayPayments, ReportWriteFile::savePaymentReportToFile, paymentSortOptions);
    }

    public static void handleResidentialPropertyReport() throws IOException {
        Properties properties = new Properties();
        List<ResidentialProperty> residentialProperties = properties.getResidentialProperties(); // Call static method
        List<SortOption<ResidentialProperty>> residentialPropertySortOptions = getResidentialPropertySortOptions();
        handleReport("residential properties", residentialProperties, ResidentialPropertyReadFile::displayResidentialProperties, ReportWriteFile::saveResidentialPropertiesReportToFile, residentialPropertySortOptions);
    }

    public static void handleCommercialPropertyReport() throws IOException {
        Properties properties = new Properties();
        List<CommercialProperty> commercialProperties = properties.getCommercialProperties();
        List<SortOption<CommercialProperty>> commercialPropertySortOptions = getCommercialPropertySortOptions();
        handleReport("commercial properties", commercialProperties, CommercialPropertyReadFile::displayCommercialProperties, ReportWriteFile::saveCommercialPropertiesReportToFile, commercialPropertySortOptions);
    }

    public static void handleRentalAgreementReport() throws IOException {
        List<RentalAgreement> rentalAgreements = RentalAgreementReadFile.readRentalAgreementsFromFile();
        List<SortOption<RentalAgreement>> rentalAgreementSortOptions = getRentalAgreementSortOptions();
        handleReport("rental agreements", rentalAgreements, RentalAgreementReadFile::displayRentalAgreements, ReportWriteFile::saveRentalAgreementReportToFile, rentalAgreementSortOptions);
    }

    // Sort options methods
    private static List<SortOption<Tenant>> getTenantSortOptions() {
        return Arrays.asList(
                new SortOption<>("Sort by ID", Comparator.comparing(Tenant::getId)),
                new SortOption<>("Sort by Full Name", Comparator.comparing(Tenant::getFullName)),
                new SortOption<>("Sort by Date of Birth", Comparator.comparing(Tenant::getDob)),
                new SortOption<>("Sort by Contact Info", Comparator.comparing(Tenant::getContactInfo)),
                new SortOption<>("Sort by Number of Rental Agreements", Comparator.comparingInt(tenant -> tenant.getRentalAgreementList().size())),
                new SortOption<>("Sort by Number of Rented Properties", Comparator.comparingInt(tenant -> tenant.getRentedPropertyList().size())),
                new SortOption<>("Sort by Number of Payment Records", Comparator.comparingInt(tenant -> tenant.getPaymentRecords().size()))
        );
    }

    private static List<SortOption<Host>> getHostSortOptions() {
        return Arrays.asList(
                new SortOption<>("Sort by ID", Comparator.comparing(Host::getId)),
                new SortOption<>("Sort by Full Name", Comparator.comparing(Host::getFullName)),
                new SortOption<>("Sort by Date of Birth", Comparator.comparing(Host::getDob)),
                new SortOption<>("Sort by Contact Info", Comparator.comparing(Host::getContactInfo)),
                new SortOption<>("Sort by Number of Cooperating Owners", Comparator.comparingInt(host -> host.getCooperatingOwnerList().size())),
                new SortOption<>("Sort by Number of Managed Properties", Comparator.comparingInt(host -> host.getManagedPropertyList().size())),
                new SortOption<>("Sort by Number of Rental Agreements", Comparator.comparingInt(host -> host.getRentalAgreementList().size()))
        );
    }

    private static List<SortOption<Owner>> getOwnerSortOptions() {
        return Arrays.asList(
                new SortOption<>("Sort by ID", Comparator.comparing(Owner::getId)),
                new SortOption<>("Sort by Full Name", Comparator.comparing(Owner::getFullName)),
                new SortOption<>("Sort by Date of Birth", Comparator.comparing(Owner::getDob)),
                new SortOption<>("Sort by Contact Info", Comparator.comparing(Owner::getContactInfo)),
                new SortOption<>("Sort by Number of Owned Properties", Comparator.comparingInt(owner -> owner.getOwnedPropertyList().size())),
                new SortOption<>("Sort by Number of Hosts", Comparator.comparingInt(owner -> owner.getHostList().size()))
        );
    }

    private static List<SortOption<Payment>> getPaymentSortOptions() {
        return Arrays.asList(
                new SortOption<>("Sort by Payment ID", Comparator.comparing(Payment::getPaymentId)),
                new SortOption<>("Sort by Amount", Comparator.comparing(Payment::getAmount)),
                new SortOption<>("Sort by Payment Date", Comparator.comparing(Payment::getPaymentDate)),
                new SortOption<>("Sort by Payment Method", Comparator.comparing(Payment::getPaymentMethod))
        );
    }

    private static List<SortOption<ResidentialProperty>> getResidentialPropertySortOptions() {
        return Arrays.asList(
                new SortOption<>("Sort by Property ID", Comparator.comparing(ResidentialProperty::getPropertyId)),
                new SortOption<>("Sort by Address", Comparator.comparing(ResidentialProperty::getAddress)),
                new SortOption<>("Sort by Pricing", Comparator.comparing(ResidentialProperty::getPricing)),
                new SortOption<>("Sort by Status", Comparator.comparing(ResidentialProperty::getStatus)),
                new SortOption<>("Sort by Number of Bedrooms", Comparator.comparingInt(ResidentialProperty::getNumbBedrooms))
        );
    }

    private static List<SortOption<CommercialProperty>> getCommercialPropertySortOptions() {
        return Arrays.asList(
                new SortOption<>("Sort by Property ID", Comparator.comparing(CommercialProperty::getPropertyId)),
                new SortOption<>("Sort by Address", Comparator.comparing(CommercialProperty::getAddress)),
                new SortOption<>("Sort by Pricing", Comparator.comparing(CommercialProperty::getPricing)),
                new SortOption<>("Sort by Status", Comparator.comparing(CommercialProperty::getStatus)),
                new SortOption<>("Sort by Business Type", Comparator.comparing(CommercialProperty::getBusinessType)),
                new SortOption<>("Sort by Parking Space", Comparator.comparing(CommercialProperty::getParkingSpace)),
                new SortOption<>("Sort by Square Footage", Comparator.comparing(CommercialProperty::getSquareFootage))
        );
    }

    private static List<SortOption<RentalAgreement>> getRentalAgreementSortOptions() {
        return Arrays.asList(
                new SortOption<>("Sort by Agreement ID", Comparator.comparing(RentalAgreement::getAgreementId)),
                new SortOption<>("Sort by Rental Fee", Comparator.comparing(RentalAgreement::getRentalFee)),
                new SortOption<>("Sort by Contract Date", Comparator.comparing(RentalAgreement::getContractDate))
        );
    }
}