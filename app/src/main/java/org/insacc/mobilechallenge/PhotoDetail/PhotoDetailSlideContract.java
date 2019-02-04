package org.insacc.mobilechallenge.PhotoDetail;

import org.insacc.mobilechallenge.Model.Photo;

import java.util.List;

/**
 * Created by can on 31.07.2017.
 * Dagger component class for the PhotoDetailActivity.
 */

public interface PhotoDetailSlideContract {
    interface View {
        void onScrollLoadMorePhoto();

        void appendPhotosList(List<Photo> photos);
    }

    interface Presenter {
        void loadMorePhotos();

        void setCurrentPageNumber(int pageNumber);

        int getCurrentPageNumber();
    }
}
