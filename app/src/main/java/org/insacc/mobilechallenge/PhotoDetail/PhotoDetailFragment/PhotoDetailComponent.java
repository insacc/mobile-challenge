package org.insacc.mobilechallenge.PhotoDetail.PhotoDetailFragment;

import org.insacc.mobilechallenge.AppModule.AppComponent;
import org.insacc.mobilechallenge.CustomScope;

import dagger.Component;

/**
 * Created by can on 31.07.2017.
 */
@CustomScope
@Component(modules = PhotoDetailModule.class, dependencies = AppComponent.class)
public interface PhotoDetailComponent {
    void inject(PhotoDetailFragment fragment);
}
