package h_mal.appttude.com.driver.Objects;

public class LogbookObject {

    public String photoString;
    public String v5cnumber;

    public LogbookObject(String photoString, String v5cnumber) {
        this.photoString = photoString;
        this.v5cnumber = v5cnumber;
    }

    public LogbookObject() {
    }

    public String getPhotoString() {
        return photoString;
    }

    public String getV5cnumber() {
        return v5cnumber;
    }
}
