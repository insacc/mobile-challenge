package org.insacc.mobilechallenge.PhotoDetail;


/**
 * Created by can on 31.07.2017.
 * Dagger component class for the PhotoDetailSlideDialog.
 */

public interface PhotoDetailSlideContract {

    interface View {

        void onLastImageIsDisplayed();

        void broadcastLoadMorePhotos();

    }

    interface Presenter {
        void callLoadMorePhotos();


    }
}
