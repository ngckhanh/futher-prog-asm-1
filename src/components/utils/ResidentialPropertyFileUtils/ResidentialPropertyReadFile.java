package utils.ResidentialPropertyFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.*;
import models.enums.PropertyStatus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import static utils.HostFileUtils.HostReadFile.getHostByID;
//import static utils.OwnerFileUtils.OwnerReadFile.getOwnerByID;
//import static utils.PaymentFileUtils.PaymentReadFile.getPaymentRecordByID;

public class ResidentialPropertyReadFile {

    String filePath = "src/components/resource/data/propertyData/residential_property.txt";


    // Read and parse ResidentialProperty file line by line
    public static List<ResidentialProperty> readResidentialPropertiesFromFile(String filePath) throws IOException {
        List<ResidentialProperty> residentialProperties = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                residentialProperties.add(parseResidentialPropertyLine(line));
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            throw e;  // Re-throwing the exception so that it can be handled higher up the call stack
        }

        return residentialProperties;
    }

    //residential_property.txt: Residential Property{propertyID='rp-02', address='545 O'Kon Path', pricing='$1408.74', status='Rented', ownerID='o-03', hostList='h-09, h-10', numberOfBedrooms=4, gardenAvailable=false, petFriendly=true}
    public static ResidentialProperty parseResidentialPropertyLine(String line) throws IOException {
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
            String[] hosts = hostListString.split(",\\s*");  // Split by comma and optional spaces
            for (String hostId : hosts) {
                hostList.add(new Host(hostId));  // Add each Host object to the list
            }
        }

        //Extracting numbBedrooms
        String numbBedroomsStr = extractValue(line, "numberOfBedrooms=", ",");
        int numbBedrooms = numbBedroomsStr != null ? Integer.parseInt(numbBedroomsStr.trim()) : 0;


        //Extracting garden availability
        String gardenAvailableStr = extractValue(line, "gardenAvailable=", ",");
        boolean gardenAvailable = gardenAvailableStr != null ? Boolean.parseBoolean(gardenAvailableStr.trim()) : false;

        //Extracting pet friendliness
        String petFriendlyStr = extractValue(line, "petFriendly=", "}");
        boolean petFriendly = petFriendlyStr != null ? Boolean.parseBoolean(petFriendlyStr.trim()) : false;

        // Create and return a new ResidentialProperty object
        return new ResidentialProperty(propertyId, address, pricing, status, owner, hostList, numbBedrooms, gardenAvailable, petFriendly);
    }

    private static String extractValue(String line, String prefix, String suffix) {
        int startIndex = line.indexOf(prefix);
        if (startIndex == -1) return null;
        startIndex += prefix.length();

        int endIndex = suffix != null ? line.indexOf(suffix, startIndex) : line.length();
        if (endIndex == -1) return null;

        return line.substring(startIndex, endIndex).trim();
    }

    // Method to display Residential Property list
    public static void displayResidentialProperties(List<ResidentialProperty> residentialProperties) {
        for (ResidentialProperty residentialProperty : residentialProperties) {
            System.out.println(residentialProperty);
        }
    }
}
