package com.ict_life.merchantapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.activities.CommentsActivity;


public class AsksRepliesAdapter extends RecyclerView.Adapter<AsksRepliesAdapter.MyViewHolder> {

    Context ctx;
    private LayoutInflater inflater;
    View view;



    public AsksRepliesAdapter (Context ctx) {
        this.ctx=ctx;
        inflater = LayoutInflater.from(ctx);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        view = inflater.inflate(R.layout.asks_replies_rv_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(ctx).load(R.drawable.plumber).into(holder.image_one);
        Glide.with(ctx).load(R.drawable.plumber).into(holder.image_two);

        holder.comments_btn.setOnClickListener(v->{
            Intent intent=new Intent(ctx, CommentsActivity.class);
            ctx.startActivity(intent);
        });

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
        return 5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image_one,image_two;
        ImageButton comments_btn;


        MyViewHolder(View itemView) {
            super(itemView);

            image_one=itemView.findViewById(R.id.image_one);
            image_two=itemView.findViewById(R.id.image_two);
            comments_btn=itemView.findViewById(R.id.comments_btn);


        }

    }

}
