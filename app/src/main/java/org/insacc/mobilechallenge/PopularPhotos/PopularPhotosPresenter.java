package org.insacc.mobilechallenge.PopularPhotos;


import org.insacc.mobilechallenge.Model.PhotosResponse;
import org.insacc.mobilechallenge.Network.Config;
import org.insacc.mobilechallenge.Service.NetworkService.GetPhotosService;

import java.util.List;

/**
 * Created by can on 31.07.2017.
 */

public class PopularPhotosPresenter implements PopularPhotosContract.Presenter, GetPhotosService.GetPhotosCallback {

    private PopularPhotosContract.View mView;

    private GetPhotosService mGetPhotosService;

    private boolean mShouldNotifySlider;

    public PopularPhotosPresenter(PopularPhotosContract.View view, GetPhotosService getPhotosService) {
        this.mView = view;
        this.mGetPhotosService = getPhotosService;
    }


    @Override
    public void loadPhotos(int pageNumber, String consumerKey, boolean shouldNotifySlider) {

        mShouldNotifySlider = shouldNotifySlider;
        mGetPhotosService.getPhotos(Config.FEATURE_CATEGORY, Config.EXCLUDE_CATEGORY, pageNumber,
                consumerKey, this);


    }

    @Override
    public void callFullScreenPhotoDialog(int position) {
        mView.openFullScreenPhotoDialog(position);
    }

    @Override
    public void unSubscribe() {
        mGetPhotosService.unSubscribe();
    }

    @Override
    public void onPhotoListLoaded(PhotosResponse photosResponse) {
        mView.populatePhotosList(photosResponse.getPhotos());
        mView.setPhotosResponse(photosResponse);
        if (mShouldNotifySlider)
            mView.notifySliderPhotosUpdated();

        mShouldNotifySlider = false;
    }

    @Override
    public void onPhotoListLoadFail() {
        mView.displayLoadPhotoErrorMsg();
        mShouldNotifySlider = false;
    }
}
