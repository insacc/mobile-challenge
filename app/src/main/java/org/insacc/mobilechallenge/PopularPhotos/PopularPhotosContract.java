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
        void setPhotosList(List<Photo> photos);

        void appendPhotosList(List<Photo> photos);

        void showLoadingIndicator();

        void displayLoadPhotoErrorMsg();

        void onPhotoClicked(int position);

        void openFullScreenPhotoDialog(int position);

        void onScrollLoadMorePhoto();
    }

    interface Presenter {
        void loadPhotos();

        void refreshPhotos();

        void callFullScreenPhotoDialog(int position);

        void unSubscribe();

        int getCurrentPageNumber();

        void setCurrentPageNumber(int pageNumber);
    }
}
