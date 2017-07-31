package org.insacc.mobilechallenge.Service.NetworkService;

import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.Model.PhotosResponse;
import org.insacc.mobilechallenge.Network.ApiCall;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;


import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import io.reactivex.schedulers.Schedulers;


/**
 * Created by can on 31.07.2017.
 */

public class GetPhotosServiceImp implements GetPhotosService {

    private ApiCall mApiCall;

    private Disposable mSubscription;

    public GetPhotosServiceImp(ApiCall apiCall) {
        mApiCall = apiCall;
    }


    @Override
    public void getPhotos(String feature, String excludeCategory, int pageNumber, String consumerKey,
                          final GetPhotosCallback callback) {
        Observable<PhotosResponse> getPhotos = mApiCall.getPhotos(feature, excludeCategory, pageNumber,
                consumerKey, 20)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

        getPhotos.subscribe(new Observer<PhotosResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                mSubscription = disposable;
            }

            @Override
            public void onNext(@NonNull PhotosResponse photos) {
                callback.onPhotoListLoaded(photos);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                callback.onPhotoListLoadFail();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null && !mSubscription.isDisposed())
            mSubscription.dispose();
    }
}
