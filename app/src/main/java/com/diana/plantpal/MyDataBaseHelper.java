package com.diana.plantpal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class MyDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME="PlantLibrary.db";
    public static final int DATABASE_VERSION=1;
    public static final String TABLE_NAME="my_library";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_NAME="plant_name";
    public static final String COLUMN_LASTDAY="plant_lastday";
    public static final String COLUMN_PERIOD="plant_period";
    public  static final String COLUMN_NEXTDAY="plant_nextday";
    public static String nextDay(String lastday, String period){
        LocalDate LastDay=date(lastday);
        LastDay = LastDay.plusDays(Integer.parseInt(period));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LastDay.format(formatter);
    }
    public static LocalDate date(String day){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(day, formatter);
    }

    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query=
                "CREATE TABLE " +TABLE_NAME+ " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, "+ COLUMN_LASTDAY + " TEXT, " + COLUMN_PERIOD + " INTEGER, "+COLUMN_NEXTDAY+" TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
    void addPlant(String title, String lastday, int per){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, title);
        cv.put(COLUMN_LASTDAY, lastday);
        cv.put(COLUMN_PERIOD, per);
        cv.put(COLUMN_NEXTDAY, nextDay(lastday, String.valueOf(per)));
        long result =db.insert(TABLE_NAME, null, cv);
        if (result==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAllData(){
        String query="SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=null;
        if (db!=null){
            cursor=db.rawQuery(query, null);
        }
        return cursor;
    }
    void updateData(String row_id, String title, String lastday, int per){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, title);
        cv.put(COLUMN_LASTDAY, lastday);
        cv.put(COLUMN_PERIOD, per);
        cv.put(COLUMN_NEXTDAY, nextDay(lastday, String.valueOf(per)));
        long result =db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result==-1){
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
    }
    void deleteOneRow(String row_id){
        SQLiteDatabase db=this.getWritableDatabase();
        long result= db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result==-1){
            Toast.makeText(context, "Ошибка удаления.", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(context, "Успешно удалено!", Toast.LENGTH_SHORT).show();
    }
    void deleteAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }
}