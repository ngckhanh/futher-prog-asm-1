package utils.CommercialPropertyFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.CommercialProperty;
import models.entities.Host;
import models.entities.Owner;
import models.enums.PropertyStatus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import static utils.HostFileUtils.HostReadFile.getHostByID;
//import static utils.OwnerFileUtils.OwnerReadFile.getOwnerByID;

public class CommercialPropertyReadFile {

    // Method to read commercial properties from a file
    public static List<CommercialProperty> readCommercialPropertiesFromFile(String filePath) throws IOException {
        List<CommercialProperty> commercialProperties = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CommercialProperty property = parseCommercialPropertyLine(line);
                if (property != null) {
                    commercialProperties.add(property);
                } else {
                    // Log or handle the case where parsing fails
                    System.err.println("Failed to parse line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            throw e;  // Re-throwing the exception so that it can be handled higher up the call stack
        }

        return commercialProperties;
    }

    //commericial_property.txt: Commercial Property{propertyID='cp-01', address='25452 Delila Ports', pricing='$1469.26', status='Rented', ownerID='o-08', hostList='h-07, h-08, h-03', businessType='Government Administration', parkingSpaces=10, squareFootage=5465.17}
    public static CommercialProperty parseCommercialPropertyLine(String line) {
        // Extracting propertyID
        String propertyId = extractValue(line, "propertyID='", "'");

        // Extracting address
        String address = extractValue(line, "address='", "'");

        // Extracting pricing (removing the dollar sign and parsing as double)
        String pricingStr = extractValue(line, "pricing='", "'");
        double pricing = pricingStr != null ? Double.parseDouble(pricingStr.replace("$", "").trim()) : 0.0;

        // Extracting status (assuming PropertyStatus is a simple string for now)
        String statusStr = extractValue(line, "status='", "'");
        PropertyStatus status = PropertyStatus.valueOf(statusStr.toUpperCase());

        // Extracting owner (assuming owner is identified by ownerID)
        String ownerId = extractValue(line, "ownerID='", "'");
        Owner owner = new Owner(ownerId);

        // Extracting host list
        String hostListString = extractValue(line, "hostList='", "'");
        List<Host> hostList = new ArrayList<>();
        if (hostListString != null) {
            //System.out.println("Host List String: " + hostListString);  // Debugging output
            String[] hosts = hostListString.split(",\\s*");  // Split by comma and optional spaces
            for (String hostId : hosts) {
                //System.out.println("Parsed Host ID: " + hostId.trim());  // Debugging output
                hostList.add(new Host(hostId.trim()));  // Add each Host object to the list
            }
        }
//        String hostListString = extractValue(line, "hostList='", "'");
//        List<Host> hostList = new ArrayList<>();
//        if (hostListString != null) {
//            String[] hosts = hostListString.split(",\\s*");  // Split by comma and optional spaces
//            for (String hostId : hosts) {
//                hostList.add(new Host(hostId));  // Add each Host object to the list
//            }
//        }

        // Extracting businessType
        String businessType = extractValue(line, "businessType='", "'");

        // Extracting parkingSpaces
        String parkingSpacesStr = extractValue(line, "parkingSpaces=", ",");
        int parkingSpaces = parkingSpacesStr != null ? Integer.parseInt(parkingSpacesStr.trim()) : 0;

        // Extracting square footage
        String squareFootageStr = extractValue(line, "squareFootage=", null);

        // Remove any non-numeric characters (like closing curly brace '}') before parsing
        if (squareFootageStr != null) {
            squareFootageStr = squareFootageStr.replaceAll("[^\\d.]", "");  // Keep only digits and dot
        }

        double squareFootage = squareFootageStr != null ? Double.parseDouble(squareFootageStr.trim()) : 0.0;

        // Create and return a new CommercialProperty object
        return new CommercialProperty(propertyId, address, pricing, status, owner, hostList, businessType, parkingSpaces, squareFootage);
    }

    private static String extractValue(String line, String prefix, String suffix) {
        int startIndex = line.indexOf(prefix);
        if (startIndex == -1) return null;
        startIndex += prefix.length();

        int endIndex = suffix != null ? line.indexOf(suffix, startIndex) : line.length();
        if (endIndex == -1) return null;

        return line.substring(startIndex, endIndex).trim();
    }

    // Method to get a CommercialProperty by its ID
    public static CommercialProperty getPropertyByID(String id) throws IOException {
        String filePath = "src/components/resource/data/propertyData/commercial_property.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Use a more precise method to extract the property ID
                if (line.startsWith("Commercial Property{propertyID='" + id + "'")) {
                    return parseCommercialPropertyLine(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            throw e;  // Re-throwing the exception for higher-level handling
        }

        return null;  // Return null if the property ID is not found
    }

    // Method to display Commercial Property list
    public static void displayCommercialProperties(List<CommercialProperty> commercialProperties) {
        for (CommercialProperty commercialProperty : commercialProperties) {
            System.out.println(commercialProperty);
        }
    }
}
