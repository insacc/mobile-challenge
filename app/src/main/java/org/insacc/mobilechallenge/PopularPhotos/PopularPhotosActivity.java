package org.insacc.mobilechallenge.PopularPhotos;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.fivehundredpx.greedolayout.GreedoLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.insacc.mobilechallenge.Adapter.PhotoListAdapter;
import org.insacc.mobilechallenge.AppModule.GetPhotosServiceModule;
import org.insacc.mobilechallenge.Events.LoadMorePhotoEvent;
import org.insacc.mobilechallenge.Events.PhotosUpdatedEvent;
import org.insacc.mobilechallenge.Events.ScrollToPositionEvent;
import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.MyApplication;
import org.insacc.mobilechallenge.PhotoDetail.PhotoDetailActivity;
import org.insacc.mobilechallenge.R;

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
    private static final String CLASS_NAME = PopularPhotosActivity.class.getName();
    private static final String ARG_KEY_PHOTOS_LIST = CLASS_NAME + ".photosList";
    private static final String ARG_KEY_LIST_VIEW_LAST_POSITION = CLASS_NAME + "listLastPosition";
    private static final String ARG_KEY_PAGE_COUNT = CLASS_NAME + "pageCount";
    private static final int REQUEST_PHOTO_DETAIL = 1001;
    @Inject
    PopularPhotosContract.Presenter mPresenter;
    private GreedoLayoutManager mGreedoLayoutManager;
    private PhotoListAdapter mPhotoListAdapter;
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
        mPhotoListAdapter = new PhotoListAdapter(this, new ArrayList<Photo>());
        mGreedoLayoutManager = new GreedoLayoutManager(mPhotoListAdapter);
        mPhotosRecyclerList.setLayoutManager(mGreedoLayoutManager);
        mPhotosRecyclerList.setAdapter(mPhotoListAdapter);

        if (savedInstanceState == null)
            mPresenter.loadPhotos();
        else {
            int pageCount = savedInstanceState.getInt(ARG_KEY_PAGE_COUNT);
            mPresenter.setCurrentPageNumber(pageCount);
            List<Photo> photosList = savedInstanceState.getParcelableArrayList(ARG_KEY_PHOTOS_LIST);
            mPresenter.setPhotosList(photosList);
            int lastListPosition = savedInstanceState.getInt(ARG_KEY_LIST_VIEW_LAST_POSITION);
            mPhotoListAdapter.updatePhotoList(photosList);
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
        outState.putParcelableArrayList(ARG_KEY_PHOTOS_LIST, mPresenter.getPhotosList());
        outState.putInt(ARG_KEY_LIST_VIEW_LAST_POSITION, mGreedoLayoutManager.findFirstVisibleItemPosition());
        outState.putInt(ARG_KEY_PAGE_COUNT, mPresenter.getCurrentPageNumber());
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
     * Opens the dialog fragment to display the selected
     * image in fullscreen.
     *
     * @param position the position of the image that is clicked
     */
    @Override
    public void openFullScreenPhotoDialog(int position) {
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        intent.putExtra(PhotoDetailActivity.ARG_CURRENT_PHOTO_POSITION, position);
        intent.putExtra(PhotoDetailActivity.ARG_KEY_PHOTOS_LIST, mPresenter.getPhotosList());

        startActivityForResult(intent, REQUEST_PHOTO_DETAIL);
    }

    /**
     * Called when the user is scrolled to the bottom and when a new page of
     * photos need to be fetched from the server.
     */
    @Override
    public void onScrollLoadMorePhoto() {
        loadMorePhotos();
    }

    /**
     * Calls the presenter's method to fetch more photos from the server.
     * The loading more photo flag indicates whether the presenter has been already
     * fetching additional photos from the server or not. If it has been already fetching
     * new photos then it does nothing.
     */
    private void loadMorePhotos() {
        mPresenter.loadPhotos();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PHOTO_DETAIL && resultCode == Activity.RESULT_OK) {
            mPresenter.setCurrentPageNumber(data.getIntExtra(PhotoDetailActivity.ARG_CURRENT_PHOTO_POSITION, 0));
            mPhotoListAdapter.setPhotosList(data.<Photo>getParcelableArrayListExtra(PhotoDetailActivity.ARG_KEY_PHOTOS_LIST));
            mPhotosRecyclerList.scrollToPosition(data.getIntExtra(PhotoDetailActivity.ARG_CURRENT_PHOTO_POSITION, 0));
        }
    }
}
