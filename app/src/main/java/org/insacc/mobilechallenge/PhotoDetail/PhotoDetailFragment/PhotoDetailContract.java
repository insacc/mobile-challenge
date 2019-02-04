package org.insacc.mobilechallenge.PhotoDetail.PhotoDetailFragment;

/**
 * Created by can on 31.07.2017.
 * Contract class that declares the functions that needs to implemented by the view and presenter
 * classes that are responsible for displaying a photo in full screen.
 */

public interface PhotoDetailContract {
    interface View {
        void loadPhoto();

        void setPhotoDescription();
    }

    interface Presenter {
        void callLoadPhotoDetails();
    }
}
