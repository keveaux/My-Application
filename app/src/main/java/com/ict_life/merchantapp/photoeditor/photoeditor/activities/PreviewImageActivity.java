package com.ict_life.merchantapp.photoeditor.photoeditor.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ict_life.merchantapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class PreviewImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);

        ImageView edited_image = findViewById(R.id.edited_image);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("edited_post");
//      String position = bundle.getString("position");

//
//        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
//
//        File imgFile = new File(message);
//        if (imgFile.exists()) {
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            //Drawable d = new BitmapDrawable(getResources(), myBitmap);
////            edited_image.setImageBitmap(myBitmap);
//
//
            Glide.with(getApplicationContext()).load(message).into(edited_image);
//
//
//        }
//
//
//        boolean deleted = imgFile.delete();

//        Toast.makeText(PreviewImageActivity.this, "" + deleted, Toast.LENGTH_SHORT).show();


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }
}
