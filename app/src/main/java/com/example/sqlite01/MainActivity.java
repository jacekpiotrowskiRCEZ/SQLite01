package com.example.sqlite01;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity<SQLDatabase> extends AppCompatActivity {
SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=openOrCreateDatabase("Szkola",MODE_PRIVATE,null);
        String sqlDB="CREATE TABLE IF NOT EXISTS UCZNIOWIE (Id INTEGER, Imie VARCHAR, Nazwisko VARCHAR)";
        db.execSQL(sqlDB);

        String sqlCount= "SELECT count(*) FROM UCZNIOWIE";
        Cursor cursor = db.rawQuery(sqlCount,null);
        cursor.moveToFirst();
        int ilosc=cursor.getInt(0);
        cursor.close();
        if(ilosc==0){
            String sqlUczen="INSERT INTO UCZNIOWIE VALUES(?,?,?)";
            SQLiteStatement statement = db.compileStatement(sqlUczen);

            statement.bindLong(1,1);
            statement.bindString(2,"John");
            statement.bindString(3,"Doe");
            statement.executeInsert();

            statement.bindLong(1,2);
            statement.bindString(2,"Joan");
            statement.bindString(3,"Collins");
            statement.executeInsert();

        }

    }
    public void onClick(View view){
        ArrayList<String> wyniki = new ArrayList<String>();
        Cursor c= db.rawQuery("SELECT Id, Imie, Nazwisko from Uczniowie ",null);
        if (c.moveToFirst()){
            do{
                int id= c.getInt(c.getColumnIndex("Id"));
                String imie= c.getString(c.getColumnIndex("Imie"));
                String nazwisko=c.getString(c.getColumnIndex("Nazwisko"));
                wyniki.add("Id: "+id+", imię: "+imie+", nazwisko: "+nazwisko);
            }while(c.moveToNext());
        }
        ListView listView=(ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,wyniki);
        listView.setAdapter(adapter);
        c.close();

    }


}