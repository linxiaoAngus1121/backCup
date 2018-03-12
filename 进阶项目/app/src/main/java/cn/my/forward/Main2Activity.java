package cn.my.forward;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private EditText ed1, ed2, ed3, ed4;

    private static final String NANME = "12306";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mButton1 = (Button) findViewById(R.id.button);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);
        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        ed3 = (EditText) findViewById(R.id.ed3);
        ed4 = (EditText) findViewById(R.id.ed4);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = getSharedPreferences(NANME, MODE_PRIVATE).edit();
                edit.putString("name", ed1.getText().toString());
                edit.putInt("age", Integer.valueOf(ed2.getText().toString()));
                edit.putFloat("value", Float.valueOf(ed3.getText().toString()));
                edit.putLong("long", Long.valueOf(ed4.getText().toString()));
                edit.apply();
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(NANME, MODE_PRIVATE);
                String name = sharedPreferences.getString("name", "没有姓名");
                int age = sharedPreferences.getInt("age", 0);
                float value = sharedPreferences.getFloat("value", 0.0f);
                sharedPreferences.getLong("long", (long) 0.0);
                Toast.makeText(Main2Activity.this, "姓名" + name + "年龄" + age + "价值" + value, Toast
                        .LENGTH_SHORT).show();
            }
        });


    }

}
