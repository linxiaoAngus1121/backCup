package com.example.uiforapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    private Button login;
    private EditText editname,editpass;
    private CheckBox remeber;
    private SharedPreferences preferences;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       login= findViewById(R.id.id);
       editname=findViewById(R.id.edt);
       editpass=findViewById(R.id.edt2);

        remeber=(CheckBox) findViewById(R.id.checkbox);

        preferences  = PreferenceManager.getDefaultSharedPreferences(this);

        boolean state = preferences.getBoolean("state", false);
        if(state){
            editname.setText(preferences.getString("name",""));
            editpass.setText(preferences.getString("pass",""));
        }
        login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            String name=   editname.getText().toString();
            String pass = editpass.getText().toString();
            if(name.equals("admin") && pass.equals("admin")){
               if(remeber.isChecked()){
                   SharedPreferences.Editor editor = preferences.edit();
                   editor.putString("name",name);
                   editor.putString("pass",pass);
                   editor.putBoolean("state",true);
                   editor.apply();
               }
                Intent intent = new Intent(MainActivity.this,secondActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(MainActivity.this,"账号密码错误",Toast.LENGTH_LONG).show();
            }
           }
       });
    }
}
