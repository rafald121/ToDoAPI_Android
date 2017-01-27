package com.example.android.todoapi_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rafaello on 2017-01-26.
 */

public class ListOfTasksActivity extends AppCompatActivity{

    public final static String getTasksListURL = "http://10.0.2.2:5000/tasks";
    private static final String TAG = ListOfTasksActivity.class.getSimpleName();
    List<Task> listOfTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getListOfTasks(new VolleyCallback(){

                @Override
                public void onSuccess(JSONObject result) throws JSONException {
                    Log.i(TAG, "onSuccess: " + result.toString());
                    listOfTask = HttpUtils.getListOfTask(result);
                }

                @Override
                public void onFailure(VolleyError error) {
                    Log.i(TAG, "onFailure: " + error.toString());
                }
            });
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }


    }

    private void getListOfTasks(final VolleyCallback volleyCallback) throws AuthFailureError {

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SESSIONINFO,
                Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token", "");

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(getTasksListURL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: HAO");
                        try {
                            volleyCallback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse response = error.networkResponse;
                        if(response!=null)
                        Log.e(TAG, "onErrorResponse: responseCode: " + response.statusCode );

                        Log.e(TAG, "onErrorResponse: error: " +  error.toString());


                        volleyCallback.onFailure(error);

                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("token", token);

                return headers;
            }

        };
        Log.i(TAG, "getListOfTasks: headers: " + request.getHeaders().toString());
        mRequestQueue.add(request);

    }
}
