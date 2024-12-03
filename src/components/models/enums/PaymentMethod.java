package models.enums;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
public enum PaymentMethod {
    CASH("Cash"),
    CREDIT_CARD("Credit_card");

    private final String paymentMethod;

    PaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
}
