package org.insacc.mobilechallenge.AppModule;

import org.insacc.mobilechallenge.CustomScope;
import org.insacc.mobilechallenge.Network.ApiCall;
import org.insacc.mobilechallenge.Service.NetworkService.GetPhotosService;
import org.insacc.mobilechallenge.Service.NetworkService.GetPhotosServiceImp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by can on 31.07.2017.
 */

@Module
public class GetPhotosServiceModule {
    @Provides
    @CustomScope
    public GetPhotosService providesGetPhotosService(ApiCall apiCall) {
        return new GetPhotosServiceImp(apiCall);
    }
}
