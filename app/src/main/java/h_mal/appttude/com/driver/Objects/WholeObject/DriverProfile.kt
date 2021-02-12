package h_mal.appttude.com.driver.Objects.WholeObject;

import h_mal.appttude.com.driver.Objects.DriverProfileObject;
import h_mal.appttude.com.driver.Objects.DriversLicenseObject;
import h_mal.appttude.com.driver.Objects.PrivateHireObject;

public class DriverProfile {

    public DriverProfileObject driver_profile;
    public DriversLicenseObject driver_license;
    public PrivateHireObject private_hire;

    public DriverProfile(DriverProfileObject driver_profile, DriversLicenseObject driver_license, PrivateHireObject private_hire) {
        this.driver_profile = driver_profile;
        this.driver_license = driver_license;
        this.private_hire = private_hire;
    }

    public DriverProfile() {
    }

    public DriverProfileObject getDriver_profile() {
        return driver_profile;
    }

    public DriversLicenseObject getDriver_license() {
        return driver_license;
    }

    public PrivateHireObject getPrivate_hire() {
        return private_hire;
    }
}
