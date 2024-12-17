package entities;

public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role;

    public Customer() {}

    public Customer(String firstName, String lastName, String email,
                    String password, String phone, String address, String role) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;

    }

    public int GetID() {
        return this.id;
    }
    public void GetID(int tId) {
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

    public String GetRole() { return role; }
    public void SetRole(String role) { this.role = role; }

}
