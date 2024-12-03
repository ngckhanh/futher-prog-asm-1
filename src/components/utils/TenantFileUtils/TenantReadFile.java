package utils.TenantFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.*;
import utils.PaymentFileUtils.PaymentReadFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import utils.PropertyFileUtils.PropertyReadFile;
import  utils.RentalAgreementFileUtils.RentalAgreementReadFile;

public class TenantReadFile {
    private static final String FILE_PATH = "src/components/resource/data/tenantData/tenant.txt"; // Path to the tenant file

    private static Map<String, Tenant> tenantMap;

    public static Map<String, Tenant> getTenantMap() throws IOException {
        if (tenantMap == null) {
            tenantMap = readTenantsFirstTime();
            tenantMap = readTenantsSecondTime(tenantMap);
        }
        return tenantMap;
    }

    // This method will read the tenant data file and return a list of Tenant objects
    public static List<Tenant> readTenantsFromFile() throws IOException {
        // Step 1: Read tenant for the first time
        Map<String, Tenant> tenants = readTenantsFirstTime();

        // Step 2: Enrich data by read tenants for the second time
        readTenantsSecondTime(tenants);
        // Convert the map back to a list for return
        return new ArrayList<>(tenants.values());
    }

    // Method to display tenant list
    public static void displayTenants(List<Tenant> tenants) {
        for (Tenant tenant : tenants) {
            System.out.println(tenant);
        }
    }

    private static String extractValue(String line, String prefix, String suffix) {
        int startIndex = line.indexOf(prefix);
        if (startIndex == -1) return null;
        startIndex += prefix.length();

        int endIndex = suffix != null ? line.indexOf(suffix, startIndex) : line.length();
        if (endIndex == -1) return null;

        return line.substring(startIndex, endIndex).trim();
    }

    //tenant.txt: Tenant{ID='t-02', fullName='Brant Rempel', dob='Wed Jun 24 20:18:11 ICT 1981', contactInfo='linwood.lemke@yahoo.com', rentalAgreementList='ra-05, ra-10, ra-01, ra-09, ra-08', rentedPropertyList='p-01, p-07, p-02, p-08, p-09', paymentRecords='pr-07'}
    // Method to read tenant for the first time and return a map of tenants
    public static Map<String, Tenant> readTenantsFirstTime() throws IOException {
        Map<String, Tenant> cachedTenants = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String id = extractValue(line, "ID='", "'");
                    String fullName = extractValue(line, "fullName='", "'");
                    String dobStr = extractValue(line, "dob='", "'");
                    String contactInfo = extractValue(line, "contactInfo='", "'");

                    Date dob = null;
                    if (dobStr != null) {
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                            dob = dateFormat.parse(dobStr);
                        } catch (ParseException e) {
                            e.printStackTrace();  // Optionally log or handle the exception
                        }
                    }

                    Tenant tenant = new Tenant(id, fullName, dob, contactInfo);
                    cachedTenants.put(id, tenant);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cachedTenants;
    }

    // Method to enrich the tenant data based on additional information
    public static Map<String, Tenant> readTenantsSecondTime(Map<String, Tenant> tenantsMap) throws IOException {

        Map<String, RentalAgreement> agreementMap = RentalAgreementReadFile.getAgreementMap();
        Map<String, Property> propertyMap = PropertyReadFile.combinePropertiesToMap();
        Map<String, Payment> paymentMap = PaymentReadFile.getPaymentMap();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {

                String id = extractValue(line, "ID='", "'");
                Tenant tenant = tenantsMap.get(id);
                if (tenant == null) {
                    System.err.println("Tenant not found for ID: " + id);
                    continue;
                }

                String rentalAgreementsIds = extractValue(line, "rentalAgreementList='", "'");  // Extract comma-separated tenant IDs
                if ("None".equals(rentalAgreementsIds)) {
                    rentalAgreementsIds = null;  // If rentalAgreementsIds is 'None', set it to null
                }
                List<RentalAgreement> rentalAgreementList = new ArrayList<>();
                if (rentalAgreementsIds != null) {
                    String[] rentalAgreementIdArray = rentalAgreementsIds.split(",");
                    for (String agreementId : rentalAgreementIdArray) {
                        agreementId = agreementId.trim();
                        RentalAgreement rentalAgreement = agreementMap.get(agreementId);
                        if (rentalAgreement != null) {
                            rentalAgreementList.add(rentalAgreement);
                        } else {
                            //System.err.println("Rental Agreement not found for ID: " + agreementId);
                        }
                    }
                }

                String rentedPropertiesIds = extractValue(line, "rentedPropertyList='", "'");  // Extract comma-separated tenant IDs
                if ("None".equals(rentedPropertiesIds)) {
                    rentedPropertiesIds = null;  // If rentedPropertiesIds is 'None', set it to null
                }
                List<Property> rentedPropertyList = new ArrayList<>();
                if (rentedPropertiesIds != null) {
                    String[] rentedPropertyIdArray = rentedPropertiesIds.split(",");

                    for (String propertyId : rentedPropertyIdArray) {
                        propertyId = propertyId.trim();
                        Property property = propertyMap.get(propertyId);
                        if (property != null) {
                            rentedPropertyList.add(property);
                        } else {
                            System.err.println("Property not found for ID: " + propertyId);
                        }
                    }
                }

                String paymentRecordsIds = extractValue(line, "paymentRecords='", "'");  // Extract comma-separated tenant IDs
                if ("None".equals(paymentRecordsIds)) {
                    paymentRecordsIds = null;  // If paymentRecords is 'None', set it to null
                }
                List<Payment> paymentRecords = new ArrayList<>();
                if (paymentRecordsIds != null) {
                    String[] rentalAgreementIdArray = paymentRecordsIds.split(",");
                    for (String paymentId : rentalAgreementIdArray) {
                        paymentId = paymentId.trim();
                        Payment payment = paymentMap.get(paymentId);
                        if (payment != null) {
                            paymentRecords.add(payment);
                        } else {
                            System.err.println("Payment not found for ID: " + paymentId);
                        }
                    }
                }

                tenant.setRentalAgreements(rentalAgreementList);
                tenant.setRentedProperties(rentedPropertyList);
                tenant.setPaymentRecords(paymentRecords);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tenantsMap;
    }

}
