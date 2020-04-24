package com.ict_life.merchantapp.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.adapters.AsksAdapter;
import com.ict_life.merchantapp.entities.AsksModel;

import java.util.ArrayList;

public class AsksFragment extends Fragment {

    View view;
       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_asks, container, false);

        RecyclerView asks_recyclerview=view.findViewById(R.id.asks_recyclerview);

        asks_recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        ArrayList<AsksModel> asksModelsArrayList=new ArrayList<>() ;

        AsksAdapter asksAdapter=new AsksAdapter(getContext(),asksModelsArrayList);

        asks_recyclerview.setAdapter(asksAdapter);

           return view;
    }


}
