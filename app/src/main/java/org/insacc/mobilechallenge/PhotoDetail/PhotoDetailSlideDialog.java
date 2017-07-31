package org.insacc.mobilechallenge.PhotoDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.insacc.mobilechallenge.PhotoDetail.PhotoDetailFragment.PhotoDetailContract;

/**
 * Created by can on 31.07.2017.
 * Represents the dialog fragment which contains the viewpager to display details of a photo.
 */

public class PhotoDetailSlideDialog extends DialogFragment implements PhotoDetailSlideContract.View {

    PhotoDetailSlideContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
