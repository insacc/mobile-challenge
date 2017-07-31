package org.insacc.mobilechallenge.PhotoDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.insacc.mobilechallenge.Adapter.PhotoDetailViewPagerAdapter;
import org.insacc.mobilechallenge.Events.DismissDialogEvent;
import org.insacc.mobilechallenge.Model.PhotosResponse;
import org.insacc.mobilechallenge.MyApplication;
import org.insacc.mobilechallenge.PhotoDetail.PhotoDetailFragment.PhotoDetailContract;
import org.insacc.mobilechallenge.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by can on 31.07.2017.
 * Represents the dialog fragment which contains the viewpager to display details of a photo.
 */

public class PhotoDetailSlideDialog extends DialogFragment implements PhotoDetailSlideContract.View {
    @Inject
    PhotoDetailSlideContract.Presenter mPresenter;

    private static final String PHOTO_RESPONSE = "photoResponse";
    private static final String SELECTED_PHOTO_POSITION = "photoPosition";

    @BindView(R.id.full_screen_photo_view_pager)
    ViewPager mPhotoSlider;


    private PhotosResponse mPhotosResponse;

    private PhotoDetailViewPagerAdapter mPhotoSliderAdapter;

    public static PhotoDetailSlideDialog newInstance(PhotosResponse photosResponse, int photoPosition) {

        PhotoDetailSlideDialog photoDetailSlideDialog = new PhotoDetailSlideDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PHOTO_RESPONSE, photosResponse);
        bundle.putInt(SELECTED_PHOTO_POSITION, photoPosition);
        photoDetailSlideDialog.setArguments(bundle);

        return photoDetailSlideDialog;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extractPhotosResponse();
        if (mPhotosResponse != null)
            mPhotoSliderAdapter = new PhotoDetailViewPagerAdapter(getChildFragmentManager(),
                    mPhotosResponse.getPhotos(), this);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme);
    }

    private void extractPhotosResponse() {
        if (getArguments() != null && getArguments().getParcelable(PHOTO_RESPONSE) != null)
            mPhotosResponse = getArguments().getParcelable(PHOTO_RESPONSE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.full_screen_photo_container, container, false);
        DaggerPhotoDetailSlideViewComponent.builder().appComponent(((MyApplication) getActivity().getApplicationContext()).getAppComponent())
                .photoDetailSlideModule(new PhotoDetailSlideModule(this)).build().inject(this);
        ButterKnife.bind(this, root);

        if (mPhotoSliderAdapter != null)
            mPhotoSlider.setAdapter(mPhotoSliderAdapter);

        int selectedPosition = 0;
        if (getArguments() != null)
            selectedPosition = getArguments().getInt(SELECTED_PHOTO_POSITION);

        mPhotoSlider.setCurrentItem(selectedPosition);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return root;
    }


    @Override
    public void onLastImageIsDisplayed() {

    }

    @Override
    public void broadcastLoadMorePhotos() {

    }

    @Subscribe
    public void onDismissDialogEvent(DismissDialogEvent event) {
        this.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}
