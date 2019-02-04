package org.insacc.mobilechallenge.Service.NetworkService;

import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.Service.BaseNetworkService;

import java.util.List;

/**
 * Created by can on 31.07.2017.
 */

public interface GetPhotosService extends BaseNetworkService {
    void loadPhotos(GetPhotosCallback callback);

    void refreshPhotos(GetPhotosCallback callback);

    int getCurrentPageNumber();

    void setCurrentPageNumber(int pageNumber);

    interface GetPhotosCallback {
        void onFirstPhotoPageLoaded(List<Photo> photosResponse);

        void onNextPhotoPageLoaded(List<Photo> photosList);

        void onPhotoListLoadFail();
    }
}
