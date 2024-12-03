package models.entities;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.enums.PropertyStatus;

import java.util.List;
import java.util.stream.Collectors;

public class ResidentialProperty extends Property {
    private int numbBedrooms;
    private boolean gardenAvailable;
    private boolean petFriendly;

    public ResidentialProperty(String propertyId, String address, double pricing, PropertyStatus status, Owner owner, List<Host> hostList,
                               int numbBedrooms, boolean gardenAvailable, boolean petFriendly) {
        super(propertyId, address, pricing, status, owner, hostList);
        this.numbBedrooms = numbBedrooms;
        this.gardenAvailable = gardenAvailable;
        this.petFriendly = petFriendly;
    }

    public boolean isGardenAvailable() {
        return gardenAvailable;
    }

    public int getNumbBedrooms() {
        return numbBedrooms;
    }

    public boolean isPetFriendly() {
        return petFriendly;
    }

    @Override
    public String toString() {
        List<Host> hosts = getHostList(); // Fetch the host list from the Property class
        String hostListIds = (hosts != null && !hosts.isEmpty())
                ? hosts.stream().map(Host::getId).collect(Collectors.joining(", "))
                : "null"; // If the list is null or empty, display "null"

        return "Residential Property{" +
                "propertyID='" + getPropertyId() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", pricing='" + getPricing() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", ownerID='" + getOwner() + '\'' +
                ", hostList='" + hostListIds + '\'' + // Display host IDs or "null"
                ", numberOfBedrooms=" + numbBedrooms +
                ", gardenAvailable=" + gardenAvailable +
                ", petFriendly=" + petFriendly +
                '}';
    }

}
