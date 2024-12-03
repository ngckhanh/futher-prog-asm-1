package models.entities;

/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.enums.PropertyStatus;
import utils.CommercialPropertyFileUtils.CommercialPropertyReadFile;
import utils.CommercialPropertyFileUtils.CommercialPropertyWriteFile;
import utils.ResidentialPropertyFileUtils.ResidentialPropertyReadFile;
import utils.ResidentialPropertyFileUtils.ResidentialPropertyWriteFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Properties {
    private ArrayList<Property> propertiesList; // Changed to instance variable

    public Properties() {
        System.out.println("Properties initialized from: " + Thread.currentThread().getStackTrace()[2]);
        this.propertiesList = new ArrayList<>();
        loadFromFile();
    }

//    public static Properties getInstance() {
//        if (instance == null) {
//            instance = new Properties();
//        }
//        return instance;
//    }

    public void addProperty(Property property) {
        propertiesList.add(property);
    }

    public void saveToDisk(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(propertiesList);
        } catch (IOException e) {
            System.err.println("Error saving properties to disk: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        try {
            List<ResidentialProperty> residentialProperties = ResidentialPropertyReadFile.readResidentialPropertiesFromFile("src/components/resource/data/propertyData/residential_property.txt");
            List<CommercialProperty> commercialProperties = CommercialPropertyReadFile.readCommercialPropertiesFromFile("src/components/resource/data/propertyData/commercial_property.txt");

            propertiesList.addAll(residentialProperties);
            propertiesList.addAll(commercialProperties);

            // Debug output to verify loaded properties
            //System.out.println("Loaded properties: " + propertiesList.size());
            for (Property property : propertiesList) {
                //System.out.println(property);
            }
        } catch (IOException e) {
            System.err.println("Error loading properties: " + e.getMessage());
            propertiesList = new ArrayList<>(); // Initialize to avoid null issues
        }
    }

    public List<Property> getProperties() {
        return propertiesList;
    }

    public void saveResidentialPropertiesReportToFile(List<ResidentialProperty> residentialProperties) throws IOException {
        ResidentialPropertyWriteFile.writeResidentialPropertiesToFile("src/components/resource/data/propertyData/residential_property_report.txt", residentialProperties);
    }

    public void saveCommercialPropertiesReportToFile(List<CommercialProperty> commercialProperties) throws IOException {
        CommercialPropertyWriteFile.writeCommercialPropertiesToFile("src/components/resource/data/propertyData/commercial_property_report.txt", commercialProperties);
    }

    public List<ResidentialProperty> getResidentialProperties() {
        List<ResidentialProperty> residentialProperties = new ArrayList<>();
        for (Property property : propertiesList) {
            if (property instanceof ResidentialProperty) {
                residentialProperties.add((ResidentialProperty) property);
            }
        }
        return residentialProperties;
    }

    public List<CommercialProperty> getCommercialProperties() {
        List<CommercialProperty> commercialProperties = new ArrayList<>();
        for (Property property : propertiesList) {
            if (property instanceof CommercialProperty) {
                commercialProperties.add((CommercialProperty) property);
            }
        }
        return commercialProperties;
    }

    public void displayResidentialProperties() {
        List<ResidentialProperty> residentialProperties = getResidentialProperties();
        if (residentialProperties.isEmpty()) {
            System.out.println("No residential properties to display.");
            return;
        }
        System.out.println("Residential Properties:");
        for (ResidentialProperty property : residentialProperties) {
            System.out.println(property); // Assuming toString() is overridden in ResidentialProperty
        }
    }

    public void displayCommercialProperties() {
        List<CommercialProperty> commercialProperties = getCommercialProperties();
        if (commercialProperties.isEmpty()) {
            System.out.println("No commercial properties to display.");
            return;
        }
        System.out.println("Commercial Properties:");
        for (CommercialProperty property : commercialProperties) {
            System.out.println(property); // Assuming toString() is overridden in CommercialProperty
        }
    }

    public void displayAllProperties() {
        if (propertiesList.isEmpty()) {
            System.out.println("No properties to display.");
            return;
        }
        System.out.println("All Properties:");
        for (Property property : propertiesList) {
            System.out.println(property); // Assuming toString() is overridden in Property
        }
    }

    public List<Property> getAvailableProperties() {
        List<Property> availableProperties = new ArrayList<>();
        for (Property property : propertiesList) {
            if (property.getStatus() == PropertyStatus.AVAILABLE) {
                availableProperties.add(property);
            }
        }
        return availableProperties;
    }



    public Property getPropertyById(String propertyId) {
        for (Property property : propertiesList) {
            if (property.getPropertyId().equals(propertyId)) {
                return property;
            }
        }
        return null;
    }

    public boolean removeProperty(String propertyId) {
        return propertiesList.removeIf(property -> property.getPropertyId().equals(propertyId));
    }

    public void sortProperties(Comparator<Property> comparator) {
        propertiesList.sort(comparator);
    }

    public List<Property> getPropertiesByType(Class<? extends Property> type) {
        List<Property> filteredProperties = new ArrayList<>();
        for (Property property : propertiesList) {
            if (type.isInstance(property)) {
                filteredProperties.add(property);
            }
        }
        return filteredProperties;
    }

    public String getPropertyDetailsById(String propertyId) {
        Property property = getPropertyById(propertyId);
        if (property != null) {
            return property.toString(); // Assuming toString() is overridden in each subclass
        } else {
            return "Property not found.";
        }
    }
}