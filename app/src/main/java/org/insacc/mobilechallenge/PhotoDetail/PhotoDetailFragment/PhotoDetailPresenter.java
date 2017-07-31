package org.insacc.mobilechallenge.PhotoDetail.PhotoDetailFragment;

/**
 * Created by can on 31.07.2017.
 */

public class PhotoDetailPresenter implements PhotoDetailContract.Presenter {

    private PhotoDetailContract.View mView;

    public PhotoDetailPresenter(PhotoDetailContract.View view) {
        this.mView = view;
    }
}
