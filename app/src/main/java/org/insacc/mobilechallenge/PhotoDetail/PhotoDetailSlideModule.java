package org.insacc.mobilechallenge.PhotoDetail;

import org.insacc.mobilechallenge.CustomScope;
import org.insacc.mobilechallenge.Service.NetworkService.GetPhotosService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by can on 31.07.2017.
 * Dagger module class for the photo detail dialog fragment
 */
@Module
public class PhotoDetailSlideModule {
    private PhotoDetailSlideContract.View mView;

    public PhotoDetailSlideModule(PhotoDetailSlideContract.View view) {
        this.mView = view;
    }

    @Provides
    @CustomScope
    public PhotoDetailSlideContract.Presenter providesPresenter(GetPhotosService getPhotosService) {
        return new PhotoDetailSlidePresenter(mView, getPhotosService);
    }
}
