package org.insacc.mobilechallenge.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by can on 31.07.2017.
 * Model class which represents a photo object.
 */

public class Photo {

    @SerializedName("width")
    private long mWidth;
    @SerializedName("height")
    private long mHeight;
    @SerializedName("id")
    private long mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("camera")
    private String mCamera;

    @SerializedName("lens")
    private String mLens;

    @SerializedName("times_viewed")
    private long mTimesViewed;

    @SerializedName("rating")
    private float mRating;
    @SerializedName("comments_count")
    private int mCommentsCount;
    @SerializedName("favorites_count")
    private int mFavoritesCount;

    @SerializedName("highest_rating")
    private float mHighestRating;

    @SerializedName("user")
    private User mUser;

    @SerializedName("image_url")
    private String mImageUrl;


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


    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getCamera() {
        return mCamera;
    }

    public void setCamera(String mCamera) {
        this.mCamera = mCamera;
    }

    public String getLens() {
        return mLens;
    }

    public void setLens(String mLens) {
        this.mLens = mLens;
    }

    public long getTimesViewed() {
        return mTimesViewed;
    }

    public void setTimesViewed(long mTimesViewed) {
        this.mTimesViewed = mTimesViewed;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float mRating) {
        this.mRating = mRating;
    }

    public int getCommentsCount() {
        return mCommentsCount;
    }

    public void setCommentsCount(int mCommentsCount) {
        this.mCommentsCount = mCommentsCount;
    }

    public int getFavoritesCount() {
        return mFavoritesCount;
    }

    public void setmFavoritesCount(int mFavoritesCount) {
        this.mFavoritesCount = mFavoritesCount;
    }

    public float getmHighestRating() {
        return mHighestRating;
    }

    public void setmHighestRating(float mHighestRating) {
        this.mHighestRating = mHighestRating;
    }

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
