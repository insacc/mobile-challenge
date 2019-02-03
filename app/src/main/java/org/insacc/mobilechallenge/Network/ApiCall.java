package org.insacc.mobilechallenge.Network;

import org.insacc.mobilechallenge.Model.Photo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by can on 31.07.2017.
 * Class to declare api endpoints for the retrofit object.
 */

public interface ApiCall {
    @GET("photos")
    Observable<List<Photo>> getPhotos(@Query("client_id") String consumerKey,
                                      @Query("page") int pageNumber,
                                      @Query("per_page") int rpp);
}
