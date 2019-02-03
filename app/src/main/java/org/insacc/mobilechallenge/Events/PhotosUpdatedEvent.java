package org.insacc.mobilechallenge.Events;

import org.insacc.mobilechallenge.Model.Photo;

import java.util.List;

/**
 * Created by can on 31.07.2017.
 */

public class PhotosUpdatedEvent {
    private List<Photo> mPhotosList;

    public PhotosUpdatedEvent(List<Photo> photoList) {
        this.mPhotosList = photoList;
    }

    public List<Photo> getPhotosList() {
        return mPhotosList;
    }
}
