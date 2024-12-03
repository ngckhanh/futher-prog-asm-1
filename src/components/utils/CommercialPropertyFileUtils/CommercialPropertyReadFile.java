package utils.CommercialPropertyFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.*;
import models.enums.PropertyStatus;
import utils.HostFileUtils.HostReadFile;
import utils.OwnerFileUtils.OwnerReadFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CommercialPropertyReadFile {
    private static String filePath = "src/components/resource/data/propertyData/commercial_property.txt";

    private static Map<String, CommercialProperty> commercialPropertyMap;
    public static Map<String, CommercialProperty> getCommercialPropertyMap() throws IOException {
        if (commercialPropertyMap == null) {
            commercialPropertyMap = readCommercialPropertiesFirstTime();
            commercialPropertyMap = readCommercialPropertiesSecondTime(commercialPropertyMap);
        }
        return commercialPropertyMap;
    }

    // Method to display Commercial Property list
    public static void displayCommercialProperties(List<CommercialProperty> commercialProperties) {
        for (CommercialProperty commercialProperty : commercialProperties) {
            System.out.println(commercialProperty);
        }
    }

    // Method to read commercial properties from a file
    public static List<CommercialProperty> readCommercialPropertiesFromFile() throws IOException {
        // Step 1: Read CommercialProperty for the first time
        Map<String, CommercialProperty> commercialProperties = readCommercialPropertiesFirstTime();
        readCommercialPropertiesSecondTime(commercialProperties);
        // Convert the map back to a list for return
        return new ArrayList<>(commercialProperties.values());
    }

    //commericial_property.txt: Commercial Property{propertyID='cp-01', address='25452 Delila Ports', pricing='$1469.26', status='Rented', ownerID='o-08', hostList='h-07, h-08, h-03', businessType='Government Administration', parkingSpaces=10, squareFootage=5465.17}
    public static Map<String, CommercialProperty> readCommercialPropertiesFirstTime() {
        Map<String, CommercialProperty> cachedCommercialProperty = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String propertyId = extractValue(line, "propertyID='", "'");
                    String address = extractValue(line, "address='", "'");
                    String pricingStr = extractValue(line, "pricing='", "'");
                    double pricing = parsePricing(pricingStr);

                    String propertyStatusStr = extractValue(line, "status='", "'");
                    PropertyStatus status = PropertyStatus.valueOf(propertyStatusStr.toUpperCase());

                    String businessType = extractValue(line, "businessType='", "'");

                    String parkingSpacesStr = extractValue(line, "parkingSpaces=", ",");
                    int parkingSpaces = 0;  // Default value
                    if (parkingSpacesStr != null) {
                        try {
                            parkingSpaces = Integer.parseInt(parkingSpacesStr.trim());
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid parking spaces value: " + parkingSpacesStr);
                        }
                    } else {
                        System.err.println("Missing parking spaces value for line: " + line);
                    }


                    String squareFootageStr = extractValue(line, "squareFootage=", null);

                    if (squareFootageStr != null) {
                        squareFootageStr = squareFootageStr.replaceAll("[^\\d.]", "");  // Keep only digits and dot
                    }

                    double squareFootage = squareFootageStr != null ? Double.parseDouble(squareFootageStr.trim()) : 0.0;

                    // Create CommercialProperty object
                    CommercialProperty commercialProperty = new CommercialProperty(
                            propertyId, address, pricing, status, businessType, parkingSpaces, squareFootage);

                    // Add to map
                    cachedCommercialProperty.put(propertyId, commercialProperty);

                } catch (Exception e) {
                    System.err.println("Error processing line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cachedCommercialProperty; // Return the map of commercial properties
    }

    public static Map<String, CommercialProperty> readCommercialPropertiesSecondTime(Map<String, CommercialProperty> commercialPropertiesMap) throws IOException {

        Map<String, Owner> ownerMap = OwnerReadFile.getOwnerMap();
        Map<String, Host> hostMap = HostReadFile.getHostMap();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                String propertyId = extractValue(line, "propertyID='", "'");
                CommercialProperty commercialProperty = commercialPropertiesMap.get(propertyId);
                if (commercialProperty == null) {
                    System.err.println("commercialProperty not found for ID: " + propertyId);
                    continue;
                }

                String ownerId = extractValue(line, "ownerID='", "'");
                Owner owner = ownerMap.get(ownerId);
                if (owner == null) {
                    System.err.println("Owner not found for ID: " + ownerId);
                    continue;
                }



                String hostListIds = extractValue(line, "hostList='", "'");  // Extract comma-separated tenant IDs
                if ("null".equals(hostListIds)) {
                    hostListIds = null;  // If hostListIds is 'None', set it to null
                }
                List<Host> hostList = new ArrayList<>();
                if (hostListIds != null) {
                    String[] hostListIdArray = hostListIds.split(",");

                    for (String hostId : hostListIdArray) {
                        hostId = hostId.trim();
                        Host host = hostMap.get(hostId);
                        if (host != null) {
                            hostList.add(host);
                        } else {
                            //System.err.println("Host not found for ID: " + hostId);
                        }
                    }
                }

                commercialProperty.setOwner(owner);
                commercialProperty.setHostList(hostList);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commercialPropertiesMap;
    }

    private static double parsePricing(String feeString) {
        if (feeString == null || feeString.isEmpty()) return 0.0;
        return Double.parseDouble(feeString.replace("$", "").replace(",", ""));
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
