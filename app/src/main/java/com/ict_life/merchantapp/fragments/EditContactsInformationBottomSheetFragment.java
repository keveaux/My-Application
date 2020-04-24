package com.ict_life.merchantapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ict_life.merchantapp.R;

public class EditContactsInformationBottomSheetFragment extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private setContactInfoCallBack callBack;

    public EditContactsInformationBottomSheetFragment(setContactInfoCallBack callBack) {
        this.callBack = callBack;
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
        return inflater.inflate(R.layout.bottom_sheet_edit_contact, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText edit_business_contact_et=view.findViewById(R.id.edit_business_contact_et);
        Button finish_editing_contact_btn=view.findViewById(R.id.finish_editing_contact_btn);

        finish_editing_contact_btn.setOnClickListener(v->{
            callBack.setContactInfoCallbackMethod(edit_business_contact_et.getText().toString());
            dismiss();
        });

    }

    public interface setContactInfoCallBack {
        void setContactInfoCallbackMethod(String s);
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

