package com.ict_life.merchantapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.activities.AsksRepliesActivity;
import com.ict_life.merchantapp.activities.CommentsActivity;
import com.ict_life.merchantapp.activities.RecommendOtherActivity;
import com.ict_life.merchantapp.entities.AsksModel;

import java.util.ArrayList;

public class AsksAdapter extends RecyclerView.Adapter<AsksAdapter.MyViewHolder> {

    ArrayList<AsksModel> postsModelArrayList;
    Context ctx;
    private LayoutInflater inflater;
    View view;



    public AsksAdapter (Context ctx, ArrayList<AsksModel> postsModelArrayList) {
        this.ctx=ctx;
        this.postsModelArrayList = postsModelArrayList;
        inflater = LayoutInflater.from(ctx);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        view = inflater.inflate(R.layout.asks_rv_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.view_replies_layout.setOnClickListener(v->{
           openActivity();
        });

        holder.replies_iv.setOnClickListener(v->{
            openActivity();
        });

        holder.replies_tv.setOnClickListener(v->{
            openActivity();
        });

        holder.reply_asks_layout.setOnClickListener(v->{
            holder.send_reply_layout.setVisibility(View.VISIBLE);
        });



        holder.recommend_btn.setOnClickListener(v->{
            holder.send_reply_layout.setVisibility(View.VISIBLE);
        });

        holder.recommend_tv.setOnClickListener(v->{
            holder.send_reply_layout.setVisibility(View.VISIBLE);
        });

        holder.cancel_button.setOnClickListener(v->{
            holder.send_reply_layout.setVisibility(View.GONE);
        });

        holder.recommend_myself_layout.setOnClickListener(v->{
            holder.image_lotie_recommend.playAnimation();
            holder.myself_tv.setTextColor(ctx.getResources().getColor(R.color.colorAccent));
        });

        holder.recommend_other_layout.setOnClickListener(v->{
            openRecommendOtherActivity();
        });

        holder.other_iv.setOnClickListener(v->{
            openRecommendOtherActivity();
        });

        holder.other_tv.setOnClickListener(v->{
            openRecommendOtherActivity();
        });

    }

    private void openRecommendOtherActivity(){
        Intent intent=new Intent(ctx, RecommendOtherActivity.class);
        ctx.startActivity(intent);
    }

    private void openActivity(){
        Intent intent=new Intent(ctx, AsksRepliesActivity.class);
        ctx.startActivity(intent);
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

        //        TextView likes_tv,time_tv;
        LinearLayout view_replies_layout,reply_asks_layout;
        RelativeLayout send_reply_layout,recommend_myself_layout,recommend_other_layout;
        ImageButton cancel_button;
        LottieAnimationView image_lotie_recommend;
        TextView myself_tv,recommend_tv,replies_tv,other_tv;
        ImageButton recommend_btn,replies_iv;
        ImageView other_iv;




        MyViewHolder(View itemView) {
            super(itemView);

            view_replies_layout=itemView.findViewById(R.id.view_replies_layout);
            reply_asks_layout=itemView.findViewById(R.id.reply_asks_layout);
            send_reply_layout=itemView.findViewById(R.id.send_reply_layout);
            cancel_button=itemView.findViewById(R.id.cancel_button);
            recommend_myself_layout=itemView.findViewById(R.id.recommend_myself_layout);
            image_lotie_recommend=itemView.findViewById(R.id.image_lotie_recommend);
            myself_tv=itemView.findViewById(R.id.myself_tv);
            recommend_tv=itemView.findViewById(R.id.recommend_tv);
            recommend_btn=itemView.findViewById(R.id.recommend_btn);
            replies_tv=itemView.findViewById(R.id.replies_tv);
            replies_iv=itemView.findViewById(R.id.replies_iv);
            other_iv=itemView.findViewById(R.id.other_iv);
            other_tv=itemView.findViewById(R.id.other_tv);
            recommend_other_layout=itemView.findViewById(R.id.recommend_other_layout);

        }

    }

}
