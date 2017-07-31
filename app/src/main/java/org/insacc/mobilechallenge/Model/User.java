package org.insacc.mobilechallenge.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by can on 31.07.2017.
 */

public class User {
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
}
