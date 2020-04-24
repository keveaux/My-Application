package com.ict_life.merchantapp.photoeditor.photoeditor.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.adapters.FontTypeAdapter;
import com.ict_life.merchantapp.entities.FontsModel;
import com.ict_life.merchantapp.photoeditor.photoeditor.adapters.BackgroundColorPickerAdapter;
import com.ict_life.merchantapp.photoeditor.photoeditor.adapters.ColorPickerAdapter;

import java.util.ArrayList;


/**
 * Created by Burhanuddin Rashid on 1/16/2018.
 */

public class TextEditorDialogFragment extends DialogFragment implements FontTypeAdapter.ChangeFontTypeInterface {

    public static final String TAG = TextEditorDialogFragment.class.getSimpleName();
    public static final String EXTRA_INPUT_TEXT = "extra_input_text";
    public static final String EXTRA_COLOR_CODE = "extra_color_code";
    public static String BG_COLOR_CODE = "bg_color_code";


    private EditText mAddTextEditText;
    private TextView mAddTextDoneTextView;
    private InputMethodManager mInputMethodManager;
    private int mColorCode,backgroundColorCode;
    private TextEditor mTextEditor;
    FontTypeAdapter.ChangeFontTypeInterface changeFontTypeInterface;
    RecyclerView font_recycler_view;
    Typeface typeface;
    Button color_btn,font_btn,size_btn,bg_btn;
    RecyclerView add_tv_background_rv;
    RelativeLayout text_size_layout;
    SeekBar size_seekbar;
    int seekbar_progress=40;
    static Typeface curr_typeface;

    @Override
    public void changefont(Typeface typeface) {
        mAddTextEditText.setTypeface(typeface);
        this.typeface=typeface;
    }

    public interface TextEditor {
        void onDone(Typeface typeface,String inputText, int colorCode,int textsize,int backgroundColor);
    }


    //Show dialog with provide text and text color
    public static TextEditorDialogFragment show(@NonNull AppCompatActivity appCompatActivity,
                                                @NonNull String inputText,
                                                @ColorInt int colorCode,int backgroundColor,Typeface current_typeface) {
        Bundle args = new Bundle();
        args.putString(EXTRA_INPUT_TEXT, inputText);
        args.putInt(EXTRA_COLOR_CODE, colorCode);
        args.putInt(BG_COLOR_CODE,backgroundColor);

        curr_typeface=current_typeface;

        TextEditorDialogFragment fragment = new TextEditorDialogFragment();
        fragment.setArguments(args);
        fragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return fragment;
    }

