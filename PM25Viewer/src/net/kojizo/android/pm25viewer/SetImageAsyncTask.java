package net.kojizo.android.pm25viewer;

import gueei.binding.Observable;

import java.io.InputStream;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

public class SetImageAsyncTask extends AsyncTask<Object, Integer, Drawable> {

    private Observable<Drawable> _drawable;

    public SetImageAsyncTask(Observable<Drawable> drawable) {
        _drawable = drawable;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Drawable doInBackground(Object... params) {
        // 内蔵カレンダーの時刻を元に、PM2.5分布予測の画像を取得する
        String uri = (String) params[0];
        ImageCache cache = (ImageCache) params[1];

        try {
            //Webから画像を取得してDrawableを返す
            URL url = new URL(uri);
            InputStream istream = url.openStream();
            Drawable drowable = Drawable.createFromStream(istream, "webimg");
            cache.add(url.toString(), drowable);
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
