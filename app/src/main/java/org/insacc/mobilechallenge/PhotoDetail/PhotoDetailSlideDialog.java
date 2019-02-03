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
 * Represents the dialog fragment which contains the viewpager(photo slider) to display photos in fullscreen.
 */

public class PhotoDetailSlideDialog extends DialogFragment implements PhotoDetailSlideContract.View {
    //Tags, used to save the state of the activity
    private static final String PHOTO_RESPONSE = "photoResponse";
    private static final String SELECTED_PHOTO_POSITION = "photoPosition";
    private static final String SLIDER_CURRENT_POSITION = "sliderCurrPos";
    @Inject
    PhotoDetailSlideContract.Presenter mPresenter;
    //Server response object which contains the list of photos
    private PhotosResponse mPhotosResponse;
    //Position of the image that is selected by the user to open it in full screen.
    private int mSelectedPhotoPosition;
    private PhotoDetailViewPagerAdapter mPhotoSliderAdapter;
    @BindView(R.id.full_screen_photo_view_pager)
    ViewPager mPhotoSlider;

    /**
     * @param photosResponse the server response object that contains the list of photos
     * @param photoPosition  the position of the image that is selected by the user
     * @return An instance of this view with arguments @param photosResponse and @param photoPosition
     */
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

    /**
     * Saves the current position of the image and the  server response object, when the screen orientation
     * is changed by the user.
     *
     * @param outState the object that saves the current state of the dialog fragment
     *                 when the screen orientation is changed.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PHOTO_RESPONSE, mPhotosResponse);
        outState.putInt(SELECTED_PHOTO_POSITION, mPhotoSlider.getCurrentItem());
    }

    /**
     * Extracts the server response object from the arguments and sets it.
     */
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

    /**
     * Called when the user opens the last image of the current list of photos.
     */
    @Override
    public void onLastImageIsDisplayed() {
        mPresenter.callLoadMorePhotos();
    }

    /**
     * Publish the event to indicate that a new page of the photos should be fetched from the
     * server
     */
    @Override
    public void broadcastLoadMorePhotos() {
        EventBus.getDefault().post(new LoadMorePhotoEvent());
    }

    /**
     * Called when a new page of photos are fetched from the server and when this slider
     * needs to be updated.
     *
     * @param event The event object that contains the new list of photos.
     */
    @Subscribe
    public void onPhotosUpdateEvent(PhotosUpdatedEvent event) {
        this.mPhotosResponse.setPhotos(event.getPhotosList());
        updateViewPager();
    }

    /**
     * Updates the view pager adapter with the new list of photos and displays the  last image
     * the user saw.
     */
    private void updateViewPager() {
        int currentPosition = mPhotoSlider.getCurrentItem();
        this.mPhotoSliderAdapter.notifyDataSetChanged();
        mPhotoSlider.setCurrentItem(currentPosition);
    }

    /**
     * Called when the user clicks on the back button on the slider. Broadcast the  position of the last
     * image the user saw, so that the recycler view list can be scrolled to that image.
     *
     * @param event the event object to indicate that the dialog needs to be dismissed.
     */
    @Subscribe
    public void onDismissDialogEvent(DismissDialogEvent event) {
        sendLastPosition();
        this.dismiss();
    }

    /**
     * Broadcast the  position of the last
     * image the user saw, so that the recycler view list can be scrolled to that image.
     */
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
