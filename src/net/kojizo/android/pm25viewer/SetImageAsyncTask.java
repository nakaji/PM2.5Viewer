package net.kojizo.android.pm25viewer;

import gueei.binding.Observable;

import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

public class SetImageAsyncTask extends AsyncTask<Calendar, Integer, Drawable> {

    private Observable<Drawable> _drawable;
    private final String fileNameFormat = "japan_detail_%04d-%02d-%02d-%02d-00-00_large.jpg";
    private final String BaseUrl = "http://guide.tenki.jp/static_images/particulate_matter/japan_detail/";

    public SetImageAsyncTask(Observable<Drawable> drawable) {
        _drawable = drawable;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Drawable doInBackground(Calendar... params) {
        // 内蔵カレンダーの時刻を元に、PM2.5分布予測の画像を取得する
        Calendar cal = params[0];
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        String fileName = String.format(fileNameFormat, year, month, date, hour);
        try {
            //Webから画像を取得してDrawableを返す
            URL url = new URL(BaseUrl + fileName);
            InputStream istream = url.openStream();
            Drawable drowable = Drawable.createFromStream(istream, "webimg");
            return drowable;
        } catch (Exception e) {
            Log.d("PM25Viewer", e.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Drawable drawable) {
        _drawable.set(drawable);
    }
}
