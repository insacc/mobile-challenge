package org.insacc.mobilechallenge.PhotoDetail;

import org.insacc.mobilechallenge.AppModule.AppComponent;
import org.insacc.mobilechallenge.CustomScope;

import dagger.Component;

/**
 * Created by can on 31.07.2017.
 * This interface declares the functions used by PhotoDetailSlideDialog and PhotoDetailSlidePresenter classes.
 *
 */
@CustomScope
@Component(modules = PhotoDetailSlideModule.class, dependencies = AppComponent.class)
public interface PhotoDetailSlideViewComponent {

    void inject(PhotoDetailSlideDialog view);


}
