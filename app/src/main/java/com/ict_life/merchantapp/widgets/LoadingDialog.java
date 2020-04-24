package com.ict_life.merchantapp.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import com.ict_life.merchantapp.R;

public class LoadingDialog {

      Dialog dialog;

    public void showDialog(Activity activity){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup_loading);


        dialog.show();

    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
