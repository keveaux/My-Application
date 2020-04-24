package com.ict_life.merchantapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.entities.PostsModel;


import java.util.ArrayList;

public class PostsListingAdapter extends RecyclerView.Adapter<PostsListingAdapter.MyViewHolder> {

    ArrayList<PostsModel> postsModelArrayList;
    Context ctx;
    private LayoutInflater inflater;
    View view;



    public PostsListingAdapter(Context ctx, ArrayList<PostsModel> postsModelArrayList) {
        this.ctx=ctx;
        this.postsModelArrayList = postsModelArrayList;
        inflater = LayoutInflater.from(ctx);

    }

    @NonNull
    @Override
    public PostsListingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


         view = inflater.inflate(R.layout.posts_recycler_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        holder.likes_tv.setText(postsModelArrayList.get(position).getLikes());
//        holder.time_tv.setText(postsModelArrayList.get(position).getTime());


        Glide.with(ctx)
                .load(postsModelArrayList.get(position).getImages_list()) // Uri of the picture
                .into(holder.posts_pics_iv);

//        ArrayList<Integer> bitmapList = new ArrayList<Integer>();
//
//        for(PostsModel postsModel:postsModelArrayList){
//            bitmapList.add(postsModel.getImages_list());
//        }
//
//        view.setOnClickListener(v->{
//            Intent intent = new Intent(ctx, ViewPagerActivity.class);
//            intent.putIntegerArrayListExtra("path_list", bitmapList);
//            intent.putExtra("position",position);
//            ctx.startActivity(intent);
//        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return postsModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

//        TextView likes_tv,time_tv;
        ImageView posts_pics_iv;

        MyViewHolder(View itemView) {
            super(itemView);

//            likes_tv=itemView.findViewById(R.id.tv_likes);
//            time_tv=itemView.findViewById(R.id.tv_time);
            posts_pics_iv=itemView.findViewById(R.id.posts_imageview);

        }

    }
}

