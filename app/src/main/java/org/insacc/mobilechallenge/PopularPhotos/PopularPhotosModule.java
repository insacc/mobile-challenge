package org.insacc.mobilechallenge.PopularPhotos;

import org.insacc.mobilechallenge.CustomScope;
import org.insacc.mobilechallenge.Service.NetworkService.GetPhotosService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by can on 31.07.2017.
 */
@Module
public class PopularPhotosModule {

    private PopularPhotosContract.View mView;

    public PopularPhotosModule(PopularPhotosContract.View view) {
        this.mView = view;
    }

    @CustomScope
    @Provides
    public PopularPhotosContract.Presenter providesPresenter(GetPhotosService getPhotosService) {
        return new PopularPhotosPresenter(mView, getPhotosService);
    }
}
