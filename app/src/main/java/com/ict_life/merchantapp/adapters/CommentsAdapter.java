package com.ict_life.merchantapp.adapters;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ict_life.merchantapp.R;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mImagesList;

    public CommentsAdapter(Context context) {
        mContext = context;
//        mImagesList = imagesList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.comments_recycler_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        String sourceString = "<b>" + "Miriam Makeba" + "</b> " + mContext.getResources().getString();
//        holder.name_and_comment_tv.setText(Html.fromHtml(sourceString));

        String name="Miriam Makeba";
        String comment="left the following comment what do you think of this particular comment?";

        String name_and_comment=name+" "+comment;

        Spannable wordtoSpan = new SpannableString(name_and_comment);

        wordtoSpan.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.black)), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan .setSpan(new RelativeSizeSpan(1.1f), 0,name.length(), 0);


        holder.name_and_comment_tv.setText(wordtoSpan);

        holder.comment_replies_btn.setOnClickListener(v -> {
                holder.comment_replies_rv.setAdapter(new CommentsRepliesAdapter(mContext));
                holder.show_comments_layout.setVisibility(View.GONE);
                holder.show_less_tv.setVisibility(View.VISIBLE);
        });

        holder.show_less_tv.setOnClickListener(v -> {
            holder.comment_replies_rv.setAdapter(null);
            holder.show_comments_layout.setVisibility(View.VISIBLE);
            holder.show_less_tv.setVisibility(View.GONE);
        });

        holder.like_comment_btn.setOnClickListener(v->{

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
        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name_and_comment_tv, time_tv, reply_tv, no_of_comments_tv, no_of_likes_tv,show_less_tv;
        ImageButton like_comment_btn, comment_replies_btn;
        RecyclerView comment_replies_rv;
        LinearLayout show_comments_layout;


        public ViewHolder(View v) {
            super(v);

            name_and_comment_tv = v.findViewById(R.id.name_and_comment_tv);
            time_tv = v.findViewById(R.id.time_tv);
            reply_tv = v.findViewById(R.id.reply_tv);
            like_comment_btn = v.findViewById(R.id.like_comment_btn);
            comment_replies_btn = v.findViewById(R.id.comment_replies_btn);
            no_of_comments_tv = v.findViewById(R.id.no_of_comments_tv);
            no_of_likes_tv = v.findViewById(R.id.no_of_likes_tv);
            comment_replies_rv = v.findViewById(R.id.comment_replies_rv);
            show_less_tv = v.findViewById(R.id.show_less_tv);
            show_comments_layout = v.findViewById(R.id.show_comments_layout);



        }


    }
}
