package net.kojizo.android.pm25viewer.test;

import java.util.Hashtable;

import net.kojizo.android.pm25viewer.ImageCache;
import net.kojizo.android.pm25viewer.MainActivity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;

public class ImageCacheTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public ImageCacheTest(Class<MainActivity> activityClass) {
        super(activityClass);
        // TODO 自動生成されたコンストラクター・スタブ
    }
    private ImageCache cache;

    public ImageCacheTest() {
        super(MainActivity.class);
    }
    public void setUp() {
        Hashtable<String, Drawable> hash = new Hashtable<String, Drawable>() ;

        Drawable d = (BitmapDrawable) this.getActivity().getResources().getDrawable(R.drawable.ic_launcher);
        hash.put("hoge.png", d);

        cache = new ImageCache(hash);
    }

    public void testキャッシュにヒットするものがなければnullを返す() {
        Drawable result = cache.getImage("hoge.jpg");

        assertNull(result);
    }
    public void testキャッシュにヒットするものがあればDrawableを返す() {
        Drawable result = cache.getImage("hoge.png");

        assertNotNull(result);
    }
}
