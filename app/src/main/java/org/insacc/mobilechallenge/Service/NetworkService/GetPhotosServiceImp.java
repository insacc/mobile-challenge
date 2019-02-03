package org.insacc.mobilechallenge.Service.NetworkService;

import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.Network.ApiCall;
import org.insacc.mobilechallenge.Network.Config;

import java.util.List;

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
    private int mPageNumber = 1;
    private boolean mLoading = false;

    public GetPhotosServiceImp(ApiCall apiCall) {
        mApiCall = apiCall;
    }

    /**
     * Fetches the photos from the server using the parameters declared.
     * @param callback the callback which will be called once the network request is done.
     */
    @Override
    public void loadPhotos(final GetPhotosCallback callback) {
        if (mLoading) return;

        Observable<List<Photo>> getPhotos = mApiCall.getPhotos(Config.API_KEY, mPageNumber, 25)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        mLoading = true;
        getPhotos.subscribe(new Observer<List<Photo>>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                mSubscription = disposable;
            }

            @Override
            public void onNext(@NonNull List<Photo> photos) {
                mLoading = false;
                if (mPageNumber == 1) {
                    callback.onFirstPhotoPageLoaded(photos);
                } else {
                    callback.onNextPhotoPageLoaded(photos);
                }
                mPageNumber++;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mLoading = false;
                callback.onPhotoListLoadFail();
            }

            @Override
            public void onComplete() { }
        });
    }

    @Override
    public int getCurrentPageNumber() {
        return mPageNumber;
    }

    @Override
    public void setCurrentPageNumber(int pageNumber) {
        mPageNumber = pageNumber;
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
