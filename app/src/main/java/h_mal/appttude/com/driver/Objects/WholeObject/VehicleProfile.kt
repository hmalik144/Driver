package h_mal.appttude.com.driver.Objects.WholeObject;

import h_mal.appttude.com.driver.Objects.InsuranceObject;
import h_mal.appttude.com.driver.Objects.LogbookObject;
import h_mal.appttude.com.driver.Objects.MotObject;
import h_mal.appttude.com.driver.Objects.PrivateHireVehicleObject;
import h_mal.appttude.com.driver.Objects.VehicleProfileObject;

public class VehicleProfile {

    public InsuranceObject insurance_details;
    public LogbookObject log_book;
    public MotObject mot_details;
    public VehicleProfileObject vehicle_details;
    public PrivateHireVehicleObject private_hire_vehicle;

    public VehicleProfile() {
    }

//    public VehicleProfile(InsuranceObject insurance_details, LogbookObject log_book, MotObject mot_details, VehicleProfileObject vehicle_details) {
//        this.insurance_details = insurance_details;
//        this.log_book = log_book;
//        this.mot_details = mot_details;
//        this.vehicle_details = vehicle_details;
//    }

        public VehicleProfile(InsuranceObject insurance_details, LogbookObject log_book, MotObject mot_details, VehicleProfileObject vehicle_details, PrivateHireVehicleObject private_hire_vehicle) {
        this.insurance_details = insurance_details;
        this.log_book = log_book;
        this.mot_details = mot_details;
        this.vehicle_details = vehicle_details;
        this.private_hire_vehicle = private_hire_vehicle;
    }


    public PrivateHireVehicleObject getPrivateHireVehicleObject() {
        return private_hire_vehicle;
    }

    public InsuranceObject getInsurance_details() {
        return insurance_details;
    }

    public LogbookObject getLog_book() {
        return log_book;
    }

    public MotObject getMot_details() {
        return mot_details;
    }

    public VehicleProfileObject getVehicle_details() {
        return vehicle_details;
    }
}
