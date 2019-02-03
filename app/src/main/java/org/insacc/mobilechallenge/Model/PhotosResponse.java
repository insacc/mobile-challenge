package org.insacc.mobilechallenge.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by can on 31.07.2017.
 */

public class PhotosResponse implements Parcelable {
    private List<Photo> mPhotos;

    protected PhotosResponse(Parcel in) {
        mPhotos = in.createTypedArrayList(Photo.CREATOR);
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

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(List<Photo> mPhotos) {
        this.mPhotos = mPhotos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mPhotos);
    }
}
