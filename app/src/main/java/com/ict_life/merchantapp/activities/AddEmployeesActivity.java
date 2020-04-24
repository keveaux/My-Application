package com.ict_life.merchantapp.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ict_life.merchantapp.R;

public class AddEmployeesActivity extends AppCompatActivity {

    private static final int PICK_CONTACT = 100;
    String et_pno_value;
    EditText name_et;
    boolean error = true;
    TextView et_pno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employees);

        et_pno = findViewById(R.id.et_pno);
        Spinner role_spinner = findViewById(R.id.role_spinner);
        Button invite_btn = findViewById(R.id.invite_btn);
        name_et = findViewById(R.id.name_et);


        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        getSupportActionBar().setTitle("Add Employees");

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        et_pno.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(i, PICK_CONTACT);
        });

        ArrayAdapter<CharSequence> adapter_date_spinner = ArrayAdapter.createFromResource(this,
                R.array.roles, R.layout.spinner_item);
        adapter_date_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role_spinner.setAdapter(adapter_date_spinner);

        invite_btn.setOnClickListener(v -> {

            if (name_et.getText().toString().equals("")) {
                name_et.setError("Enter name");
            } else if (et_pno.getText().toString().equals("")) {
                et_pno.setError("Enter Phone number");
            } else {
                successPopUp();
            }
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

                    et_pno.setText(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                }
            }
        }
    }


    private void successPopUp() {

        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(AddEmployeesActivity.this);
        View layoutView = (AddEmployeesActivity.this).getLayoutInflater().inflate(R.layout.success_dialog_layout, null);
        dialogBuilder.setView(layoutView);


        Button ok_btn = layoutView.findViewById(R.id.ok_btn);
        TextView text_tv = layoutView.findViewById(R.id.text_tv);

        text_tv.setText("Invitation sent to " + name_et.getText());


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
