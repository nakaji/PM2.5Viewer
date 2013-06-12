package net.kojizo.android.pm25viewer.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import net.kojizo.android.pm25viewer.ImageCache;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.test.AndroidTestCase;

public class ImageCacheTest extends AndroidTestCase {

    private ImageCache cache;

    /***
     * テストプロジェクトのコンテキストを返す
     * @return
     */
    public Context getExTestContext() {
        Context context = null;
        @SuppressWarnings("unchecked")
        Class clz = (Class) this.getClass();
        try {
            Method method = clz.getMethod("getTestContext");
            context = (Context) method.invoke(this);
        } catch (SecurityException e) {
            fail();
        } catch (NoSuchMethodException e) {
            fail();
        } catch (IllegalArgumentException e) {
            fail();
        } catch (IllegalAccessException e) {
            fail();
        } catch (InvocationTargetException e) {
            fail();
        }
        return context;
    }

    public void setUp() {
        Hashtable<String, Drawable> hash = new Hashtable<String, Drawable>();

        Drawable d = (BitmapDrawable) getExTestContext().getResources().getDrawable(R.drawable.ic_launcher);
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
