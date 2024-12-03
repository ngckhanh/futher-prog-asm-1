package utils.OwnerFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.*;
import utils.HostFileUtils.HostReadFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static utils.PropertyFileUtils.PropertyReadFile.combinePropertiesToMap;

public class OwnerReadFile {
    private static final String FILE_PATH = "src/components/resource/data/ownerData/owner.txt";

    private static Map<String, Owner> ownerMap;
    public static Map<String, Owner> getOwnerMap() throws IOException {
        if (ownerMap == null) {
            ownerMap = readOwnersFirstTime();
            ownerMap = readOwnersSecondTime(ownerMap);
        }
        return ownerMap;
    }

    // This method reads the file and returns a list of Owner objects
    public static List<Owner> readOwnersFromFile() throws IOException {
        // Step 1: Read owners for the first time
        Map<String, Owner> owners = readOwnersFirstTime();

        readOwnersSecondTime(owners);
        return new ArrayList<>(owners.values());
    }

    // Method to display owner list
    public static void displayOwners(List<Owner> owners) {
        for (Owner owner : owners) {
            System.out.println(owner);
        }
    }

    // Owner{ID='o-02', fullName='Mrs. Shandra Jacobs', dob='Wed Jul 26 03:19:31 ICT 2000', contactInfo='willis.gislason@yahoo.com', ownedPropertyList='p-04, p-02, p-09', hostList='h-08, h-10, h-06, h-04'}
    // Method to read owners for the first time and return a map of owners
    public static Map<String, Owner> readOwnersFirstTime() throws IOException {
        Map<String, Owner> cachedOwners = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
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

                    Owner owner = new Owner(id, fullName, dob, contactInfo);
                    cachedOwners.put(id, owner);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }  // Correctly placed closing brace
        }
        return cachedOwners;
    }

    // Method to enrich the owner data based on additional information
    public static Map<String, Owner> readOwnersSecondTime(Map<String, Owner> ownersMap) throws IOException {
        Map<String, Property> propertyMap = combinePropertiesToMap();
        Map<String, Host> hostMap = HostReadFile.getHostMap();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {

                String id = extractValue(line, "ID='", "'");
                Owner owner = ownersMap.get(id);
                if (owner == null) {
                    System.err.println("Owner not found for ID: " + id);
                    continue;
                }

                String ownedPropertiesIds = extractValue(line, "ownedPropertyList='", "'");  // Extract comma-separated tenant IDs
                if ("None".equals(ownedPropertiesIds)) {
                    ownedPropertiesIds = null;  // If ownedPropertiesIds is 'None', set it to null
                }
                List<Property> ownedPropertyList = new ArrayList<>();
                if (ownedPropertiesIds != null) {
                    String[] ownedPropertyIdArray = ownedPropertiesIds.split(",");

                    for (String propertyId : ownedPropertyIdArray) {
                        propertyId = propertyId.trim();
                        Property property = propertyMap.get(propertyId);
                        if (property != null) {
                            ownedPropertyList.add(property);
                        } else {
                            System.err.println("Property not found for ID: " + propertyId);
                        }
                    }
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

                owner.setOwnedProperties(ownedPropertyList);
                owner.setHostLists(hostList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ownersMap;
    }

    // Utility method to extract values from strings
    private static String extractValue(String line, String prefix, String suffix) {
        int startIndex = line.indexOf(prefix);
        if (startIndex == -1) return null;
        startIndex += prefix.length();

        int endIndex = suffix != null ? line.indexOf(suffix, startIndex) : line.length();
        if (endIndex == -1) return null;

        return line.substring(startIndex, endIndex).trim();
    }


    public static Owner getOwnerByProperty(Property property) {
        List<Owner> owners;
        try {
            owners = OwnerReadFile.readOwnersFromFile(); // Read the list of owners
        } catch (IOException e) {
            System.out.println("Error reading owners from file: " + e.getMessage());
            return null; // Return null if there's an error
        }

        // Check if the property has an owner ID
        String ownerId = property.getOwnerID(); // Assuming getOwnerId() returns the owner ID
        for (Owner owner : owners) {
            if (owner.getId().equals(ownerId)) {
                return owner; // Return the owner if found
            }
        }

        return null; // Return null if no owner is found
    }
}
