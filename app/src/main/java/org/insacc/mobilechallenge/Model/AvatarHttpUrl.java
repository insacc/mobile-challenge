package org.insacc.mobilechallenge.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by can on 31.07.2017.
 */

public class AvatarHttpUrl implements Parcelable {
    @SerializedName("https")
    private String mUrl;

    protected AvatarHttpUrl(Parcel in) {
        mUrl = in.readString();
    }

    public static final Creator<AvatarHttpUrl> CREATOR = new Creator<AvatarHttpUrl>() {
        @Override
        public AvatarHttpUrl createFromParcel(Parcel in) {
            return new AvatarHttpUrl(in);
        }

        @Override
        public AvatarHttpUrl[] newArray(int size) {
            return new AvatarHttpUrl[size];
        }
    };

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
    }
}
