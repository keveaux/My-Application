package com.ict_life.merchantapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.entities.FontsModel;

import java.util.ArrayList;

public class FontTypeAdapter extends RecyclerView.Adapter<FontTypeAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<FontsModel> font_list;
    ChangeFontTypeInterface changeFontTypeInterface;

    public FontTypeAdapter(Context context, ArrayList<FontsModel> font_list,ChangeFontTypeInterface changeFontTypeInterface){
        mContext = context;
        this.font_list = font_list;
        this.changeFontTypeInterface=changeFontTypeInterface;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.font_recyclerview_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.font_type_tv.setText(font_list.get(position).getFont_name());
        holder.AG_tv.setTypeface(font_list.get(position).getTypeface());
        holder.font_type_tv.setTypeface(font_list.get(position).getTypeface());

        holder.font_type_card.setOnClickListener(v->{
            changeFontTypeInterface.changefont(font_list.get(position).getTypeface());
        });

    }

    @Override
    public int getItemCount() {
        return font_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView font_type_tv,AG_tv;
        CardView font_type_card;

        public ViewHolder(View v) {
            super(v);
            font_type_tv=v.findViewById(R.id.font_type_tv);
            AG_tv=v.findViewById(R.id.AG_tv);
            font_type_card=v.findViewById(R.id.font_type_card);

        }

    }

    public interface ChangeFontTypeInterface {
        void changefont(Typeface typeface);
    }
}
