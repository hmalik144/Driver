package h_mal.appttude.com.driver.Objects;

import java.util.HashMap;
import java.util.List;

public class ArchiveObject {

    public HashMap<String,DriversLicenseObject> driver_license;
    public HashMap<String,PrivateHireObject>  private_hire;
    public HashMap<String,VehicleProfileObject>  vehicle_details;
    public HashMap<String,InsuranceObject>  insurance_details;
    public HashMap<String,MotObject>  mot_details;
    public HashMap<String,LogbookObject>  log_book;
    public HashMap<String,PrivateHireVehicleObject>  private_hire_vehicle;

    public ArchiveObject() {
    }

    public ArchiveObject(HashMap<String, DriversLicenseObject> driver_license, HashMap<String, PrivateHireObject> private_hire, HashMap<String, VehicleProfileObject> vehicle_details, HashMap<String, InsuranceObject> insurance_details, HashMap<String, MotObject> mot_details, HashMap<String, LogbookObject> log_book, HashMap<String, PrivateHireVehicleObject> private_hire_vehicle) {
        this.driver_license = driver_license;
        this.private_hire = private_hire;
        this.vehicle_details = vehicle_details;
        this.insurance_details = insurance_details;
        this.mot_details = mot_details;
        this.log_book = log_book;
        this.private_hire_vehicle = private_hire_vehicle;
    }

    public HashMap<String, PrivateHireVehicleObject> getPh_car() {
        return private_hire_vehicle;
    }

    public HashMap<String, DriversLicenseObject> getDriver_license() {
        return driver_license;
    }

    public HashMap<String, PrivateHireObject> getPrivate_hire() {
        return private_hire;
    }

    public HashMap<String, VehicleProfileObject> getVehicle_details() {
        return vehicle_details;
    }

    public HashMap<String, InsuranceObject> getInsurance_details() {
        return insurance_details;
    }

    public HashMap<String, MotObject> getMot_details() {
        return mot_details;
    }

    public HashMap<String, LogbookObject> getLog_book() {
        return log_book;
    }
}
