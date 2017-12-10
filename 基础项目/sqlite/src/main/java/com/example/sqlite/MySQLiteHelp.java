package com.example.sqlite;

import android.accessibilityservice.FingerprintGestureController;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 123456 on 2017/7/15.
 */

public class MySQLiteHelp extends SQLiteOpenHelper {

    private static final String sql="create table book ("
            + "id integer primary key autoincrement,"
            +"author text,"
            +"price real,"
            +"pages integer,"
            +"name text)";

    private static final String sql2="create table category ("
            + "id integer primary key autoincrement,"
            +"category_code integer,"
            +"category_name text)";

    private Context mContext;
    public MySQLiteHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists book");
        db.execSQL("drop table if exists category");
        onCreate(db);
    }
}
