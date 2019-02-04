package org.insacc.mobilechallenge.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.PhotoDetail.PhotoDetailFragment.PhotoDetailFragment;
import org.insacc.mobilechallenge.PhotoDetail.PhotoDetailSlideContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 31.07.2017.
 * View pager adapter, which displays photo details.
 */

public class PhotoDetailViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int LOAD_NEXT_PAGE_THRESHOLD = 3;
    //The list of photos that this slider needs to display
    private List<Photo> mPhotoList;
    //The slider view which contains the view pager ui object.
    private PhotoDetailSlideContract.View mView;

    public PhotoDetailViewPagerAdapter(FragmentManager fm, List<Photo> photos, PhotoDetailSlideContract.View view) {
        super(fm);
        this.mPhotoList = photos == null ? new ArrayList<Photo>() : photos;
        this.mView = view;
    }

    @Override
    public Fragment getItem(int position) {
        PhotoDetailFragment photoDetailFragment = PhotoDetailFragment.newInstance(mPhotoList.get(position));
        if (position == getCount() - LOAD_NEXT_PAGE_THRESHOLD) {
            mView.onScrollLoadMorePhoto();
        }

        return photoDetailFragment;
    }

    @Override
    public int getCount() {
        return mPhotoList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    public void bindNextPhotos(List<Photo> photosList) {
        mPhotoList.addAll(photosList);
        notifyDataSetChanged();
    }

    public List<Photo> getPhotosList() {
        return mPhotoList;
    }
}
