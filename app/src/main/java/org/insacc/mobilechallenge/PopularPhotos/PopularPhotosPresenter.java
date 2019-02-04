package org.insacc.mobilechallenge.PopularPhotos;

import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.Service.NetworkService.GetPhotosService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 31.07.2017.
 */

public class PopularPhotosPresenter implements PopularPhotosContract.Presenter, GetPhotosService.GetPhotosCallback {
    private PopularPhotosContract.View mView;
    //Network service class
    private GetPhotosService mGetPhotosService;

    public PopularPhotosPresenter(PopularPhotosContract.View view, GetPhotosService getPhotosService) {
        this.mView = view;
        this.mGetPhotosService = getPhotosService;
    }

    /**
     * Using the network service object this method loads photos from the server
     */
    @Override
    public void loadPhotos() {
        mGetPhotosService.loadPhotos(this);
    }

    @Override
    public void refreshPhotos() {
        mGetPhotosService.refreshPhotos(this);
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

    @Override
    public int getCurrentPageNumber() {
        return mGetPhotosService.getCurrentPageNumber();
    }

    @Override
    public void setCurrentPageNumber(int pageNumber) {
        mGetPhotosService.setCurrentPageNumber(pageNumber);
    }

    /**
     * Called when the photos are fetched from the server. Populates the recycler view list adapter
     * and notifies the slider view depending on the flag.
     * @param photosResponse the server response object that is fetched from the server
     */
    @Override
    public void onFirstPhotoPageLoaded(List<Photo> photosResponse) {
        mView.setPhotosList(photosResponse);
    }

    // TODO handle refresh and next page loads separately
    @Override
    public void onNextPhotoPageLoaded(List<Photo> photosList) {
        mView.appendPhotosList(photosList);
    }

    /**
     * Called when the application could not fetch the photos from the server.
     */
    @Override
    public void onPhotoListLoadFail() {
        mView.displayLoadPhotoErrorMsg();
    }
}
