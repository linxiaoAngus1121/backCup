package com.example.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   private Button mCreate_button;
   private MySQLiteHelp db;
   private Button mInsert_data;
    private Button mUpdate;
    private Button mQuery;
    private Button mDelete;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCreate_button=(Button) findViewById(R.id.createDatabase);
        mInsert_data=(Button)findViewById(R.id.add_data);
        mQuery=(Button)findViewById(R.id.query);
        mUpdate=(Button)findViewById(R.id.update);
        mDelete=(Button)findViewById(R.id.delete);
        db = new MySQLiteHelp(this,"data.db",null,2);
        mCreate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.getWritableDatabase();
            }
        });
        mInsert_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("author","林晓");
                values.put("price",12.56);
                values.put("pages",456);
                values.put("name","西游记");
                database.insert("book",null,values);
                values.clear();
                values.put("author","林杉");
                values.put("price",99.36);
                values.put("pages",999);
                values.put("name","快乐一家");
                database.insert("book",null,values);
                Toast.makeText(MainActivity.this,"add data success",Toast.LENGTH_LONG).show();
            }
        });
        mQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = db.getWritableDatabase();
                Cursor cursor = database.query("book", null, null, null, null, null, null, null);
                if (cursor.moveToFirst()){
                    do {
                        String author=cursor.getString(cursor.getColumnIndex("author"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        Log.i("000","author is" +author);
                        Log.i("000","price is"+price);
                        Log.i("000","pages is"+pages);
                        Log.i("000","name is"+name);
                    }while (cursor.moveToNext());
                }cursor.close();
            }
        });


        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = db.getWritableDatabase();

                ContentValues values= new ContentValues();
                values.put("author","林莎");
                database.update("book",values,"author = ?",new String[]{"林杉"});
                Toast.makeText(MainActivity.this,"成功更新数据",Toast.LENGTH_LONG).show();
            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = db.getWritableDatabase();
                database.delete("book"," name = ?" ,new String[]{"西游记"});
            }
        });

    }
}
