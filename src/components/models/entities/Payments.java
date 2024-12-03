package models.entities;

import utils.PaymentFileUtils.PaymentReadFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Payments {
    private ArrayList<Payment> paymentsList;

    public Payments() {
        System.out.println("Payments initilalize from: " + Thread.currentThread().getStackTrace()[2]);
        this.paymentsList = new ArrayList<>();
        loadFromFile();
    }

    public void addPayment(Payment payment) {
        this.paymentsList.add(payment);
    }

    public void saveToDisk(String filePath){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(paymentsList);
        } catch (IOException e) {
            System.err.println("Error saving payments to disk: " + e.getMessage());
        }
    }
    public void loadFromFile() {
        try {
            List<Payment> payments = PaymentReadFile.readPaymentsFromFile("");

            paymentsList.addAll(payments);
            //System.out.println("Loaded payments: " + paymentsList.size());

            for (Payment payment : paymentsList) {
                System.out.println(payment);
            }
        } catch (IOException e) {
            System.err.println("Error loading payments: " + e.getMessage());
            paymentsList = new ArrayList<>();
            //throw new RuntimeException(e);
        }
    }

    public List<Payment> getPayments(){
        return paymentsList;
    }

    public Payment getPaymentById(String paymentId) {
        for (Payment payment : paymentsList) {
            if (payment.getPaymentId().equals(paymentId)){
                return payment;
            }
        }
        return null;
    }

    public boolean removePayment(String paymentId) {
        return paymentsList.removeIf(payment -> payment.getPaymentId().equals(paymentId));
    }

    public String getPaymentDetailsById(String paymentId) {
        Payment payment = getPaymentById(paymentId);
        if (payment != null) {
            return payment.toString();
        } else {
            return "Payment not found";
        }
    }

}
