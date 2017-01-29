package com.example.android.todoapi_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rafaello on 2017-01-26.
 */

public class ListOfTasksActivity extends AppCompatActivity implements RecyclerViewClickListener{

    public final static String getTasksListURL = "http://10.0.2.2:5000/tasks";
    private static final String TAG = ListOfTasksActivity.class.getSimpleName();

    String taskTag;
    List<Task> listOfTask;
    RecyclerView recyclerView;
    ListOfTaskAdapter listOfTaskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_task);
        Context context = this.getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

//        recyclerView.addOnItemTouchListener();
        String tag = getIntent().getStringExtra("tag");

        if(tag.equals("school"))
            taskTag="/school";
        else if(tag.equals("work"))
            taskTag="/work";
        else if(tag.equals("home"))
            taskTag="/home";
        else
            taskTag="";

        try {
            getListOfTasks(new VolleyCallbackArray(){
                @Override
                public void onSuccess(JSONArray result) throws JSONException {
                    Log.i(TAG, "onSuccess: " + result.toString());
                    listOfTask = HttpUtils.getListOfTask(result);
                    Log.i(TAG, "onSuccess: list : " + listOfTask.toString());

                    listOfTaskAdapter = new ListOfTaskAdapter(getApplicationContext(),listOfTask,
                            new RecyclerViewClickListener() {
                                @Override
                                public void recyclerViewListClicked(View v, int position) {
                                    Task task = listOfTask.get(position);
                                    Log.i(TAG, "recyclerViewListClicked: INFOBOUT" + task.toString
                                            ());

                                }
                            });
                    recyclerView.setAdapter(listOfTaskAdapter);
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

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }


//
//    @Override
//    public void recyclerViewListClicked(View v, int position) {
//
//    }

    public static class RecyclerItemClickListener implements RecyclerView.OnTouchListener{

        private AdapterView.OnItemClickListener mListener;
        GestureDetector mGestureDetector;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);

            public void onLongItemClick(View view, int position);
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }



    }

    @Override
    protected void onPause() {
        super.onPause();
        taskTag="";
    }

    private void getListOfTasks(final VolleyCallbackArray volleyCallbackArray) throws
            AuthFailureError {

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SESSIONINFO,
                Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token", "");

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        Log.i(TAG, "getListOfTasks: URL: " + getTasksListURL + taskTag);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                getTasksListURL+taskTag,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            volleyCallbackArray.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyCallbackArray.onFailure(error);
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
