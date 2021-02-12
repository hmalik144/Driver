package h_mal.appttude.com.driver.Objects;

public class PrivateHireObject {

    public String phImageString;
    public String phNumber;
    public String phExpiry;

    public PrivateHireObject() {
    }

    public PrivateHireObject(String phImageString, String phNumber, String phExpiry) {
        this.phImageString = phImageString;
        this.phNumber = phNumber;
        this.phExpiry = phExpiry;
    }

    public String getPhImageString() {
        return phImageString;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public String getPhExpiry() {
        return phExpiry;
    }
}
