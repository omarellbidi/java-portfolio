package bank;

import java.util.Date;

/**
 * Represents a customer of the bank. Each customer is uniquely identified by an ID
 * and has personal attributes like name and birth date.
 */
public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private Date birthDay;

    public Customer(String id, String firstName, String lastName, Date birthDay) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }
}

