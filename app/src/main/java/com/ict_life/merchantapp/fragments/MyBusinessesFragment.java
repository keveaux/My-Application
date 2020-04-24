package com.ict_life.merchantapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.activities.NewRegistrationActivity;
import com.ict_life.merchantapp.adapters.MyBusinessesAdapter;
import com.ict_life.merchantapp.entities.MyBusinessesDetailsModel;

import java.util.ArrayList;


public class MyBusinessesFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_businesses, container, false);

        RecyclerView my_businesses_recyclerview=view.findViewById(R.id.my_businesses_recyclerview);
        RelativeLayout add_business_layout=view.findViewById(R.id.add_business_layout);

        ArrayList<MyBusinessesDetailsModel> myBusinessesDetailsModelArrayList=new ArrayList<>();

        myBusinessesDetailsModelArrayList.add(new MyBusinessesDetailsModel("FabGuru","4.4","5 reviews",true,R.drawable.plumber));
        myBusinessesDetailsModelArrayList.add(new MyBusinessesDetailsModel("Esther Ltd","4.7","53 reviews",false,0));

        MyBusinessesAdapter myBusinessesAdapter=new MyBusinessesAdapter(getContext(),myBusinessesDetailsModelArrayList);

        my_businesses_recyclerview.setAdapter(myBusinessesAdapter);


        add_business_layout.setOnClickListener(v->{
            Intent intent=new Intent(getContext(), NewRegistrationActivity.class);
            intent.putExtra("from","business");
            startActivity(intent);
        });






        return view;
    }


}
