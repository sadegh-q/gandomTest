package com.ghandroid.app.test_01.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.ghandroid.app.test_01.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ghandroid.app.test_01.ui.activity.DetailActivity.SHOW_FULL_SIZE_IMAGE;

public class PhotoActivity extends AppCompatActivity {
    @BindView(R.id.imageViewFullSize)
    SubsamplingScaleImageView imageViewFullSize;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        String photoString = getIntent().getStringExtra(SHOW_FULL_SIZE_IMAGE);
        if (!TextUtils.isEmpty(photoString)) {
            imageUrl = getString(R.string.image_base_url) + photoString;
            updateView();
        }
    }

    private void updateView() {
        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_broken_image_gray_24dp)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageViewFullSize.setImage(ImageSource.bitmap(((GlideBitmapDrawable) resource).getBitmap()));
                    }
                });
    }
}
