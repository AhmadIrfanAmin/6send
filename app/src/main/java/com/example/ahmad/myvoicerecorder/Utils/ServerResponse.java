package com.example.ahmad.myvoicerecorder.Utils;

import com.android.volley.VolleyError;

/**
 * Created by Logixcess on 3/10/2018.
 */

public interface ServerResponse {
    void onResponse(String response);
    void onError(VolleyError error);
}
