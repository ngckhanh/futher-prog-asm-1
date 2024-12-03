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

    public Owner(String id, String fullName, Date dob, String contactInfo, List<Property> ownedPropertyList, List<Host> hostList) {
        super(id, fullName, dob, contactInfo);
        this.ownedPropertyList = ownedPropertyList != null ? ownedPropertyList : new ArrayList<>();
        this.hostList = hostList != null ? hostList : new ArrayList<>();
    }

    public Owner(String id, String fullName, Date dob, String contactInfo) {
        super(id, fullName, dob, contactInfo);
        this.ownedPropertyList = new ArrayList<>();
        this.hostList = new ArrayList<>();
    }

    public Owner(String ownerId) {
        super(ownerId, "", new Date(), "");
        this.ownedPropertyList = new ArrayList<>();
        this.hostList = new ArrayList<>();
    }



    public void addOwnedProperty(Property property) {
        this.ownedPropertyList.add(property);
    }
    public void addHost(Host host) {
        this.hostList.add(host);
    }

    public List<Host> getHostList() {
        return hostList;
    }

    public List<Property> getOwnedPropertyList() {
        return ownedPropertyList;
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
                ", hostList='" + (hostListIds.length() > 0 ? hostListIds.toString() : "None") + '\'' +
                ", ownedPropertyList='" + (ownedPropertyListIds.length() > 0 ? ownedPropertyListIds.toString() : "None") + '\'' +
                '}';
    }

}
