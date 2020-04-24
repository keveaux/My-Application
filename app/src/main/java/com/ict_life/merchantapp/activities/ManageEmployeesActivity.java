package com.ict_life.merchantapp.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.adapters.MyEmployeesRvAdapter;
import com.ict_life.merchantapp.entities.EmployeeDetails;

import java.util.ArrayList;

public class ManageEmployeesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_employees);

        Toolbar toolbar = findViewById(R.id.toolbar);
        LinearLayout empty_list_layout = findViewById(R.id.empty_list_layout);
        RecyclerView my_employees_rv = findViewById(R.id.my_employees_rv);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));


        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ArrayList<EmployeeDetails> employeeDetailsArrayList = new ArrayList<>();

        employeeDetailsArrayList.add(new EmployeeDetails("Kevin Mirera","0715353311"));
        employeeDetailsArrayList.add(new EmployeeDetails("Esther Fab","0715353311"));
        employeeDetailsArrayList.add(new EmployeeDetails("Miriam Makeba","0715353311"));
        employeeDetailsArrayList.add(new EmployeeDetails("Vince Staples","0715353311"));

        MyEmployeesRvAdapter myEmployeesRvAdapter = new MyEmployeesRvAdapter(getApplicationContext(),employeeDetailsArrayList);

        my_employees_rv.setAdapter(myEmployeesRvAdapter);

        if(employeeDetailsArrayList.isEmpty()){
            empty_list_layout.setVisibility(View.VISIBLE);
        }else {
            empty_list_layout.setVisibility(View.GONE);
        }


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

    public void OpenAddEmployeesActivity(View view) {
        Intent intent = new Intent(this,AddEmployeesActivity.class);
        startActivity(intent);
    }
}
