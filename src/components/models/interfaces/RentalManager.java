package models.interfaces;

/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import java.util.List;
import java.io.IOException;
import models.entities.RentalAgreement;

public interface RentalManager {
    void addRentalAgreement(RentalAgreement agreement) throws IOException;
    void updateRentalAgreement(RentalAgreement currentAgreement, RentalAgreement updatedAgreement) throws IOException;
    void deleteRentalAgreement(String agreementId) throws IOException;

    RentalAgreement getRentalAgreementById(String agreementId);
    List<RentalAgreement> getAllRentalAgreements();
    List<RentalAgreement> getNewRentalAgreements();
    List<RentalAgreement> getRentalAgreementsByOwnerName(String ownerName);
    List<RentalAgreement> getRentalAgreementsByPropertyAddress(String propertyAddress);
    List<RentalAgreement> getRentalAgreementsByStatus(String status);
}