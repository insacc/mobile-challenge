package org.insacc.mobilechallenge.PhotoDetail;

import org.insacc.mobilechallenge.AppModule.AppComponent;

import dagger.Component;

/**
 * Created by can on 31.07.2017.
 * This interface declares the functions used by PhotoDetailSlideDialog and PhotoDetailSlidePresenter classes.
 *
 */
@Component(modules = PhotoDetailSlideModule.class, dependencies = AppComponent.class)
public interface PhotoDetailSlideViewComponent {


}
