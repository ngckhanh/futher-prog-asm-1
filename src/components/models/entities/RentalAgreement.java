package models.entities;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.enums.AgreementStatus;
import models.enums.PeriodType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


//import static utils.RentalAgreementFileUtils.RentalAgreementReadFile.getRentalAgreementByID;
//import utils.RentalAgreementFileUtils.RentalAgreementReadFile.readRentalAgreementFromFile;

public class RentalAgreement {
    private final String agreementId;
    private Host host;
    private Property property;
    private Tenant mainTenant;
    private List<Tenant> subTenants;
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

    public RentalAgreement(String agreementId) {
        this.agreementId = agreementId;
        this.subTenants = new ArrayList<>();
        this.status = AgreementStatus.NEW;
        //this.status = status;
    }

    public void addSubTenant(Tenant subTenant) {
        if (subTenant != null) {
            subTenants.add(subTenant);
        }
    }

//    public static void viewAllRentalAgreements() throws IOException {
//        String ANSI_RESET = "\u001B[0m";
//        String ANSI_GREEN = "\u001B[32m";
//        String ANSI_BLUE = "\u001B[34m";
//        String ANSI_CYAN = "\u001B[36m";
//        String ANSI_RED = "\u001B[31m";
//        String yellow = "\u001B[33m";
//        Scanner scanner = new Scanner(System.in);
//
//        List<RentalAgreement> agreementList = rentalAgreementReadFile.readRentalAgreementFromFile("src/components/resource/data/rentalAgreementData/rental_agreement.txt");
//
//        // Check if the list is empty or null
//        if (agreementList == null || agreementList.isEmpty()) {
//            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
//            System.out.println("╟" + ANSI_CYAN + "        RENTAL AGREEMENTS LIST IS EMPTY" + "                ║");
//            System.out.println("╟────────────────────────────────────────────────────────╢" + ANSI_RESET);
//            System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
//            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);
//            System.out.print("Press any key to return...");
//            scanner.next();  // Wait for the user to press Enter
//            return;  // Exit the method if no rental agreements are found
//        }
//
//      System.out.println(ANSI_CYAN +"All RENTAL AGREEMENTS");
//
//        // Loop through the list and display rental agreements
//        for (int i = 0; i < agreementList.size(); i++) {
//            RentalAgreement rentalAgreement = agreementList.get(i);
//
//            System.out.println("[" + (i + 1) + "]" +
//                    " Agreement ID: " + rentalAgreement.getAgreementId() +
//                    " | Host: " + rentalAgreement.getHost() +
//                    " | Property: " + rentalAgreement.getProperty() +
//                    " | Main Tenant: " + rentalAgreement.getMainTenant() +
//                    " | Sub Tenants: " + rentalAgreement.getSubTenants() +
//                    " | Period: " + rentalAgreement.getPeriod() +
//                    " | Contract Date: " + rentalAgreement.getContractDate() +
//                    " | Rental Fee: $" + rentalAgreement.getRentalFee() +
//                    " | Status: " + rentalAgreement.getStatus());
//        }
//
//        System.out.println("Press any key to return...");
//        scanner.next();
//    }


    public String getAgreementId() {
        return agreementId;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public Host getHost() {
        return host;
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
