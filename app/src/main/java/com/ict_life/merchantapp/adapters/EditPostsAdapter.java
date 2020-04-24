package com.ict_life.merchantapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.photoeditor.photoeditor.activities.EditImageActivity;

import java.util.ArrayList;

public class EditPostsAdapter extends RecyclerView.Adapter<EditPostsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mImagesList;
    Activity activity;
    ShowDialogInterface showDialogInterface;

    public EditPostsAdapter(Context context, ArrayList<String> imagesList, Activity activity,ShowDialogInterface showDialogInterface){
        mContext = context;
        mImagesList = imagesList;
        this.activity=activity;
        this.showDialogInterface=showDialogInterface;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.edit_posts_recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext).load(mImagesList.get(holder.getAdapterPosition())).into(holder.getImage());


        holder.post_image.setOnClickListener(v->{

            Intent intent = new Intent(mContext, EditImageActivity.class);
            intent.putExtra("post_to_edit", mImagesList.get(holder.getAdapterPosition()));
            intent.putExtra("position",position);
            intent.putStringArrayListExtra("images_list", mImagesList);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
//            activity.finish();

            showDialogInterface.showLoading("loading");

            activity.finish();

        });

        holder.remove_image_btn.setOnClickListener(v->{
            showDialogInterface.removeSelectedPost(position);
        });

    }

    @Override
    public int getItemCount() {
        return mImagesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView post_image;
        private ImageView remove_image_btn;

        public ViewHolder(View v) {
            super(v);
            post_image = v.findViewById(R.id.edit_post_iv);
            remove_image_btn = v.findViewById(R.id.remove_image_btn);
        }

        public ImageView getImage() {
            return post_image;
        }

    }

    public interface ShowDialogInterface {
        void showLoading(String s);
        void removeSelectedPost(int position);
    }
}
