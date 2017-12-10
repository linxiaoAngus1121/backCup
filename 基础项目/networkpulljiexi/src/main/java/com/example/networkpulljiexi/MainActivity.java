package com.example.networkpulljiexi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bu1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bu1=(Button) findViewById(R.id.but);
        bu1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.but){
            sendRequestWithOKhttp();
        }
    }

    private void sendRequestWithOKhttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request req=new Request.Builder()
                        .url("http://192.168.138.2:8080/project1/NewFile.xml")
                        .build();
                try {
                    Response response = client.newCall(req).execute();
                    final String data = response.body().string();
                    parseXmlWithPull(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseXmlWithPull(String xmldata) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser pullParser = factory.newPullParser();
            pullParser.setInput(new StringReader(xmldata));
            int type = pullParser.getEventType();
            String id="";
            String name="";
            String version ="";
            /*
            * 开始解析
            * */
            while (type!=XmlPullParser.END_DOCUMENT){
                String pullname = pullParser.getName();
                switch (type){
                    case XmlPullParser.START_TAG:
                        if ("id".equals(pullname)){
                              id  =pullParser.nextText();
                        }else if("name".equals(pullname)){
                            name=pullParser.nextText();
                    }else if("version".equals(pullname)){
                            version=pullParser.nextText();
                        }
                        break;
                    /*
                    * 完成解析
                    * */
                    case XmlPullParser.END_TAG:
                        if ("app".equals(pullname)) {
                            Log.i("000",id.trim());
                            Log.i("000",name.trim());
                            Log.i("000",version.trim());
                        }
                }
            }
            type=pullParser.next();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
