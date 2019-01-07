package h_mal.appttude.com.driver.Objects;

public class DriverProfileObject {

    public String driverPic;
    public String forenames;
    public String address;
    public String postcode;
    public String dob;
    public String ni;
    public String dateFirst;

    public DriverProfileObject() {
    }

    public DriverProfileObject(String driverPic, String forenames, String address,
                               String postcode, String dob, String ni, String dateFirst) {
        this.driverPic = driverPic;
        this.forenames = forenames;
        this.address = address;
        this.postcode = postcode;
        this.dob = dob;
        this.ni = ni;
        this.dateFirst = dateFirst;
    }

    public String getDriverPic() {
        return driverPic;
    }

    public String getForenames() {
        return forenames;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getDob() {
        return dob;
    }

    public String getNi() {
        return ni;
    }

    public String getDateFirst() {
        return dateFirst;
    }

}
