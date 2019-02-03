package org.insacc.mobilechallenge.AppModule;

import android.content.Context;

import org.insacc.mobilechallenge.MyApplication;
import org.insacc.mobilechallenge.Network.ApiCall;
import org.insacc.mobilechallenge.Network.ApiWrapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by can on 31.07.2017.
 */
@Module
public class AppModule {
    private MyApplication mApplication;

    public AppModule(MyApplication application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    public ApiCall providesApiCall() {
        return new ApiWrapper().createRetrofitObject();
    }

    @Provides
    @Singleton
    public Context provideApplication() {
        return mApplication;
    }
}
