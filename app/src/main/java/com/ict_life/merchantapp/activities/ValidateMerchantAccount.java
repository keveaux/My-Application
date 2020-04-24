package com.ict_life.merchantapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.prefs.PrefManager;

public class ValidateMerchantAccount extends AppCompatActivity {


    Button cancel_button_phone_no,validate_phone_no_btn,cancel_button_paybill,validate_paybill_btn,validate_buy_goods_button,cancel_button_buy_goods;
    EditText phone_no_et,paybill_no_et,account_no_et,et_till_no;
    boolean phone_no_selected,till_no_selected,paybill_selected=false;
    String phone_number;
    String chosen_phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_merchant_account);

        TextView validate_tv = findViewById(R.id.validate_txt);
        LinearLayout select_payment_method_cv=findViewById(R.id.select_payment_method_cv);
        CardView buy_goods_cv=findViewById(R.id.buy_goods_cv);
        CardView paybill_cv=findViewById(R.id.paybill_cv);
        CardView send_money_cv=findViewById(R.id.send_money_cv);
        CardView buy_goods_selected_cv=findViewById(R.id.buy_goods_selected_cv);
        CardView paybill_selected_cv=findViewById(R.id.paybill_selected_cv);
        CardView send_money_selected_cv=findViewById(R.id.send_money_selected_cv);
        cancel_button_buy_goods=findViewById(R.id.cancel_button_buy_goods);
        cancel_button_phone_no=findViewById(R.id.cancel_button_phone_no);
        validate_phone_no_btn=findViewById(R.id.validate_phone_no_btn);
        cancel_button_paybill=findViewById(R.id.cancel_button_paybill);
        validate_paybill_btn=findViewById(R.id.validate_paybill_btn);
        validate_buy_goods_button=findViewById(R.id.validate_buy_goods_button);
        phone_no_et=findViewById(R.id.phone_no_et);
        paybill_no_et=findViewById(R.id.paybill_no_et);
        account_no_et=findViewById(R.id.account_no_et);
        et_till_no=findViewById(R.id.et_till_no);

        PrefManager prefManager=new PrefManager(this);

        String first_name=prefManager.getFirstName();
        phone_number=prefManager.getCountryCode()+prefManager.getPhoneNumber();


        int name_length=first_name.length();


        Spannable wordtoSpan = new SpannableString(getResources().getString(R.string.one_last_thing)+first_name+getResources().getString(R.string.validatetxt));

        wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 61+name_length, 68+name_length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), 99+name_length, 105+name_length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        validate_tv.setText(wordtoSpan);

        buy_goods_cv.setOnClickListener(v->{

            select_payment_method_cv.setVisibility(View.GONE);
            buy_goods_selected_cv.setVisibility(View.VISIBLE);

        });

        paybill_cv.setOnClickListener(v->{

            select_payment_method_cv.setVisibility(View.GONE);
            paybill_selected_cv.setVisibility(View.VISIBLE);

        });

        send_money_cv.setOnClickListener(v->{
            phone_no_et.setText(phone_number);

            select_payment_method_cv.setVisibility(View.GONE);
            send_money_selected_cv.setVisibility(View.VISIBLE);
        });

        cancel_button_buy_goods.setOnClickListener(v->{
            select_payment_method_cv.setVisibility(View.VISIBLE);
            buy_goods_selected_cv.setVisibility(View.GONE);
        });

        cancel_button_paybill.setOnClickListener(V->{
            select_payment_method_cv.setVisibility(View.VISIBLE);
            paybill_selected_cv.setVisibility(View.GONE);
        });

        cancel_button_phone_no.setOnClickListener(v->{
            select_payment_method_cv.setVisibility(View.VISIBLE);
            send_money_selected_cv.setVisibility(View.GONE);
        });

        validate_buy_goods_button.setOnClickListener(v->{

            till_no_selected=true;
            phone_no_selected=false;
            paybill_selected=false;

            OpenConfirmationPopUp();
        });

        validate_paybill_btn.setOnClickListener(v->{
            till_no_selected=false;
            phone_no_selected=false;
            paybill_selected=true;

            OpenConfirmationPopUp();
        });

        validate_phone_no_btn.setOnClickListener(v->{
            till_no_selected=false;
            phone_no_selected=true;
            paybill_selected=false;

            chosen_phone_number=phone_no_et.getText().toString();

            OpenConfirmationPopUp();
        });




    }

    private void openWaitingActivity(){
        Intent intent=new Intent(ValidateMerchantAccount.this,WaitingScreenActivity.class);
        startActivity(intent);
    }

    private void OpenConfirmationPopUp() {

        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(ValidateMerchantAccount.this);
        View layoutView = (ValidateMerchantAccount.this).getLayoutInflater().inflate(R.layout.confirm_payment_method_pop_up, null);
        dialogBuilder.setView(layoutView);

        Button confirm_paybill_btn=layoutView.findViewById(R.id.confirm_paybill_btn);
        LinearLayout paybill_layout=layoutView.findViewById(R.id.paybill_layout);
        LinearLayout tillno_layout=layoutView.findViewById(R.id.tillno_layout);
        LinearLayout phone_no_layout=layoutView.findViewById(R.id.phone_no_layout);
        TextView selected_payment_tv=layoutView.findViewById(R.id.selected_payment_tv);
        TextView pop_up_pno_tv=layoutView.findViewById(R.id.pop_up_pno_tv);

        pop_up_pno_tv.setText(chosen_phone_number);

        if(till_no_selected){
            tillno_layout.setVisibility(View.VISIBLE);
            selected_payment_tv.setText("Buy Goods");
        }else if(paybill_selected){
            paybill_layout.setVisibility(View.VISIBLE);
            selected_payment_tv.setText("Paybill");

        }else if(phone_no_selected){
            phone_no_layout.setVisibility(View.VISIBLE);
            selected_payment_tv.setText("Send Money");
        }

        android.app.AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();

        confirm_paybill_btn.setOnClickListener(v->{
            successPopUp();
            alertDialog.dismiss();
        });

    }


    private void successPopUp() {

        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(ValidateMerchantAccount.this);
        View layoutView = (ValidateMerchantAccount.this).getLayoutInflater().inflate(R.layout.success_dialog_layout, null);
        dialogBuilder.setView(layoutView);


        Button ok_btn=layoutView.findViewById(R.id.ok_btn);

        ok_btn.setOnClickListener(v->{
            Intent intent=new Intent(ValidateMerchantAccount.this,SetUpProfileStepsActivity.class);
            intent.putExtra("position",0);
            startActivity(intent);
        });

        android.app.AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();



    }



}
