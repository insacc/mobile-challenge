package org.insacc.mobilechallenge.PopularPhotos;

/**
 * Created by can on 31.07.2017.
 */

public class PopularPhotosPresenter implements PopularPhotosContract.Presenter {

    PopularPhotosContract.View mView;

    public PopularPhotosPresenter(PopularPhotosContract.View view) {
        mView = view;
    }


    @Override
    public void loadPhotos(int pageNumber) {

    }
}
