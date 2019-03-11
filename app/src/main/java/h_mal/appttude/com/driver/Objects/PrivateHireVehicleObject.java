package h_mal.appttude.com.driver.Objects;

public class PrivateHireVehicleObject {

    public String phCarImageString;
    public String phCarNumber;
    public String phCarExpiry;

    public PrivateHireVehicleObject(String phCarImageString, String phCarNumber, String phCarExpiry) {
        this.phCarImageString = phCarImageString;
        this.phCarNumber = phCarNumber;
        this.phCarExpiry = phCarExpiry;
    }

    public PrivateHireVehicleObject() {
    }

    public String getPhCarImageString() {
        return phCarImageString;
    }

    public String getPhCarNumber() {
        return phCarNumber;
    }

    public String getPhCarExpiry() {
        return phCarExpiry;
    }
}
