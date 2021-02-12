package h_mal.appttude.com.driver.Objects.WholeObject;

import android.os.Parcel;
import android.os.Parcelable;

import h_mal.appttude.com.driver.Objects.WholeDriverObject;

public class MappedObject implements Parcelable {

    public String userId;
    public WholeDriverObject wholeDriverObject;

    public MappedObject(String userId, WholeDriverObject wholeDriverObject) {
        this.userId = userId;
        this.wholeDriverObject = wholeDriverObject;
    }

    public MappedObject() {
    }

    protected MappedObject(Parcel in) {
        userId = in.readString();
    }

    public static final Creator<MappedObject> CREATOR = new Creator<MappedObject>() {
        @Override
        public MappedObject createFromParcel(Parcel in) {
            return new MappedObject(in);
        }

        @Override
        public MappedObject[] newArray(int size) {
            return new MappedObject[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public WholeDriverObject getWholeDriverObject() {
        return wholeDriverObject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
    }


}
