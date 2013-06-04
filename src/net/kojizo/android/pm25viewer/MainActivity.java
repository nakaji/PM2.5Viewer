package net.kojizo.android.pm25viewer;

import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private final String fileNameFormat = "japan_detail_%04d-%02d-%02d-%02d-00-00_large.jpg";
    private final String BaseUrl = "http://guide.tenki.jp/static_images/particulate_matter/japan_detail/";
    private Calendar _cal;

    private final MainActivity mainActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //StrictModeを設定 penaltyDeathを取り除く
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());

        calendarInit();

        drawMap();

        Button prev = (Button) findViewById(R.id.buttonPrev);
        prev.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                _cal.add(Calendar.HOUR, -3);

                drawMap();
            }
        });

        Button next = (Button) findViewById(R.id.buttonNext);
        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                _cal.add(Calendar.HOUR_OF_DAY, 3);

                drawMap();
            }
        });
    }

    /*
     * 画面に地図を描画する
     */
    private void drawMap() {
        Drawable drawable = getDrawableByCalendar();

        ImageView imageView = (ImageView) this.findViewById(R.id.imageView);

        imageView.setImageDrawable(drawable);
    }

    /*
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

    /*
     * 内蔵カレンダーの時刻を元に、PM2.5分布予測の画像を取得する
     */
    private Drawable getDrawableByCalendar() {
        int year = _cal.get(Calendar.YEAR);
        int month = _cal.get(Calendar.MONTH);
        int date = _cal.get(Calendar.DATE);
        int hour = _cal.get(Calendar.HOUR_OF_DAY);

        String fileName = String.format(fileNameFormat, year, month, date, hour);

        Drawable drawable = getDrawableFromInternet(BaseUrl + fileName);

        return drawable;
    }

    /*
     * Webから画像を取得してDrawableを返す
     */
    private Drawable getDrawableFromInternet(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
            InputStream istream = url.openStream();
            Drawable drowable = Drawable.createFromStream(istream, "webimg");
            return drowable;
        } catch (Exception e) {
            Log.d("PM25Viewer", e.toString());
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}