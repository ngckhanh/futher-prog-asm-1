package models.managers;

/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.CommercialProperty;
import models.entities.Property;
import models.entities.ResidentialProperty;
import models.enums.PropertyStatus;
import utils.CommercialPropertyFileUtils.CommercialPropertyReadFile;
import utils.ResidentialPropertyFileUtils.ResidentialPropertyReadFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Properties {
    private ArrayList<Property> propertiesList; // Changed to instance variable

    public Properties() {
        this.propertiesList = new ArrayList<>();
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            List<ResidentialProperty> residentialProperties = ResidentialPropertyReadFile.readResidentialPropertiesFromFile();
            List<CommercialProperty> commercialProperties = CommercialPropertyReadFile.readCommercialPropertiesFromFile();

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

    public List<Property> getAvailableProperties() {
        List<Property> availableProperties = new ArrayList<>();
        for (Property property : propertiesList) {
            if (property.getStatus() == PropertyStatus.AVAILABLE) {
                availableProperties.add(property);
            }
        }
        return availableProperties;
    }

    public Property findPropertyById(String propertyId, List<ResidentialProperty> residentialProperties, List<CommercialProperty> commercialProperties) {
        for (Property property : residentialProperties) {
            if (property.getPropertyId().equals(propertyId)) {
                return property; // Return the property if found in residential
            }
        }
        for (Property property : commercialProperties) {
            if (property.getPropertyId().equals(propertyId)) {
                return property; // Return the property if found in commercial
            }
        }
        return null; // Return null if not found in either list
    }

    // Method to get a list of property addresses
    public List<String> getPropertyAddresses() {
        List<String> propertyAddresses = new ArrayList<>();
        for (Property property : propertiesList) {
            propertyAddresses.add(property.getAddress());
        }
        return propertyAddresses;
    }
}