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
import org.insacc.mobilechallenge.Events.LoadMorePhotoEvent;
import org.insacc.mobilechallenge.Events.PhotosUpdatedEvent;
import org.insacc.mobilechallenge.Events.ScrollToPositionEvent;
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
    private static final String SLIDER_CURRENT_POSITION = "sliderCurrPos";

    @BindView(R.id.full_screen_photo_view_pager)
    ViewPager mPhotoSlider;


    private PhotosResponse mPhotosResponse;

    private int mSelectedPhotoPosition;

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
        if (savedInstanceState != null) {
            mPhotosResponse = savedInstanceState.getParcelable(PHOTO_RESPONSE);
            mSelectedPhotoPosition = savedInstanceState.getInt(SLIDER_CURRENT_POSITION);
        } else {
            extractPhotosResponse();

        }

        if (mPhotosResponse != null)
            mPhotoSliderAdapter = new PhotoDetailViewPagerAdapter(getChildFragmentManager(),
                    mPhotosResponse.getPhotos(), this);


        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PHOTO_RESPONSE, mPhotosResponse);
        outState.putInt(SELECTED_PHOTO_POSITION, mPhotoSlider.getCurrentItem());
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


        if (getArguments() != null)
            mSelectedPhotoPosition = getArguments().getInt(SELECTED_PHOTO_POSITION);

        mPhotoSlider.setCurrentItem(mSelectedPhotoPosition);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return root;
    }


    @Override
    public void broadcastLoadMorePhotos() {
        EventBus.getDefault().post(new LoadMorePhotoEvent());
    }

    @Subscribe
    public void onPhotosUpdateEvent(PhotosUpdatedEvent event) {
        this.mPhotosResponse.setPhotos(event.getPhotosList());
        updateViewPager();

    }

    private void updateViewPager() {
        int currentPosition = mPhotoSlider.getCurrentItem();
        this.mPhotoSliderAdapter.notifyDataSetChanged();
        mPhotoSlider.setCurrentItem(currentPosition);
    }

    @Subscribe
    public void onDismissDialogEvent(DismissDialogEvent event) {
        sendLastPosition();
        this.dismiss();
    }

    private void sendLastPosition() {
        int currentPosition = mPhotoSlider.getCurrentItem();
        EventBus.getDefault().post(new ScrollToPositionEvent(currentPosition));
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
