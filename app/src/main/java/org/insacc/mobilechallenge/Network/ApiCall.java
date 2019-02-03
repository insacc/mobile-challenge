package org.insacc.mobilechallenge.Network;

import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.Model.PhotosResponse;

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
    Observable<PhotosResponse> getPhotos(@Query("feature") String featureName, @Query("exclude")
            String excludeCategory, @Query("page") int pageNumber, @Query("consumer_key") String consumerKey,
                                         @Query("image_size") String imageSize);
}
