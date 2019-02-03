package org.insacc.mobilechallenge.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by can on 31.07.2017.
 */

public class User implements Parcelable {
    @SerializedName("id")
    private long mID;
    @SerializedName("username")
    private String mUserName;
    @SerializedName("firstname")
    private String mFirstName;
    @SerializedName("lastname")
    private String mLastName;
    @SerializedName("city")
    private String mCity;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("avatars")
    private AvatarResponse mAvatarUrls;

    protected User(Parcel in) {
        mID = in.readLong();
        mUserName = in.readString();
        mFirstName = in.readString();
        mLastName = in.readString();
        mCity = in.readString();
        mCountry = in.readString();
        mAvatarUrls = in.readParcelable(AvatarResponse.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public long getID() {
        return mID;
    }

    public void setID(long mID) {
        this.mID = mID;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String mCity) {
        this.mCity = mCity;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public AvatarResponse getAvatarUrls() {
        return mAvatarUrls;
    }

    public void setAvatarUrls(AvatarResponse mAvatarUrls) {
        this.mAvatarUrls = mAvatarUrls;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mID);
        dest.writeString(mUserName);
        dest.writeString(mFirstName);
        dest.writeString(mLastName);
        dest.writeString(mCity);
        dest.writeString(mCountry);
        dest.writeParcelable(mAvatarUrls, flags);
    }
}
