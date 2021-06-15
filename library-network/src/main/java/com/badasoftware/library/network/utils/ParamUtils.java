package com.badasoftware.library.network.utils;

import android.util.Log;
import java.util.Map;
import java.util.TreeMap;

public class ParamUtils {

    public static final String TAG = ParamUtils.class.getSimpleName();

    private Map<String, Object> params;

    public ParamUtils addParams(String key, Object value) {
        if (params == null) {
            params = new TreeMap<>();
        }

        params.put(key, value);
        return this;
    }

    public Map getParams() {
        if (params == null) {
            return null;
        }

        return params;
    }

    public void clearParams() {
        if (params != null)
            params.clear();
    }

    public String createUrlFromParams(String url, Map<String, String> params) {
        try
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(url);

            if (url.indexOf('&') > 0 || url.indexOf('?') > 0) {
                stringBuilder.append("&");
            } else {
                stringBuilder.append("?");
            }

            for (Map.Entry<String, String> urlParams : params.entrySet()) {
                String urlValues = urlParams.getValue();
                stringBuilder.append(urlParams.getKey()).append("=").append(urlValues).append("&");
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            return stringBuilder.toString();
        }
        catch (Exception exception) {
            Log.e(TAG, exception.getMessage());
        }
        return url;
    }
}
