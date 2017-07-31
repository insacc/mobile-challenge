package org.insacc.mobilechallenge.PopularPhotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.fivehundredpx.greedolayout.GreedoLayoutManager;
import com.fivehundredpx.greedolayout.GreedoSpacingItemDecoration;

import org.insacc.mobilechallenge.Adapter.PhotoListAdapter;
import org.insacc.mobilechallenge.AppModule.GetPhotosServiceModule;
import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.MyApplication;
import org.insacc.mobilechallenge.R;
import org.insacc.mobilechallenge.Util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularPhotosActivity extends AppCompatActivity implements PopularPhotosContract.View {

    @Inject
    PopularPhotosContract.Presenter mPresenter;

    @BindView(R.id.popular_photo_list)
    RecyclerView mPhotosRecyclerList;

    private GreedoLayoutManager mGreedoLayoutManager;

    private PhotoListAdapter mPhotoListAdapter;

    private int mPageCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_photos_activity);

        ButterKnife.bind(this);

        DaggerPopularPhotosComponent.builder().appComponent(((MyApplication) getApplicationContext()).getAppComponent())
                .getPhotosServiceModule(new GetPhotosServiceModule()).popularPhotosModule(new PopularPhotosModule(this))
                .build().inject(this);

        mPhotoListAdapter = new PhotoListAdapter(this, new ArrayList<Photo>());
        mGreedoLayoutManager = new GreedoLayoutManager(mPhotoListAdapter);

        mPhotosRecyclerList.setLayoutManager(mGreedoLayoutManager);
        mPhotosRecyclerList.setAdapter(mPhotoListAdapter);

        mGreedoLayoutManager.setMaxRowHeight(Util.dpToPx(300, this));

        mPresenter.loadPhotos(mPageCount, getString(R.string.consumer_key));


    }

    @Override
    public void populatePhotosList(List<Photo> photos) {
        mPhotoListAdapter.updatePhotoList(photos);
    }

    @Override
    public void displayLoadPhotoErrorMsg() {

        Toast.makeText(this, getString(R.string.photo_load_fail), Toast.LENGTH_LONG).show();
    }
}
