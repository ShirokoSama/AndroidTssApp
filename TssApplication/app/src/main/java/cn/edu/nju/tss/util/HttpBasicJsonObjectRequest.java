package cn.edu.nju.tss.util;



import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Srf on 2017/6/12
 */

public class HttpBasicJsonObjectRequest extends Request<JSONObject> {

    private Listener<JSONObject> listener;
    private SharedPreferences sp;

    public HttpBasicJsonObjectRequest(String url, SharedPreferences sp, Listener<JSONObject> listener, ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = listener;
        this.sp = sp;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        String base64 = sp.getString("auth", null);
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", base64);
        return header;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }

}
