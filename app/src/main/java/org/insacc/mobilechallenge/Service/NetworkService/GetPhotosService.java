package org.insacc.mobilechallenge.Service.NetworkService;

import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.Model.PhotosResponse;
import org.insacc.mobilechallenge.Service.BaseNetworkService;

import java.util.List;

/**
 * Created by can on 31.07.2017.
 */

public interface GetPhotosService extends BaseNetworkService {
    void getPhotos(String feature, String exclude, int pageNumber, String consumerKey, GetPhotosCallback callback);

    interface GetPhotosCallback {
        void onPhotoListLoaded(PhotosResponse photosResponse);

        void onPhotoListLoadFail();
    }
}
