package utils.PaymentFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.Owner;
import models.entities.Payment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class PaymentWriteFile {
    public static void writePaymentToFile(List<Payment> payments, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Payment payment : payments) {
                writer.write(payment.toString());
                writer.newLine();
            }
        }
    }
}
