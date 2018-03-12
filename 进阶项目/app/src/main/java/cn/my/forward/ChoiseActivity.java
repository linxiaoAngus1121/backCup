package cn.my.forward;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ChoiseActivity extends AppCompatActivity {

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise);
    }

    /**
     * 课表查询
     *
     * @param view 视图
     */
    public void toTable(View view) {
        mIntent = new Intent(ChoiseActivity.this, TimeTableActivity.class);
        startActivity(mIntent);
    }

    /**
     * 成绩查询
     *
     * @param view 视图
     */
    public void toScore(View view) {
        mIntent = new Intent(ChoiseActivity.this, SourceQueryActivity.class);
        startActivity(mIntent);
    }
}
