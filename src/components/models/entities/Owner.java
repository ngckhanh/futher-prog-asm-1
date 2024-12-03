package models.entities;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Owner extends Person{
    private List<Property> ownedPropertyList;
    private List<Host> hostList;

    public Owner(String id, String fullName, Date dob, String contactInfo) {
        super(id, fullName, dob, contactInfo);
        this.ownedPropertyList = new ArrayList<>();
        this.hostList = new ArrayList<>();
    }

    public List<Host> getHostList() {
        return hostList;
    }

    public List<Property> getOwnedPropertyList() {
        return ownedPropertyList;
    }

    public void setHostLists(List<Host> hostList) {
        this.hostList = hostList;
    }

    public void setOwnedProperties(List<Property> ownedPropertyList) {
        this.ownedPropertyList = ownedPropertyList;
    }

    @Override
    public String toString() {
        StringBuilder hostListIds = new StringBuilder();
        for (Host host : hostList) {
            hostListIds.append(host.getId()).append(", ");
        }
        if (hostListIds.length() > 0) {
            hostListIds.setLength(hostListIds.length() - 2); // Remove trailing comma
        }

        StringBuilder ownedPropertyListIds = new StringBuilder();
        for (Property property : ownedPropertyList) {
            ownedPropertyListIds.append(property.getPropertyId()).append(", ");
        }
        if (ownedPropertyListIds.length() > 0) {
            ownedPropertyListIds.setLength(ownedPropertyListIds.length() - 2); // Remove trailing comma
        }

        return "Owner{" +
                "ID='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", dob='" + getDob() + '\'' +
                ", contactInfo='" + getContactInfo() + '\'' +
                ", ownedPropertyList='" + (ownedPropertyListIds.length() > 0 ? ownedPropertyListIds.toString() : "None") + '\'' +
                ", hostList='" + (hostListIds.length() > 0 ? hostListIds.toString() : "None") + '\'' +
                '}';
    }

}
