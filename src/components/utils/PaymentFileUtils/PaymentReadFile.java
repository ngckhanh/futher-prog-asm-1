package utils.PaymentFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.entities.CommercialProperty;
import models.entities.Payment;
import models.entities.Payments;
import models.enums.AgreementStatus;
import models.enums.PaymentMethod;
import models.enums.PeriodType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PaymentReadFile {
    public static final String FILE_PATH = "src/components/resource/data/paymentData/payment.txt";

    public static Payments payments;

    static{
        payments = new Payments();
    }

    public static Payment getPaymentRecordByID(String paymentId) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Use a more precise matching approach
                if (line.contains("paymentId='" + paymentId + "'")) {
                    return parsePaymentLine(line);
                }
            }
        } catch (IOException e) {
            // Handle file reading exceptions
            throw new IOException("Error reading payment records from file: " + e.getMessage(), e);
        }

        // Optionally log that the payment was not found
        System.out.println("Payment record with ID '" + paymentId + "' not found.");
        return null; // Return null if no record is found
    }

    public static List<Payment> readPaymentsFromFile(String filePath) throws IOException {
        List<Payment> payments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                payments.add(parsePaymentLine(line));
            }
        }
        return payments;
    }

    private static String extractValue(String line, String prefix, String suffix) {
        int startIndex = line.indexOf(prefix);
        if (startIndex == -1) return null;
        startIndex += prefix.length();

        int endIndex = suffix != null ? line.indexOf(suffix, startIndex) : line.length();
        if (endIndex == -1) return null;

        return line.substring(startIndex, endIndex).trim();
    }


    //payment.txt: Payment{paymentId='p-02', amount='$287.32', paymentDate='Wed Nov 06 23:59:33 ICT 2024', status='Cash'}
    public static Payment parsePaymentLine(String line) throws IOException {
            // Extract and parse individual fields
        String paymentId = extractValue(line, "paymentId='", "'");

        // Parse the payment amount
        String amountStr = extractValue(line, "amount='", "'");
        double amount = amountStr != null ? Double.parseDouble(amountStr.replace("$", "").trim()) : 0.0;

        // Parse the payment date
        String dateString = extractValue(line, "paymentDate='", "'");
        Date paymentDate = null;
        if (dateString != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                paymentDate = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();  // Optionally log or handle the exception
            }
        }

            // Parse the payment method
        String paymentMethodString = extractValue(line, "method='", "'");
        PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentMethodString.toUpperCase());

            // Return the constructed Payment object
            return new Payment(paymentId, amount, paymentDate, paymentMethod);
    }

    // Method to display Payments list
    public static void displayPayments(List<Payment> payments) {
        for (Payment payment : payments) {
            System.out.println(payment);
        }
    }

}
