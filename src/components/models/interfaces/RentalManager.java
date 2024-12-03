package models.interfaces;

/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import java.util.List;
import java.io.IOException;
import models.entities.RentalAgreement;
import models.enums.AgreementStatus;

public interface RentalManager {
    void addRentalAgreement(RentalAgreement agreement);
    void updateRentalAgreement(RentalAgreement agreement);
    void deleteRentalAgreement(RentalAgreement agreement);

    RentalAgreement getRentalAgreementById(String agreementId);
    List<RentalAgreement> getAllRentalAgreements();
    List<RentalAgreement> getRentalAgreementsByOwnerName(String ownerName);
    List<RentalAgreement> getRentalAgreementsByPropertyAddress(String propertyAddress);
    List<RentalAgreement> getRentalAgreementsByStatus(String status);
}