package org.insacc.mobilechallenge.Adapter;

import android.content.Context;

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
    private static final int LOAD_MORE_PAGE_THRESHOLD = 3;
    private PopularPhotosContract.View mView;
    private List<Photo> mPhotoList = new ArrayList<>();

    public PhotoListAdapter(PopularPhotosContract.View view) {
        this.mView = view;
    }

    public void setPhotosList(List<Photo> photosList) {
        mPhotoList = photosList;
        notifyDataSetChanged();
    }

    public void appendPhotosList(List<Photo> photos) {
        mPhotoList.addAll(photos);
        notifyDataSetChanged();
    }

    public List<Photo> getPhotosList() {
        return mPhotoList;
    }

    @Override
    public PhotoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item, parent, false);

        return new PhotoListViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final PhotoListViewHolder holder, int position) {
        Photo currentPhoto = mPhotoList.get(position);
        Glide.with((Context) mView)
                .load(currentPhoto.getImageUrl().getSmall())
                .apply(RequestOptions.placeholderOf(R.color.dimGrey))
                .into(holder.mPhotoView);
        holder.mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();

                if (position == RecyclerView.NO_POSITION) return;
                mView.onPhotoClicked(position);
            }
        });
        if (position == mPhotoList.size() - LOAD_MORE_PAGE_THRESHOLD) {
            mView.onScrollLoadMorePhoto();
        }
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
