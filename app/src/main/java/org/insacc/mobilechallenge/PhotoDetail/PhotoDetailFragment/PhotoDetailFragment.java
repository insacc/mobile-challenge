package org.insacc.mobilechallenge.PhotoDetail.PhotoDetailFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.insacc.mobilechallenge.Events.DismissDialogEvent;
import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.MyApplication;
import org.insacc.mobilechallenge.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by can on 31.07.2017.
 */

public class PhotoDetailFragment extends Fragment implements PhotoDetailContract.View {


    @Inject
    PhotoDetailContract.Presenter mPresenter;

    private static String PHOTO_DETAIL = "photoDetail";

    private Photo mPhotoDetail;

    @BindView(R.id.full_screen_photo_item)
    ImageView mFullScreenImage;

    @BindView(R.id.full_screen_photo_title)
    TextView mPhotoTitle;

    @BindView(R.id.full_screen_photo_back_btn)
    ImageButton mBackButton;

    private View.OnClickListener mListener;


    public static PhotoDetailFragment newInstance(Photo photo) {

        PhotoDetailFragment photoDetailFragment = new PhotoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PHOTO_DETAIL, photo);
        photoDetailFragment.setArguments(bundle);


        return photoDetailFragment;
    }

    private void getPhotoDetail() {
        if (getArguments() != null && getArguments().getParcelable(PHOTO_DETAIL) != null)
            mPhotoDetail = getArguments().getParcelable(PHOTO_DETAIL);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListener = providesButtonListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.full_screen_photo_fragment, container, false);

        DaggerPhotoDetailComponent.builder().appComponent(((MyApplication) getActivity().getApplicationContext()).getAppComponent())
                .photoDetailModule(new PhotoDetailModule(this)).build().inject(this);

        ButterKnife.bind(this, root);

        mBackButton.setOnClickListener(mListener);
        getPhotoDetail();

        mPresenter.callLoadPhotoDetails();


        return root;
    }

    private View.OnClickListener providesButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.full_screen_photo_back_btn:
                        mPresenter.callDismiss();
                        break;

                }
            }
        };
    }

    @Override
    public void loadPhotoFromServer() {
        Glide.with(this)

                .load(mPhotoDetail.getImageUrl())
                .apply(RequestOptions.fitCenterTransform())
                .into(mFullScreenImage);
    }

    @Override
    public void loadPhotoDetails() {
        mPhotoTitle.setText(mPhotoDetail.getName());
    }

    @Override
    public void dismissDialog() {
        EventBus.getDefault().post(new DismissDialogEvent());
    }
}
