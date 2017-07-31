package org.insacc.mobilechallenge.PopularPhotos;

import org.insacc.mobilechallenge.AppModule.AppComponent;

import dagger.Component;

/**
 * Created by can on 31.07.2017.
 */
@Component(modules = PopularPhotosModule.class, dependencies = AppComponent.class)
public interface PopularPhotosComponent {
}
