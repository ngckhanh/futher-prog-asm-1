package models.entities;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Tenant extends Person {
    private List<RentalAgreement> rentalAgreementList;
    private List<Property> rentedPropertyList;
    private List<Payment> paymentRecords;

    // Constructor for Tenant class
    public Tenant(String id, String fullName, Date dob, String contactInfo) {
        super(id, fullName, dob, contactInfo);
        this.rentalAgreementList = rentalAgreementList != null ? rentalAgreementList : new ArrayList<>();
        this.rentedPropertyList = rentedPropertyList != null ? rentedPropertyList : new ArrayList<>();
        this.paymentRecords = paymentRecords != null ? paymentRecords : new ArrayList<>();
    }


    public void addRentedProperty(Property rentedProperty) {
        if (rentedProperty != null) {
            this.rentedPropertyList.add(rentedProperty);
        }
    }

    public void addRentalAgreement(RentalAgreement rentalAgreement) {
        if (rentalAgreement != null) {
            this.rentalAgreementList.add(rentalAgreement);
        }
    }

    // Method to remove a rental agreement
    public void removeRentalAgreement(String agreementId) {
        rentalAgreementList.removeIf(agreement -> agreement.getAgreementId().equals(agreementId));
    }

    // Method to remove a rented property
    public void removeRentedProperty(Property property) {
        rentedPropertyList.remove(property);
    }

    public void setPaymentRecords(List<Payment> paymentRecords) {
        this.paymentRecords = paymentRecords;
    }

    public void setRentedProperties(List<Property> rentedPropertyList) {
        this.rentedPropertyList = rentedPropertyList;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreementList) {
        this.rentalAgreementList = rentalAgreementList;
    }

    public List<Payment> getPaymentRecords() {
        return paymentRecords;
    }

    public List<RentalAgreement> getRentalAgreementList() {
        return rentalAgreementList;
    }

    public List<Property> getRentedPropertyList() {
        return rentedPropertyList;
    }

    @Override
    public String toString() {
        StringBuilder rentalAgreementListIds = new StringBuilder();
        for (RentalAgreement agreement : rentalAgreementList) {
            rentalAgreementListIds.append(agreement.getAgreementId()).append(", ");
        }
        if (rentalAgreementListIds.length() > 0) {
            rentalAgreementListIds.setLength(rentalAgreementListIds.length() - 2); // Remove trailing comma
        }

        StringBuilder rentedPropertyListIds = new StringBuilder();
        for (Property property : rentedPropertyList) {
            rentedPropertyListIds.append(property.getPropertyId()).append(", ");
        }
        if (rentedPropertyListIds.length() > 0) {
            rentedPropertyListIds.setLength(rentedPropertyListIds.length() - 2); // Remove trailing comma
        }

        StringBuilder paymentRecordIds = new StringBuilder();
        for (Payment payment : paymentRecords) {
            paymentRecordIds.append(payment.getPaymentId()).append(", ");
        }
        if (paymentRecordIds.length() > 0) {
            paymentRecordIds.setLength(paymentRecordIds.length() - 2); // Remove trailing comma
        }

        return "Tenant{" +
                "ID='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", dob='" + getDob() + '\'' +
                ", contactInfo='" + getContactInfo() + '\'' +
                ", rentalAgreementList='" + (rentalAgreementListIds.length() > 0 ? rentalAgreementListIds.toString() : "None") + '\'' +
                ", rentedPropertyList='" + (rentedPropertyListIds.length() > 0 ? rentedPropertyListIds.toString() : "None") + '\'' +
                ", paymentRecords='" + (paymentRecordIds.length() > 0 ? paymentRecordIds.toString() : "None") + '\'' +
                '}';
    }

}