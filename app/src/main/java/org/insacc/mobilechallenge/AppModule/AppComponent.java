package org.insacc.mobilechallenge.AppModule;

import android.content.Context;

import org.insacc.mobilechallenge.Network.ApiCall;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by can on 31.07.2017.
 * Main dagger component class, which create singleton object of retrofit and  context.
 */

@Singleton
@Component(modules = AppModule.class)

public interface AppComponent {

    ApiCall providesRetrofit();

    Context providesContext();
}
