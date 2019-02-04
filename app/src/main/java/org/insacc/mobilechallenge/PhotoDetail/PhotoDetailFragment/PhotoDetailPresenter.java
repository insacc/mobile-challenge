package org.insacc.mobilechallenge.PhotoDetail.PhotoDetailFragment;

/**
 * Created by can on 31.07.2017.
 * Presenter class for the PhotoDetailFragment which manages the view calls.
 */

public class PhotoDetailPresenter implements PhotoDetailContract.Presenter {
    private PhotoDetailContract.View mView;

    public PhotoDetailPresenter(PhotoDetailContract.View view) {
        this.mView = view;
    }

    /**
     * Calls the functions to load the details of the photo.
     */
    @Override
    public void setPhotoDetails() {
        mView.loadPhoto();
        mView.setPhotoDescription();
    }
}
