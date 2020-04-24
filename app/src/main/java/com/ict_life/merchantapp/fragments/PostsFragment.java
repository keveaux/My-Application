package com.ict_life.merchantapp.fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.activities.EditPostImagesActivity;
import com.ict_life.merchantapp.activities.MainActivity;
import com.ict_life.merchantapp.activities.NewPostsActivity;
import com.ict_life.merchantapp.adapters.FullDetailsPostAdapter;
import com.ict_life.merchantapp.adapters.ImagesAdapter;
import com.ict_life.merchantapp.adapters.PostsListingAdapter;
import com.ict_life.merchantapp.entities.FullDetailsPostsModel;
import com.ict_life.merchantapp.entities.PostsModel;
import com.ict_life.merchantapp.multipleimagepickerlibrary.MultiImageSelector;
import com.ict_life.merchantapp.prefs.PrefManager;
import com.ict_life.merchantapp.utils.SpannedGridLayoutManager;

import java.util.ArrayList;
import java.util.Objects;


public class PostsFragment extends Fragment {


    View view;
    private RecyclerView posts_recycler_view;

    private final int MAX_IMAGE_SELECTION_LIMIT = 10;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 401;
    private final int REQUEST_IMAGE = 301;
    RelativeLayout illustration_layout;
    CardView add_images_cv;
    private ArrayList<FullDetailsPostsModel> fullDetailsPostsModelArrayList = new ArrayList<>();
    private ArrayList<FullDetailsPostsModel> examplePostsModelArrayList = new ArrayList<>();

