package net.kojizo.android.pm25viewer;

import gueei.binding.Binder;
import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Binder.init(this);
    }
}