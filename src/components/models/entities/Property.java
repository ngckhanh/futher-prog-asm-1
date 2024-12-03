package models.entities;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.enums.PropertyStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Property {
    private String propertyId;
    private String address;
    private double pricing;
    private PropertyStatus status;
    private Owner owner;
    private String ownerId;
    private List<Host> hostList;
    private List<String> hostIds;

    // Constructor
    public Property(String propertyId, String address, double pricing, PropertyStatus status, String ownerId, List<String> hostIds) {
        this.propertyId = propertyId;
        this.address = address;
        this.pricing = pricing;
        this.status = status;
        this.ownerId = ownerId;
        this.hostIds = hostIds != null ? hostIds : new ArrayList<>();
    }

    public Property(String propertyId, String address, double pricing, PropertyStatus status, Owner owner, List<Host> hostList) {
        this.propertyId = propertyId;
        this.address = address;
        this.pricing = pricing;
        this.status = status;
        this.owner = owner;
        this.hostList = hostList != null ? hostList : new ArrayList<>(); // Initialize hostList to an empty list if null
    }

    // Getters
    public String getAddress() {
        return address;
    }

    public List<Host> getHostList() { // Change to an instance method
        return hostList;
    }

    public List<String> getHostListIDs() {
        return hostList.stream()
                .map(Host::getId)
                .collect(Collectors.toList());
    }

    public Owner getOwner() {
        return owner;
    }

    public String getOwnerFullName() {
        return owner != null ? owner.getFullName() : "Unknown"; // Handle potential null owner
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setPricing(double pricing) {
        this.pricing = pricing;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    // Method to change property status
    public void updateStatus(PropertyStatus newStatus) {
        this.status = newStatus;
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
                ", ownerID='" + owner + '\'' +
                ", hostList='" + hostListIds + '\'' +
                '}';
    }
}