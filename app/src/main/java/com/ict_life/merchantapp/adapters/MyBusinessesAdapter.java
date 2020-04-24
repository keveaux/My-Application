package com.ict_life.merchantapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.entities.MyBusinessesDetailsModel;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class MyBusinessesAdapter extends RecyclerView.Adapter<MyBusinessesAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private static Context ctx;
    View view_;

    ArrayList<MyBusinessesDetailsModel> my_business_details_list;
    private final View.OnClickListener mOnClickListener = new MyOnClickListener();



    public MyBusinessesAdapter(Context ctx, ArrayList<MyBusinessesDetailsModel> my_business_details_list) {
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
        this.my_business_details_list = my_business_details_list;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.my_businesses_recycler_item, parent, false);

        view_=view;

        view_.setOnClickListener(mOnClickListener);



        return new MyViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        view_.setTag(position);

        if(!my_business_details_list.get(position).isSelected_business()){
            holder.business_pic_image_layout.setBackground(null);
        }

        holder.business_name_tv.setText(my_business_details_list.get(position).getBusiness_name());
        holder.rating_txt.setText(my_business_details_list.get(position).getBusiness_rating());
        holder.reviews_txt.setText(my_business_details_list.get(position).getBusiness_reviews());

        if(my_business_details_list.get(position).getImage_drawable()==0){

            holder.business_image_cv.setVisibility(View.GONE);
            holder.first_letter_layout.setVisibility(View.VISIBLE);

            String first_letter= String.valueOf(my_business_details_list.get(position).getBusiness_name().charAt(0));

            holder.first_letter_tv.setText(first_letter);

        }else {
            Glide.with(ctx).load(my_business_details_list.get(position).getImage_drawable()).into(holder.business_image);

        }





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
        return my_business_details_list.size();
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            SwitchAccountsPopUp((int)v.getTag());
        }
    }

    private void SwitchAccountsPopUp(int position) {

        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(ctx);
        View layoutView = inflater.inflate(R.layout.switch_accounts_pop_up, null);
        dialogBuilder.setView(layoutView);

        TextView switch_to_tv=layoutView.findViewById(R.id.switch_to_tv);

        switch_to_tv.append(my_business_details_list.get(position).getBusiness_name());

        android.app.AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();


    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout business_pic_image_layout,first_letter_layout;
        TextView business_name_tv,rating_txt,reviews_txt,first_letter_tv;
        CardView business_image_cv;
        ImageView business_image;


        MyViewHolder(View itemView) {
            super(itemView);

            business_pic_image_layout=itemView.findViewById(R.id.business_pic_image_layout);
            business_name_tv=itemView.findViewById(R.id.business_name_tv);
            rating_txt=itemView.findViewById(R.id.rating_txt);
            reviews_txt=itemView.findViewById(R.id.reviews_txt);
            business_image_cv=itemView.findViewById(R.id.business_image_cv);
            business_image=itemView.findViewById(R.id.business_image);
            first_letter_layout=itemView.findViewById(R.id.first_letter_layout);
            first_letter_tv=itemView.findViewById(R.id.first_letter_tv);



        }

    }



}
