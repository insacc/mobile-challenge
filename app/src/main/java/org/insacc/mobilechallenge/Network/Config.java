package org.insacc.mobilechallenge.Network;

/**
 * Created by can on 31.07.2017.
 */

public class Config {
    //Base url of the api
    public static final String BASE_URL = "https://api.500px.com/v1/";
    //Category that need to be excluded
    public static final String EXCLUDE_CATEGORY = "Nude";
    //Category that needs to be fetched from the server
    public static final String FEATURE_CATEGORY = "Popular";
    //The index for the image size of 20
    public static final int IMAGE_SIZE_20_INDEX = 0;
    //The index for the image size of 31
    public static final int IMAGE_SIZE_31_INDEX = 1;
}
