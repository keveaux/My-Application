package com.ict_life.merchantapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.ict_life.merchantapp.R;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class PostsRecyclerAdapter extends RecyclerView.Adapter<PostsRecyclerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<String> images_list;
    private static Context ctx;



    public PostsRecyclerAdapter(Context ctx, ArrayList<String> images_list) {
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
        this.images_list = images_list;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.posts_viewpager_items, parent, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new MyViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Toast.makeText(ctx, "" + images_list.get(position), Toast.LENGTH_SHORT).show();

        Glide.with(ctx)
                .load(images_list.get(position)) // Uri of the picture
                .into(holder.posts_imageview);


    }



    @Override
    public int getItemCount() {
        return images_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView posts_imageview;

        MyViewHolder(View itemView) {
            super(itemView);
            posts_imageview = itemView.findViewById(R.id.viewpager_imageview);

//            scaleImage(posts_imageview);
        }

    }




}
