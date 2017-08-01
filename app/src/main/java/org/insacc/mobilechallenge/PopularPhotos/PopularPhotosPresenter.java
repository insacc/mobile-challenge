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

    //Network service class
    private GetPhotosService mGetPhotosService;

    //Flag to indicate whether the slider needs to be notified when new photos are fetched.
    private boolean mShouldNotifySlider;


    public PopularPhotosPresenter(PopularPhotosContract.View view, GetPhotosService getPhotosService) {
        this.mView = view;
        this.mGetPhotosService = getPhotosService;
    }

    /**
     * Using the network service object this method loads photos from the server
     * @param pageNumber the page number that needs to fetched from the server
     * @param consumerKey the consumer key of the application to reach to the server
     * @param shouldNotifySlider Flag to indicate whether the slider needs to be notified
     *                           when new photos are fetched.
     */
    @Override
    public void loadPhotos(int pageNumber, String consumerKey, boolean shouldNotifySlider) {

        mShouldNotifySlider = shouldNotifySlider;
        mGetPhotosService.getPhotos(Config.FEATURE_CATEGORY, Config.EXCLUDE_CATEGORY, pageNumber,
                consumerKey, this);


    }

    /**
     * Calls the full screen slider ui for the image at the position @param position.
     * @param position the position of the image, that needs to be
     *                 displayed in full screen slider ui
     */
    @Override
    public void callFullScreenPhotoDialog(int position) {
        mView.openFullScreenPhotoDialog(position);
    }

    /**
     * Unsubscribes the network service
     */
    @Override
    public void unSubscribe() {
        mGetPhotosService.unSubscribe();
    }

    /**
     * Called when the photos are fetched from the server. Populates the recycler view list adapter
     * and notifies the slider view depending on the flag.
     * @param photosResponse the server response object that is fetched from the server
     */
    @Override
    public void onPhotoListLoaded(PhotosResponse photosResponse) {
        mView.populatePhotosList(photosResponse.getPhotos());
        mView.setPhotosResponse(photosResponse);
        if (mShouldNotifySlider)
            mView.notifySliderPhotosUpdated();

        mShouldNotifySlider = false;
    }

    /**
     * Called when the application could not fetch the photos from the server.
     */
    @Override
    public void onPhotoListLoadFail() {
        mView.displayLoadPhotoErrorMsg();
        mShouldNotifySlider = false;
    }
}
