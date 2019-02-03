package org.insacc.mobilechallenge.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PhotoLinks implements Parcelable {
    @SerializedName("raw")
    private String mRaw;
    @SerializedName("full")
    private String mFull;
    @SerializedName("regular")
    private String mRegular;
    @SerializedName("small")
    private String mSmall;
    @SerializedName("thumb")
    private String mThumb;

    protected PhotoLinks(Parcel in) {
        mRaw = in.readString();
        mFull = in.readString();
        mRegular = in.readString();
        mSmall = in.readString();
        mThumb = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mRaw);
        dest.writeString(mFull);
        dest.writeString(mRegular);
        dest.writeString(mSmall);
        dest.writeString(mThumb);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhotoLinks> CREATOR = new Creator<PhotoLinks>() {
        @Override
        public PhotoLinks createFromParcel(Parcel in) {
            return new PhotoLinks(in);
        }

        @Override
        public PhotoLinks[] newArray(int size) {
            return new PhotoLinks[size];
        }
    };

    public String getRaw() {
        return mRaw;
    }

    public void setRaw(String mRaw) {
        this.mRaw = mRaw;
    }

    public String getFull() {
        return mFull;
    }

    public void setFull(String mFull) {
        this.mFull = mFull;
    }

    public String getRegular() {
        return mRegular;
    }

    public void setRegular(String mRegular) {
        this.mRegular = mRegular;
    }

    public String getSmall() {
        return mSmall;
    }

    public void setSmall(String mSmall) {
        this.mSmall = mSmall;
    }

    public String getThumb() {
        return mThumb;
    }

    public void setThumb(String mThumb) {
        this.mThumb = mThumb;
    }
}
