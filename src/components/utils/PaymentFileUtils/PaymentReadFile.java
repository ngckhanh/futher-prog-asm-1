package utils.PaymentFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.entities.*;
import models.enums.PaymentMethod;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PaymentReadFile {
    public static final String FILE_PATH = "src/components/resource/data/paymentData/payment.txt";
    private static Map<String, Payment> paymentMap;
    public static Map<String, Payment> getPaymentMap() throws IOException {
        if (paymentMap == null) {
            paymentMap = readPaymentsFirstTime();
            paymentMap = readPaymentsSecondTime(paymentMap);
        }
        return paymentMap;
    }

    public static List<Payment> readPaymentsFromFile() throws IOException {
        Map<String, Payment> payments = readPaymentsFirstTime();

        readPaymentsSecondTime(payments);
        return new ArrayList<>(payments.values());
    }

    public static void displayPayments(List<Payment> payments) {
        for (Payment payment : payments) {
            System.out.println(payment);
        }
    }

    private static Map<String, Payment> readPaymentsFirstTime() throws IOException {
        Map<String, Payment> cachedPayments = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String paymentId = extractValue(line, "paymentId='", "'");
                    String amountStr = extractValue(line, "amount='", "'");
                    double amount = parseAmount(amountStr);

                    String paymentDateStr = extractValue(line, "paymentDate='", "'");

                    Date paymentDate = null;
                    if (paymentDateStr != null) {
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                            paymentDate = dateFormat.parse(paymentDateStr);
                        } catch (ParseException e){
                            e.printStackTrace();
                        }
                    }

                    String methodStr = extractValue(line, "method='", "'");
                    PaymentMethod method = PaymentMethod.valueOf(methodStr.toUpperCase());

                    Payment payment = new Payment(paymentId, amount, paymentDate, method);
                    cachedPayments.put(paymentId, payment);

                } catch (IllegalArgumentException e){
                    System.err.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cachedPayments;
    }

    private static Map<String, Payment> readPaymentsSecondTime(Map<String, Payment> paymentsMap) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {

                String paymentId = extractValue(line, "paymentId='", "'");
                Payment payment = paymentsMap.get(paymentId);
                if (payment == null) {
                    System.err.println("Error: Payment not found: " + paymentId);
                    continue;
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return paymentsMap;
    }

    private static String extractValue(String line, String prefix, String suffix) {
        int startIndex = line.indexOf(prefix);
        if (startIndex == -1) return null;
        startIndex += prefix.length();
        int endIndex = line.indexOf(suffix, startIndex);
        return (endIndex != -1) ? line.substring(startIndex, endIndex).trim() : null;
    }

    private static double parseAmount(String amountStr) {
        return amountStr != null && !amountStr.isEmpty()
                ? Double.parseDouble(amountStr.replace("$", "").replace(",", ""))
                : 0.0;
    }
}
