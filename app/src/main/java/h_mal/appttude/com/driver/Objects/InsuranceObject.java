package h_mal.appttude.com.driver.Objects;

import java.util.List;

public class InsuranceObject {

    public List<String> photoStrings;
    public String insurerName;
    public String expiryDate;

    public InsuranceObject() {
    }

    public InsuranceObject(List<String> photoStrings, String insurerName, String expiryDate) {
        this.photoStrings = photoStrings;
        this.insurerName = insurerName;
        this.expiryDate = expiryDate;
    }

    public List<String> getPhotoStrings() {
        return photoStrings;
    }

    public String getInsurerName() {
        return insurerName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }
}
