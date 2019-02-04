package org.insacc.mobilechallenge.PhotoDetail;

import org.insacc.mobilechallenge.AppModule.AppComponent;
import org.insacc.mobilechallenge.AppModule.GetPhotosServiceModule;
import org.insacc.mobilechallenge.CustomScope;

import dagger.Component;

/**
 * Created by can on 31.07.2017.
 * This interface declares the functions used by PhotoDetailActivity and PhotoDetailSlidePresenter classes.
 */
@CustomScope
@Component(modules = {PhotoDetailSlideModule.class, GetPhotosServiceModule.class}, dependencies = AppComponent.class)
public interface PhotoDetailSlideViewComponent {
    void inject(PhotoDetailActivity view);
}
