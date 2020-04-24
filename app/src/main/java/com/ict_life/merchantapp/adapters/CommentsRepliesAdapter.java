package com.ict_life.merchantapp.adapters;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ict_life.merchantapp.R;

import java.util.ArrayList;

public class CommentsRepliesAdapter extends RecyclerView.Adapter<CommentsRepliesAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mImagesList;

    public CommentsRepliesAdapter(Context context){
        mContext = context;
//        mImagesList = imagesList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.comment_replies_rv_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String name="Stacey Otiende";
        String comment="left the following comment what do you think of this particular comment?";

        String name_and_comment=name+" "+comment;

        Spannable wordtoSpan = new SpannableString(name_and_comment);

        wordtoSpan.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.black)), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan .setSpan(new RelativeSizeSpan(1.1f), 0,name.length(), 0);

        holder.name_and_comment_tv.setText(wordtoSpan);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name_and_comment_tv;

        public ViewHolder(View v) {
            super(v);

            name_and_comment_tv=v.findViewById(R.id.name_and_comment_tv);

        }


    }

}
