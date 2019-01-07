package h_mal.appttude.com.driver.Objects;

public class VehicleProfileObject {

    public String reg;
    public String make;
    public String model;
    public String colour;
    public String keeperName;
    public String keeperAddress;
    public String keeperPostCode;
    public String startDate;
    public boolean seized;
    public MotObject motObject;
    public InsuranceObject insuranceObject;
    public LogbookObject logbookObject;

    public VehicleProfileObject() {
    }

    public String getReg() {
        return reg;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColour() {
        return colour;
    }

    public String getKeeperName() {
        return keeperName;
    }

    public String getKeeperAddress() {
        return keeperAddress;
    }

    public String getKeeperPostCode() {
        return keeperPostCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public boolean isSeized() {
        return seized;
    }

    public MotObject getMotObject() {
        return motObject;
    }

    public InsuranceObject getInsuranceObject() {
        return insuranceObject;
    }

    public LogbookObject getLogbookObject() {
        return logbookObject;
    }
}
