package models.enums;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
public enum PeriodType {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    FORTNIGHTLY("Fortnightly"),
    MONTHLY("Monthly");

    private final String period;

    PeriodType(String period) {
        this.period = period;
    }
    public String getPeriod() {
        return period;
    }
}
