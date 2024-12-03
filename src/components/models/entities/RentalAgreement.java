package models.entities;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.enums.AgreementStatus;
import models.enums.PeriodType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RentalAgreement {
    private String agreementId;
    private Host host;
    private String hostId;
    private Property property;
    private String propertyId;
    private Tenant mainTenant;
    private String mainTenantId;
    private List<Tenant> subTenants;
    private List<String> subTenantIds;
    private PeriodType period;
    private Date contractDate;
    private double rentalFee;
    private AgreementStatus status;

    public RentalAgreement(String agreementId, Host host, Property property, Tenant mainTenant, List<Tenant> subTenants, PeriodType period, Date contractDate,  double rentalFee, AgreementStatus status) {
        this.agreementId = agreementId;
        this.mainTenant = mainTenant;
        this.subTenants = subTenants != null ? subTenants : new ArrayList<>();
        this.property = property;
        this.host = host;
        this.period = period;
        this.contractDate = contractDate;
        this.rentalFee = rentalFee;
        this.status = AgreementStatus.NEW;
        //this.status = status;
    }

    public RentalAgreement(String agreementId, PeriodType period, Date contractDate, double rentalFee, AgreementStatus status) {
        this.agreementId = agreementId;
        this.period = period;
        this.contractDate = contractDate;
        this.rentalFee = rentalFee;
        this.status = status;
    }

//    public void addSubTenant(Tenant subTenant) {
//        if (subTenant != null) {
//            subTenants.add(subTenant);
//        }
//    }

    public String getPropertyId() {
        return propertyId;
    }

    public String getAgreementId() {
        return agreementId;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public Host getHost() {
        return host;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Tenant getMainTenant() {
        return mainTenant;
    }

    public void setMainTenant(Tenant mainTenant) {
        this.mainTenant = mainTenant;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }

    public AgreementStatus getStatus() {
        return status;
    }

    public void setStatus(AgreementStatus status) {
        this.status = status;
    }

    public List<Tenant> getSubTenants() {
        return new ArrayList<>(subTenants);
    }

    public void setSubTenants(List<Tenant> subTenants) {
        this.subTenants = subTenants;
    }

    public List<String> getSubTenantIDs() {
        return subTenants.stream()
                .map(Tenant::getId)
                .collect(Collectors.toList());
    }

    public PeriodType getPeriod() {
        return period;
    }

    public void setPeriod(PeriodType period) {
        this.period = period;
    }

    public double getTotalPaymentsHavePaid() {
        double totalPayments = 0.0;

        // Add payments from the main tenant
        totalPayments += mainTenant.getPaymentRecords().stream()
                .mapToDouble(Payment::getAmount) // Assuming getAmount returns a double
                .sum();

        // Add payments from sub-tenants
        for (Tenant subTenant : subTenants) {
            totalPayments += subTenant.getPaymentRecords().stream()
                    .mapToDouble(Payment::getAmount) // Assuming getAmount returns a double
                    .sum();
        }

        return totalPayments;
    }

    public double getBalanceDue() {
        double totalPayments = getTotalPaymentsHavePaid();
        double balanceDue = rentalFee - totalPayments; // Calculate the balance due
        return Math.max(balanceDue, 0); // Return 0 if balance due is less than 0
    }

    public void displayRentalFee() {
        double rentalFee = getRentalFee();
        String formattedRentalFee = String.format("$%.2f", rentalFee);
        System.out.println("Rental Fee of this agreement: " + formattedRentalFee);
    }
    public void displayTotalPayments() {
        double totalPayments = getTotalPaymentsHavePaid();
        String formattedTotalPayments = String.format("$%.2f", totalPayments);
        System.out.println("Total payment amounts have paid: " + formattedTotalPayments);
    }

    public void displayBalanceDue() {
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";
        String blue = "\u001B[34m";
        String purple = "\u001B[35m";
        String cyan = "\u001B[36m";
        String white = "\u001B[37m";
        String reset = "\u001B[0m";
        double balanceDue = getBalanceDue();
        String formattedBalanceDue = String.format("$%.2f", balanceDue);
        if (balanceDue == 0){
            System.out.println(yellow+"This agreement have is fully paid"+reset);
        } else {
            System.out.println("The tenant still owes: " + red +formattedBalanceDue +reset);
        }
    }

    public boolean isRentalFeePaid() {
        return getTotalPaymentsHavePaid() >= rentalFee; // Allow deletion if total payments are greater than or equal to rental fee
    }

    @Override
    public String toString() {
        StringBuilder subTenantIds = new StringBuilder();
        for (Tenant tenant : subTenants) {
            subTenantIds.append(tenant.getId()).append(", ");
        }
        if (subTenantIds.length() > 0) {
            subTenantIds.setLength(subTenantIds.length() - 2); // Remove trailing comma
        }

        return "Rental Agreement{" +
                "agreementID='" + agreementId + '\'' +
                ", hostID='" + (host != null ? host.getId() : "None") + '\'' +
                ", propertyID='" + (property != null ? property.getPropertyId() : "None") + '\'' +
                ", mainTenantID='" + (mainTenant != null ? mainTenant.getId() : "None") + '\'' +
                ", subTenantIDs='" + (subTenantIds.length() > 0 ? subTenantIds.toString() : "None") + '\'' +
                ", periodType='" + period.getPeriod() + '\'' +
                ", contractDate='" + contractDate + '\'' +
                ", rentalFee='$" + rentalFee + '\'' +
                ", agreementStatus='" + status.getStatus() + '\'' +
                '}';
    }

}
