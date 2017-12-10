package com.example.login;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener{

   private FloatingActionButton button;
   private Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        button=(FloatingActionButton)findViewById(R.id.done);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.done:
                Snackbar.make(view,"点我一下",Snackbar.LENGTH_SHORT)
                        .setAction("点我注册", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent intent1 = new Intent(ThirdActivity.this,FourActivity.class);
                                        startActivity(intent1);
                                    }
                                }
                        )
                        .show();
                break;

        }
    }
}
