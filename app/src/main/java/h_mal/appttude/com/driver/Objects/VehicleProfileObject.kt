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

    public VehicleProfileObject() {
    }

    public VehicleProfileObject(String reg, String make, String model, String colour, String keeperName,
                                String keeperAddress, String keeperPostCode, String startDate, boolean seized) {
        this.reg = reg;
        this.make = make;
        this.model = model;
        this.colour = colour;
        this.keeperName = keeperName;
        this.keeperAddress = keeperAddress;
        this.keeperPostCode = keeperPostCode;
        this.startDate = startDate;
        this.seized = seized;
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

}
