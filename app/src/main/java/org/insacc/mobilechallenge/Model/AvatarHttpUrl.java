package org.insacc.mobilechallenge.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by can on 31.07.2017.
 */

public class AvatarHttpUrl {

    @SerializedName("https")
    private String mUrl;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
