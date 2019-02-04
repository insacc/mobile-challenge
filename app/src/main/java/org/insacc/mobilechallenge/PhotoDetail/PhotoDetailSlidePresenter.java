package org.insacc.mobilechallenge.PhotoDetail;

import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.Service.NetworkService.GetPhotosService;

import java.util.List;

/**
 * Created by can on 31.07.2017.
 * Presenter class for the PhotoDetailActivity.
 */

public class PhotoDetailSlidePresenter implements PhotoDetailSlideContract.Presenter, GetPhotosService.GetPhotosCallback {
    private PhotoDetailSlideContract.View mView;
    private GetPhotosService mGetPhotosService;

    public PhotoDetailSlidePresenter(PhotoDetailSlideContract.View view, GetPhotosService getPhotosService) {
        this.mView = view;
        this.mGetPhotosService = getPhotosService;
    }

    /**
     * Loads more photos from the server using the network service
     */
    @Override
    public void loadMorePhotos() {
        mGetPhotosService.loadPhotos(this);
    }

    /**
     * Sets the current photo page number to the network service
     * @param pageNumber the current page number
     */
    @Override
    public void setCurrentPageNumber(int pageNumber) {
        mGetPhotosService.setCurrentPageNumber(pageNumber);
    }

    /**
     *
     * @return the current photo page number
     */
    @Override
    public int getCurrentPageNumber() {
        return mGetPhotosService.getCurrentPageNumber();
    }

    @Override
    public void onLoading() {
        // No op
    }

    @Override
    public void onFirstPhotoPageLoaded(List<Photo> photosResponse) {
        // No op
    }

    @Override
    public void onNextPhotoPageLoaded(List<Photo> photosList) {
        mView.appendPhotosList(photosList);
    }

    @Override
    public void onPhotoListLoadFailed() {
        // TODO display load error msg
    }
}
