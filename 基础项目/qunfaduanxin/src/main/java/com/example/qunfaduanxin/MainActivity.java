package com.example.qunfaduanxin;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.SensorDirectChannel;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button send,select;
    private EditText content,number;
    private ArrayList<String> list = new ArrayList<>(); //存放群发的号码
    private SmsManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send=(Button)findViewById(R.id.send);
        select=(Button)findViewById(R.id.select);
        content=(EditText)findViewById(R.id.edt2);
        number=(EditText)findViewById(R.id.edt1);
        manager=SmsManager.getDefault();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //遍历list，逐一发送短信
                for (String i:list){
                   PendingIntent pi = PendingIntent.getActivity(MainActivity.this,0,new Intent(),0);
                   manager.sendTextMessage(i,null,content.getText().toString(),pi,null);
               }
                Toast.makeText(MainActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
            }
        });



        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Cursor cursor = getContentResolver().query(CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

                BaseAdapter adapter = new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return cursor.getCount();
                    }

                    @Override
                    public Object getItem(int i) {
                        return i;
                    }

                    @Override
                    public long getItemId(int i) {
                        return i;
                    }

                    @Override
                    public View getView(int i, View view, ViewGroup viewGroup) {
                       cursor.moveToPosition(i);
                        CheckBox checkBox = new CheckBox(MainActivity.this);
                        String haoma = cursor.getString(cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER)).replace("-","");
                        checkBox.setText(haoma);
                        if (ischecked(haoma)){
                            checkBox.setChecked(true);
                        }
                        return checkBox;
                    }
                };
                View diagoview = LayoutInflater.from(MainActivity.this).inflate(R.layout.list, null);
                final ListView listView=(ListView)diagoview.findViewById(R.id.list);
                listView.setAdapter(adapter);
                new AlertDialog.Builder(MainActivity.this)
                        .setView(diagoview)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                list.clear();
                            for (int a=0;a<listView.getCount();a++){
                                CheckBox box = (CheckBox) listView.getChildAt(a);
                                if (box.isChecked()){
                                    list.add(box.getText().toString());
                                }
                            }
                            number.setText(list.toString());
                            }
                        })
                        .show();
            }
        });



    }

    public boolean ischecked(String s){
        for (String l:list){
            if (l.equals(s)){
                return true;
            }
        }
        return false;
    }
}
