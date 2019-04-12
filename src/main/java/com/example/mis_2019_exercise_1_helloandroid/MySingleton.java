package com.example.mis_2019_exercise_1_helloandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.View;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

//Singleton class for Volley RequestQueue and for loading image
//Source: "https://developer.android.com/training/volley/requestqueue"
public class MySingleton {
    private static MySingleton instance;
    private static RequestQueue requestQueue;
    private static ImageLoader imageLoader;
    private static Context ctx;



    private MySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized MySingleton getInstance(View.OnClickListener context) {
        if (instance == null) {
            instance = new MySingleton((Context) context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public static synchronized ImageLoader getImageLoader() {
        return imageLoader;
    }

}
