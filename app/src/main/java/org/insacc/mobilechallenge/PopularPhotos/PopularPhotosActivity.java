package org.insacc.mobilechallenge.PopularPhotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import android.widget.Toast;

import com.fivehundredpx.greedolayout.GreedoLayoutManager;
import com.fivehundredpx.greedolayout.GreedoSpacingItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.insacc.mobilechallenge.Adapter.PhotoListAdapter;
import org.insacc.mobilechallenge.AppModule.GetPhotosServiceModule;
import org.insacc.mobilechallenge.Events.LoadMorePhotoEvent;
import org.insacc.mobilechallenge.Events.PhotosUpdatedEvent;
import org.insacc.mobilechallenge.Events.ScrollToPositionEvent;
import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.Model.PhotosResponse;
import org.insacc.mobilechallenge.MyApplication;
import org.insacc.mobilechallenge.PhotoDetail.PhotoDetailSlideDialog;
import org.insacc.mobilechallenge.R;
import org.insacc.mobilechallenge.Util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity class to display popular photos in recycler view list.
 */
public class PopularPhotosActivity extends AppCompatActivity implements PopularPhotosContract.View {
    //Tags, used to save the state of the activity
    private static final String PHOTO_RESPONSE_STATE = "photoResponseState";
    private static final String LIST_VIEW_LAST_POSITION = "listLastPosition";
    private static final String PAGE_COUNT_STATE = "pageCount";
    @Inject
    PopularPhotosContract.Presenter mPresenter;
    private GreedoLayoutManager mGreedoLayoutManager;
    private PhotoListAdapter mPhotoListAdapter;
    //Pagination current page count
    private int mPageCount = 1;
    //Flag to determine whether the network service is working to load more photos or not
    private boolean mLoadingMorePhotoFlag;
    //Server response object
    private PhotosResponse mPhotosResponse;
    @BindView(R.id.popular_photo_list)
    RecyclerView mPhotosRecyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_photos_activity);
        ButterKnife.bind(this);
        DaggerPopularPhotosComponent.builder()
                .appComponent(((MyApplication) getApplicationContext()).getAppComponent())
                .getPhotosServiceModule(new GetPhotosServiceModule())
                .popularPhotosModule(new PopularPhotosModule(this))
                .build().inject(this);
        mLoadingMorePhotoFlag = false;
        mPhotoListAdapter = new PhotoListAdapter(this, new ArrayList<Photo>());
        mGreedoLayoutManager = new GreedoLayoutManager(mPhotoListAdapter);
        mPhotosRecyclerList.setLayoutManager(mGreedoLayoutManager);
        mPhotosRecyclerList.setAdapter(mPhotoListAdapter);
        mGreedoLayoutManager.setMaxRowHeight(Util.dpToPx(300, this));

        if (savedInstanceState == null)
            mPresenter.loadPhotos(mPageCount, getString(R.string.consumer_key), false);
        else {
            mPageCount = savedInstanceState.getInt(PAGE_COUNT_STATE);
            mPhotosResponse = savedInstanceState.getParcelable(PHOTO_RESPONSE_STATE);
            int lastListPosition = savedInstanceState.getInt(LIST_VIEW_LAST_POSITION);
            mPhotoListAdapter.updatePhotoList(mPhotosResponse.getPhotos());
            mGreedoLayoutManager.scrollToPosition(lastListPosition);
        }
    }

    /**
     * Saves the current pagination count, server response object and the position
     * of the first visible item in the recycler view, when the screen orientation
     * is changed by the user.
     *
     * @param outState the object that saves the current state of the acitivty
     *                 when the screen orientation is changed.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PHOTO_RESPONSE_STATE, mPhotosResponse);
        outState.putInt(LIST_VIEW_LAST_POSITION, mGreedoLayoutManager.findFirstVisibleItemPosition());
        outState.putInt(PAGE_COUNT_STATE, mPageCount);
    }

    /**
     * Updates the list adapter to display the received photos from
     * server using @param photos
     *
     * @param photos the list of photos received from the server
     */
    @Override
    public void populatePhotosList(List<Photo> photos) {
        mPhotoListAdapter.updatePhotoList(photos);
        mLoadingMorePhotoFlag = false;
    }

    /**
     * Displays an error message if the photos could not be loaded from the
     * server
     */
    @Override
    public void displayLoadPhotoErrorMsg() {
        Toast.makeText(this, getString(R.string.photo_load_fail), Toast.LENGTH_LONG).show();
    }

    /**
     * Called whenever the user clicks on an image
     * in the recycler list view.
     *
     * @param position the position of the image that is clicked
     *                 by the user.
     */
    @Override
    public void onImageClicked(int position) {
        mPresenter.callFullScreenPhotoDialog(position);
    }

    /**
     * Sets or updates the server response object depending
     * on whether the server response obj already
     * declared or not.
     *
     * @param photosResponse the wrapper object for the
     *                       server response object
     */
    @Override
    public void setPhotosResponse(PhotosResponse photosResponse) {
        if (mPhotosResponse == null) {
            mPhotosResponse = photosResponse;
        } else {
            mPhotosResponse.getPhotos().addAll(photosResponse.getPhotos());
        }
    }

    /**
     * Opens the dialog fragment to display the selected
     * image in fullscreen.
     *
     * @param position the position of the image that is clicked
     */
    @Override
    public void openFullScreenPhotoDialog(int position) {
        PhotoDetailSlideDialog photoDetailSlideDialog = PhotoDetailSlideDialog
                .newInstance(mPhotosResponse, position);
        photoDetailSlideDialog.show(getSupportFragmentManager(), "");
    }

    /**
     * Called when the user is scrolled to the bottom and when a new page of
     * photos need to be fetched from the server.
     */
    @Override
    public void onScrollLoadMorePhoto() {
        loadMorePhotos(false);
    }

    /**
     * Notifies the fullscreenslider view to indicate that
     * a new page of photos are fetched from the server.
     */
    @Override
    public void notifySliderPhotosUpdated() {
        EventBus.getDefault().post(new PhotosUpdatedEvent(mPhotosResponse.getPhotos()));
    }

    /**
     * Calls the presenter's method to fetch more photos from the server.
     * Increments the current page count.
     * The loading more photo flag indicates whether the presenter has been already
     * fetching additional photos from the server or not. If it has been already fetching
     * new photos then it does nothing.
     *
     * @param shouldNotifySlider flag to indicate whether the slider should be
     *                           notified when a new page of photos are fetched from the server
     */
    private void loadMorePhotos(boolean shouldNotifySlider) {
        if (!mLoadingMorePhotoFlag) {
            mPageCount++;
            mPresenter.loadPhotos(mPageCount, getString(R.string.consumer_key), shouldNotifySlider);
            mLoadingMorePhotoFlag = true;
        }
    }

    /**
     * Called when the user closes the slider view and scrolls to the position of
     * the last image the user saw.
     *
     * @param event the event that sends the position of the last image the user saw.
     */
    @Subscribe
    public void onPhotoSliderCloseScroll(ScrollToPositionEvent event) {
        mGreedoLayoutManager.scrollToPosition(event.getPosition());
    }

    /**
     * Called when the slider reaches to the last element and when
     * new page of photos should be fetched from the server.
     *
     * @param event event that sends the position of the slider.
     */
    @Subscribe
    public void onSliderLastItemIsDisplayed(LoadMorePhotoEvent event) {
        loadMorePhotos(true);
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

    /**
     * On destroy it kills the server connection, so that the callback
     * does not cause any exception.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }
}
