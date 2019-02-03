package org.insacc.mobilechallenge.PopularPhotos;

import org.insacc.mobilechallenge.AppModule.AppComponent;
import org.insacc.mobilechallenge.AppModule.GetPhotosServiceModule;
import org.insacc.mobilechallenge.CustomScope;

import dagger.Component;

/**
 * Created by can on 31.07.2017.
 */
@CustomScope
@Component(modules = {PopularPhotosModule.class, GetPhotosServiceModule.class}, dependencies = AppComponent.class)
public interface PopularPhotosComponent {
    void inject(PopularPhotosActivity activity);
}
