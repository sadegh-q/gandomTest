package com.ghandroid.app.test_01.ui.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghandroid.app.test_01.R;
import com.ghandroid.app.test_01.ui.model.Photo;
import com.ghandroid.app.test_01.utils.DragListHelper.ItemTouchHelperAdapter;
import com.ghandroid.app.test_01.utils.DragListHelper.ItemTouchHelperViewHolder;
import com.ghandroid.app.test_01.utils.DragListHelper.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private OnItemClickListener onItemClickListener;
    private List<Photo> photos;
    private Context context;
    private final OnStartDragListener mDragStartListener;

    public ListAdapter(Context context, OnStartDragListener dragStartListener) {
        this.context = context;
        mDragStartListener = dragStartListener;
        this.photos = new ArrayList<>();
    }


    public void updateList(List<Photo> photos) {
        if (photos != null) {
            this.photos.clear();
            this.photos.addAll(photos);
            notifyDataSetChanged();
        }
    }

    public void setOnClickListener(ListAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(position);
        holder.imageViewHandle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(photos, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        photos.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        @BindView(R.id.textViewTitle)
        TextView textViewTitle;
        @BindView(R.id.imageViewHandle)
        ImageView imageViewHandle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(final int position) {
            final Photo photo = photos.get(position);
            textViewTitle.setText(photo.getDescription());

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(itemView, position);
                }
            });
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);

        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);

        }
    }
}

