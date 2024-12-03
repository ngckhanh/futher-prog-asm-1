package models.entities;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.enums.PropertyStatus;

import java.util.ArrayList;
import java.util.List;

public class Property {
    private String propertyId;
    private String address;
    private double pricing;
    private PropertyStatus status;
    private Owner owner;
    private String ownerId;
    private List<Host> hostList;
    private List<String> hostListIds;

    // Constructor
    public Property(String propertyId, String address, double pricing, PropertyStatus status, String ownerId, List<String> hostListIds) {
        this.propertyId = propertyId;
        this.address = address;
        this.pricing = pricing;
        this.status = status;
        this.ownerId = ownerId;
        this.hostListIds = hostListIds != null ? hostListIds : new ArrayList<>(); // Initialize hostList to an empty list if null
    }

    public Property(String propertyId, String address, Double pricing, PropertyStatus status) {
        this.propertyId = propertyId;
        this.address = address;
        this.pricing = pricing;
        this.status = status;
    }

    // Getters
    public String getAddress() {
        return address;
    }

    public List<Host> getHostList() { // Change to an instance method
        return hostList;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getOwnerID() {
        return owner != null ? owner.getId() : "None"; // Handle potential null owner
    }

    public double getPricing() {
        return pricing;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }


        @Override
    public String toString() {
        StringBuilder hostListIds = new StringBuilder();
        if (hostList != null && !hostList.isEmpty()) {
            for (Host host : hostList) {
                hostListIds.append(host.getId()).append(", ");
            }
            if (hostListIds.length() > 0) {
                hostListIds.setLength(hostListIds.length() - 2); // Remove trailing comma
            }
        } else {
            hostListIds.append("None"); // Handle the case where hostList is null or empty
        }

        return "Property{" +
                "propertyId='" + propertyId + '\'' +
                ", address='" + address + '\'' +
                ", pricing=" + pricing +
                ", status=" + status +
                ", ownerID='" + owner.getId() + '\'' +
                ", hostList='" + hostListIds + '\'' +
                '}';
    }
}