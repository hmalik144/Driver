package h_mal.appttude.com.driver.Objects;

import h_mal.appttude.com.driver.Objects.ApprovalsObject;
import h_mal.appttude.com.driver.Objects.ArchiveObject;
import h_mal.appttude.com.driver.Objects.UserObject;
import h_mal.appttude.com.driver.Objects.WholeObject.DriverProfile;
import h_mal.appttude.com.driver.Objects.WholeObject.VehicleProfile;

public class WholeDriverObject {

    public DriverProfile driver_profile;
    public String role;
    public ArchiveObject archive;
    public UserObject user_details;
    public VehicleProfile vehicle_profile;
    public ApprovalsObject approvalsObject;
    public String driver_number;

    public WholeDriverObject(DriverProfile driver_profile, String role, ArchiveObject archive, UserObject user_details, VehicleProfile vehicle_profile, ApprovalsObject approvalsObject, String driver_number) {
        this.driver_profile = driver_profile;
        this.role = role;
        this.archive = archive;
        this.user_details = user_details;
        this.vehicle_profile = vehicle_profile;
        this.approvalsObject = approvalsObject;
        this.driver_number = driver_number;
    }

    public WholeDriverObject() {
    }

    public String getDriver_number() {
        return driver_number;
    }

    public ArchiveObject getArchive() {
        return archive;
    }

    public DriverProfile getDriver_profile() {
        return driver_profile;
    }

    public String getRole() {
        return role;
    }

    public UserObject getUser_details() {
        return user_details;
    }

    public VehicleProfile getVehicle_profile() {
        return vehicle_profile;
    }

    public ApprovalsObject getApprovalsObject() {
        return approvalsObject;
    }
}
