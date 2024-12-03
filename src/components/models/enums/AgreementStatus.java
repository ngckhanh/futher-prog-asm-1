package models.enums;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
public enum AgreementStatus {
    NEW("New"),
    ACTIVE("Active"),
    COMPLETED("Completed");

    private final String status;

    AgreementStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
