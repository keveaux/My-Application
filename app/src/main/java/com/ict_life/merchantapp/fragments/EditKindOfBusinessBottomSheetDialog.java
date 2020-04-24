package com.ict_life.merchantapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.adapters.SingleAdapter;
import com.ict_life.merchantapp.entities.KindOfBusiness;
import com.ict_life.merchantapp.interfaces.KindOfBusinessInterface;

import java.util.ArrayList;

public class EditKindOfBusinessBottomSheetDialog extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private RecyclerView recyclerView;
    private ArrayList<KindOfBusiness> kindOfBusinesses = new ArrayList<>();
    private SingleAdapter adapter;
    private Button btnGetSelected;
    KindOfBusinessInterface kindOfBusinessInterface;


    public EditKindOfBusinessBottomSheetDialog(KindOfBusinessInterface kindOfBusinessInterface) {
        this.kindOfBusinessInterface = kindOfBusinessInterface;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_kind_of_business_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnGetSelected=view.findViewById(R.id.btnGetSelected);
        this.recyclerView =  view.findViewById(R.id.recyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter = new SingleAdapter(getContext(), kindOfBusinesses);
        recyclerView.setAdapter(adapter);

        createList();

        btnGetSelected.setOnClickListener(view1 -> {
            if (adapter.getSelected() != null) {
                showToast(adapter.getSelected().getName());
                kindOfBusinessInterface.setKindOfBusiness(adapter.getSelected().getName());
                dismiss();
            } else {
                showToast("No Selection");
            }
        });
    }

    private void createList() {
        kindOfBusinesses = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            KindOfBusiness kindOfBusiness = new KindOfBusiness();
            kindOfBusiness.setName("Business " + (i + 1));
            kindOfBusinesses.add(kindOfBusiness);
        }
        adapter.setKindOfBusinesses(kindOfBusinesses);
    }

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
