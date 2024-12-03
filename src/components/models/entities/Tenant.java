package models.entities;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Tenant extends Person {
    private List<RentalAgreement> rentalAgreementList;
    private List<Property> rentedPropertyList;
    private List<Payment> paymentRecords;

    // Constructor for Tenant class
    public Tenant(String id, String fullName, Date dob, String contactInfo,
                  List<RentalAgreement> rentalAgreementList, List<Property> rentedPropertyList,
                  List<Payment> paymentRecords) {
        super(id, fullName, dob, contactInfo);
        this.rentalAgreementList = rentalAgreementList != null ? rentalAgreementList : new ArrayList<>();
        this.rentedPropertyList = rentedPropertyList != null ? rentedPropertyList : new ArrayList<>();
        this.paymentRecords = paymentRecords != null ? paymentRecords : new ArrayList<>();
    }

    // Constructor for creating a tenant with just an ID
    public Tenant(String tenantId) {
        super(tenantId, "Unknown", new Date(), "Unknown");
        this.rentalAgreementList = new ArrayList<>();
        this.rentedPropertyList = new ArrayList<>();
        this.paymentRecords = new ArrayList<>();
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

    public void addPaymentRecord(Payment paymentRecord) {
        if (paymentRecord != null) {
            this.paymentRecords.add(paymentRecord);
        }
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
                ", paymentRecord='" + (paymentRecordIds.length() > 0 ? paymentRecordIds.toString() : "None") + '\'' +
                '}';
    }

}