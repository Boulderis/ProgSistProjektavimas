package models;

public class User {

    private long userID;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String address;
    private String password;

    public User() {

    }

    public User(String name, String surname, String phoneNumber, String email, String address, String password) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public long getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder userString = new StringBuilder();
        userString.append("Vartotojo ID: ");
        userString.append(userID);
        userString.append(", vardas: ");
        userString.append(name);
        userString.append(", pavardė: ");
        userString.append(surname);
        userString.append(", telefono numeris: ");
        userString.append(phoneNumber);
        userString.append(", elektroninis paštas: ");
        userString.append(email);
        userString.append(", adresas: ");
        userString.append(address);
        userString.append(", slaptažodis:");
        userString.append(password);
        return userString.toString();
    }

}
