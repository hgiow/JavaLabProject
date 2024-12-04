package entities;

public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;

    public Customer() {}

    public Customer(String tFirstName, String tLastName, String tEmail,
                    String tPassword, String tPhone, String tAddress) {

        this.firstName = tFirstName;
        this.lastName = tLastName;
        this.email = tEmail;
        this.password = tPassword;
        this.phone = tPhone;
        this.address = tAddress;

    }

    public int GetId() {
        return this.id;
    }

    public void SetId(int tId) {
        this.id = tId;
    }

    public String GetFirstName() {
        return this.firstName;
    }

    public void SetFirstName(String tFirstName) {
        this.firstName = tFirstName;
    }

    public String GetLastName() {
        return this.lastName;
    }

    public void SetLastName(String tLastName) {
        this.lastName = tLastName;
    }

    public String GetEmail() {
        return this.email;
    }

    public void SetEmail(String tEmail) {
        this.email = tEmail;
    }

    public String GetPassword() {
        return this.password;
    }

    public void SetPassword(String tPassword) {
        this.password = tPassword;
    }

    public String GetPhone() {
        return this.phone;
    }

    public void SetPhone(String tPhone) {
        this.phone = tPhone;
    }

    public String GetAddress() {
        return this.address;
    }

    public void SetAddress(String tAddress) {
        this.address = tAddress;
    }

}
