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
import android.widget.ImageView;

public class MainActivity extends Activity {

    String fileNameFormat = "japan_detail_%04d-%02d-%02d-%02d-00-00_large.jpg";
    String BaseUrl = "http://guide.tenki.jp/static_images/particulate_matter/japan_detail/";
    Calendar _cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //StrictModeを設定 penaltyDeathを取り除く
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());

        _cal = Calendar.getInstance();

        Drawable drawable = getDrawableByCalendar();

        ImageView imageView = (ImageView) this.findViewById(R.id.imageView);

        imageView.setImageDrawable(drawable);

    }

    private Drawable getDrawableByCalendar() {
        int year = _cal.get(Calendar.YEAR);
        int month = _cal.get(Calendar.MONTH) + 1;
        int date = _cal.get(Calendar.DATE);
        int hour = (int) (Math.floor(_cal.get(Calendar.HOUR) / 3)*3+3);

        String fileName = String.format(fileNameFormat, year, month, date, hour);

        Drawable drawable = getDrawableFromInternet(BaseUrl + fileName);

        return drawable;

    }

    private Drawable getDrawableFromInternet(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
            InputStream istream = url.openStream();
            //Drawableが必要な場合
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
