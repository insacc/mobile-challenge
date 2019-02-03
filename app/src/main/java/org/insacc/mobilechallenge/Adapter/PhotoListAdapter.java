package org.insacc.mobilechallenge.Adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fivehundredpx.greedolayout.GreedoLayoutSizeCalculator;

import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.Network.Config;
import org.insacc.mobilechallenge.PopularPhotos.PopularPhotosContract;
import org.insacc.mobilechallenge.R;

import java.util.List;

/**
 * Created by can on 31.07.2017.
 * RecyclerView list adapter, which displays the popular photos in a grid layout.
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoListViewHolder>
        implements GreedoLayoutSizeCalculator.SizeCalculatorDelegate {
    private PopularPhotosContract.View mView;
    private List<Photo> mPhotoList;

    public PhotoListAdapter(PopularPhotosContract.View view, List<Photo> photoList) {
        this.mView = view;
        this.mPhotoList = photoList;
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
        final int tempPosition = position;
        Glide.with((Context) mView)
                .load(currentPhoto.getImageUrl().get(Config.IMAGE_SIZE_20_INDEX).getHttpsUrl())
                .into(holder.mPhotoView);
        holder.mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.onImageClicked(tempPosition);
            }
        });
        if (position == mPhotoList.size() - 1)
            mView.onScrollLoadMorePhoto();
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    @Override
    public double aspectRatioForIndex(int index) {
        if (index >= getItemCount()) return 1.0;
        Photo photo = mPhotoList.get(index);

        return (double) photo.getWidth() / (double) photo.getHeight();
    }


    public class PhotoListViewHolder extends RecyclerView.ViewHolder {
        ImageView mPhotoView;

        public PhotoListViewHolder(View itemView) {
            super(itemView);
            mPhotoView = (ImageView) itemView;
        }
    }
}
