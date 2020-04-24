package com.ict_life.merchantapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.entities.KindOfBusiness;

import java.util.ArrayList;

public class SingleAdapter extends RecyclerView.Adapter<SingleAdapter.SingleViewHolder> {

    private Context context;
    private ArrayList<KindOfBusiness> kindOfBusinesses;
    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition ;

    public SingleAdapter(Context context, ArrayList<KindOfBusiness> kindOfBusinesses) {
        this.context = context;
        this.kindOfBusinesses = kindOfBusinesses;
    }

    public void setKindOfBusinesses(ArrayList<KindOfBusiness> kindOfBusinesses) {
        this.kindOfBusinesses = new ArrayList<>();
        this.kindOfBusinesses = kindOfBusinesses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee, viewGroup, false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder singleViewHolder, int position) {
        singleViewHolder.bind(kindOfBusinesses.get(position));
    }

    @Override
    public int getItemCount() {
        return kindOfBusinesses.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void bind(final KindOfBusiness kindOfBusiness) {
            if (checkedPosition == -1) {
                imageView.setVisibility(View.GONE);
            } else {
                if (checkedPosition == getAdapterPosition()) {
//                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE);
                }
            }
            textView.setText(kindOfBusiness.getName());

            itemView.setOnClickListener(view -> {
                imageView.setVisibility(View.VISIBLE);
                if (checkedPosition != getAdapterPosition()) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = getAdapterPosition();
                }
            });
        }
    }

    public KindOfBusiness getSelected() {
        if (checkedPosition != -1) {
            return kindOfBusinesses.get(checkedPosition);
        }
        return null;
    }
}
