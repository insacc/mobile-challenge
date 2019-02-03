package org.insacc.mobilechallenge.PhotoDetail;

import android.os.Bundle;
import android.os.Parcelable;
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
import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.MyApplication;
import org.insacc.mobilechallenge.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by can on 31.07.2017.
 * Represents the dialog fragment which contains the viewpager(photo slider) to display photos in fullscreen.
 */

public class PhotoDetailSlideDialog extends DialogFragment implements PhotoDetailSlideContract.View {
    private static final String CLASS_NAME = PhotoDetailSlideDialog.class.getName();
    //Tags, used to save the state of the activity
    private static final String ARG_KEY_PHOTOS_LIST = CLASS_NAME + ".photoResponse";
    private static final String ARG_KEY_SELECTED_PHOTO_POSITION = CLASS_NAME + ".photoPosition";
    private static final String ARG_KEY_SLIDER_CURRENT_POSITION = CLASS_NAME + ".sliderCurrPos";
    @Inject
    PhotoDetailSlideContract.Presenter mPresenter;
    //Server response object which contains the list of photos
    private List<Photo> mPhotosList;
    //Position of the image that is selected by the user to open it in full screen.
    private int mSelectedPhotoPosition;
    private PhotoDetailViewPagerAdapter mPhotoSliderAdapter;
    @BindView(R.id.full_screen_photo_view_pager)
    ViewPager mPhotoSlider;

    /**
     * @param photosList the server response object that contains the list of photos
     * @param photoPosition  the position of the image that is selected by the user
     * @return An instance of this view with arguments @param photosResponse and @param photoPosition
     */
    public static PhotoDetailSlideDialog newInstance(List<Photo> photosList, int photoPosition) {
        PhotoDetailSlideDialog photoDetailSlideDialog = new PhotoDetailSlideDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARG_KEY_PHOTOS_LIST, (ArrayList<? extends Parcelable>) photosList);
        bundle.putInt(ARG_KEY_SELECTED_PHOTO_POSITION, photoPosition);
        photoDetailSlideDialog.setArguments(bundle);

        return photoDetailSlideDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPhotosList = savedInstanceState.getParcelableArrayList(ARG_KEY_PHOTOS_LIST);
            mSelectedPhotoPosition = savedInstanceState.getInt(ARG_KEY_SLIDER_CURRENT_POSITION);
        } else {
            extractPhotosResponse();
        }
        if (mPhotosList != null)
            mPhotoSliderAdapter = new PhotoDetailViewPagerAdapter(getChildFragmentManager(),
                    mPhotosList, this);
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
        outState.putParcelableArrayList(ARG_KEY_PHOTOS_LIST, (ArrayList<? extends Parcelable>) mPhotosList);
        outState.putInt(ARG_KEY_SELECTED_PHOTO_POSITION, mPhotoSlider.getCurrentItem());
    }

    /**
     * Extracts the server response object from the arguments and sets it.
     */
    private void extractPhotosResponse() {
        if (getArguments() != null && getArguments().getParcelableArrayList(ARG_KEY_PHOTOS_LIST) != null) {
            mPhotosList = getArguments().getParcelableArrayList(ARG_KEY_PHOTOS_LIST);
        }
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
            mSelectedPhotoPosition = getArguments().getInt(ARG_KEY_SELECTED_PHOTO_POSITION);
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
        this.mPhotosList  = (event.getPhotosList());
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
