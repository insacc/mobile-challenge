package org.insacc.mobilechallenge.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by can on 31.07.2017.
 */

public class PhotosResponse {

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
}
