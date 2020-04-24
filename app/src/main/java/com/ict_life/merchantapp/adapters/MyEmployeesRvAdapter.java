package com.ict_life.merchantapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.entities.EmployeeDetails;

import java.util.ArrayList;

public class MyEmployeesRvAdapter extends RecyclerView.Adapter<MyEmployeesRvAdapter.MyViewHolder> {

    ArrayList<EmployeeDetails> employeeDetailsArrayList;
    Context ctx;
    private LayoutInflater inflater;
    View view;



    public MyEmployeesRvAdapter (Context ctx, ArrayList<EmployeeDetails> employeeDetailsArrayList) {
        this.ctx=ctx;
        this.employeeDetailsArrayList = employeeDetailsArrayList;
        inflater = LayoutInflater.from(ctx);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        view = inflater.inflate(R.layout.employees_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(ctx).load(R.drawable.plumber).into(holder.profile_pic);
        holder.employee_name_tv.setText(employeeDetailsArrayList.get(position).getName());
        holder.employee_pno_tv.setText(employeeDetailsArrayList.get(position).getPhone_number());


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return employeeDetailsArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView profile_pic;
        TextView employee_name_tv,employee_pno_tv;
        ImageButton delete_employee_btn;

        MyViewHolder(View itemView) {
            super(itemView);

            employee_name_tv=itemView.findViewById(R.id.employee_name_tv);
            employee_pno_tv=itemView.findViewById(R.id.employee_pno_tv);
            profile_pic=itemView.findViewById(R.id.profile_pic);
            delete_employee_btn=itemView.findViewById(R.id.delete_employee_btn);

        }

    }
}
