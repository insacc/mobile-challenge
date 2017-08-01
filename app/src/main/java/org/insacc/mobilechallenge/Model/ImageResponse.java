package org.insacc.mobilechallenge.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by can on 31.07.2017.
 */

public class ImageResponse implements Parcelable {

    @SerializedName("size")
    private String mSize;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("https_url")
    private String mHttpsUrl;

    protected ImageResponse(Parcel in) {
        mSize = in.readString();
        mUrl = in.readString();
        mHttpsUrl = in.readString();
    }

    public static final Creator<ImageResponse> CREATOR = new Creator<ImageResponse>() {
        @Override
        public ImageResponse createFromParcel(Parcel in) {
            return new ImageResponse(in);
        }

        @Override
        public ImageResponse[] newArray(int size) {
            return new ImageResponse[size];
        }
    };

    public String getSize() {
        return mSize;
    }

    public void setSize(String mSize) {
        this.mSize = mSize;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getHttpsUrl() {
        return mHttpsUrl;
    }

    public void setHttpsUrl(String mHttpsUrl) {
        this.mHttpsUrl = mHttpsUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSize);
        dest.writeString(mUrl);
        dest.writeString(mHttpsUrl);
    }
}
