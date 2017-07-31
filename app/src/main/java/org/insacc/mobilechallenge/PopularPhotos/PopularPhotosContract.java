package org.insacc.mobilechallenge.PopularPhotos;

import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.Model.PhotosResponse;

import java.util.List;

/**
 * Created by can on 31.07.2017.
 */

public interface PopularPhotosContract {

    interface View {

        void populatePhotosList(List<Photo> photos);

        void displayLoadPhotoErrorMsg();

        void onImageClicked(int position);

        void setPhotosResponse(PhotosResponse photosResponse);

        void openFullScreenPhotoDialog(int position);
    }

    interface Presenter {

        void loadPhotos(int pageNumber, String consumerKey);

        void callFullScreenPhotoDialog(int position);
    }
}
