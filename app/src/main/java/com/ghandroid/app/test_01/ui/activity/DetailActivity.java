package com.ghandroid.app.test_01.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.ghandroid.app.test_01.R;
import com.ghandroid.app.test_01.ui.adapter.PhotoAdapter;
import com.ghandroid.app.test_01.ui.model.Photo;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ghandroid.app.test_01.ui.activity.MainActivity.GO_TO_DETAILS;

public class DetailActivity extends AppCompatActivity {
    public static final String SHOW_FULL_SIZE_IMAGE = "showFullSizeImage";
    @BindView(R.id.textViewListTitle)
    TextView textViewListTitle;
    @BindView(R.id.recyclerViewPhotos)
    RecyclerView recyclerViewPhotos;

    private Photo photo;
    private PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        String photoString = getIntent().getStringExtra(GO_TO_DETAILS);
        if (!TextUtils.isEmpty(photoString)) {
            photo = new Gson().fromJson(photoString, Photo.class);
            updateView();
        }

    }

    private void updateView() {
        textViewListTitle.setText(photo.getDescription());

        photoAdapter = new PhotoAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewPhotos.setAdapter(photoAdapter);
        recyclerViewPhotos.setLayoutManager(layoutManager);
        photoAdapter.updateList(photo.getImageUrls());
        photoAdapter.setOnClickListener((view, position) -> {
            Intent intent = new Intent(this, PhotoActivity.class);
            intent.putExtra(SHOW_FULL_SIZE_IMAGE, photo.getImageUrls().get(position));
            startActivity(intent);
        });
    }
}
