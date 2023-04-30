package com.example.eventfinder;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class BackendAPI {

    private static final String BASE_URL = "https://webtech-deployment-ass8.uw.r.appspot.com";

    public static String constructURL(String url, Map<String, String> params) {
        StringBuilder paramStringBuilder = new StringBuilder();
        paramStringBuilder.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramStringBuilder.append(entry.getKey());
            paramStringBuilder.append("=");
            paramStringBuilder.append(entry.getValue());
            paramStringBuilder.append("&");
        }
        String paramString = paramStringBuilder.toString();
        System.out.println("final URL: " + BASE_URL + url + paramString.substring(0, paramString.length() - 1));
        return BASE_URL + url + paramString.substring(0, paramString.length() - 1);
    }

    public static void callBackendAPI(Context context, String url, Map<String, String> params, Response.Listener<String> listener,
                                      Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, constructURL(url, params),
                listener, errorListener);
        queue.add(stringRequest);
    }

}


