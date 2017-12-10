package com.example.shujuku;

import android.app.AlertDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button read_but,write_but;
    private EditText tex1,tex2;
    final  String name="/crazy.bin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tex1=(EditText)findViewById(R.id.edit1);
        tex2=(EditText)findViewById(R.id.edit2);
        read_but=(Button)findViewById(R.id.read);
        write_but=(Button)findViewById(R.id.write);
        read_but.setOnClickListener(this);
        write_but.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.read:
                try {
                    tex2.setText(read());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.write:
                write(tex1.getText().toString().trim());
                break;
                default:
        }
    }

    private void write(String tex1) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File SDcardDir = Environment.getExternalStorageDirectory();
            try {
                File file = new File(SDcardDir.getCanonicalPath()+name);
                RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
                randomAccessFile.seek(file.length());
                randomAccessFile.write(tex1.getBytes());
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private String read() throws IOException {
        BufferedReader reader = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File SDcardDir = Environment.getExternalStorageDirectory();
            try {
                FileInputStream inputStream = new FileInputStream(SDcardDir.getCanonicalPath() + name);
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                StringBuilder builder = new StringBuilder();
                while ((line = (reader.readLine())) != null) {
                    builder.append(line);
                }
                return builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
        return null;
    }

}
