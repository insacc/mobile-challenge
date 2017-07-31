package org.insacc.mobilechallenge.PopularPhotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.R;

import java.util.List;

public class PopularPhotosActivity extends AppCompatActivity implements PopularPhotosContract.View {

    private PopularPhotosContract.Presenter mPresenter;

    RecyclerView mPhotosRecyclerList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void populatePhotosList(List<Photo> photos) {

    }
}
