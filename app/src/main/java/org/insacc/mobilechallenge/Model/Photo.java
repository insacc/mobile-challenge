package org.insacc.mobilechallenge.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("images")
    private List<ImageResponse> mImageUrl;

    protected Photo(Parcel in) {
        mWidth = in.readLong();
        mHeight = in.readLong();
        mId = in.readLong();
        mName = in.readString();
        mCamera = in.readString();
        mLens = in.readString();
        mTimesViewed = in.readLong();
        mRating = in.readFloat();
        mCommentsCount = in.readInt();
        mFavoritesCount = in.readInt();
        mHighestRating = in.readFloat();
        mUser = in.readParcelable(User.class.getClassLoader());
        mImageUrl = in.createTypedArrayList(ImageResponse.CREATOR);
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

    public List<ImageResponse> getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(List<ImageResponse> mImageUrl) {
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
        dest.writeLong(mId);
        dest.writeString(mName);
        dest.writeString(mCamera);
        dest.writeString(mLens);
        dest.writeLong(mTimesViewed);
        dest.writeFloat(mRating);
        dest.writeInt(mCommentsCount);
        dest.writeInt(mFavoritesCount);
        dest.writeFloat(mHighestRating);
        dest.writeParcelable(mUser, flags);
        dest.writeTypedList(mImageUrl);
    }
}
