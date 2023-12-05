package com.diana.plantpal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListCustomAdapter extends RecyclerView.Adapter<ListCustomAdapter.ViewHolder>{
    Context context;
    Activity activity;
    public ArrayList plant_paint, plant2_name, plant_text;
    ListCustomAdapter(Activity activity, Context context, ArrayList plant_paint, ArrayList plant2_name, ArrayList plant_text){
        this.activity=activity;
        this.context=context;
        this.plant_paint=plant_paint;
        this.plant2_name=plant2_name;
        this.plant_text=plant_text;
    }
    @NonNull
    @Override
    public ListCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.plant_item, parent, false);
        return new ListCustomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCustomAdapter.ViewHolder holder, int position) {
        holder.plant2_name_txt.setText(String.valueOf(plant2_name.get(position)));

        holder.plantLayout.setOnClickListener(new View.OnClickListener() {
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
        return plant2_name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView plant2_name_txt;
        ImageView plant_paint_view;
        LinearLayout plantLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            plant2_name_txt=itemView.findViewById(R.id.plant2_name_txt);
            plant_paint_view=itemView.findViewById(R.id.plant_lastday_txt);
            plantLayout=itemView.findViewById(R.id.plantLayout);
        }
    }
}
