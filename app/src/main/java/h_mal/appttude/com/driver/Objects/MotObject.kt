package h_mal.appttude.com.driver.Objects;

public class MotObject {

    public String motImageString;
    public String motExpiry;

    public MotObject() {
    }

    public MotObject(String motImageString, String motExpiry) {
        this.motImageString = motImageString;
        this.motExpiry = motExpiry;
    }

    public String getMotImageString() {
        return motImageString;
    }

    public String getMotExpiry() {
        return motExpiry;
    }
}
