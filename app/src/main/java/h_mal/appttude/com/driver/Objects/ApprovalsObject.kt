package h_mal.appttude.com.driver.Objects;

public class ApprovalsObject {

    public int driver_details_approval;
    public int driver_license_approval;
    public int private_hire_approval;
    public int vehicle_details_approval;
    public int mot_details_approval;
    public int insurance_details_approval;
    public int log_book_approval;
    public int private_hire_vehicle_approval;

    public ApprovalsObject() {
    }

    public ApprovalsObject(int driver_details_approval, int driver_license_approval, int private_hire_approval, int vehicle_details_approval, int mot_details_approval, int insurance_details_approval, int log_book_approval, int private_hire_vehicle_approval) {
        this.driver_details_approval = driver_details_approval;
        this.driver_license_approval = driver_license_approval;
        this.private_hire_approval = private_hire_approval;
        this.vehicle_details_approval = vehicle_details_approval;
        this.mot_details_approval = mot_details_approval;
        this.insurance_details_approval = insurance_details_approval;
        this.log_book_approval = log_book_approval;
        this.private_hire_vehicle_approval = private_hire_vehicle_approval;
    }

    public int getPh_car_approval() {
        return private_hire_vehicle_approval;
    }

    public int getDriver_details_approval() {
        return driver_details_approval;
    }

    public int getDriver_license_approval() {
        return driver_license_approval;
    }

    public int getPrivate_hire_approval() {
        return private_hire_approval;
    }

    public int getVehicle_details_approval() {
        return vehicle_details_approval;
    }

    public int getMot_details_approval() {
        return mot_details_approval;
    }

    public int getInsurance_details_approval() {
        return insurance_details_approval;
    }

    public int getLog_book_approval() {
        return log_book_approval;
    }
}
