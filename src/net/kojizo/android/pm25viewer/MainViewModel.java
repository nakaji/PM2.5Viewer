package net.kojizo.android.pm25viewer;

import gueei.binding.Command;
import gueei.binding.Observable;

import java.util.Calendar;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

public class MainViewModel {
    private Calendar _cal;
    public Observable<Drawable> Image = new Observable<Drawable>(Drawable.class);

    public MainViewModel() {
        calendarInit();
        drawMap();
    }

    /***
     * 3時間前の状況を表示する
     * @param v
     */
    public Command DisplayPrev = new Command() {
        @Override
        public void Invoke(View arg0, Object... arg1) {
            Log.d("PM25View", "DisplayPrev");
            _cal.add(Calendar.HOUR_OF_DAY, -3);
            drawMap();
        }
    };

    /***
     * 3時間後の状況を表示する
     * @param v
     */
    public Command DisplayNext = new Command() {
        @Override
        public void Invoke(View arg0, Object... arg1) {
            Log.d("PM25View", "DisplayNext");
            _cal.add(Calendar.HOUR_OF_DAY, 3);
            drawMap();
        }
    };

    /***
     * 現在の状況を表示する
     * @param v
     */
    public Command DisplayNow = new Command() {
        @Override
        public void Invoke(View arg0, Object... arg1) {
            Log.d("PM25View", "DisplayNow");
            calendarInit();
            drawMap();
        }
    };

    /***
     * 画面に地図を描画する
     */
    private void drawMap() {
        //非同期実行用のタスクを作成し、実行
        SetImageAsyncTask task = new SetImageAsyncTask(Image);
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

}
