package org.insacc.mobilechallenge.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by can on 31.07.2017.
 * Model class which represents a photo object.
 */

public class Photo implements Parcelable {
    @SerializedName("width")
    private long mWidth;
    @SerializedName("height")
    private long mHeight;
    @SerializedName("id")
    private String mId;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("user")
    private User mUser;
    @SerializedName("urls")
    private PhotoLinks mImageUrl;

    protected Photo(Parcel in) {
        mWidth = in.readLong();
        mHeight = in.readLong();
        mId = in.readString();
        mDescription = in.readString();
        mUser = in.readParcelable(User.class.getClassLoader());
        mImageUrl = in.readParcelable(PhotoLinks.class.getClassLoader());
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public long getWidth() {
        return mWidth;
    }

    public void setWidth(long mWidth) {
        this.mWidth = mWidth;
    }

    public long getHeight() {
        return mHeight;
    }

    public void setHeight(long mHeight) {
        this.mHeight = mHeight;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    public PhotoLinks getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(PhotoLinks mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mWidth);
        dest.writeLong(mHeight);
        dest.writeString(mId);
        dest.writeString(mDescription);
        dest.writeParcelable(mUser, flags);
        dest.writeParcelable(mImageUrl, flags);
    }
}
