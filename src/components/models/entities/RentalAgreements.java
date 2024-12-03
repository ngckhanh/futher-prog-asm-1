package models.entities;

import models.enums.AgreementStatus;
import utils.RentalAgreementFileUtils.RentalAgreementReadFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.addAll;

public class RentalAgreements {
    private List<RentalAgreement> agreementsList;

    public RentalAgreements() {
        System.out.println("Agreements initialized" + Thread.currentThread().getStackTrace()[2]);
        this.agreementsList = new ArrayList<>();
        loadFromFile();
    }

    // Method to add a rental agreement
    public void addAgreement(RentalAgreement agreement) {
        agreementsList.add(agreement);
    }

    // Method to save agreements to disk
    public void saveToDisk(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(agreementsList);
        } catch (IOException e) {
            System.err.println("Error saving agreements to disk: " + e.getMessage());
        }
    }

    // Method to load agreements from file
    public void loadFromFile() {
        try {
            List<RentalAgreement> agreements = RentalAgreementReadFile.readRentalAgreementsFromFile("src/components/resource/data/rentalAgreementData/rental_agreement.txt");

            agreementsList.addAll(agreements);

            for(RentalAgreement agreement : agreementsList) {
                System.out.println(agreement);
            }
        } catch (IOException e) {
            System.err.println("Error loading agreements: " + e.getMessage());
            agreementsList = new ArrayList<>();
        }
    }

    public List<RentalAgreement> getAgreements(){
        return agreementsList;
    }

    //To get list of agreement with status = New
    public List<RentalAgreement> getNewRentalAgreements(){
        List<RentalAgreement> newAgreements = new ArrayList<>();
        for(RentalAgreement agreement : agreementsList) {
            if (agreement.getStatus() == AgreementStatus.NEW){
                newAgreements.add(agreement);
            }
        }
        return newAgreements;
    }
    // Method to get an agreement by ID
    public RentalAgreement getAgreementById(String id) {
        for (RentalAgreement agreement : agreementsList) {
            if (agreement.getAgreementId().equals(id)) {
                return agreement;
            }
        }
        return null; // Return null if not found
    }

    // Method to get all agreements
    public List<RentalAgreement> getAllAgreements() {
        return new ArrayList<>(agreementsList);
    }

    public String getAgreementDetailsById(String agreementId) {
        RentalAgreement agreement = getAgreementById(agreementId);
        if (agreement != null) {
            return agreement.toString();
        } else {
            return "Agreement not found";
        }
    }


}