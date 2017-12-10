package com.example.gestgure;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class TTs extends AppCompatActivity {

    private TextToSpeech textToSpeech;      //语音朗读
    private EditText edit_tts;
    private Button speak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        speak=(Button)findViewById(R.id.speak);
        edit_tts=(EditText)findViewById(R.id.edit_tts);
        //初始化 new TextToSpeech.OnInitListener()里面进行一系列的初始化操作，即判读是否支持所设置的语言
        textToSpeech=new TextToSpeech(TTs.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                /*
                * 如果支持setlanguage设置的语言，就会返回LANG_COUNTRY_AVAILABLE，还有LANG_AVAILABLE
                * */
                int language = textToSpeech.setLanguage(Locale.US);
                if (language!=TextToSpeech.LANG_COUNTRY_AVAILABLE
                        && language!=TextToSpeech.LANG_AVAILABLE){
                    Toast.makeText(TTs.this,"惨了，不能支持你所设置的语言",Toast.LENGTH_SHORT).show();
                }
            }
        });

        speak.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String trim = edit_tts.getText().toString().trim();
                textToSpeech.speak(trim,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

    }
/*
*
* 一定要在destroy中shutdown  texttospeech 占有的资源
* */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech!=null){
            textToSpeech.shutdown();
        }
    }
}
