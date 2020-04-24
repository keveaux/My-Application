package com.ict_life.merchantapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.activities.CommentsActivity;
import com.ict_life.merchantapp.entities.FullDetailsPostsModel;

import java.util.ArrayList;

public class FullDetailsPostAdapter extends RecyclerView.Adapter<FullDetailsPostAdapter.MyViewHolder> {

    ArrayList<FullDetailsPostsModel> postsModelArrayList;
    Context ctx;
    private LayoutInflater inflater;
    View view;
    private TextView[] dots;


    public FullDetailsPostAdapter (Context ctx, ArrayList<FullDetailsPostsModel> postsModelArrayList) {
        this.ctx=ctx;
        this.postsModelArrayList = postsModelArrayList;
        inflater = LayoutInflater.from(ctx);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        view = inflater.inflate(R.layout.full_view_posts_layout_items, parent, false);
        MyViewHolder holder = new MyViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int pos=position;

        String likes=postsModelArrayList.get(position).getNumber_of_likes()+" likes";

        holder.caption_tv.setText(postsModelArrayList.get(position).getCaption());
        holder.posts_date_tv.setText(postsModelArrayList.get(position).getDate());
        holder.likes_tv.setText(likes);
        holder.comments_tv.setText(String.valueOf(postsModelArrayList.get(position).getNumber_of_comments()));

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(ctx,postsModelArrayList.get(position).getPost_images());
        holder.posts_images_viewpager.setAdapter(viewPagerAdapter);

        addBottomDots(0,postsModelArrayList.get(position).getPost_images().size(),holder.dotsLayout);

        int length=postsModelArrayList.get(position).getPost_images().size();

        holder.posts_images_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                addBottomDots(position,length,holder.dotsLayout);
                Toast.makeText(ctx, ""+pos, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        holder.comments_icon.setOnClickListener(v->{
            Intent intent=new Intent(ctx, CommentsActivity.class);
            ctx.startActivity(intent);
        });


    }

    private void addBottomDots(int currentPage,int length,LinearLayout dotsLayout) {

        if(length>1){
            dots = new TextView[length];
            int colorsActive = ctx.getResources().getColor(R.color.colorPrimary);
            int colorsInactive = ctx.getResources().getColor(R.color.grey);
            dotsLayout.removeAllViews();
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(ctx);
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(colorsInactive);
                dots[i].setWidth(30);
                dots[i].setGravity(Gravity.BOTTOM);
                dotsLayout.addView(dots[i]);
            }
            if (dots.length > 0)
                dots[currentPage].setTextColor(colorsActive);
        }


//        Toast.makeText(ctx, ""+length, Toast.LENGTH_SHORT).show();
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

        TextView caption_tv,posts_date_tv,likes_tv,comments_tv;
        ViewPager posts_images_viewpager;
        LinearLayout dotsLayout;
        ImageView comments_icon;


        MyViewHolder(View itemView) {
            super(itemView);

            caption_tv=itemView.findViewById(R.id.caption_tv);
            posts_date_tv=itemView.findViewById(R.id.posts_date_tv);
            likes_tv=itemView.findViewById(R.id.likes_tv);
            comments_tv=itemView.findViewById(R.id.comments_tv);
            posts_images_viewpager=itemView.findViewById(R.id.posts_images_viewpager);
            dotsLayout=itemView.findViewById(R.id.viewPagerCountDots);
            comments_icon=itemView.findViewById(R.id.comments_icon);



        }

    }
}