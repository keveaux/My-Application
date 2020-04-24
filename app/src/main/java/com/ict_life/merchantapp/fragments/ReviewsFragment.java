package com.ict_life.merchantapp.fragments;


import android.opengl.Visibility;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.activities.MainActivity;
import com.ict_life.merchantapp.adapters.ReviewsAdapter;

import java.util.Objects;


public class ReviewsFragment extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_reviews, container, false);

        RecyclerView reviews_recycler_view=view.findViewById(R.id.reviews_recycler_view);
        Spinner spinner_rating=view.findViewById(R.id.spinner_rating);
        Spinner date_spinner=view.findViewById(R.id.date_spinner);
        LinearLayout filter_layout=view.findViewById(R.id.filter_layout);

        reviews_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        ReviewsAdapter reviewsAdapter=new ReviewsAdapter(getContext());

        reviews_recycler_view.setAdapter(reviewsAdapter);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.stars, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_rating.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter_date_spinner = ArrayAdapter.createFromResource(getContext(),
                R.array.dates, R.layout.spinner_item);
        adapter_date_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_spinner.setAdapter(adapter_date_spinner);



        reviews_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && ((MainActivity) Objects.requireNonNull(getActivity())).navigation.getVisibility()==View.GONE){

                    ((MainActivity) Objects.requireNonNull(getActivity())).navigation.setVisibility(View.VISIBLE);

                }

                else if (dy > 0 && ((MainActivity) Objects.requireNonNull(getActivity())).navigation.getVisibility()==View.VISIBLE){

                    ((MainActivity) Objects.requireNonNull(getActivity())).navigation.setVisibility(View.GONE);

                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        return view;
    }

}
