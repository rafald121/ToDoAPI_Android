package com.example.android.todoapi_android;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rafaello on 2017-01-26.
 */

public interface VolleyCallback{
    void onSuccess(JSONObject result) throws JSONException;
    void onFailure(VolleyError error);
}