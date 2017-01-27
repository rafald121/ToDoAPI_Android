package com.example.android.todoapi_android;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Rafaello on 2017-01-27.
 */

public interface VolleyCallbackArray {
    void onSuccess(JSONArray result) throws JSONException;
    void onFailure(VolleyError error);
}
