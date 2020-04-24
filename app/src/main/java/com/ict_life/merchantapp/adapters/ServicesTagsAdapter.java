package com.ict_life.merchantapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.interfaces.RemoveServicesTagInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ServicesTagsAdapter extends RecyclerView.Adapter<ServicesTagsAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private static Context ctx;

    ArrayList<String> services_added;
    RemoveServicesTagInterface removeServicesTagInterface;


    public ServicesTagsAdapter(Context ctx, ArrayList<String> services_added, RemoveServicesTagInterface removeServicesTagInterface) {
        this.removeServicesTagInterface=removeServicesTagInterface;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
        this.services_added = services_added;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.add_service_tags_layout, parent, false);

//        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(lp);

        return new MyViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.services_tags_tv.setText(services_added.get(position));

        holder.services_tags_tv.setOnClickListener(v->{
            OpenConfirmationPopUp(position);
        });

    }

    private void OpenConfirmationPopUp(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

        builder.setMessage("Remove "+services_added.get(position))
                .setTitle("My Services/Products");
        // Add the buttons

        builder.setPositiveButton(R.string.ok, (dialog, id) -> {
            // User okd the dialog
            removeServicesTagInterface.removeItem(position);
        });

        builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
            // User cancelled the dialog
        });
        // Set other dialog properties

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        dialog.show();
    }


    @Override
    public int getItemCount() {
        return services_added.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView services_tags_tv;

        MyViewHolder(View itemView) {
            super(itemView);
            services_tags_tv = itemView.findViewById(R.id.services_tags_tv);

        }

    }




}

