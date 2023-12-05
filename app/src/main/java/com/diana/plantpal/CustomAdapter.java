package com.diana.plantpal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    Context context;
    Activity activity;
    public ArrayList plant_id, plant_name, plant_lastday, plant_period, plant_nextday;

    CustomAdapter(Activity activity, Context context, ArrayList plant_id, ArrayList plant_name, ArrayList plant_lastday,
                  ArrayList plant_period, ArrayList plant_nextday){
        this.activity=activity;
        this.context=context;
        this.plant_id=plant_id;
        this.plant_name=plant_name;
        this.plant_lastday=plant_lastday;
        this.plant_period=plant_period;
        this.plant_nextday=plant_nextday;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.plant_name_txt.setText(String.valueOf(plant_name.get(position)));
        holder.plant_lastday_txt.setText(String.valueOf(plant_lastday.get(position)));
        holder.plant_period_txt.setText(String.valueOf(plant_period.get(position)));
        holder.plant_nextday_txt.setText(String.valueOf(plant_nextday.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(context, UdpateActivity.class);
                    intent.putExtra("id", String.valueOf(plant_id.get(holder.getAdapterPosition())));
                intent.putExtra("name", String.valueOf(plant_name.get(holder.getAdapterPosition())));
                intent.putExtra("lastday", String.valueOf(plant_lastday.get(holder.getAdapterPosition())));
                intent.putExtra("period", String.valueOf(plant_period.get(holder.getAdapterPosition())));
                intent.putExtra("nextday", String.valueOf(plant_nextday.get(holder.getAdapterPosition())));
                    activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plant_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView plant_name_txt, plant_lastday_txt, plant_period_txt, plant_nextday_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            plant_name_txt=itemView.findViewById(R.id.plant_name_txt);
            plant_lastday_txt=itemView.findViewById(R.id.plant_lastday_txt);
            plant_period_txt=itemView.findViewById(R.id.plant_period_txt);
            plant_nextday_txt=itemView.findViewById(R.id.plant_nextday_txt);
            mainLayout=itemView.findViewById(R.id.mainLayout);
        }
    }
}
