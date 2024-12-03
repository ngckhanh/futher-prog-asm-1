package utils.TenantFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.*;
import models.entities.Properties;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
//import utils.RentalAgreementFileUtils.RentalAgreementReadFile.getRentalAgreementByID;

public class TenantReadFile {
    public static Properties properties;
    public static Persons persons;
    public static RentalAgreements agreements;
    public static Payments payments;


    static {
        properties = new Properties();
        persons = new Persons();
        agreements = new RentalAgreements();
        payments = new Payments();
    }

    private static final String FILE_PATH = "src/components/resource/data/tenantData/tenant.txt"; // Path to the tenant file

    // Get tenant by ID
    public static Tenant getTenantByID(String id) throws IOException {
        return null; // Return null if no tenant with the given ID is found
    }

    // This method will read the tenant data file and return a list of Tenant objects
    public static List<Tenant> readTenantsFromFile(String fileName) throws IOException {
        List<Tenant> tenants = new ArrayList<>();
        // Create a BufferedReader to read the file line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Map<String, Object> data = parseTenantLine(line);
                System.out.println("Parsed Tenant Data: " + data);
                Tenant tenant = linkDataToObjects(data);
                if (tenant != null) { // Only add the tenant if it was created successfully
                    tenants.add(tenant);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tenants;
    }

    // Method to display tenant list
    public static void displayTenants(List<Tenant> tenants) {
        for (Tenant tenant : tenants) {
            System.out.println(tenant);
        }
    }

    //tenant.txt: Tenant{ID='t-02', fullName='Brant Rempel', dob='Wed Jun 24 20:18:11 ICT 1981', contactInfo='linwood.lemke@yahoo.com', rentalAgreementList='ra-05, ra-10, ra-01, ra-09, ra-08', rentedPropertyList='p-01, p-07, p-02, p-08, p-09', paymentRecords='pr-07'}
    public static Map<String, Object> parseTenantLine(String line) {
        Map<String, Object> data = new HashMap<>();

        data.put("tenantId", extractValue(line, "ID='", "'"));
        data.put("fullName", extractValue(line, "fullName='", "'"));

        String dobString = extractValue(line, "dob='", "'");
        Date dob = null;
        if (dobString != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                dob = dateFormat.parse(dobString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        data.put("dob", dob);
        data.put("contactInfo", extractValue(line, "contactInfo='", "'"));

        List<String> rentalAgreementsIds = new ArrayList<>();
        String agreementsList = extractValue(line, "rentalAgreementList='", "'");
        if (agreementsList != null && !agreementsList.equalsIgnoreCase("None")) {
            String[] agreementIds = agreementsList.split(",\\s*");
            for (String agreementId : agreementIds) {
                rentalAgreementsIds.add(agreementId.trim());
            }
        }
        data.put("rentalAgreementsIds", rentalAgreementsIds);



        List<String> rentedPropertiesIds = new ArrayList<>();
        String propertiesList = extractValue(line, "rentedPropertyList='", "'");
        if (propertiesList != null && !propertiesList.equalsIgnoreCase("None")) {
            String[] propertyIds = propertiesList.split(",\\s*");
            for (String propertyId : propertyIds) {
                rentedPropertiesIds.add(propertyId.trim());
            }
        }
        data.put("rentedPropertiesIds", rentedPropertiesIds);

        List<String> paymentRecordIds = new ArrayList<>();
        String paymentsList = extractValue(line, "paymentRecords='", "'");
        if (paymentsList != null && !paymentsList.equalsIgnoreCase("None")) {
            String[] paymentIds = paymentsList.split(",\\s*");
            for (String paymentId : paymentIds) {
                paymentRecordIds.add(paymentId.trim());
            }
        }
        data.put("paymentRecordIds", paymentRecordIds);

        return data;
    }

    public static Tenant linkDataToObjects(Map<String, Object> data) {

        List<String> rentalAgreementsIds = (List<String>) data.get("rentalAgreementsIds");
        List<String> rentedPropertiesIds = (List<String>) data.get("rentedPropertiesIds");
        List<String> paymentRecordIds = (List<String>) data.get("paymentRecordIds");


        List<RentalAgreement> rentalAgreementList = new ArrayList<>();
        if (rentalAgreementsIds != null && !rentalAgreementsIds.isEmpty()) {
            for (String id : rentalAgreementsIds) {
                RentalAgreement agreement = agreements.getAgreementById(id);
                //System.out.println("Fetching Rental Agreement for ID: " + id);
                if (agreement != null) {
                    rentalAgreementList.add(agreement);
                    //System.out.println("Added Rental Agreement: " + agreement);
                } else {
                    System.out.println("Rental Agreement not found for ID: " + id);
                }
            }
        } else {
            System.out.println("No rental agreements found.");
        }

        // Initialize rented property list
        List<Property> rentedPropertyList = new ArrayList<>();
        if (rentedPropertiesIds != null) {
            for (String id : rentedPropertiesIds) {
                Property property = properties.getPropertyById(id);
                if (property != null) {
                    rentedPropertyList.add(property);
                } else {
                    System.out.println("Rented property not found for ID: " + id);
                }
            }
        } else {
            System.out.println("No rented property list is found");
        }

        // Initialize payment record list
        List<Payment> paymentRecords = new ArrayList<>();
        if (paymentRecordIds != null) {
            for (String id : paymentRecordIds) {
                Payment payment = payments.getPaymentById(id);
                if (payment != null) {
                    paymentRecords.add(payment);
                } else {
                    System.out.println("Payment Records not found for ID: " + id);
                }
            }
        } else {
            System.out.println("Payment Records list not found");
        }

        return new Tenant(
                (String) data.get("ownerId"),
                (String) data.get("fullName"),
                (Date) data.get("dob"),
                (String) data.get("contactInfo"),
                rentalAgreementList,
                rentedPropertyList,
                paymentRecords
        );
    }


    private static List<String> extractList(String line, String prefix, String suffix) {
        List<String> list = new ArrayList<>();
        String listStr = extractValue(line, prefix, suffix);
        if (listStr != null && !listStr.isEmpty()) {
            String[] items = listStr.split(",\\s*");
            Collections.addAll(list, items);
        }
        return list;
    }

    private static String extractValue(String line, String prefix, String suffix) {
        int startIndex = line.indexOf(prefix);
        if (startIndex == -1) return null;
        startIndex += prefix.length();

        int endIndex = suffix != null ? line.indexOf(suffix, startIndex) : line.length();
        if (endIndex == -1) return null;

        return line.substring(startIndex, endIndex).trim();
    }

}
