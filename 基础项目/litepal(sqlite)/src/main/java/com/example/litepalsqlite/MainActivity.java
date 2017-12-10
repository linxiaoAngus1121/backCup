package com.example.litepalsqlite;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

public class MainActivity extends AppCompatActivity {

    private Button mcreat;
    private Button mAdd;
    private Button mUpdate;
    private Button mDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcreat=(Button)findViewById(R.id.create_litepal);
        mAdd=(Button)findViewById(R.id.add_data);
        mUpdate=(Button)findViewById(R.id.update) ;
        mDelete=(Button)findViewById(R.id.delete) ;
        mcreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Connector.getDatabase();
                Toast.makeText(MainActivity.this,"success create",Toast.LENGTH_SHORT).show();
            }
        });
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                Category category=new Category();
                book.setName("林莎");
                book.setPages(5);
                book.setPrices(12.63);
                book.setAuthor("里小鸟");
                Log.i("000","第一次添加成功");
                book.save();
                category.setCategoryCode(1);
                category.setCateroryName("林峰");
                Log.i("000","第2次categroy添加成功");
                book.save();

            }
        });
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.setAuthor("林莎");
                book.updateAll("name=?","林莎");
                book.setToDefault("pages");
                Toast.makeText(MainActivity.this,"成功更新",Toast.LENGTH_SHORT).show();
                book.updateAll();

            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"成功删除",Toast.LENGTH_SHORT).show();
                DataSupport.deleteAll(Book.class,"prices = ?","12.63");

            }
        });
    }
}
