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
import org.insacc.mobilechallenge.Network.Config;
import org.insacc.mobilechallenge.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by can on 31.07.2017.
 * This fragment is used in view pager(photo slider) and display a photo that is passed to the
 * fragment in full screen.
 */

public class PhotoDetailFragment extends Fragment implements PhotoDetailContract.View {

    //Tag, used to set the bundle arguments
    private static String PHOTO_DETAIL = "photoDetail";

    @Inject
    PhotoDetailContract.Presenter mPresenter;

    private Photo mPhotoDetail;

    @BindView(R.id.full_screen_photo_item)
    ImageView mFullScreenImage;

    @BindView(R.id.full_screen_photo_title)
    TextView mPhotoTitle;

    @BindView(R.id.full_screen_photo_back_btn)
    ImageButton mBackButton;

    //Click listener for the buttons.
    private View.OnClickListener mListener;

    /**
     *  Creates and returns a PhotoDetailFragment object for the given Photo @param photo
     * @param photo The photo object that needs to be displayed in full screen.
     * @return the photoDetailFragment with the photo object set as Bundle argument
     */
    public static PhotoDetailFragment newInstance(Photo photo) {

        PhotoDetailFragment photoDetailFragment = new PhotoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PHOTO_DETAIL, photo);
        photoDetailFragment.setArguments(bundle);


        return photoDetailFragment;
    }

    /**
     * Extracts the details of the photo from the fragment bundle.
     */
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

    /**
     * Provides Click listener object for the button clicks
     * @return the click listener object which handles the back button click.
     */
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

    /**
     * Loads the image for the given photo object from the server and displays it using the
     * Glide library. The image that is fetched from the server is size of 31.
     */
    @Override
    public void loadPhotoFromServer() {
        Glide.with(this)

                .load(mPhotoDetail.getImageUrl().get(Config.IMAGE_SIZE_31_INDEX).getHttpsUrl())
                .apply(RequestOptions.fitCenterTransform())
                .into(mFullScreenImage);
    }

    /**
     * Populates the details of the photo.
     */
    @Override
    public void loadPhotoDetails() {
        mPhotoTitle.setText(mPhotoDetail.getName());
    }

    /**
     * Broadcasts and event to dismiss the dialog fragment.
     */
    @Override
    public void dismissDialog() {
        EventBus.getDefault().post(new DismissDialogEvent());
    }
}
