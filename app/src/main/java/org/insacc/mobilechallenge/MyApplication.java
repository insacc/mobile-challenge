package org.insacc.mobilechallenge;

import android.app.Application;

import org.insacc.mobilechallenge.AppModule.AppComponent;
import org.insacc.mobilechallenge.AppModule.AppModule;
import org.insacc.mobilechallenge.AppModule.DaggerAppComponent;

/**
 * Created by can on 31.07.2017.
 * Custom application class to generate dagger  main component.
 */

public class MyApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();


        this.mAppComponent =  DaggerAppComponent.builder()
               .appModule(new AppModule(this))
              .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }


}
