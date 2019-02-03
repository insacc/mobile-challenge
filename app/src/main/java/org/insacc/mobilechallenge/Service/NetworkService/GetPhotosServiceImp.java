package org.insacc.mobilechallenge.Service.NetworkService;

import org.insacc.mobilechallenge.Model.PhotosResponse;
import org.insacc.mobilechallenge.Network.ApiCall;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by can on 31.07.2017.
 * The network service to fetch the photos from server. Takes advantage of the rxjava callbacks to make
 * the server call asynchronous
 */

public class GetPhotosServiceImp implements GetPhotosService {
    private ApiCall mApiCall;
    private Disposable mSubscription;

    public GetPhotosServiceImp(ApiCall apiCall) {
        mApiCall = apiCall;
    }

    /**
     * Fetches the photos from the server using the parameters declared.
     * @param feature the feature category of the photos that will be fetched from server
     * @param excludeCategory the category that needs to be excluded from the server response.
     * @param pageNumber the page number which needs to be fetched from the server
     * @param consumerKey the consumer key to be able to connect to the server
     * @param callback the callback which will be called once the network request is done.
     */
    @Override
    public void getPhotos(String feature, String excludeCategory, int pageNumber, String consumerKey,
                          final GetPhotosCallback callback) {
        Observable<PhotosResponse> getPhotos = mApiCall.getPhotos(feature, excludeCategory, pageNumber,
                consumerKey, "20,31")
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
            public void onComplete() { }
        });
    }

    /**
     * UnSubscribes the server call, so that the callback will not be called if it has been
     * already set.
     */
    @Override
    public void unSubscribe() {
        if (mSubscription != null && !mSubscription.isDisposed())
            mSubscription.dispose();
    }
}
