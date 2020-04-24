package com.ict_life.merchantapp.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ict_life.merchantapp.R;

public class RecommendOtherActivity extends AppCompatActivity {

    private static final int PICK_CONTACT = 100 ;
    EditText name_et,description_et;
    Button submit_btn;
    TextView pno_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_other);

        pno_et=findViewById(R.id.pno_et);
        name_et=findViewById(R.id.name_et);
        description_et=findViewById(R.id.description_et);
        submit_btn=findViewById(R.id.submit_btn);



        Toolbar toolbar =  findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        getSupportActionBar().setTitle("Recommend Someone");

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pno_et.setOnClickListener(v->{
            Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(i, PICK_CONTACT);
        });

        submit_btn.setOnClickListener(v->{
            successPopUp();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contactsData = data.getData();
                CursorLoader loader = new CursorLoader(this, contactsData, null, null, null, null);
                Cursor c = loader.loadInBackground();
                if (c.moveToFirst()) {

                    pno_et.setText(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    name_et.setText(c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

                }
            }
        }
    }

    private void successPopUp() {

        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(RecommendOtherActivity.this);
        View layoutView = (RecommendOtherActivity.this).getLayoutInflater().inflate(R.layout.success_dialog_layout, null);
        dialogBuilder.setView(layoutView);


        Button ok_btn = layoutView.findViewById(R.id.ok_btn);
        TextView text_tv = layoutView.findViewById(R.id.text_tv);

        text_tv.setText("You've recommended " + name_et.getText() +" for an ask made by Miriam");


        android.app.AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();


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
}
