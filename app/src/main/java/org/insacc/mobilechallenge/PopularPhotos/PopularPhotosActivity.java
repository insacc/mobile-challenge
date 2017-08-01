package org.insacc.mobilechallenge.PopularPhotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class PopularPhotosActivity extends AppCompatActivity implements PopularPhotosContract.View {

    private static final String PHOTO_RESPONSE_STATE = "photoResponseState";
    private static final String LIST_VIEW_LAST_POSITION = "listLastPosition";
    private static final String PAGE_COUNT_STATE = "pageCount";


    @Inject
    PopularPhotosContract.Presenter mPresenter;

    @BindView(R.id.popular_photo_list)
    RecyclerView mPhotosRecyclerList;

    private GreedoLayoutManager mGreedoLayoutManager;

    private PhotoListAdapter mPhotoListAdapter;

    private int mPageCount = 1;

    private boolean mLoadingMorePhotoFlag;

    private PhotosResponse mPhotosResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_photos_activity);

        ButterKnife.bind(this);

        DaggerPopularPhotosComponent.builder().appComponent(((MyApplication) getApplicationContext()).getAppComponent())
                .getPhotosServiceModule(new GetPhotosServiceModule()).popularPhotosModule(new PopularPhotosModule(this))
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PHOTO_RESPONSE_STATE, mPhotosResponse);
        outState.putInt(LIST_VIEW_LAST_POSITION, mGreedoLayoutManager.findFirstVisibleItemPosition());
        outState.putInt(PAGE_COUNT_STATE, mPageCount);
    }

    @Override
    public void populatePhotosList(List<Photo> photos) {
        mPhotoListAdapter.updatePhotoList(photos);
        mLoadingMorePhotoFlag = false;
    }

    @Override
    public void displayLoadPhotoErrorMsg() {

        Toast.makeText(this, getString(R.string.photo_load_fail), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onImageClicked(int position) {
        mPresenter.callFullScreenPhotoDialog(position);
    }

    @Override
    public void setPhotosResponse(PhotosResponse photosResponse) {
        if (mPhotosResponse == null)
            mPhotosResponse = photosResponse;
        else
            mPhotosResponse.getPhotos().addAll(photosResponse.getPhotos());
    }

    @Override
    public void openFullScreenPhotoDialog(int position) {
        PhotoDetailSlideDialog photoDetailSlideDialog = PhotoDetailSlideDialog.newInstance(mPhotosResponse, position);
        photoDetailSlideDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onScrollLoadMorePhoto() {
        loadMorePhotos(false);
    }

    @Override
    public void notifySliderPhotosUpdated() {
        EventBus.getDefault().post(new PhotosUpdatedEvent(mPhotosResponse.getPhotos()));
    }

    private void loadMorePhotos(boolean shouldNotifySlider) {
        if (!mLoadingMorePhotoFlag) {
            mPageCount++;
            mPresenter.loadPhotos(mPageCount, getString(R.string.consumer_key), shouldNotifySlider);
            mLoadingMorePhotoFlag = true;
        }

    }

    @Subscribe
    public void onPhotoSliderCloseScroll(ScrollToPositionEvent event) {
        mGreedoLayoutManager.scrollToPosition(event.getPosition());


    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }
}
