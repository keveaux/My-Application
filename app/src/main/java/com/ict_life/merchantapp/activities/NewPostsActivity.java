package com.ict_life.merchantapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.adapters.PostsRecyclerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class NewPostsActivity extends AppCompatActivity {

    PackageManager pm ;
    ArrayList<String> list_images;
    RelativeLayout share_to_facebook_layout, twitter_layout, instagram_layout;
    ImageButton back_arrow;
    Button finish_uploading_posts_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_posts);

        back_arrow = findViewById(R.id.back_arrow);
        share_to_facebook_layout = findViewById(R.id.share_to_facebook_layout);
        instagram_layout = findViewById(R.id.instagram_layout);
        twitter_layout = findViewById(R.id.twitter_layout);
        finish_uploading_posts_btn=findViewById(R.id.finish_uploading_posts_btn);


        list_images = getIntent().getExtras().getStringArrayList("list_images");


        RecyclerView posts_recycler = findViewById(R.id.posts_recycler);
        posts_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        PostsRecyclerAdapter postsRecyclerAdapter = new PostsRecyclerAdapter(getApplicationContext(), list_images);

        posts_recycler.setAdapter(postsRecyclerAdapter);

        pm=getPackageManager();

        share_to_facebook_layout.setOnClickListener(v -> {
            publishImage();
        });

        instagram_layout.setOnClickListener(v -> {
            shareToInstagram();
        });

        twitter_layout.setOnClickListener(v -> {
            shareToWhatsapp();
        });


        back_arrow.setOnClickListener(v->{
            onBackPressed();
        });
    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void shareToFacebook() {

        if (isPackageInstalled("com.facebook.katana", pm)) {


            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Here are some files.");
            intent.setPackage("com.facebook.katana");

            ArrayList<Uri> files = new ArrayList<Uri>();

            for (String path : list_images /* List of the files you want to send */) {
                File file = new File(path);
                Uri uri = Uri.fromFile(file);
                files.add(uri);
            }

            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
            intent.setType("image/jpeg"); /* This example is sharing jpeg images. */
            startActivity(Intent.createChooser(intent, "Share to"));
        }else {
            Toast.makeText(this, "Please Install Facebook", Toast.LENGTH_SHORT).show();
        }

    }

    public void shareToWhatsapp() {

        if (isPackageInstalled("com.whatsapp", pm)) {


            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Here are some files.");
            intent.setPackage("com.whatsapp");

            ArrayList<Uri> files = new ArrayList<Uri>();

            for (String path : list_images /* List of the files you want to send */) {
                File file = new File(path);
                Uri uri = Uri.fromFile(file);
                files.add(uri);
            }

            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
            intent.setType("image/jpeg"); /* This example is sharing jpeg images. */
            startActivity(Intent.createChooser(intent, "Share to"));
        }else {
            Toast.makeText(this, "Please Install Whatsapp", Toast.LENGTH_SHORT).show();
        }

    }



    private void shareToInstagram() {

        if (isPackageInstalled("com.instagram.android", pm)) {

            String type = "image/*";
            Bitmap bmap = getBitmap(list_images.get(0));
            Uri bmpUri = getImageUri(getApplicationContext(), bmap);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setPackage("com.instagram.android");
            share.setType(type);
            share.putExtra(Intent.EXTRA_STREAM, bmpUri);
            startActivity(Intent.createChooser(share, "Share to"));
        }else {
            Toast.makeText(this, "Please install instagram", Toast.LENGTH_SHORT).show();

        }

    }

    private void publishImage(){
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();

        ShareApi.share(content, null);

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public Bitmap getBitmap(String path) {
        try {
            Bitmap bitmap = null;
            File f = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
