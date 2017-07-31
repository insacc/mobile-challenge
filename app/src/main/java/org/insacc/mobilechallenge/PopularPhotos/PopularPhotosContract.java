package org.insacc.mobilechallenge.PopularPhotos;

import org.insacc.mobilechallenge.Model.Photo;

import java.util.List;

/**
 * Created by can on 31.07.2017.
 */

public interface PopularPhotosContract {

    interface View {

        void populatePhotosList(List<Photo> photos);

        void displayLoadPhotoErrorMsg();
    }

    interface Presenter {

        void loadPhotos(int pageNumber, String consumerKey);
    }
}
