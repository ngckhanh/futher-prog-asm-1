package models.entities;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Host extends Person{
    private List<Property> managedPropertyList;
    private List<Owner> cooperatingOwnerList;
    private List<RentalAgreement> rentalAgreementList;

    public Host(String id, String fullName, Date dob, String contactInfo
                ) {
        super(id, fullName, dob, contactInfo);
        this.managedPropertyList = new ArrayList<>();
        this.cooperatingOwnerList = new ArrayList<>();
        this.rentalAgreementList = new ArrayList<>();
    }


    public void addManagedProperty(Property property) {
        if (property != null && !managedPropertyList.contains(property)) {
            managedPropertyList.add(property);
            System.out.println("Property " + property.getPropertyId() + " added to host " + this.getId());
        } else {
            System.out.println("Property " + (property != null ? property.getPropertyId() : "null") + " already exists in host " + this.getId());
        }
    }

    public void addCooperatingOwner(Owner owner) {
        this.cooperatingOwnerList.add(owner);
    }

    public void addRentalAgreement(RentalAgreement rentalAgreement) {
        rentalAgreementList.add(rentalAgreement);
    }

    public void removeManagedProperty(Property property) {
        managedPropertyList.remove(property);
    }

    public void removeCooperatingOwner(Owner owner) {
        cooperatingOwnerList.remove(owner );
    }


    public void removeRentalAgreement(String agreementId) {
        rentalAgreementList.removeIf(agreement -> agreement.getAgreementId().equals(agreementId));
    }

    public List<Owner> getCooperatingOwnerList() {
        return cooperatingOwnerList;
    }

    public void setCooperatingOwners(List<Owner> cooperatingOwnerList) {
        this.cooperatingOwnerList = cooperatingOwnerList;
    }

    public List<Property> getManagedPropertyList() {
        return managedPropertyList;
    }

    public void setManagedProperties(List<Property> managedPropertyList) {
        this.managedPropertyList = managedPropertyList;
    }

    public List<RentalAgreement> getRentalAgreementList() {
        return rentalAgreementList;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreementList) {
        this.rentalAgreementList = rentalAgreementList;
    }

    @Override
    public String toString() {
        StringBuilder managedPropertyListIds = new StringBuilder();
        for (Property property : managedPropertyList) {
            managedPropertyListIds.append(property.getPropertyId()).append(", ");
        }
        if (managedPropertyListIds.length() > 0) {
            managedPropertyListIds.setLength(managedPropertyListIds.length() - 2); // Remove trailing comma
        }

        StringBuilder cooperatingOwnerListIds = new StringBuilder();
        for (Owner owner : cooperatingOwnerList) {
            cooperatingOwnerListIds.append(owner.getId()).append(", ");
        }
        if (cooperatingOwnerListIds.length() > 0) {
            cooperatingOwnerListIds.setLength(cooperatingOwnerListIds.length() - 2); // Remove trailing comma
        }

        StringBuilder rentalAgreementListIds = new StringBuilder();
        for (RentalAgreement agreement : rentalAgreementList) {
            rentalAgreementListIds.append(agreement.getAgreementId()).append(", ");
        }
        if (rentalAgreementListIds.length() > 0) {
            rentalAgreementListIds.setLength(rentalAgreementListIds.length() - 2); // Remove trailing comma
        }

        return "Host{" +
                "ID='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", dob='" + getDob() + '\'' +
                ", contactInfo='" + getContactInfo() + '\'' +
                ", managedPropertyList='" + (managedPropertyListIds.length() > 0 ? managedPropertyListIds.toString() : "None") + '\'' +
                ", cooperatingOwnerList='" + (cooperatingOwnerListIds.length() > 0 ? cooperatingOwnerListIds.toString() : "None") + '\'' +
                ", rentalAgreementList='" + (rentalAgreementListIds.length() > 0 ? rentalAgreementListIds.toString() : "None") + '\'' +
                '}';
    }

}
