package utils.HostFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static utils.PropertyFileUtils.PropertyReadFile.combinePropertiesToMap;

import utils.OwnerFileUtils.OwnerReadFile;

import utils.RentalAgreementFileUtils.RentalAgreementReadFile;

public class HostReadFile {
    private static String hostFilePath = "src/components/resource/data/hostData/host.txt";

    private static Map<String, Host> hostMap;
    public static Map<String, Host> getHostMap() throws IOException {
        if (hostMap == null) {
            hostMap = readHostsFirstTime();
            hostMap = readHostsSecondTime(hostMap);
        }
        return hostMap;
    }

    // This method will read the file and return a list of Host objects
    public static List<Host> readHostsFromFile() throws IOException {
        // Step 1: Read hosts for the first time
        Map<String, Host> hosts = readHostsFirstTime();
        // Step 2: Enrich data by read hosts for the second time
        readHostsSecondTime(hosts);
        return new ArrayList<>(hosts.values());
    }

    // Method to display host list
    public static void displayHosts(List<Host> hosts) {
        for (Host host : hosts) {
            System.out.println(host);
        }
    }

    public static Map<String, Host> readHostsFirstTime() {
        Map<String, Host> cachedHosts = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(hostFilePath))) {
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

                    Host host = new Host(id, fullName, dob, contactInfo);
                    cachedHosts.put(id, host);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cachedHosts;
    }

    public static Map<String, Host> readHostsSecondTime(
            Map<String, Host> hostsMap) throws IOException {
        Map<String, Owner> ownerMap = OwnerReadFile.getOwnerMap();
        Map<String, Property> propertyMap = combinePropertiesToMap();
        Map<String, RentalAgreement> agreementMap = RentalAgreementReadFile.getAgreementMap();

        try (BufferedReader br = new BufferedReader(new FileReader(hostFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                String id = extractValue(line, "ID='", "'");
                Host host = hostsMap.get(id);
                if (host == null) {
                    //System.err.println("Host not found for ID: " + id);
                    continue;
                }

                String managedPropertiesIds = extractValue(line, "managedPropertyList='", "'");  // Extract comma-separated tenant IDs
                if ("None".equals(managedPropertiesIds)) {
                    managedPropertiesIds = null;  // If managedPropertiesIds is 'None', set it to null
                }
                List<Property> managedPropertyList = new ArrayList<>();
                if (managedPropertiesIds != null) {
                    String[] managedPropertyIdArray = managedPropertiesIds.split(",");

                    for (String propertyId : managedPropertyIdArray) {
                        propertyId = propertyId.trim();
                        Property property = propertyMap.get(propertyId);
                        if (property != null) {
                            managedPropertyList.add(property);
                        } else {
                            System.err.println("Property not found for ID: " + propertyId);
                        }
                    }
                }


                String cooperatingOwnersIds = extractValue(line, "cooperatingOwnerList='", "'");  // Extract comma-separated tenant IDs
                if ("None".equals(cooperatingOwnersIds)) {
                    cooperatingOwnersIds = null;  // If cooperatingOwnersIds is 'None', set it to null
                }
                List<Owner> cooperatingOwnerList = new ArrayList<>();
                if (cooperatingOwnersIds != null) {
                    String[] cooperatingOwnerIdArray = cooperatingOwnersIds.split(",");

                    for (String ownerId : cooperatingOwnerIdArray) {
                        ownerId = ownerId.trim();
                        Owner owner = ownerMap.get(ownerId);
                        if (owner != null) {
                            cooperatingOwnerList.add(owner);
                        } else {
                            System.err.println("Owner not found for ID: " + ownerId);
                        }
                    }

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

                host.setManagedProperties(managedPropertyList);
                host.setCooperatingOwners(cooperatingOwnerList);
                host.setRentalAgreements(rentalAgreementList);
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return hostsMap;
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
