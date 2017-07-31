package org.insacc.mobilechallenge.PhotoDetail.PhotoDetailFragment;

import org.insacc.mobilechallenge.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by can on 31.07.2017.
 */
@Module
public class PhotoDetailModule {

    private PhotoDetailContract.View mView;

    public PhotoDetailModule(PhotoDetailContract.View view) {
        this.mView = view;
    }

    @Provides
    @CustomScope
    public PhotoDetailContract.Presenter providesPresenter() {
        return new PhotoDetailPresenter(mView);
    }
}
