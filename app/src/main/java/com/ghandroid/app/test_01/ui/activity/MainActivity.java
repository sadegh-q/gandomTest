package com.ghandroid.app.test_01.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.ghandroid.app.test_01.R;
import com.ghandroid.app.test_01.ui.adapter.ListAdapter;
import com.ghandroid.app.test_01.ui.model.Photo;
import com.ghandroid.app.test_01.ui.model.PhotoResponse;
import com.ghandroid.app.test_01.network.APIClient;
import com.ghandroid.app.test_01.network.APIInterface;
import com.ghandroid.app.test_01.utils.DragListHelper.OnStartDragListener;
import com.ghandroid.app.test_01.utils.DragListHelper.SimpleItemTouchHelperCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnStartDragListener {
    public static final String GO_TO_DETAILS = "goToDetail";
    private ListAdapter listAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @BindView(R.id.recyclerViewCategory)
    RecyclerView recyclerViewCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getCategories();
    }

    private void setupRecyclerView(List<Photo> photos) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewCategory.setLayoutManager(layoutManager);
        listAdapter = new ListAdapter(this, this);
        listAdapter.updateList(photos);
        recyclerViewCategory.setAdapter(listAdapter);
        listAdapter.setOnClickListener((view, position) -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(GO_TO_DETAILS, new Gson().toJson(photos.get(position)));
            startActivity(intent);
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(listAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerViewCategory);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    private void getCategories() {
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);

        Call<PhotoResponse> call = apiInterface.getPhotosCategory();

        call.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, getString(R.string.connected), Toast.LENGTH_SHORT).show();
                    List<Photo> photos = response.body().getPhotos();
                    setupRecyclerView(photos);
                }
            }

            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
