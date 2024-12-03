package utils.RentalAgreementFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.entities.*;
import models.entities.Properties;
import models.enums.AgreementStatus;
import models.enums.PeriodType;
import models.enums.PropertyStatus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RentalAgreementReadFile {
    public static Persons persons;
    public static Properties properties;
    public static RentalAgreements agreements;


    static {
        persons = new Persons();
        properties = new Properties();
        agreements = new RentalAgreements();
    }

    private static final String FILE_PATH = "src/components/resource/data/rentalAgreementData/rental_agreement.txt";

    private static String extractValue(String line, String prefix, String suffix) {
        int startIndex = line.indexOf(prefix);
        if (startIndex == -1) return null;
        startIndex += prefix.length();

        int endIndex = suffix != null ? line.indexOf(suffix, startIndex) : line.length();
        if (endIndex == -1) return null;

        return line.substring(startIndex, endIndex).trim();
    }

//     Reads all rental agreements from a specified file
    public static List<RentalAgreement> readRentalAgreementsFromFile(String fileName) throws IOException {
        List<RentalAgreement> rentalAgreements = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Map<String, Object> data = parseRentalAgreementLine(line);
                System.out.println(data);
                RentalAgreement rentalAgreement = linkDataToObjects(data);
                rentalAgreements.add(rentalAgreement);
            }
        }
        return rentalAgreements;
    }


    //rental_agreement.txt: Rental Agreement{agreementId='a-03', hostId='h-03', propertyId='p-06', mainTenant='t-07', subTenant='None', periodType='Fortnightly', contractDate='Mon Jun 19 06:07:59 ICT 1989', rentalFee='$2479.20', agreementStatus='Completed'}
    public static Map<String, Object> parseRentalAgreementLine(String line) throws IOException {
        Map<String, Object> data = new HashMap<>();

        // Extract the agreementId
        data.put("agreementId", extractValue(line, "agreementID='", "'"));

        // Extract the hostId
        data.put("hostId", extractValue(line, "hostID='", "'"));

        // Extract the propertyId
        data.put("propertyId", extractValue(line, "propertyID='", "'"));

        // Extract the main tenant ID
        data.put("mainTenantId", extractValue(line, "mainTenantID='", "'"));

        // Extract the subTenantIDs
        List<String> subTenantIds = new ArrayList<>();
        String subTenantList = extractValue(line, "subTenantIDs='", "'");
        if (subTenantList != null && !subTenantList.equalsIgnoreCase("None")) {
            String[] subTenantIDs = subTenantList.split(",\\s*");
            for (String subTenantID : subTenantIDs) {
                subTenantIds.add(subTenantID.trim());
            }
        }
        data.put("subTenantIds", subTenantIds);

        // Extract the periodType enum
        String periodTypeStr = extractValue(line, "periodType='", "'");
        data.put("periodType", PeriodType.valueOf(periodTypeStr.toUpperCase()));

        // Parse the contractDate
        String dobString = extractValue(line, "contractDate='", "'");
        Date contractDate = null;
        if (dobString != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                contractDate = dateFormat.parse(dobString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        data.put("contractDate", contractDate);

        // Extract the rentalFee
        String rentalFeeStr = extractValue(line, "rentalFee='", "'");
        data.put("rentalFee", rentalFeeStr != null ? Double.parseDouble(rentalFeeStr.replace("$", "").trim()) : 0.0);

        // Extract the agreementStatus enum
        String agreementStatusStr = extractValue(line, "agreementStatus='", "'");
        data.put("agreementStatus", AgreementStatus.valueOf(agreementStatusStr.toUpperCase()));

        return data;
    }

    public static RentalAgreement linkDataToObjects(Map<String, Object> data) {
        String agreementId = (String) data.get("agreementId");
//        RentalAgreement agreement = agreements.getAgreementById(agreementId);
//        if (agreement == null) {
//            System.out.println("Agreement not found for ID: " + agreementId);
//            return null;
//        }

        String hostId = (String) data.get("hostId");
        System.out.println("Host ID: " + hostId);
        Host host = persons.getHostById(hostId);
        System.out.println("Host: " + host);
        if (host == null) {
            System.out.println("Host not found for ID: " + hostId);
            return null;
        }

        String propertyId = (String) data.get("propertyId");
        Property property = properties.getPropertyById(propertyId);
        if (property == null) {
            System.out.println("Property not found for ID: " + propertyId);
            return null;
        }

        String tenantId = (String) data.get("mainTenantId");
        Tenant mainTenant = persons.getTenantById(tenantId);
        if (mainTenant == null) {
            System.out.println("Main Tenant not found for ID: " + tenantId);
            return null;
        }

        List<String> subTenantIds = (List<String>) data.get("subTenantIds");
        List<Tenant> subTenants = new ArrayList<>();
        for (String subTenantId : subTenantIds) {
            Tenant subTenant = persons.getTenantById(subTenantId);
            if (subTenant != null) {
                subTenants.add(subTenant);
            } else {
                System.out.println("Sub Tenant not found for ID: " + subTenantId);
            }
        }

        return new RentalAgreement(
                (String) data.get("agreementId"),
                host,
                property,
                mainTenant,
                subTenants,
                (PeriodType) data.get("periodType"),
                (Date) data.get("contractDate"),
                (Double) data.get("rentalFee"),
                (AgreementStatus) data.get("agreementStatus")
        );
    }

     //Method to display rental agreement list
//    public static void displayRentalAgreements(List<RentalAgreement> rentalAgreements) {
//        for (RentalAgreement rentalAgreement : rentalAgreements) {
//            System.out.println(rentalAgreement);
//        }
//    }

    public static void displayRentalAgreements(List<RentalAgreement> rentalAgreements) {
        for (RentalAgreement rentalAgreement : rentalAgreements) {
            System.out.println("Rental Agreement ID: " + rentalAgreement.getAgreementId());
            System.out.println("Host: " + (rentalAgreement.getHost().getId() != null ? rentalAgreement.getHost().getId().toString() : "null"));
            System.out.println("Property: " + (rentalAgreement.getProperty().getPropertyId() != null ? rentalAgreement.getProperty().getPropertyId().toString() : "null"));
            System.out.println("Main Tenant: " + (rentalAgreement.getMainTenant().getId() != null ? rentalAgreement.getMainTenant().getId().toString() : "null"));

            // Hiển thị thông tin về các người thuê phụ
            List<Tenant> subTenants = rentalAgreement.getSubTenants();
            if (subTenants != null && !subTenants.isEmpty()) {
                System.out.println("Sub Tenants: ");
                for (Tenant subTenant : subTenants) {
                    System.out.println(" - SubTenant ID: " + (subTenant.getId() != null ? subTenant.getId() : "null"));
                }
            } else {
                System.out.println("Sub Tenants: None");
            }
            System.out.println();
        }
    }

}
