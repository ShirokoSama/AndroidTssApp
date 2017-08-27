package cn.edu.nju.tss.util;

import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Srf on 2017/6/14
 */

public class HttpBasicJsonArrayRequest extends Request<JSONArray> {

    private Response.Listener<JSONArray> listener;
    private SharedPreferences sp;

    public HttpBasicJsonArrayRequest(String url, SharedPreferences sp, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
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
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(jsonString),HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONArray response) {
        listener.onResponse(response);
    }

}
