package com.example.sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DataBaseProvider extends ContentProvider {

    public  static  final  int BOOK_DIR=0;
    public  static  final  int BOOK_ITEM=1;
    private static UriMatcher matcher;
    private static String authority="com.example.sqlite.provider";
    private  MySQLiteHelp mySQLiteHelp;
    static {
         matcher=new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(authority,"book",BOOK_DIR);
        matcher.addURI(authority,"book/#",BOOK_ITEM);
    }

    public DataBaseProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase writableDatabase = mySQLiteHelp.getWritableDatabase();
        int i=0;
        switch (matcher.match(uri)){
            case BOOK_DIR:
                i = writableDatabase.delete("book", selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String s = uri.getPathSegments().get(1);
                i=writableDatabase.delete("book","id=?",new String[]{s});
                break;
        }
        // Implement this to handle requests to delete one or more rows.
       return i;
    }

    @Override
    public String getType(Uri uri) {
       switch (matcher.match(uri)){
           case BOOK_DIR:
               return "vnd.android.cursor.dir/vnd.con.example.provider.book";
           case BOOK_ITEM:
               return "vnd.android.cursor.item/vnd.con.example.provider.book";
       }
       return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase writableDatabase = mySQLiteHelp.getWritableDatabase();
        switch (matcher.match(uri)){
            /*
            * 因为插入数据无论时一条还是整个表
            * */
         case BOOK_DIR:
      case BOOK_ITEM:
        writableDatabase.insert("book",null,values);
        break;
}
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        mySQLiteHelp= new MySQLiteHelp(getContext(),"book.db",null,2);
        return true;
        // TODO: Implement this to initialize your content provider on startup.
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase writableDatabase = mySQLiteHelp.getWritableDatabase();
        Cursor cursor=null;
        switch (matcher.match(uri)){
            case BOOK_DIR:
                cursor=  writableDatabase.query("book",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case BOOK_ITEM:
               String id = uri.getPathSegments().get(1);
                cursor=writableDatabase.query("book",projection," id=?",new String[]{id},null,null,sortOrder);
                break;
        }

       return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase writableDatabase = mySQLiteHelp.getWritableDatabase();
        int i=0;
       switch (matcher.match(uri)){
           case BOOK_DIR:
                i = writableDatabase.update("book", values, selection, selectionArgs);
               break;
           case BOOK_ITEM:
               String s = uri.getPathSegments().get(1);
              i= writableDatabase.update("book",values,"id=?",new String[]{s});
               break;
       }
       return i;
    }
}