    //Show dialog with default text input as empty and text color white
    public static TextEditorDialogFragment show(@NonNull AppCompatActivity appCompatActivity) {
        return show(appCompatActivity,
                "", ContextCompat.getColor(appCompatActivity, R.color.white),ContextCompat.getColor(appCompatActivity, R.color.transparent),
                Typeface.createFromAsset(appCompatActivity.getAssets(), "firesans_regular.ttf"));
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        //Make dialog full screen with transparent background
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_text_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAddTextEditText = view.findViewById(R.id.add_text_edit_text);
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mAddTextDoneTextView = view.findViewById(R.id.add_text_done_tv);
        font_recycler_view=view.findViewById(R.id.font_recycler_view);
        color_btn=view.findViewById(R.id.color_btn);
        font_btn=view.findViewById(R.id.font_btn);
        size_btn=view.findViewById(R.id.size_btn);
        bg_btn=view.findViewById(R.id.bg_btn);
        add_tv_background_rv=view.findViewById(R.id.add_tv_background_rv);
        text_size_layout=view.findViewById(R.id.text_size_layout);
        size_seekbar=view.findViewById(R.id.size_seekbar);
        TextView text_font_size=view.findViewById(R.id.text);


        mAddTextEditText.requestFocus();

        //Setup the color picker for text color
        RecyclerView addTextColorPickerRecyclerView = view.findViewById(R.id.add_text_color_picker_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        addTextColorPickerRecyclerView.setLayoutManager(layoutManager);
        addTextColorPickerRecyclerView.setHasFixedSize(true);
        ColorPickerAdapter colorPickerAdapter = new ColorPickerAdapter(getActivity());
        //This listener will change the text color when clicked on any color from picker
        colorPickerAdapter.setOnColorPickerClickListener(colorCode -> {
            mColorCode = colorCode;
            mAddTextEditText.setTextColor(colorCode);
        });

        BackgroundColorPickerAdapter backgroundColorPickerAdapter=new BackgroundColorPickerAdapter(getActivity());

        backgroundColorPickerAdapter.setOnColorPickerClickListener(colorCode -> {
            backgroundColorCode=colorCode;
            mAddTextEditText.setBackgroundColor(colorCode);
        });

        addTextColorPickerRecyclerView.setAdapter(colorPickerAdapter);
        add_tv_background_rv.setAdapter(backgroundColorPickerAdapter);


        mAddTextEditText.setText(getArguments().getString(EXTRA_INPUT_TEXT));
        mColorCode = getArguments().getInt(EXTRA_COLOR_CODE);
        backgroundColorCode=getArguments().getInt(BG_COLOR_CODE);
        mAddTextEditText.setTextColor(mColorCode);
        mAddTextEditText.setBackgroundColor(backgroundColorCode);
        mAddTextEditText.setTypeface(curr_typeface);

        if(mAddTextEditText.getText().toString().trim().length() > 0){
            mAddTextEditText.setSelection(mAddTextEditText.getText().length());
        }
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);


        color_btn.setOnClickListener(v->{
            addTextColorPickerRecyclerView.setVisibility(View.VISIBLE);
            font_recycler_view.setVisibility(View.GONE);
            add_tv_background_rv.setVisibility(View.GONE);
            text_size_layout.setVisibility(View.GONE);
            color_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_btn));
            size_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_black_bg));
            font_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_black_bg));
            bg_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_black_bg));

        });

        size_btn.setOnClickListener(v->{
            addTextColorPickerRecyclerView.setVisibility(View.GONE);
            font_recycler_view.setVisibility(View.GONE);
            add_tv_background_rv.setVisibility(View.GONE);
            text_size_layout.setVisibility(View.VISIBLE);
            size_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_btn));
            color_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_black_bg));
            font_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_black_bg));
            bg_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_black_bg));

        });

        font_btn.setOnClickListener(v->{
            addTextColorPickerRecyclerView.setVisibility(View.GONE);
            add_tv_background_rv.setVisibility(View.GONE);
            text_size_layout.setVisibility(View.GONE);
            font_recycler_view.setVisibility(View.VISIBLE);
            font_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_btn));
            size_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_black_bg));
            color_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_black_bg));
            bg_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_black_bg));
        });

        bg_btn.setOnClickListener(v->{
            addTextColorPickerRecyclerView.setVisibility(View.GONE);
            text_size_layout.setVisibility(View.GONE);
            font_recycler_view.setVisibility(View.GONE);
            add_tv_background_rv.setVisibility(View.VISIBLE);
            bg_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_btn));
            size_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_black_bg));
            font_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_black_bg));
            color_btn.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_black_bg));
        });

        size_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub


                if(progress<=10){
                    seekbar_progress=10;
                }else {
                    seekbar_progress=progress;
                }

                mAddTextEditText.setTextSize(seekbar_progress);
                text_font_size.setText(seekbar_progress+"px");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });



    //Make a callback on activity when user is done with text editing
        mAddTextDoneTextView.setOnClickListener(view1 -> {
            mInputMethodManager.hideSoftInputFromWindow(view1.getWindowToken(), 0);
            dismiss();
            String inputText = mAddTextEditText.getText().toString();
            if (!TextUtils.isEmpty(inputText) && mTextEditor != null) {
                mTextEditor.onDone(typeface,inputText, mColorCode,seekbar_progress,backgroundColorCode);

            }
        });

        changeFontTypeInterface=this;

        setTextFont();

    }

    private void setTextFont(){

        ArrayList<FontsModel> fontsModelArrayList=new ArrayList<>();

        Typeface aleo = Typeface.createFromAsset(getActivity().getAssets(), "aleo_regular.ttf");
        Typeface amaticsc = Typeface.createFromAsset(getActivity().getAssets(), "amaticsc_regular.ttf");
        Typeface fascinateInline = Typeface.createFromAsset(getActivity().getAssets(), "fascinateInline_regular.ttf");
        Typeface firesans = Typeface.createFromAsset(getActivity().getAssets(), "firesans_regular.ttf");
        Typeface lobster = Typeface.createFromAsset(getActivity().getAssets(), "lobster_regular.ttf");
        Typeface oswald = Typeface.createFromAsset(getActivity().getAssets(), "oswald_regular.ttf");
        Typeface poppins = Typeface.createFromAsset(getActivity().getAssets(), "poppins_regular.ttf");


        fontsModelArrayList.add(new FontsModel("Aleo",aleo));
        fontsModelArrayList.add(new FontsModel("Amaticsc",amaticsc));
        fontsModelArrayList.add(new FontsModel("FascinateInline",fascinateInline));
        fontsModelArrayList.add(new FontsModel("Firesans",firesans));
        fontsModelArrayList.add(new FontsModel("Lobster",lobster));
        fontsModelArrayList.add(new FontsModel("Oswald",oswald));
        fontsModelArrayList.add(new FontsModel("Poppins",poppins));

        FontTypeAdapter fontTypeAdapter=new FontTypeAdapter(getContext(),fontsModelArrayList,changeFontTypeInterface);
        font_recycler_view.setAdapter(fontTypeAdapter);

    }


    //Callback to listener if user is done with text editing
    public void setOnTextEditorListener(TextEditor textEditor) {
        mTextEditor = textEditor;
    }
}
