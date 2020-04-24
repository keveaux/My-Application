package com.ict_life.merchantapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.JsonElement;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.activities.OTPVerificationActivity;
import com.ict_life.merchantapp.adapters.ServicesTagsAdapter;
import com.ict_life.merchantapp.interfaces.RemoveServicesTagInterface;
import com.ict_life.merchantapp.interfaces.ServiceProductTagsInterface;
import com.ict_life.merchantapp.prefs.PrefManager;
import com.ict_life.merchantapp.retrofit.APIService;
import com.ict_life.merchantapp.retrofit.APIUtils;
import com.ict_life.merchantapp.retrofit.entities.Services;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditServiceBottomSheetDialogFragment extends BottomSheetDialogFragment implements RemoveServicesTagInterface {

    public static final String TAG = "ActionBottomDialog";
    RemoveServicesTagInterface removeServicesTagInterface;
    ArrayList<String> service_tags = new ArrayList<>();
    RecyclerView tags_rv;
    ServiceProductTagsInterface serviceProductTagsInterface;
    EditText edit_services_et;
    APIService apiService = null;
    private PrefManager prefManager;
    Services services;
    private ProgressBar progressBar;
    TextView progress_txt;
    String tag;





    public EditServiceBottomSheetDialogFragment(ServiceProductTagsInterface serviceProductTagsInterface) {
        this.serviceProductTagsInterface = serviceProductTagsInterface;
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
        removeServicesTagInterface = this;
        apiService = APIUtils.getAPIService();
        prefManager = new PrefManager(Objects.requireNonNull(getContext()));


        return inflater.inflate(R.layout.edit_service_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        edit_services_et = view.findViewById(R.id.edit_services_et);
        tags_rv = view.findViewById(R.id.tags_rv);
        tags_rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        Button cancel_btn = view.findViewById(R.id.cancel_btn);
        Button finish_adding_tags_btn = view.findViewById(R.id.finish_adding_tags_btn);
        progressBar=view.findViewById(R.id.progressBar1);
        progress_txt=view.findViewById(R.id.progress_txt);
        ScrollView scroll_view=view.findViewById(R.id.scroll_view);

        scroll_view.post(() -> scroll_view.fullScroll(View.FOCUS_DOWN));

//        edit_services_et.requestFocus();
        edit_services_et.setImeActionLabel("ADD", EditorInfo.IME_ACTION_SEND);


        edit_services_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (edit_services_et.getText().toString().matches("")) {
                        edit_services_et.setError("add a service");
                    }else {
                        tag = edit_services_et.getText().toString();
                        AddServicesTags();
                    }
                    handled = true;
                }
                return handled;
            }
        });

        view.findViewById(R.id.add_btn).setOnClickListener(v -> {
            if (edit_services_et.getText().toString().matches("")) {
                edit_services_et.setError("add a service");
            }else {
                tag = edit_services_et.getText().toString();
                AddServicesTags();
            }
        });

        cancel_btn.setOnClickListener(v -> {
            dismiss();
        });

        finish_adding_tags_btn.setOnClickListener(v -> {

                serviceProductTagsInterface.SetServicesProductTags(service_tags);
                String[] item = service_tags.toArray(new String[service_tags.size()]);

                services=new Services(item,Integer.parseInt(prefManager.getMerchantid()));
                createBusinessTags();

        });


        PrefManager prefManager=new PrefManager(getActivity());

        if(prefManager.getServices().length>0){
            service_tags.addAll(Arrays.asList(prefManager.getServices()));
        }

        AddServicesTags();


    }

    private void AddServicesTags() {
            service_tags.add(tag);

            ServicesTagsAdapter servicesTagsAdapter = new ServicesTagsAdapter(getContext(), service_tags, removeServicesTagInterface);
            tags_rv.setAdapter(servicesTagsAdapter);

            edit_services_et.setText("");

            progressBar.setMax(60);

            switch (service_tags.size()){
                case 1:
                    progressBar.setProgress(10);
                    progress_txt.setText("Great start 5 to go!");
                    break;
                case 2:
                    progressBar.setProgress(20);
                    progress_txt.setText("Almost there keep going");
                    break;
                case 3:
                    progressBar.setProgress(30);
                    progress_txt.setText("Just 3 to go don't stop");
                    break;
                case 4:
                    progressBar.setProgress(40);
                    progress_txt.setText("Great progress");
                    break;

                case 5:
                    progressBar.setProgress(50);
                    progress_txt.setText("Almost there champ");
                    break;

                case 6:
                    progressBar.setProgress(60);
                    progress_txt.setText("Perfect!! Add more if you want");
                    break;
            }
        }


    private void createBusinessTags(){
        apiService.createMerchantServices(prefManager.getToken(),services).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        Toast.makeText(getContext(), "" + jObjError.getString("error_message"), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    JsonElement responseUser = response.body();

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        Toast.makeText(getContext(), "" + jsonObject, Toast.LENGTH_SHORT).show();
                        dismiss();

                    } catch (Exception e) {

                    }

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void removeItem(int pos) {
        service_tags.remove(pos);
        ServicesTagsAdapter servicesTagsAdapter = new ServicesTagsAdapter(getContext(), service_tags, removeServicesTagInterface);
        tags_rv.setAdapter(servicesTagsAdapter);
    }
}

