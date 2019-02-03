package org.insacc.mobilechallenge.PopularPhotos;

import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.Model.PhotosResponse;

import java.util.List;

/**
 * Created by can on 31.07.2017.
 * Contract class which defines the functions that the presenter and the view classes needs to
 * implement.
 */

public interface PopularPhotosContract {
    interface View {
        void populatePhotosList(List<Photo> photos);

        void displayLoadPhotoErrorMsg();

        void onImageClicked(int position);

        void setPhotosResponse(PhotosResponse photosResponse);

        void openFullScreenPhotoDialog(int position);

        void onScrollLoadMorePhoto();

        void notifySliderPhotosUpdated();
    }

    interface Presenter {
        void loadPhotos(int pageNumber, String consumerKey, boolean shouldNotifySlider);

        void callFullScreenPhotoDialog(int position);

        void unSubscribe();
    }
}
