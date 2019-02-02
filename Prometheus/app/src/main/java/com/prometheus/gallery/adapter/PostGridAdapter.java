package com.prometheus.gallery.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prometheus.gallery.Detail;
import com.prometheus.gallery.R;
import com.prometheus.gallery.obj.PostObj;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostGridAdapter extends RecyclerView.Adapter<PostGridAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private Activity activity;
    private ArrayList<PostObj> postObjs;


    public PostGridAdapter(Activity activity, ArrayList<PostObj> postObjs) {
        this.mInflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.postObjs = postObjs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.post_grid_layout, viewGroup, false);
        return new PostGridAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

//        viewHolder.name_tv.setText(studentObjs.get(position).getName());
        Picasso.get().load(postObjs.get(position).getPhotoPath()).placeholder(R.drawable.ic_more_horiz_24dp).error(R.drawable.ic_image_24dp).into(viewHolder.img);

        viewHolder.parent_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Detail.class);
                intent.putExtra("postid",postObjs.get(position).getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postObjs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public CardView parent_view;

        public ViewHolder(View view) {
            super(view);
            this.img = view.findViewById(R.id.img);
            this.parent_view = view.findViewById(R.id.parent_view);
        }
    }

}