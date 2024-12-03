package utils.RentalAgreementFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.entities.RentalAgreement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class RentalAgreementWriteFile {
    private static final String FILE_PATH = "src/components/resource/data/rentalAgreementData/rental_agreement.txt";

    public static void writeRentalAgreementToFile(List<RentalAgreement> rentalAgreements, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {  // true to append data
            for (RentalAgreement rentalAgreement : rentalAgreements) {
                writer.write(rentalAgreement.toString());
                writer.newLine();  // Adds a new line after each rental agreement
            }
            System.out.println("Rental Agreements have been successfully written to the file!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing rental agreements to the file: " + e.getMessage());
            throw e;  // Propagate the exception after logging it
        }
    }

}
