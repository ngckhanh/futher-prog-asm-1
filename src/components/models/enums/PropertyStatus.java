package models.enums;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
public enum PropertyStatus {
    AVAILABLE("Available"),
    RENTED("Rented"),
    UNDER_MAINTENANCE("Under_maintenance");

    private final String status;

    PropertyStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    // Method to get the enum from a string, ignoring case
    public static PropertyStatus fromString(String status) {
        for (PropertyStatus propertyStatus : PropertyStatus.values()) {
            if (propertyStatus.status.equalsIgnoreCase(status.trim())) {
                return propertyStatus;
            }
        }
        throw new IllegalArgumentException("No enum constant for status: " + status);
    }
}
