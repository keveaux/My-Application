package com.ict_life.merchantapp.photoeditor.photoeditor.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.activities.EditPostImagesActivity;
import com.ict_life.merchantapp.multipleimagepickerlibrary.MultiImageSelector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class StickerBSFragment extends BottomSheetDialogFragment {

    Context context;
    ArrayList<String> sticker_list = new ArrayList<>() ;
    private final int REQUEST_IMAGE=301;
    RecyclerView rvEmoji;


    public StickerBSFragment(Context context) {
        // Required empty public constructor
        this.context=context;
    }

    private StickerListener mStickerListener;

    public void setStickerListener(StickerListener stickerListener) {
        mStickerListener = stickerListener;
    }

    public interface StickerListener {
        void onStickerClick(Bitmap bitmap);
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sticker_emoji_dialog, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();


        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
         rvEmoji = contentView.findViewById(R.id.rvEmoji);
        TextView add_logo_tv=contentView.findViewById(R.id.add_logo_tv);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rvEmoji.setLayoutManager(gridLayoutManager);
        StickerAdapter stickerAdapter = new StickerAdapter();
        rvEmoji.setAdapter(stickerAdapter);

        add_logo_tv.setOnClickListener(v->{
            openGallery();
        });
    }

    private void openGallery(){
        MultiImageSelector mMultiImageSelector;

        //initialize multi image selector
        mMultiImageSelector = MultiImageSelector.create();

        mMultiImageSelector.showCamera(true);
        mMultiImageSelector.count(5);
        mMultiImageSelector.multi();
        mMultiImageSelector.origin(sticker_list);
//            mMultiImageSelector.start(getActivity(), REQUEST_IMAGE);
        startActivityForResult(mMultiImageSelector.createIntent(getContext()),REQUEST_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){

            try {
                sticker_list = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);

                Toast.makeText(context, "Click image to select", Toast.LENGTH_SHORT).show();

                StickerAdapter stickerAdapter=new StickerAdapter();
                rvEmoji.setAdapter(stickerAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sticker, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


            Glide.with(context).load(sticker_list.get(position)).into(holder.imgSticker);
        }

        @Override
        public int getItemCount() {
            return sticker_list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgSticker;

            ViewHolder(View itemView) {
                super(itemView);
                imgSticker = itemView.findViewById(R.id.imgSticker);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mStickerListener != null) {

                            File imgFile = new  File(sticker_list.get(getLayoutPosition()));
                            if(imgFile.exists()){
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                //Drawable d = new BitmapDrawable(getResources(), myBitmap);
                                mStickerListener.onStickerClick(myBitmap);
                            }



                            Toast.makeText(getContext(), ""+sticker_list.size(), Toast.LENGTH_SHORT).show();
                        }
                        dismiss();
                    }
                });
            }
        }
    }

    public Bitmap getBitmap(String path) {
        try {
            Bitmap bitmap = null;
            File f = new File(new URI(path));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private String convertEmoji(String emoji) {
        String returnedEmoji = "";
        try {
            int convertEmojiToInt = Integer.parseInt(emoji.substring(2), 16);
            returnedEmoji = getEmojiByUnicode(convertEmojiToInt);
        } catch (NumberFormatException e) {
            returnedEmoji = "";
        }
        return returnedEmoji;
    }

    private String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }
}