    LinearLayoutManager layoutManager;
    FullDetailsPostAdapter fullDetailsPostAdapter;
    RelativeLayout main_layout;
    PrefManager prefManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_posts, container, false);

        posts_recycler_view = view.findViewById(R.id.posts_recycler_view);
        Button change_to_grid_btn = view.findViewById(R.id.change_to_grid_btn);
        Button change_from_grid_btn = view.findViewById(R.id.change_from_grid_btn);
        Button add_new_post_btn = view.findViewById(R.id.add_new_post_btn);
        illustration_layout = view.findViewById(R.id.illustration_layout);
        add_images_cv = view.findViewById(R.id.add_images_cv);
        Button make_post_btn = view.findViewById(R.id.make_post_btn);
        Button see_post_example_btn = view.findViewById(R.id.see_post_example_btn);
        main_layout=view.findViewById(R.id.main_layout);

        prefManager=new PrefManager(getContext());

        ChangePostsRecyclerViewAdapter();


        change_to_grid_btn.setOnClickListener(v -> {
            change_to_grid_btn.setVisibility(View.GONE);
            change_from_grid_btn.setVisibility(View.VISIBLE);
            ChangePostsRecyclerViewToGrid();

        });

        change_from_grid_btn.setOnClickListener(v -> {
            change_from_grid_btn.setVisibility(View.GONE);
            change_to_grid_btn.setVisibility(View.VISIBLE);
            ChangePostsRecyclerViewAdapter();

        });


        posts_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {
                    ((MainActivity) Objects.requireNonNull(getActivity())).navigation.setVisibility(View.VISIBLE);

                } else if (dy > 0) {
                    ((MainActivity) Objects.requireNonNull(getActivity())).navigation.setVisibility(View.GONE);

                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        add_new_post_btn.setOnClickListener(v -> checkPermissions());

        make_post_btn.setOnClickListener(v -> checkPermissions());

        see_post_example_btn.setOnClickListener(v -> {
            PostsExampleRecyclerViewPopUp();
        });



        return view;
    }

    private void checkPermissions() {
        ArrayList<String> arrPerm = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            arrPerm.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            arrPerm.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


        if (!arrPerm.isEmpty()) {
            String[] permissions = new String[arrPerm.size()];
            permissions = arrPerm.toArray(permissions);
            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_ID_MULTIPLE_PERMISSIONS);
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        MultiImageSelector mMultiImageSelector;

        //initialize multi image selector
        mMultiImageSelector = MultiImageSelector.create();

        mMultiImageSelector.showCamera(true);
        mMultiImageSelector.count(MAX_IMAGE_SELECTION_LIMIT);
        mMultiImageSelector.multi();
//        mMultiImageSelector.origin(mSelectedImagesList);
//            mMultiImageSelector.start(getActivity(), REQUEST_IMAGE);
        startActivityForResult(mMultiImageSelector.createIntent(getContext()), REQUEST_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {

            try {
                ArrayList<String> mSelectedImagesList = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);


                Intent intent = new Intent(getActivity(), EditPostImagesActivity.class);
                intent.putStringArrayListExtra("list_images", mSelectedImagesList);
                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            checkPermissions();
        }
    }


    private void ChangePostsRecyclerViewAdapter() {


        ArrayList<Integer> post_data = new ArrayList<>();
        post_data.add(R.drawable.mzuri);

        ArrayList<Integer> post_data2 = new ArrayList<>();
        post_data2.add(R.drawable.pic1);
        post_data2.add(R.drawable.pic2);

        ArrayList<Integer> post_data3 = new ArrayList<>();
        post_data3.add(R.drawable.pic4);
        post_data3.add(R.drawable.pic6);
        post_data3.add(R.drawable.pic1);

        if(prefManager.getTruth()){
            fullDetailsPostsModelArrayList.add(new FullDetailsPostsModel("Awesome day","12/12/12",post_data,6,6));
            fullDetailsPostsModelArrayList.add(new FullDetailsPostsModel("Caption two","12/12/12",post_data2,3,18));
            fullDetailsPostsModelArrayList.add(new FullDetailsPostsModel("Awesome Caption","12/12/12",post_data3,23,6));
            fullDetailsPostsModelArrayList.add(new FullDetailsPostsModel("We are good yo","12/12/12",post_data2,9,6));
            fullDetailsPostsModelArrayList.add(new FullDetailsPostsModel("DAMN","12/12/12",post_data,1,6));
        }



        posts_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        fullDetailsPostAdapter = new FullDetailsPostAdapter(getContext(), fullDetailsPostsModelArrayList);
        posts_recycler_view.setAdapter(fullDetailsPostAdapter);

        if (fullDetailsPostsModelArrayList.size() == 0) {
            illustration_layout.setVisibility(View.VISIBLE);
            add_images_cv.setVisibility(View.GONE);
        }else {
            illustration_layout.setVisibility(View.GONE);
            add_images_cv.setVisibility(View.VISIBLE);
        }

    }

    private void ChangePostsRecyclerViewToGrid() {

        ArrayList<PostsModel> postsModelArrayList = new ArrayList<>();

        postsModelArrayList.add(new PostsModel("", "", R.drawable.mzuri));
        postsModelArrayList.add(new PostsModel("", "", R.drawable.pic1));
        postsModelArrayList.add(new PostsModel("", "", R.drawable.pic2));
        postsModelArrayList.add(new PostsModel("", "", R.drawable.pic4));
        postsModelArrayList.add(new PostsModel("", "", R.drawable.pic6));
        postsModelArrayList.add(new PostsModel("", "", R.drawable.mzuri));
        postsModelArrayList.add(new PostsModel("", "", R.drawable.mzuri));
        postsModelArrayList.add(new PostsModel("", "", R.drawable.pic1));
        postsModelArrayList.add(new PostsModel("", "", R.drawable.pic2));
        postsModelArrayList.add(new PostsModel("", "", R.drawable.pic4));
        postsModelArrayList.add(new PostsModel("", "", R.drawable.pic6));
        postsModelArrayList.add(new PostsModel("", "", R.drawable.mzuri));


        PostsListingAdapter postsListingAdapter = new PostsListingAdapter(getContext(), postsModelArrayList);

        posts_recycler_view.setAdapter(postsListingAdapter);


        RecyclerView.LayoutManager mLayoutManager = new SpannedGridLayoutManager(position -> {

            if (position % 12 == 0 || position % 12 == 7) {

                return new SpannedGridLayoutManager.SpanInfo(2, 2);

            } else {

                return new SpannedGridLayoutManager.SpanInfo(1, 1);

            }

        }, 3, 1f);


        posts_recycler_view.setLayoutManager(mLayoutManager);
    }

    private void PostsExampleRecyclerViewPopUp() {
        Dialog dialogBuilder = new Dialog(getContext(), R.style.CustomDialog);
        View layoutView = (this).getLayoutInflater().inflate(R.layout.post_example_pop_up_layout, null);
        dialogBuilder.setContentView(layoutView);
//        dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        ViewGroup root = (ViewGroup)dialogBuilder.getWindow().getDecorView().getRootView();

//        applyDim(root,0f);

        ImageButton back_btn = layoutView.findViewById(R.id.back_btn);
        ImageButton next_btn = layoutView.findViewById(R.id.next_btn);
        ImageButton dismiss_btn=layoutView.findViewById(R.id.dismiss_btn);


        RecyclerView post_example_recyclerview = layoutView.findViewById(R.id.post_example_recyclerview);

        SetUpExampleRecyclerView(post_example_recyclerview);

        back_btn.setOnClickListener(v -> {
            if (layoutManager.findFirstCompletelyVisibleItemPosition() < (fullDetailsPostAdapter.getItemCount() + 1)) {
                layoutManager.scrollToPosition(layoutManager.findLastCompletelyVisibleItemPosition() - 1);

            }
        });

        next_btn.setOnClickListener(v -> {
            if (layoutManager.findLastCompletelyVisibleItemPosition() < (fullDetailsPostAdapter.getItemCount() - 1)) {
                layoutManager.scrollToPosition(layoutManager.findLastCompletelyVisibleItemPosition() + 1);
            }
        });

        dismiss_btn.setOnClickListener(v->{dialogBuilder.dismiss();});


//        Dialog alertDialog = dialogBuilder.create();
//        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogBuilder.show();

        dialogBuilder.setOnDismissListener(dialog -> {

        });
    }



    public void applyDim(@NonNull ViewGroup parent, float dimAmount){
        Drawable dim = new ColorDrawable(getResources().getColor(R.color.colorAccent));
        dim.setBounds(0, 0, parent.getWidth(), 600);
        dim.setAlpha((int) (255 * dimAmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }

    private void SetUpExampleRecyclerView(RecyclerView post_example_recyclerview) {
        ArrayList<Integer> post_data = new ArrayList<>();
        post_data.add(R.drawable.mzuri);

        ArrayList<Integer> post_data2 = new ArrayList<>();
        post_data2.add(R.drawable.pic1);

        ArrayList<Integer> post_data3 = new ArrayList<>();
        post_data3.add(R.drawable.pic4);


        examplePostsModelArrayList.add(new FullDetailsPostsModel("Awesome day", "12/12/12", post_data, 6, 6));
        examplePostsModelArrayList.add(new FullDetailsPostsModel("Caption two", "12/12/12", post_data2, 3, 18));
        examplePostsModelArrayList.add(new FullDetailsPostsModel("Awesome Caption", "12/12/12", post_data3, 23, 6));


        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        post_example_recyclerview.setLayoutManager(layoutManager);


        fullDetailsPostAdapter = new FullDetailsPostAdapter(getContext(), examplePostsModelArrayList);
        post_example_recyclerview.setAdapter(fullDetailsPostAdapter);


    }

    @Override
    public void onResume() {
        Toast.makeText(getContext(),"resume",Toast.LENGTH_LONG).show();

        ChangePostsRecyclerViewAdapter();



        super.onResume();
    }
}
