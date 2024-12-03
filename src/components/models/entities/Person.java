package models.entities;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import java.util.Date;

public class Person {
    private String id;
    private String fullName;
    private Date dob;
    private String contactInfo;

    //Constructor
    public Person(String id, String fullName, Date dob, String contactInfo) {
        this.id = id;
        this.fullName = fullName;
        this.dob = dob;
        this.contactInfo = contactInfo;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public Date getDob() {
        return dob;
    }

    public String getFullName() {
        return fullName;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + fullName;
    }

}
