package h_mal.appttude.com.driver.Objects;

public class DriversLicenseObject {

    public String licenseImageString;
    public String licenseNumber;
    public String licenseExpiry;

    public DriversLicenseObject() {
    }

    public DriversLicenseObject(String licenseImageString, String licenseNumber, String licenseExpiry) {
        this.licenseImageString = licenseImageString;
        this.licenseNumber = licenseNumber;
        this.licenseExpiry = licenseExpiry;
    }

    public String getLicenseImageString() {
        return licenseImageString;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getLicenseExpiry() {
        return licenseExpiry;
    }
}
