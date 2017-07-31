package org.insacc.mobilechallenge.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by can on 31.07.2017.
 */

public class AvatarResponse {
    @SerializedName("small")
    private AvatarHttpUrl mSmallAvatarUrl;
    @SerializedName("tiny")
    private AvatarHttpUrl mTinyAvatarUrl;

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
}
