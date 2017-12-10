package com.example.materialdesign;


import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
     private FloatingActionButton floatingActionButton;
     private NavigationView navigationView;
     private Fruit[] fruits={
             new Fruit("苹果",R.drawable.apple),new Fruit("香蕉",R.drawable.banana),
             new Fruit("Orange",R.drawable.orange),new Fruit("西瓜",R.drawable.watermelon),
             new Fruit("pear",R.drawable.pear), new Fruit("不好吃",R.drawable.cherry)
     };
private Adapter adapter;
private List<Fruit> mlist= new ArrayList<Fruit>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawablelayout);
        Toolbar bar=(Toolbar)findViewById(R.id.toolbar);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.floatbut);
        navigationView=(NavigationView)findViewById(R.id.navigation);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawablelayout); initfruit();
        RecyclerView recyclerView =(RecyclerView)findViewById(R.id.recyclerview);
        GridLayoutManager manager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);
        adapter=new Adapter(mlist);
        recyclerView.setAdapter(adapter);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"点我啊",Snackbar.LENGTH_SHORT)
                        .setAction("再点我", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,"gg了，你点了我",Toast.LENGTH_SHORT).show();
                            }
                        })

                        .show();
            }
        });
        setSupportActionBar(bar);

        ActionBar actionBar = getSupportActionBar();
         if (actionBar!=null){
       actionBar.setDisplayHomeAsUpEnabled(true);
       actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
         }

    }

    private void initfruit() {
        mlist.clear();
        for (int i=0;i<50;i++){
            Random random= new Random();
            int index=random.nextInt(fruits.length);
            mlist.add(fruits[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                Log.i("000","电导率");
                break;
            case R.id.backup:
                Toast.makeText(this,"点击了back",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                break;
            case R.id.settings:
                break;


                default:
        }
        return true;
    }

}
