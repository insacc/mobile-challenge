package org.insacc.mobilechallenge.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by can on 31.07.2017.
 */

public class PhotosResponse implements Parcelable {

    @SerializedName("current_page")
    private int mCurrentPage;

    @SerializedName("total_pages")
    private long mTotalPages;

    @SerializedName("total_items")
    private long mTotalItems;

    @SerializedName("photos")
    private List<Photo> mPhotos;

    @SerializedName("feature")
    private String mFeature;


    protected PhotosResponse(Parcel in) {
        mCurrentPage = in.readInt();
        mTotalPages = in.readLong();
        mTotalItems = in.readLong();
        mPhotos = in.createTypedArrayList(Photo.CREATOR);
        mFeature = in.readString();
    }

    public static final Creator<PhotosResponse> CREATOR = new Creator<PhotosResponse>() {
        @Override
        public PhotosResponse createFromParcel(Parcel in) {
            return new PhotosResponse(in);
        }

        @Override
        public PhotosResponse[] newArray(int size) {
            return new PhotosResponse[size];
        }
    };

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int mCurrentPage) {
        this.mCurrentPage = mCurrentPage;
    }

    public long getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(long mTotalPages) {
        this.mTotalPages = mTotalPages;
    }

    public long getTotalItems() {
        return mTotalItems;
    }

    public void setTotalItems(long mTotalItems) {
        this.mTotalItems = mTotalItems;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(List<Photo> mPhotos) {
        this.mPhotos = mPhotos;
    }

    public String getFeature() {
        return mFeature;
    }

    public void setFeature(String mFeature) {
        this.mFeature = mFeature;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCurrentPage);
        dest.writeLong(mTotalPages);
        dest.writeLong(mTotalItems);
        dest.writeTypedList(mPhotos);
        dest.writeString(mFeature);
    }
}
