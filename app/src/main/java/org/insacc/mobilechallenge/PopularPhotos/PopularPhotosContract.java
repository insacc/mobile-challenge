package org.insacc.mobilechallenge.PopularPhotos;

import org.insacc.mobilechallenge.Model.Photo;

import java.util.ArrayList;
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

        void onPhotoClicked(int position);

        void openFullScreenPhotoDialog(int position);

        void onScrollLoadMorePhoto();
    }

    interface Presenter {
        void loadPhotos();

        void callFullScreenPhotoDialog(int position);

        ArrayList<Photo> getPhotosList();

        void setPhotosList(List<Photo> photosList);

        void unSubscribe();

        int getCurrentPageNumber();

        void setCurrentPageNumber(int pageNumber);
    }
}
