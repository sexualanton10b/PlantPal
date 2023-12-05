package com.diana.plantpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

RecyclerView recyclerView;
FloatingActionButton add_button, list_button;
MyDataBaseHelper myDB;
ArrayList<String> plant_id, plant_name, plant_lastday, plant_period, plant_nextday;
ImageView empty_imageview;
TextView no_data;
CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        add_button=findViewById(R.id.add_button);
        list_button=findViewById(R.id.list_button);
        no_data=findViewById(R.id.no_data);
        empty_imageview=findViewById(R.id.empty_igmageview);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent intent = new Intent(MainActivity.this, AddActivity.class);
startActivity(intent);
            }
        });
        list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
        myDB=new MyDataBaseHelper(MainActivity.this);
        plant_id=new ArrayList<>();
        plant_name=new ArrayList<>();
        plant_lastday=new ArrayList<>();
        plant_period=new ArrayList<>();
        plant_nextday=new ArrayList<>();
        storeDataInArrays();
        customAdapter=new CustomAdapter(MainActivity.this, this, plant_id,plant_name, plant_lastday, plant_period, plant_nextday);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor= myDB.readAllData();
        if (cursor.getCount()==0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else{
            Toast.makeText(this, "Yes data.", Toast.LENGTH_SHORT).show();
            while(cursor.moveToNext()){
                plant_id.add(cursor.getString(0));
                plant_name.add(cursor.getString(1));
                plant_lastday.add(cursor.getString(2));
                plant_period.add(cursor.getString(3));
                plant_nextday.add(cursor.getString(4));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.delete_all){
            confirmDialog();

        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Удалить всё?");
        builder.setMessage("Вы уверены, что хотите удалить всё?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                MyDataBaseHelper myDB=new MyDataBaseHelper(MainActivity.this);
                myDB.deleteAllData();
                //Обновляет активити
                Intent intent=new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        builder.create().show();
    }
}