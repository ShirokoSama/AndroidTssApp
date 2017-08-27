package cn.edu.nju.tss.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Srf on 2017/6/12
 */

public class SingletonRequestQueue {

    private static SingletonRequestQueue singleton;
    private RequestQueue requestQueue;
    private static Context context;

    private SingletonRequestQueue(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized SingletonRequestQueue getInstance(Context context) {
        if (singleton == null)
            singleton = new SingletonRequestQueue(context.getApplicationContext());
        return singleton;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}
