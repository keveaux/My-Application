package com.ict_life.merchantapp.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ict_life.merchantapp.R;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {

    Context ctx;
    private LayoutInflater inflater;
    View view;



    public ReviewsAdapter(Context ctx) {
        this.ctx=ctx;
        inflater = LayoutInflater.from(ctx);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        view = inflater.inflate(R.layout.reviews_recycler_items, parent, false);
        MyViewHolder holder = new MyViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            holder.btn_reply_review.setOnClickListener(v->{
                holder.btn_reply_review.setVisibility(View.GONE);
                holder.send_reply_layout.setVisibility(View.VISIBLE);
            });

            holder.send_btn.setOnClickListener(v->{
                if(holder.reply_et.getText().toString().equals("")){
                    Toast.makeText(ctx, "Please enter appropriate reply", Toast.LENGTH_SHORT).show();
                }else {
                    String reply=holder.reply_et.getText().toString();
                    holder.send_reply_layout.setVisibility(View.GONE);
                    holder.replies_layout.setVisibility(View.VISIBLE);
                    holder.merchant_review_txt.setText(reply);
                }
            });

        Glide.with(ctx).load(R.drawable.pic1).into(holder.image_one);
        Glide.with(ctx).load(R.drawable.pic4).into(holder.image_two);


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

            Button btn_reply_review,delete_review;
            ImageButton send_btn;
            RelativeLayout send_reply_layout,replies_layout;
            EditText reply_et;
            TextView merchant_review_txt;
            ImageView image_one,image_two;

        MyViewHolder(View itemView) {
            super(itemView);

            btn_reply_review=itemView.findViewById(R.id.btn_reply_review);
            send_reply_layout =itemView.findViewById(R.id.send_reply_layout);
            reply_et=itemView.findViewById(R.id.reply_et);
            send_btn=itemView.findViewById(R.id.send_btn);
            delete_review=itemView.findViewById(R.id.delete_review);
            replies_layout=itemView.findViewById(R.id.replies_layout);
            merchant_review_txt=itemView.findViewById(R.id.merchant_review_txt);
            image_one=itemView.findViewById(R.id.image_one);
            image_two=itemView.findViewById(R.id.image_two);




        }

    }
}

