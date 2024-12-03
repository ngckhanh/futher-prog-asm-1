package models.managers;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.RentalAgreement;
import models.enums.AgreementStatus;
import utils.RentalAgreementFileUtils.RentalAgreementReadFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RentalAgreements {
    private List<RentalAgreement> agreementsList;

    public RentalAgreements() {
        System.out.println("Agreements initialized" + Thread.currentThread().getStackTrace()[2]);
        this.agreementsList = new ArrayList<>();
        loadFromFile();
    }

    // Method to load agreements from file
    public void loadFromFile() {
        try {
            List<RentalAgreement> agreements = RentalAgreementReadFile.readRentalAgreementsFromFile();

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


}