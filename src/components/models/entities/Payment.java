package models.entities;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.enums.PaymentMethod;

import java.util.Date;

public class Payment {
    private String paymentId;
    private double amount;
    private Date paymentDate;
    private PaymentMethod paymentMethod;

    //Constructor
    public Payment(String paymentId, double amount, Date paymentDate, PaymentMethod paymentMethod) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
