package org.insacc.mobilechallenge.PhotoDetail;

/**
 * Created by can on 31.07.2017.
 * Presenter class for the PhotoDetailSlideDialog.
 */

public class PhotoDetailSlidePresenter implements PhotoDetailSlideContract.Presenter {

    private PhotoDetailSlideContract.View mView;

    public PhotoDetailSlidePresenter(PhotoDetailSlideContract.View view) {
        this.mView = view;
    }

    /**
     * Called when a new page needs to be fetched from the server, when the user swiped to the
     * end of the slider.
     */
    @Override
    public void callLoadMorePhotos() {
        mView.broadcastLoadMorePhotos();
    }
}
