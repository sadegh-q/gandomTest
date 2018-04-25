package com.ghandroid.app.test_01.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ghandroid.app.test_01.R;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<String> photos;
    private Context context;

    public PhotoAdapter(Context context) {
        this.context = context;
        this.photos = new ArrayList<>();
    }


    public void updateList(List<String> photos) {
        if (photos != null) {
            this.photos.clear();
            this.photos.addAll(photos);
            notifyDataSetChanged();
        }
    }

    public void setOnClickListener(PhotoAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(final int position) {
            final String photo = context.getString(R.string.image_base_url) + photos.get(position);
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(itemView, getLayoutPosition());
                }
            });

            Glide.with(context)
                    .load(photo)
                    .centerCrop()
                    .placeholder(R.drawable.ic_broken_image_gray_24dp)
                    .into(imageView);
        }
    }
}

