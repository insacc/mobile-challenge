package org.insacc.mobilechallenge.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fivehundredpx.greedolayout.GreedoLayoutSizeCalculator;

import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.PopularPhotos.PopularPhotosContract;
import org.insacc.mobilechallenge.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 31.07.2017.
 * RecyclerView list adapter, which displays the popular photos in a grid layout.
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoListViewHolder>
        implements GreedoLayoutSizeCalculator.SizeCalculatorDelegate {

    private PopularPhotosContract.View mView;


    private List<Photo> mPhotoList;
    private List<Double> mImageAspectRatios;

    public PhotoListAdapter(PopularPhotosContract.View view, List<Photo> photoList) {
        this.mView = view;
        this.mPhotoList = photoList;
        mImageAspectRatios = new ArrayList<>();

    }


    public void updatePhotoList(List<Photo> photos) {
        mPhotoList.addAll(photos);
        notifyDataSetChanged();

    }

    @Override
    public PhotoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item, parent, false);


        return new PhotoListViewHolder(root);
    }

    @Override
    public void onBindViewHolder(PhotoListViewHolder holder, int position) {
        Photo currentPhoto = mPhotoList.get(position);
        Glide
                .with((Context) mView)
                .load(currentPhoto.getImageUrl())


                .into(holder.mPhotoView);
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    @Override
    public double aspectRatioForIndex(int index) {
        if (index >= getItemCount()) return 1.0;
        //return mImageAspectRatios.get(index);
        Photo photo = mPhotoList.get(index);
        double result = photo.getWidth() / photo.getHeight();
        double resultTest = (double)photo.getWidth() / (double) photo.getHeight();
        return resultTest;


    }


    public class PhotoListViewHolder extends RecyclerView.ViewHolder {

        ImageView mPhotoView;

        public PhotoListViewHolder(View itemView) {
            super(itemView);
            mPhotoView = (ImageView) itemView;


        }
    }
}
