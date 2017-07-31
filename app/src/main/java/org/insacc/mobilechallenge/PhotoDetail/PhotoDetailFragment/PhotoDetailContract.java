package org.insacc.mobilechallenge.PhotoDetail.PhotoDetailFragment;

/**
 * Created by can on 31.07.2017.
 */

public interface PhotoDetailContract {

    interface View {
        void loadPhotoFromServer();

        void loadPhotoDetails();

        void dismissDialog();
    }

    interface Presenter {

        void callLoadPhotoDetails();

        void callDismiss();
    }
}
