package org.insacc.mobilechallenge.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.PhotoDetail.PhotoDetailFragment.PhotoDetailFragment;
import org.insacc.mobilechallenge.PhotoDetail.PhotoDetailSlideContract;

import java.util.List;

/**
 * Created by can on 31.07.2017.
 * View pager adapter, which displays photo details.
 */

public class PhotoDetailViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Photo> mPhotoList;

    private PhotoDetailSlideContract.View mView;

    public PhotoDetailViewPagerAdapter(FragmentManager fm, List<Photo> photos, PhotoDetailSlideContract.View view) {
        super(fm);
        this.mPhotoList = photos;
        this.mView = view;
    }

    @Override
    public Fragment getItem(int position) {
        PhotoDetailFragment photoDetailFragment = PhotoDetailFragment.newInstance(mPhotoList.get(position));


        return photoDetailFragment;
    }

    @Override
    public int getCount() {
        return mPhotoList.size();
    }
}
