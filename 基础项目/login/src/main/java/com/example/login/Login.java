package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by 123456 on 2017/7/23.
 */

public class Login extends SQLiteOpenHelper {

    private final String sql="create table login (name text,password text)";
   private ContentValues mValues;
    private Context mContext;

    public Login(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sql);
        Toast.makeText(mContext,"好了",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.insert("login",null,mValues);
    }

    public void rece(ContentValues values){
       mValues=values;
    }
}
