package org.insacc.mobilechallenge.PhotoDetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.insacc.mobilechallenge.Adapter.PhotoDetailViewPagerAdapter;
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

public class PhotoDetailActivity extends AppCompatActivity implements PhotoDetailSlideContract.View {
    private static final String CLASS_NAME = PhotoDetailActivity.class.getName();
    //Tags, used to save the state of the activity
    public static final String ARG_KEY_PHOTOS_LIST = CLASS_NAME + ".photoResponse";
    public static final String ARG_CURRENT_PHOTO_POSITION = CLASS_NAME + ".photoPosition";
    public static final String ARG_CURRENT_PAGE = CLASS_NAME + ".currentPage";
    @Inject
    PhotoDetailSlideContract.Presenter mPresenter;
    //Position of the image that is selected by the user to open it in full screen.
    private int mSelectedPhotoPosition;
    private PhotoDetailViewPagerAdapter mPhotoSliderAdapter;
    @BindView(R.id.full_screen_photo_view_pager)
    ViewPager mPhotoSlider;
    @BindView(R.id.toolbar_photo_detail)
    Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail_container);
        ButterKnife.bind(this);
        DaggerPhotoDetailSlideViewComponent.builder()
                .appComponent(((MyApplication) getApplicationContext()).getAppComponent())
                .photoDetailSlideModule(new PhotoDetailSlideModule(this))
                .build().inject(this);
        setupUi(savedInstanceState);
        setupToolbar();
    }

    private void setupUi(Bundle savedInstanceState) {
        List<Photo> photosList = null;
        if (savedInstanceState != null) {
            photosList = savedInstanceState.getParcelableArrayList(ARG_KEY_PHOTOS_LIST);
            mSelectedPhotoPosition = savedInstanceState.getInt(ARG_CURRENT_PHOTO_POSITION, 0);
        } else if (getIntent() != null && getIntent().getParcelableArrayListExtra(ARG_KEY_PHOTOS_LIST) != null) {
            photosList = getIntent().getParcelableArrayListExtra(ARG_KEY_PHOTOS_LIST);
            mSelectedPhotoPosition = getIntent().getIntExtra(ARG_CURRENT_PHOTO_POSITION, 0);
        }
        setupViewPager(photosList);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(ARG_KEY_PHOTOS_LIST, (ArrayList) mPhotoSliderAdapter.getPhotosList());
                intent.putExtra(ARG_CURRENT_PHOTO_POSITION, mPhotoSlider.getCurrentItem());
                intent.putExtra(ARG_CURRENT_PAGE, mPresenter.getCurrentPageNumber());
                setResult(RESULT_OK, intent);
                PhotoDetailActivity.this.finish();
            }
        });
    }

    private void setupViewPager(List<Photo> photosList) {
        mPhotoSliderAdapter = new PhotoDetailViewPagerAdapter(getSupportFragmentManager(), photosList, this);
        mPhotoSlider.setAdapter(mPhotoSliderAdapter);
        mPhotoSlider.setCurrentItem(mSelectedPhotoPosition);
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
        outState.putParcelableArrayList(ARG_KEY_PHOTOS_LIST, (ArrayList<? extends Parcelable>)
                mPhotoSliderAdapter.getPhotosList());
        outState.putInt(ARG_CURRENT_PHOTO_POSITION, mPhotoSlider.getCurrentItem());
    }

    /**
     * Called when the user opens the last image of the current list of photos.
     */
    @Override
    public void onLastImageIsDisplayed() {
        mPresenter.loadMorePhotos();
    }

    @Override
    public void populatePhotosList(List<Photo> photos) {
        mPhotoSliderAdapter.bindNextPhotos(photos);
    }
}
