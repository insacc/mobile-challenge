package org.insacc.mobilechallenge.PhotoDetail.PhotoDetailFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import org.insacc.mobilechallenge.Model.Photo;
import org.insacc.mobilechallenge.MyApplication;
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
    private static final String CLASS_NAME = PhotoDetailFragment.class.getName();
    //Tag, used to set the bundle arguments
    private static final String ARG_KEY_PHOTO_DETAIL = CLASS_NAME + ".photoDetail";
    @Inject
    PhotoDetailContract.Presenter mPresenter;
    private Photo mCurrentPhoto;
    @BindView(R.id.full_screen_photo_item)
    ImageView mFullScreenImage;
    @BindView(R.id.full_screen_photo_title)
    TextView mPhotoTitle;

    /**
     * Creates and returns a PhotoDetailFragment object for the given Photo @param photo
     *
     * @param photo The photo object that needs to be displayed in full screen.
     * @return the photoDetailFragment with the photo object set as Bundle argument
     */
    public static PhotoDetailFragment newInstance(Photo photo) {
        PhotoDetailFragment photoDetailFragment = new PhotoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_KEY_PHOTO_DETAIL, photo);
        photoDetailFragment.setArguments(bundle);

        return photoDetailFragment;
    }

    /**
     * Extracts the details of the photo from the fragment bundle.
     */
    private void extractPhotoDetail() {
        if (getArguments() != null && getArguments().getParcelable(ARG_KEY_PHOTO_DETAIL) != null)
            mCurrentPhoto = getArguments().getParcelable(ARG_KEY_PHOTO_DETAIL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.full_screen_photo_fragment, container, false);
        DaggerPhotoDetailComponent.builder()
                .appComponent(((MyApplication) getActivity().getApplicationContext()).getAppComponent())
                .photoDetailModule(new PhotoDetailModule(this)).build().inject(this);
        ButterKnife.bind(this, root);
        extractPhotoDetail();
        mPresenter.setPhotoDetails();

        return root;
    }

    /**
     * Loads the image for the given photo object from the server and displays it using the
     * Glide library.
     */
    @Override
    public void loadPhoto() {
        Glide.with(this)
                .load(mCurrentPhoto.getImageUrl().getRegular())
                .apply(RequestOptions.fitCenterTransform())
                .apply(RequestOptions.placeholderOf(R.color.lightGrey))
                .into(mFullScreenImage);
    }

    /**
     * Populates the details of the photo.
     */
    @Override
    public void setPhotoDescription() {
        mPhotoTitle.setText(mCurrentPhoto.getDescription());
    }
}
