package models.managers;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.Payment;
import utils.PaymentFileUtils.PaymentReadFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Payments {
    private ArrayList<Payment> paymentsList;

    public Payments() {
        System.out.println("Payments initilalize from: " + Thread.currentThread().getStackTrace()[2]);
        this.paymentsList = new ArrayList<>();
        loadFromFile();
    }

    public void loadFromFile() {
        try {
            List<Payment> payments = PaymentReadFile.readPaymentsFromFile();

            paymentsList.addAll(payments);

            for (Payment payment : paymentsList) {
                System.out.println(payment);
            }
        } catch (IOException e) {
            System.err.println("Error loading payments: " + e.getMessage());
            paymentsList = new ArrayList<>();
        }
    }


}
