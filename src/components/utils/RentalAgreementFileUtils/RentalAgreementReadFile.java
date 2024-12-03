package utils.RentalAgreementFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.entities.*;
import models.enums.AgreementStatus;
import models.enums.PeriodType;
import utils.HostFileUtils.HostReadFile;
import utils.TenantFileUtils.TenantReadFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static utils.PropertyFileUtils.PropertyReadFile.combinePropertiesToMap;


public class RentalAgreementReadFile {

    private static final String FILE_PATH = "src/components/resource/data/rentalAgreementData/rental_agreement.txt";

    private static Map<String, RentalAgreement> agreementMap;
    public static Map<String, RentalAgreement> getAgreementMap() throws IOException {
        if (agreementMap == null) {
            agreementMap = readAgreementsFirstTime();
            agreementMap = readAgreementsSecondTime(agreementMap);
        }
        return agreementMap;
    }

    public static List<RentalAgreement> readRentalAgreementsFromFile() throws IOException {
        // Step 1: Read agreements for the first time
        Map<String, RentalAgreement> agreements = readAgreementsFirstTime();
        // Step 2: Enrich data by read agreements for the second time
        readAgreementsSecondTime(agreements);
        return new ArrayList<>(agreements.values());
    }

    // Method to display rental agreement list
    public static void displayRentalAgreements(List<RentalAgreement> rentalAgreements) {
        for (RentalAgreement rentalAgreement : rentalAgreements) {
            System.out.println(rentalAgreement);
        }
    }

    //Rental Agreement{agreementID='a-01', hostID='h-06', propertyID='rp-07', mainTenantID='t-13', subTenantIDs='t-03, t-09, t-04', periodType='Monthly', contractDate='Sun Jan 21 22:43:48 ICT 1990', rentalFee='$1738.98', agreementStatus='Completed'}
    // Method to read rental agreements for the first time and return a map of agreements
    public static Map<String, RentalAgreement> readAgreementsFirstTime() throws IOException {
        Map<String, RentalAgreement> cachedAgreements = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String agreementId = extractValue(line, "agreementID='", "'");
                    String contractDateStr = extractValue(line, "contractDate='", "'");
                    String rentalFeeStr = extractValue(line, "rentalFee='", "'");

                    String periodTypeStr = extractValue(line, "periodType='", "'");
                    PeriodType periodType = PeriodType.valueOf(periodTypeStr.toUpperCase());

                    String agreementStatusStr = extractValue(line, "agreementStatus='", "'");
                    AgreementStatus agreementStatus = AgreementStatus.valueOf(agreementStatusStr.toUpperCase());

                    double rentalFee = parseRentalFee(rentalFeeStr);

                    Date contractDate = null;
                    if (contractDateStr != null) {
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                            contractDate = dateFormat.parse(contractDateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();  // Optionally log or handle the exception
                        }
                    }


                    RentalAgreement rentalAgreement = new RentalAgreement(
                            agreementId, periodType, contractDate, rentalFee, agreementStatus
                    );
                    cachedAgreements.put(agreementId, rentalAgreement);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cachedAgreements;
    }


    public static Map<String, RentalAgreement> readAgreementsSecondTime(Map<String, RentalAgreement> agreementsMap
                                                                 ) throws IOException {
        Map<String, Property> propertyMap = combinePropertiesToMap();
        Map<String, Host> hostMap = HostReadFile.getHostMap();
        Map<String, Tenant> tenantMap = TenantReadFile.getTenantMap();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {

                String agreementId = extractValue(line, "agreementID='", "'");
                RentalAgreement rentalAgreement = agreementsMap.get(agreementId);
                if (rentalAgreement == null) {
                    //System.err.println("Rental Agreement not found for Id: " + agreementId);
                    continue;
                }

                String hostId = extractValue(line, "hostID='", "'");
                String propertyId = extractValue(line, "propertyID='", "'");

                String mainTenantId = extractValue(line, "mainTenantID='", "'");

                Property property = propertyMap.get(propertyId);
                Tenant mainTenant = tenantMap.get(mainTenantId);
                Host host = hostMap.get(hostId);

                String subTenantIds = extractValue(line, "subTenantIDs='", "'");  // Extract comma-separated tenant IDs

                if ("None".equals(subTenantIds)) {
                    subTenantIds = null;  // If subTenantIDs is 'None', set it to null
                }

                List<Tenant> subTenants = new ArrayList<>();
                if (subTenantIds != null) {
                    // Split the subTenantIds string by commas to get an array of tenant IDs
                    String[] tenantIdArray = subTenantIds.split(",");  // Splitting by commas

                    for (String tenantId : tenantIdArray) {
                        tenantId = tenantId.trim(); // Remove any extra spaces
                        Tenant tenant = tenantMap.get(tenantId); // Get the tenant by ID
                        if (tenant != null) {
                            subTenants.add(tenant);
                        } else {
                            System.err.println("Tenant not found for ID: " + tenantId);
                        }
                    }
                }

                rentalAgreement.setProperty(property);
                rentalAgreement.setMainTenant(mainTenant);
                rentalAgreement.setHost(host);
                rentalAgreement.setSubTenants(subTenants);
            }
        } catch (IOException e) {
            System.err.println("Error reading the rental agreement file during second pass: " + e.getMessage());
        }
        return agreementsMap;
    }

    private static double parseRentalFee(String feeString) {
        if (feeString == null || feeString.isEmpty()) return 0.0;
        return Double.parseDouble(feeString.replace("$", "").replace(",", ""));
    }

    private static String extractValue(String line, String prefix, String suffix) {
        int startIndex = line.indexOf(prefix);
        if (startIndex == -1) return null;
        startIndex += prefix.length();

        int endIndex = line.indexOf(suffix, startIndex);
        if (endIndex == -1) {
            return line.substring(startIndex).trim();  // Extract the remainder of the line if no suffix is found
        }

        return line.substring(startIndex, endIndex).trim();
    }
}
