package com.ict_life.merchantapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ict_life.merchantapp.R;

public class EditBusinessNameBottomDialogFragment extends BottomSheetDialogFragment {

    static final String TAG = "ActionBottomDialog";
    private setBusinessNameCallback callback;


    EditBusinessNameBottomDialogFragment(setBusinessNameCallback callback) {
        this.callback=callback;
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
        return inflater.inflate(R.layout.bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText edit_business_name_et=view.findViewById(R.id.edit_business_name_et);

        view.findViewById(R.id.finish_editing_business_btn).setOnClickListener(v->{
                callback.setBusinessNameCallbackMethod(edit_business_name_et.getText().toString());
                dismiss();
        });

        view.findViewById(R.id.cancel_btn).setOnClickListener(v->{
            dismiss();
        });

    }

    public interface setBusinessNameCallback {
        void setBusinessNameCallbackMethod(String s);
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
