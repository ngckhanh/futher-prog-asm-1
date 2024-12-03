package utils.HostFileUtils;

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

public class HostReadFile {
    public static Properties properties;
    public static Persons persons;
    public static RentalAgreements agreements;

    static {
        properties = new Properties();
        agreements = new RentalAgreements();
        persons = new Persons();
    }


    private static String extractValue(String line, String prefix, String suffix) {
        int startIndex = line.indexOf(prefix);
        if (startIndex == -1) return null;
        startIndex += prefix.length();

        int endIndex = suffix != null ? line.indexOf(suffix, startIndex) : line.length();
        if (endIndex == -1) return null;

        return line.substring(startIndex, endIndex).trim();
    }


    public static List<Host> readHostsFromFile(String fileName) throws IOException {
        List<Host> hosts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Map<String, Object> data = parseHostLine(line);
                System.out.println("Parsed Host Data: " + data); // Debugging output
                Host host = linkDataToObjects(data);
                System.out.println("Host: " + host);
                if (host != null) { // Only add the host if it was created successfully
                    hosts.add(host);
                    System.out.println("Added Host: " + host); // Debugging output
                } else {
                    System.out.println("Host creation failed for data: " + data);
                }
            }
        }
        return hosts;
    }

    public static Map<String, Object> parseHostLine(String line) {
        Map<String, Object> data = new HashMap<>();
        data.put("hostId", extractValue(line, "ID='", "'"));
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


        List<String> managedPropertiesIds = new ArrayList<>();
        String propertiesList = extractValue(line, "managedPropertyList='", "'");
        if (propertiesList != null && !propertiesList.equalsIgnoreCase("None")) {
            String[] propertyIds = propertiesList.split(",\\s*");
            for (String propertyId : propertyIds) {
                managedPropertiesIds.add(propertyId.trim());
            }
        }
        data.put("managedPropertiesIds", managedPropertiesIds);


        List<String> cooperatingOwnersIds = new ArrayList<>();
        String ownersList = extractValue(line, "cooperatingOwnerList='", "'");
        if (ownersList != null && !ownersList.equalsIgnoreCase("None")) {
            String[] ownerIds = ownersList.split(",\\s*");
            for (String ownerId : ownerIds) {
                cooperatingOwnersIds.add(ownerId.trim());
            }
        }
        data.put("cooperatingOwnersIds", cooperatingOwnersIds);


        List<String> rentalAgreementsIds = new ArrayList<>();
        String agreementsList = extractValue(line, "rentalAgreementList='", "'");
        if (agreementsList != null && !agreementsList.equalsIgnoreCase("None")) {
            String[] agreementIds = agreementsList.split(",\\s*");
            for (String agreementId : agreementIds) {
                rentalAgreementsIds.add(agreementId.trim());
            }
        }
        data.put("rentalAgreementsIds", rentalAgreementsIds);

        return data;
    }
//    private static List<String> extractList(String line, String prefix, String suffix) {
//        List<String> list = new ArrayList<>();
//        String listStr = extractValue(line, prefix, suffix);
//        if (listStr != null && !listStr.isEmpty()) {
//            String[] items = listStr.split(",\\s*");
//            Collections.addAll(list, items);
//        }
//        return list;
//    }

    public static Host linkDataToObjects(Map<String, Object> data) {
        //System.out.println("Linking data for host ID: " + data.get("hostId"));

        // Extract the lists from the data map
        List<String> managedPropertiesIds = (List<String>) data.get("managedPropertiesIds");
        List<String> cooperatingOwnersIds = (List<String>) data.get("cooperatingOwnersIds");
        List<String> rentalAgreementsIds = (List<String>) data.get("rentalAgreementsIds");

        // Debugging output for extracted IDs
//        System.out.println("Managed Properties IDs: " + managedPropertiesIds);
//        System.out.println("Cooperating Owners IDs: " + cooperatingOwnersIds);
//        System.out.println("Rental Agreements IDs: " + rentalAgreementsIds);

        // Convert IDs to actual objects
        List<Property> managedPropertyList = new ArrayList<>();
        if (managedPropertiesIds != null && !managedPropertiesIds.isEmpty()) {
            for (String id : managedPropertiesIds) {
                Property property = properties.getPropertyById(id);
                //System.out.println("Fetching Property for ID: " + id);
                if (property != null) {
                    managedPropertyList.add(property);
                    //System.out.println("Added Property: " + property);
                } else {
                    System.out.println("Property not found for ID: " + id);
                }
            }
        } else {
            System.out.println("No managed properties found.");
        }

        List<Owner> cooperatingOwnerList = new ArrayList<>();
        if (cooperatingOwnersIds != null && !cooperatingOwnersIds.isEmpty()) {
            for (String id : cooperatingOwnersIds) {
                Owner owner = persons.getOwnerById(id);
                //System.out.println("Fetching Owner for ID: " + id);
                if (owner != null) {
                    cooperatingOwnerList.add(owner);
                    //System.out.println("Added Owner: " + owner);
                } else {
                    System.out.println("Owner not found for ID: " + id);
                }
            }
        } else {
            System.out.println("No cooperating owners found.");
        }

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

        // Create Host object with the extracted lists
        Host host = new Host(
                (String) data.get("hostId"),
                (String) data.get("fullName"),
                (Date) data.get("dob"),
                (String) data.get("contactInfo"),
                managedPropertyList,
                cooperatingOwnerList,
                rentalAgreementList
        );

        System.out.println("Created Host: " + host); // Debugging output for created Host
        return host;
    }


    public static void displayHosts(List<Host> hosts) {
        for (Host host : hosts) {
            System.out.println("Host ID: " + host.getId());
//            System.out.println("Full Name: " + host.getFullName());
//            System.out.println("Date of Birth: " + (host.getDob() != null ? host.getDob().toString() : "null"));
//            System.out.println("Contact Info: " + host.getContactInfo());
//
//            // Display managed properties
//            List<Property> managedProperties = host.getManagedPropertyList();
//            if (managedProperties != null && !managedProperties.isEmpty()) {
//                System.out.println("Managed Properties: ");
//                for (Property property : managedProperties) {
//                    System.out.println(" - " + property.toString());
//                }
//            } else {
//                System.out.println("Managed Properties : None");
//            }
//
//            // Display cooperating owners
//            List<Owner> cooperatingOwners = host.getCooperatingOwnerList();
//            if (cooperatingOwners != null && !cooperatingOwners.isEmpty()) {
//                System.out.println("Cooperating Owners: ");
//                for (Owner owner : cooperatingOwners) {
//                    System.out.println(" - " + owner.toString());
//                }
//            } else {
//                System.out.println("Cooperating Owners: None");
//            }
//
//            // Display rental agreements
//            List<RentalAgreement> rentalAgreements = host.getRentalAgreementList();
//            if (rentalAgreements != null && !rentalAgreements.isEmpty()) {
//                System.out.println("Rental Agreements: ");
//                for (RentalAgreement rentalAgreement : rentalAgreements) {
//                    System.out.println(" - Rental Agreement ID: " + rentalAgreement.getAgreementId());
//                }
//            } else {
//                System.out.println("Rental Agreements: None");
//            }
//
//            System.out.println(); // Add a blank line between hosts
        }
    }

}