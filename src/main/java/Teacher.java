import java.util.Vector;

public class Teacher {
    private String name;
    private String personalLink;
    private String address;
    private String numberPhone;
    private String email;

    Teacher() {
        this.name = "";
        this.address = "";
        this.personalLink = "";
        this.address = "";
        this.numberPhone = "";
        this.email = "";
    }

    Teacher(String _name, String _personalLink, String _address, String _numberPhone,
            String _email) {
        this.name = _name;
        this.address = _address;
        this.personalLink = _personalLink;
        this.email = _email;
        this.numberPhone = _numberPhone;
    }

    void printTeacher() {
        System.out.println(name + ":" + "\n\t" + address + "\n\t" + personalLink
                + "\n\t" + email + "\n\t" + numberPhone);
    }

    void setAll(String _name, String _personalLink, String _address, String _numberPhone, String _email) {
        this.name = _name;
        this.address = _address;
        this.personalLink = _personalLink;
        this.email = _email;
        this.numberPhone = _numberPhone;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPersonalLink() {
        return personalLink;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPersonalLink(String personalLink) {
        this.personalLink = personalLink;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

}