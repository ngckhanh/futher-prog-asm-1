package utils.OwnerFileUtils;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

//import static utils.CommercialPropertyFileUtils.CommercialPropertyReadFile.getPropertyByID;
//import static utils.HostFileUtils.HostReadFile.getHostByID;
//import static utils.RentalAgreementFileUtils.RentalAgreementReadFile.getRentalAgreementByID;

public class OwnerReadFile {
    public static Properties properties;
    public static Persons persons;
    public static RentalAgreements agreements;

    static {
        properties = new Properties();
        persons = new Persons();
        agreements = new RentalAgreements();
    }

    private static final String FILE_PATH = "src/components/resource/data/ownerData/owner.txt";

    private static String extractValue(String line, String prefix, String suffix) {
        int startIndex = line.indexOf(prefix);
        if (startIndex == -1) return null;
        startIndex += prefix.length();

        int endIndex = suffix != null ? line.indexOf(suffix, startIndex) : line.length();
        if (endIndex == -1) return null;

        return line.substring(startIndex, endIndex).trim();
    }

    // This method will read the file and return a list of Owner objects
    public static List<Owner> readOwnersFromFile(String fileName) {
        List<Owner> owners = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Read line: " + line);
                Map<String, Object> data = parseOwnerLine(line);
                System.out.println("Parsed Owner Data: " + data);
                Owner owner = linkDataToObjects(data);
                System.out.println("Owner: " + owner);
                if (owner != null) {
                    owners.add(owner);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing data: " + e.getMessage());
        }
        return owners;
    }
    //owner.txt: Owner{ID='o-02', fullName='Mrs. Shandra Jacobs', dob='Wed Jul 26 03:19:31 ICT 2000', contactInfo='willis.gislason@yahoo.com', ownedPropertyList='p-04, p-02, p-09', hostList='h-08, h-10, h-06, h-04'}
    public static Map<String, Object> parseOwnerLine(String line) {
        Map<String, Object> data = new HashMap<>();
        data.put("ownerId", extractValue(line, "ID='", "'"));
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

        List<String> ownedPropertiesIds = new ArrayList<>();
        String propertiesList = extractValue(line, "ownedPropertyList='", "'");
        if (propertiesList != null && !propertiesList.equalsIgnoreCase("None")) {
            String[] propertyIds = propertiesList.split(",\\s*");
            for (String propertyId : propertyIds) {
                ownedPropertiesIds.add(propertyId.trim());
            }
        }
        data.put("ownedPropertiesIds", ownedPropertiesIds);

        List<String> hostIds = new ArrayList<>();
        String hostsList = extractValue(line, "hostList='", "'");
        if (hostsList != null && !hostsList.equalsIgnoreCase("None")) {
            String[] hostIdsArray = hostsList.split(",\\s*");
            for (String hostId : hostIdsArray) {
                hostIds.add(hostId.trim());
            }
        }
        data.put("hostIds", hostIds);
        return data;
    }

    public static Owner linkDataToObjects(Map<String, Object> data) {
        System.out.println("Linking data for owner ID: " + data.get("ownerId"));

        List<String> ownedPropertyIds = (List<String>) data.get("ownedPropertiesIds");
        List<String> hostIds = (List<String>) data.get("hostIds");

        List<Property> ownedPropertyList = new ArrayList<>();
        if (ownedPropertyIds != null) {
            for (String id : ownedPropertyIds) {
                Property ownedProperty = properties.getPropertyById(id);
                if (ownedProperty != null) {
                    ownedPropertyList.add(ownedProperty);
                } else {
                    System.out.println("Owned property not found for ID: " + id);
                }
            }
        } else {
            System.out.println("Owned property list is null for owner ID: " + data.get("ownerId"));
        }

        // Initialize cooperating owner list
        List<Host> hostList = new ArrayList<>();
        if (hostIds != null && hostIds.isEmpty()) {
            for (String id : hostIds) {
                Host host = persons.getHostById(id);
                if (host != null) {
                    hostList.add(host);
                } else {
                    System.out.println("Host list not found for ID: " + id);
                }
            }
        } else {
            System.out.println("Host list is null for host ID: " + data.get("id"));
        }
        return new Owner(
                (String) data.get("ownerId"),
                (String) data.get("fullName"),
                (Date) data.get("dob"),
                (String) data.get("contactInfo"),
                ownedPropertyList,
                hostList
        );
    }



    // Method to display owner list
    public static void displayOwners(List<Owner> owners) {
        for (Owner owner : owners) {
            System.out.println(owner);
        }
    }
}
