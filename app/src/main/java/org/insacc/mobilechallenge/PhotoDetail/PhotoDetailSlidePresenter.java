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

    @Override
    public void loadMorePhotos() {
        mGetPhotosService.loadPhotos(this);
    }

    @Override
    public void setCurrentPageNumber(int pageNumber) {
        mGetPhotosService.setCurrentPageNumber(pageNumber);
    }

    @Override
    public int getCurrentPageNumber() {
        return mGetPhotosService.getCurrentPageNumber();
    }

    @Override
    public void onFirstPhotoPageLoaded(List<Photo> photosResponse) {
        // No op
    }

    @Override
    public void onNextPhotoPageLoaded(List<Photo> photosList) {
        mView.populatePhotosList(photosList);
    }

    @Override
    public void onPhotoListLoadFail() {
        // TODO display load error msg
    }
}
