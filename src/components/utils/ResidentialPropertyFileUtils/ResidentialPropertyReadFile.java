package utils.ResidentialPropertyFileUtils;
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

public class ResidentialPropertyReadFile {
    static String filePath = "src/components/resource/data/propertyData/residential_property.txt";

    private static Map<String, ResidentialProperty> residentialPropertyMap;
    public static Map<String, ResidentialProperty> getResidentialPropertyMap() throws IOException {
        if (residentialPropertyMap == null) {
            residentialPropertyMap = readResidentialPropertiesFirstTime();
            residentialPropertyMap = readResidentialPropertiesSecondTime(residentialPropertyMap);
        }
        return residentialPropertyMap;
    }

    // Method to display Residential Property list
    public static void displayResidentialProperties(List<ResidentialProperty> residentialProperties) {
        for (ResidentialProperty residentialProperty : residentialProperties) {
            System.out.println(residentialProperty);
        }
    }

    // Method to read residential properties from a file
    public static List<ResidentialProperty> readResidentialPropertiesFromFile() throws IOException {
        // Step 1: Read CommercialProperty for the first time
        Map<String, ResidentialProperty> residentialProperties = readResidentialPropertiesFirstTime();

        readResidentialPropertiesSecondTime(residentialProperties);
        // Convert the map back to a list for return
        return new ArrayList<>(residentialProperties.values());
    }

    //residential_property.txt: Residential Property{propertyID='rp-02', address='545 O'Kon Path', pricing='$1408.74', status='Rented', ownerID='o-03', hostList='h-09, h-10', numberOfBedrooms=4, gardenAvailable=false, petFriendly=true}
    public static Map<String, ResidentialProperty> readResidentialPropertiesFirstTime() {
        Map<String, ResidentialProperty> cachedResidentialProperty = new LinkedHashMap<>();
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

                    int numbBedrooms = Integer.parseInt(extractValue(line, "numberOfBedrooms=", ","));
                    boolean gardenAvailable = Boolean.parseBoolean(extractValue(line, "gardenAvailable=", ","));
                    boolean petFriendly = Boolean.parseBoolean(extractValue(line, "petFriendly=", "}"));

                    // Create ResidentialProperty object
                    ResidentialProperty residentialProperty = new ResidentialProperty(
                            propertyId, address, pricing, status, numbBedrooms, gardenAvailable, petFriendly);

                    // Add to map
                    cachedResidentialProperty.put(propertyId, residentialProperty);

                } catch (Exception e) {
                    System.err.println("Error processing line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cachedResidentialProperty;
    }

    public static Map<String, ResidentialProperty> readResidentialPropertiesSecondTime(Map<String, ResidentialProperty> residentialPropertiesMap) throws IOException  {
        Map<String, Owner> ownerMap = OwnerReadFile.getOwnerMap();
        Map<String, Host> hostMap = HostReadFile.getHostMap();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                String propertyId = extractValue(line, "propertyID='", "'");
                ResidentialProperty residentialProperty = residentialPropertiesMap.get(propertyId);
                if (residentialProperty == null) {
                    System.err.println("residentialProperty not found for ID: " + propertyId);
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


                residentialProperty.setHostList(hostList);
                residentialProperty.setOwner(owner);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return residentialPropertiesMap;
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
