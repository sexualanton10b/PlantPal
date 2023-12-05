package com.diana.plantpal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UdpateActivity extends AppCompatActivity {
    EditText title_input, lastday_input, period_input;
    Button change_button, update_button, delete_button;
    String id, name, lastday, period, nextday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udpate);
        title_input = findViewById(R.id.title_input2);
        lastday_input = findViewById(R.id.lastday_input2);
        period_input = findViewById(R.id.period_input2);
        change_button = findViewById(R.id.change_button);
        update_button=findViewById(R.id.update_button);
        delete_button=findViewById(R.id.delete_button);
        //Сначала вызываем метод
        getaAndSetIntentData();
        ActionBar ab=getSupportActionBar();
        if (ab!=null){
            ab.setTitle(name);
        }
        change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDataBaseHelper myDB=new MyDataBaseHelper(UdpateActivity.this);
                name=title_input.getText().toString().trim();
                lastday=lastday_input.getText().toString().trim();
                period=period_input.getText().toString().trim();
                // И только потом мы вызываем это
                myDB.updateData(id, name, lastday, Integer.valueOf(period));
            }
        });
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBaseHelper myDB=new MyDataBaseHelper(UdpateActivity.this);
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                nextday = today.format(formatter);
                myDB.updateData(id, name, nextday, Integer.valueOf(period));
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });


    }

    void getaAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("lastday")
                && getIntent().hasExtra("period") && getIntent().hasExtra("nextday")) {
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            lastday = getIntent().getStringExtra("lastday");
            period = getIntent().getStringExtra("period");
            nextday=getIntent().getStringExtra("nextday");
            //Setting Intent Data
            title_input.setText(name);
            lastday_input.setText(lastday);
            period_input.setText(period);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Удалить "+name+"?");
        builder.setMessage("Вы уверены, что хотите удалить "+name+"?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                MyDataBaseHelper myDB=new MyDataBaseHelper(UdpateActivity.this);
                myDB.deleteOneRow(id);
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