package org.insacc.mobilechallenge.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by can on 31.07.2017.
 */

public class AvatarResponse implements Parcelable{
    @SerializedName("small")
    private AvatarHttpUrl mSmallAvatarUrl;
    @SerializedName("tiny")
    private AvatarHttpUrl mTinyAvatarUrl;

    protected AvatarResponse(Parcel in) {
        mSmallAvatarUrl = in.readParcelable(AvatarHttpUrl.class.getClassLoader());
        mTinyAvatarUrl = in.readParcelable(AvatarHttpUrl.class.getClassLoader());
    }

    public static final Creator<AvatarResponse> CREATOR = new Creator<AvatarResponse>() {
        @Override
        public AvatarResponse createFromParcel(Parcel in) {
            return new AvatarResponse(in);
        }

        @Override
        public AvatarResponse[] newArray(int size) {
            return new AvatarResponse[size];
        }
    };

    public AvatarHttpUrl getSmallAvatarUrl() {
        return mSmallAvatarUrl;
    }

    public void setSmallAvatarUrl(AvatarHttpUrl mSmallAvatarUrl) {
        this.mSmallAvatarUrl = mSmallAvatarUrl;
    }

    public AvatarHttpUrl getTinyAvatarUrl() {
        return mTinyAvatarUrl;
    }

    public void setTinyAvatarUrl(AvatarHttpUrl mTinyAvatarUrl) {
        this.mTinyAvatarUrl = mTinyAvatarUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mSmallAvatarUrl, flags);
        dest.writeParcelable(mTinyAvatarUrl, flags);
    }
}
