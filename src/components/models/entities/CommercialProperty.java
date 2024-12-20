package models.entities;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.enums.PropertyStatus;

import java.util.List;
import java.util.stream.Collectors;

public class CommercialProperty extends Property{
    private String businessType;
    private int parkingSpace;
    private double squareFootage;

    public CommercialProperty(String propertyId, String address, double pricing, PropertyStatus status,
                              String businessType, int parkingSpace, double squareFootage) {
        super(propertyId, address, pricing, status);
        this.businessType = businessType;
        this.parkingSpace = parkingSpace;
        this.squareFootage = squareFootage;
    }

    public String getBusinessType() {
        return businessType;
    }

    public int getParkingSpace() {
        return parkingSpace;
    }

    public double getSquareFootage() {
        return squareFootage;
    }

    @Override
    public String toString() {
        // Using streams to create a comma-separated string of host IDs
        List<Host> hosts = getHostList(); // Fetch the host list from the Property class
        String hostListIds = (hosts != null && !hosts.isEmpty())
                ? hosts.stream().map(Host::getId).collect(Collectors.joining(", "))
                : "null"; // If the list is null or empty, display "null"

        // Format pricing as currency
        String formattedPricing = String.format("$%.2f", getPricing());

        // Format status to ensure it's not in uppercase
        String formattedStatus = getStatus().toString(); // Adjust if needed

        return "Commercial Property{" +
                "propertyID='" + getPropertyId() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", pricing='" + formattedPricing + '\'' + // Enclose pricing in quotes
                ", status='" + formattedStatus + '\'' + // Ensure status is formatted correctly
                ", ownerID='" + getOwnerID() + '\'' +
                ", hostList='" + hostListIds + '\'' + // Display host IDs or "null"
                ", businessType='" + businessType + '\'' + // Enclose businessType in quotes
                ", parkingSpaces=" + parkingSpace +
                ", squareFootage=" + squareFootage +
                '}';
    }

}
