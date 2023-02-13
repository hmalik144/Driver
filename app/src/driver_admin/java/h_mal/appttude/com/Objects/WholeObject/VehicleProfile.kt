package h_mal.appttude.com.Objects.WholeObject

import h_mal.appttude.com.model.InsuranceObject
import h_mal.appttude.com.model.LogbookObject
import h_mal.appttude.com.model.PrivateHireVehicleObject
import h_mal.appttude.com.model.MotObject
import h_mal.appttude.com.model.VehicleProfileObject


class VehicleProfile {
    var insurance_details: InsuranceObject? = null
    var log_book: LogbookObject? = null
    var mot_details: MotObject? = null
    var vehicle_details: VehicleProfileObject? = null
    var privateHireVehicleObject: PrivateHireVehicleObject? = null

    constructor()

    //    public VehicleProfile(InsuranceObject insurance_details, LogbookObject log_book, MotObject mot_details, VehicleProfileObject vehicle_details) {
    //        this.insurance_details = insurance_details;
    //        this.log_book = log_book;
    //        this.mot_details = mot_details;
    //        this.vehicle_details = vehicle_details;
    //    }
    constructor(
        insurance_details: InsuranceObject?,
        log_book: LogbookObject?,
        mot_details: MotObject?,
        vehicle_details: VehicleProfileObject?,
        private_hire_vehicle: PrivateHireVehicleObject?
    ) {
        this.insurance_details = insurance_details
        this.log_book = log_book
        this.mot_details = mot_details
        this.vehicle_details = vehicle_details
        privateHireVehicleObject = private_hire_vehicle
    }
}