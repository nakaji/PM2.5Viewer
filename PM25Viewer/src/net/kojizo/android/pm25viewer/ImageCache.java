package net.kojizo.android.pm25viewer;

import java.util.Hashtable;

import android.graphics.drawable.Drawable;

public class ImageCache {

    Hashtable<String, Drawable> _hash;

    public ImageCache(Hashtable<String, Drawable> hash) {
        _hash = hash;
    }

    public Drawable getImage(String  uri) {
            Drawable d = _hash.get(uri);
        return d;
    }

}
