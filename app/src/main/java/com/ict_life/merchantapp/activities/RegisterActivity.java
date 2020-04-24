package com.ict_life.merchantapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ict_life.merchantapp.R;

public class RegisterActivity extends AppCompatActivity {

    boolean is_business_details_section=true;
    LinearLayout business_sign_up_layout;
    TextView one_txt,two_txt,business_txt,personal_txt;
    ImageView done_iv;
    CardView  two_layout;
    LinearLayout personal_sign_up_layout;
    RelativeLayout one_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button sign_in_button_next=findViewById(R.id.sign_in_button_next);
        Button back_btn=findViewById(R.id.back_btn);
        Button create_account_btn=findViewById(R.id.create_account_btn);
        EditText password_et=findViewById(R.id.password_et);
        TextView strengh_tv=findViewById(R.id.strengh_tv);
        EditText confirm_password_tv=findViewById(R.id.confirm_password_tv);
         business_sign_up_layout=findViewById(R.id.business_sign_up_layout);
         one_txt=findViewById(R.id.one_txt);
         done_iv=findViewById(R.id.done_iv);
          two_layout=findViewById(R.id.two_layout);
         two_txt=findViewById(R.id.two_txt);
         business_txt=findViewById(R.id.business_txt);
         personal_txt=findViewById(R.id.personal_txt);
         personal_sign_up_layout=findViewById(R.id.personal_sign_up_layout);
         one_layout=findViewById(R.id.one_layout);

        sign_in_button_next.setOnClickListener(v->{

            showPersonalDetailsSection();

        });

        back_btn.setOnClickListener(v->{
            showBusinessDetailsSection();
        });

//        create_account_btn.setOnClickListener(v->{
//            Intent intent=new Intent(RegisterActivity.this,ValidateAccountActivity.class);
//            startActivity(intent);
//        });

        password_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(PasswordStrength.calculateStrength(charSequence.toString()).getValue() < PasswordStrength.STRONG.getValue())
//                {

                    switch (PasswordStrength.calculateStrength(charSequence.toString()).getValue()){
                        case 0:
                            strengh_tv.setText("WEAK");
                            strengh_tv.setTextColor(PasswordStrength.calculateStrength(charSequence.toString()).getColor());
                            break;

                        case 1:
                            strengh_tv.setText("MEDIUM");
                            strengh_tv.setTextColor(PasswordStrength.calculateStrength(charSequence.toString()).getColor());
                            break;

                        case 2:
                            strengh_tv.setText("STRONG");
                            strengh_tv.setTextColor(PasswordStrength.calculateStrength(charSequence.toString()).getColor());
                            break;

                        case 3:
                            strengh_tv.setText("VERY STRONG");
                            strengh_tv.setTextColor(PasswordStrength.calculateStrength(charSequence.toString()).getColor());
                            break;
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        confirm_password_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().matches(password_et.getText().toString())){
                    confirm_password_tv.setError("password does not match");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void showBusinessDetailsSection(){
        if(!is_business_details_section){
            is_business_details_section=true;
        }

        personal_sign_up_layout.setVisibility(View.GONE);


        one_txt.setVisibility(View.VISIBLE);
        done_iv.setVisibility(View.GONE);

        two_layout.setCardBackgroundColor(getResources().getColor(R.color.white));
        two_txt.setTextColor(getResources().getColor(R.color.colorAccent));
        business_txt.setTextColor(getResources().getColor(R.color.black));
        personal_txt.setTextColor(getResources().getColor(R.color.grey));
        business_sign_up_layout.setVisibility(View.VISIBLE);
        one_layout.setBackground(getResources().getDrawable(R.drawable.circle_layout_accent));
    }

    private void showPersonalDetailsSection(){
        if(is_business_details_section){
            is_business_details_section=false;
        }

        business_sign_up_layout.setVisibility(View.GONE);

        one_txt.setVisibility(View.GONE);
        done_iv.setVisibility(View.VISIBLE);

        two_layout.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
        two_txt.setTextColor(getResources().getColor(R.color.white));
        business_txt.setTextColor(getResources().getColor(R.color.grey));
        personal_txt.setTextColor(getResources().getColor(R.color.black));
        personal_sign_up_layout.setVisibility(View.VISIBLE);
        one_layout.setBackground(getResources().getDrawable(R.drawable.circle_layout_primary));
    }

    @Override
    public void onBackPressed() {

        if(is_business_details_section){
            super.onBackPressed();
        }else {
            showBusinessDetailsSection();
        }

    }

    public enum PasswordStrength
    {

        WEAK(0, Color.RED), MEDIUM(1, Color.argb(255, 220, 185, 0)), STRONG(2, Color.GREEN), VERY_STRONG(3, Color.GREEN);

        //--------REQUIREMENTS--------
        static int REQUIRED_LENGTH = 6;
        static int MAXIMUM_LENGTH = 6;
        static boolean REQUIRE_SPECIAL_CHARACTERS = true;
        static boolean REQUIRE_DIGITS = true;
        static boolean REQUIRE_LOWER_CASE = true;
        static boolean REQUIRE_UPPER_CASE = true;

        int resId;
        int color;

        PasswordStrength(int resId, int color)
        {
            this.resId = resId;
            this.color = color;
        }

        public int getValue()
        {
            return resId;
        }

        public int getColor()
        {
            return color;
        }

        public static PasswordStrength calculateStrength(String password)
        {
            int currentScore = 0;
            boolean sawUpper = false;
            boolean sawLower = false;
            boolean sawDigit = false;
            boolean sawSpecial = false;

            for (int i = 0; i < password.length(); i++)
            {
                char c = password.charAt(i);

                if (!sawSpecial && !Character.isLetterOrDigit(c))
                {
                    currentScore += 1;
                    sawSpecial = true;
                }
                else
                {
                    if (!sawDigit && Character.isDigit(c))
                    {
                        currentScore += 1;
                        sawDigit = true;
                    }
                    else
                    {
                        if (!sawUpper || !sawLower)
                        {
                            if (Character.isUpperCase(c))
                                sawUpper = true;
                            else
                                sawLower = true;
                            if (sawUpper && sawLower)
                                currentScore += 1;
                        }
                    }
                }
            }

            if (password.length() > REQUIRED_LENGTH)
            {
                if ((REQUIRE_SPECIAL_CHARACTERS && !sawSpecial) || (REQUIRE_UPPER_CASE && !sawUpper) || (REQUIRE_LOWER_CASE && !sawLower) || (REQUIRE_DIGITS && !sawDigit))
                {
                    currentScore = 1;
                }
                else
                {
                    currentScore = 2;
                    if (password.length() > MAXIMUM_LENGTH)
                    {
                        currentScore = 3;
                    }
                }
            }
            else
            {
                currentScore = 0;
            }

            switch (currentScore)
            {
                case 0:
                    return WEAK;
                case 1:
                    return MEDIUM;
                case 2:
                    return STRONG;
                case 3:
                    return VERY_STRONG;
                default:
            }

            return VERY_STRONG;
        }

    }
}
