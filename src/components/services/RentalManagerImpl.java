package services;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.RentalAgreement;
import models.interfaces.RentalManager;
import utils.RentalAgreementFileUtils.RentalAgreementReadFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RentalManagerImpl implements RentalManager {
    private List<RentalAgreement> rentalAgreements;


    public RentalManagerImpl() {
        this.rentalAgreements = new ArrayList<>();
        loadRentalAgreementsFromFile();
    }

    private void loadRentalAgreementsFromFile() {
        try {
            List<RentalAgreement> agreementsFromFile = RentalAgreementReadFile.readRentalAgreementsFromFile("src/components/resource/data/rentalAgreementData/rental_agreement.txt");
            rentalAgreements.addAll(agreementsFromFile);
        } catch (IOException e) {
            System.out.println("Error loading rental agreements from file: " + e.getMessage());
        }
    }

    @Override
    public void addRentalAgreement(RentalAgreement agreement) {
        rentalAgreements.add(agreement);
    }

    @Override
    public void updateRentalAgreement(RentalAgreement agreement) {
        RentalAgreement existingAgreement = getRentalAgreementById(agreement.getAgreementId());
        if (existingAgreement != null) {
            existingAgreement.setHost(agreement.getHost());
            existingAgreement.setProperty(agreement.getProperty());
            existingAgreement.setMainTenant(agreement.getMainTenant());
            existingAgreement.setSubTenants(agreement.getSubTenants());
            existingAgreement.setPeriod(agreement.getPeriod());
            existingAgreement.setContractDate(agreement.getContractDate());
            existingAgreement.setRentalFee(agreement.getRentalFee());
            existingAgreement.setStatus(agreement.getStatus());
        }
    }

    @Override
    public void deleteRentalAgreement(RentalAgreement agreement) {
        if (agreement != null) {
            rentalAgreements.removeIf(existingAgreement ->
                    existingAgreement.getAgreementId().equals(agreement.getAgreementId()));
        } else {
            System.out.println("The provided rental agreement is null.");
        }
    }

    @Override
    public RentalAgreement getRentalAgreementById(String agreementId) {
        return rentalAgreements.stream()
                .filter(agreement -> agreement.getAgreementId().equals(agreementId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<RentalAgreement> getAllRentalAgreements() {
        //System.out.println("Rental Agreements Count: " + rentalAgreements.size()); // Debugging
        return new ArrayList<>(rentalAgreements);

    }

    @Override
    public List<RentalAgreement> getRentalAgreementsByOwnerName(String ownerName) {
        return rentalAgreements.stream()
                .filter(agreement -> agreement.getProperty().getOwner().getFullName().equalsIgnoreCase(ownerName))
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalAgreement> getRentalAgreementsByPropertyAddress(String propertyAddress) {
        return rentalAgreements.stream()
                .filter(agreement -> agreement.getProperty().getAddress().equalsIgnoreCase(propertyAddress))
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalAgreement> getRentalAgreementsByStatus(String status) {
        return rentalAgreements.stream()
                .filter(agreement -> agreement.getStatus().toString().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
}