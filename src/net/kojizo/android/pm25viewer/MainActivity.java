package net.kojizo.android.pm25viewer;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private Calendar _cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayNow(null);
    }

    /***
     * 3時間前の状況を表示する
     * @param v
     */
    public void displayPrev(View v) {
        _cal.add(Calendar.HOUR_OF_DAY, -3);

        drawMap();
    }

    /***
     * 3時間後の状況を表示する
     * @param v
     */
    public void displayNext(View v) {
        _cal.add(Calendar.HOUR_OF_DAY, 3);

        drawMap();
    }

    /***
     * 現在の状況を表示する
     * @param v
     */
    public void displayNow(View v) {
        calendarInit();

        drawMap();
    }

    /***
     * 画面に地図を描画する
     */
    private void drawMap() {
        //非同期実行用のタスクを作成し、実行
        SetImageAsyncTask task = new SetImageAsyncTask((ImageView) findViewById(R.id.imageView));
        task.execute(_cal);
    }

    /***
     * 内蔵カレンダーを直近の3時間単位で初期化
     * 例）22:00に実行された場合は0:00
     */
    private void calendarInit() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int date = now.get(Calendar.DATE);
        int hour = (int) (Math.floor(now.get(Calendar.HOUR_OF_DAY) / 3) * 3 + 3);

        _cal = Calendar.getInstance();
        _cal.set(year, month, date, hour, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
