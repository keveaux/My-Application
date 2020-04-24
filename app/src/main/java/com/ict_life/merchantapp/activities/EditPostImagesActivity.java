package com.ict_life.merchantapp.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.adapters.EditPostsAdapter;
import com.ict_life.merchantapp.prefs.PrefManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class EditPostImagesActivity extends AppCompatActivity implements EditPostsAdapter.ShowDialogInterface {

    ArrayList<String> list_images;
    ProgressDialog mProgressDialog;
    EditPostsAdapter.ShowDialogInterface showDialogInterface;
    ImageView share_button;
    PackageManager pm ;
    RecyclerView posts_recycler;
    private CallbackManager callbackManager;
    SharePhotoContent content;
    PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post_images);

        pm=getPackageManager();
        callbackManager = CallbackManager.Factory.create();
        prefManager=new PrefManager(this);

        Button done_btn=findViewById(R.id.done_btn);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        getSupportActionBar().setTitle("Edit");

        showDialogInterface=this;

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        list_images = getIntent().getExtras().getStringArrayList("list_images");
        share_button=findViewById(R.id.share_button);


         posts_recycler = findViewById(R.id.edit_posts_recyclerview);
        posts_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        EditPostsAdapter editPostsAdapter = new EditPostsAdapter(getApplicationContext(), list_images,EditPostImagesActivity.this,showDialogInterface);

        posts_recycler.setAdapter(editPostsAdapter);

        share_button.setOnClickListener(v->{
                showMenuBuilder(v);
        });

        done_btn.setOnClickListener(v->{
            finish();
            prefManager.setTruth(true);
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    protected void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        hideLoading();

    }

    @Override
    protected void onStop() {
        super.onStop();
        hideLoading();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void showLoading(String message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void removeSelectedPost(int position) {
        list_images.remove(position);
        EditPostsAdapter editPostsAdapter = new EditPostsAdapter(getApplicationContext(), list_images,EditPostImagesActivity.this,showDialogInterface);
        posts_recycler.setAdapter(editPostsAdapter);
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
                Uri imageUri = FileProvider.getUriForFile(
                        EditPostImagesActivity.this,
                        "com.ict_life.merchantapp.provider", //(use your app signature + ".provider" )
                        file);
                files.add(imageUri);
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

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
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
            if(f.exists()){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
            }else {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(path));
            }


            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @SuppressLint("RestrictedApi")
    public void showMenuBuilder(View view) {
        MenuBuilder menuBuilder = new MenuBuilder(EditPostImagesActivity.this);
        MenuInflater inflater = new MenuInflater(EditPostImagesActivity.this);
        inflater.inflate(R.menu.share_menu, menuBuilder);
        MenuPopupHelper optionsMenu = new MenuPopupHelper(EditPostImagesActivity.this, menuBuilder, view);
        optionsMenu.setForceShowIcon(true);

        // Set Item Click Listener
        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share_to_facebook: // Handle option1 Click
//                        shareToFacebook();
                        publishImage();
                        return true;
                    case R.id.share_to_whatsapp: // Handle option2 Click
                        shareToWhatsapp();
                        return true;
                    default:
                        shareToInstagram();
                        return false;
                }
            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {
            }
        });


        // Display the menu
        optionsMenu.show();


    }

    private void publishImage(){

        ArrayList <SharePhoto> facebook_images=new ArrayList<>();

        for (String image_str:list_images){
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(getBitmap(image_str))
                    .build();

            facebook_images.add(photo);

        }

         content = new SharePhotoContent.Builder()
                .addPhotos(facebook_images)
                .build();

        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(EditPostImagesActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(EditPostImagesActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                    // Don't use the app for sharing in case of null-error
                Toast.makeText(EditPostImagesActivity.this, "error: "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,    Intent data)
    {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);

        Toast.makeText(this, ""+data.getDataString(), Toast.LENGTH_SHORT).show();

    }
}
