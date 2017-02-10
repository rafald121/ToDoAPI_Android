package com.example.android.todoapi_android.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.todoapi_android.Adapters.ListOfTaskAdapter;
import com.example.android.todoapi_android.DTO.Task;
import com.example.android.todoapi_android.Interfaces.RecyclerViewClickListener;
import com.example.android.todoapi_android.Interfaces.VolleyCallbackArray;
import com.example.android.todoapi_android.R;
import com.example.android.todoapi_android.Utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rafaello on 2017-01-26.
 */

public class ListOfTasksActivity extends AppCompatActivity implements RecyclerViewClickListener {

    public final static String getTasksListURL = "http://10.0.2.2:5000/tasks";
    private static final String TAG = ListOfTasksActivity.class.getSimpleName();

    String taskTag,doneOrUndone;
    List<Task> listOfTask;
    RecyclerView recyclerView;
    ListOfTaskAdapter listOfTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_task);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent().hasExtra("tag")) {

            final String tag = getIntent().getStringExtra("tag");

            if (tag.equals("school"))
                taskTag = "/school";
            else if (tag.equals("work"))
                taskTag = "/work";
            else if(tag.equals("home"))
                taskTag = "/home";
            else
                Log.e(TAG, "onCreate: other tag" );

        } else if(getIntent().hasExtra("done")) {

            final String done = getIntent().getStringExtra("done");

            if(done.equals("done"))
                taskTag = "/done";
            else if (done.equals("undone"))
                taskTag =  "/undone";
            else
                Log.e(TAG, "onCreate: unbelieviable");

            Log.i(TAG, "onCreate: DONE OR UNDONE: " + doneOrUndone);
        }
        else {
            taskTag = "";
        }

        Log.i(TAG, "onCreate: SELECTED TASK TAG: " + taskTag);

        try {
            getListOfTasks(new VolleyCallbackArray(){
                @Override
                public void onSuccess(JSONArray result) throws JSONException {
                    listOfTask = HttpUtils.getListOfTask(result);

                    listOfTaskAdapter = new ListOfTaskAdapter(ListOfTasksActivity.this,
                            listOfTask,
                            new RecyclerViewClickListener() {
                                @Override
                                public void recyclerViewListClicked(View v, int position) {
                                    Task task = listOfTask.get(position);
                                    Log.i(TAG, "recyclerViewListClicked: INFOBOUT" + task.toString
                                            ());

                                    TaskContentActivity taskContentActivity = new
                                            TaskContentActivity(ListOfTasksActivity.this, task);
                                    taskContentActivity.show();

                                }
                            });

                    recyclerView.setAdapter(listOfTaskAdapter);
                }
                @Override
                public void onFailure(VolleyError error) {
                    Log.e(TAG, "onFailure: " + error.toString());
                }
            });
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
    }


    @Override
    public void recyclerViewListClicked(View v, int position) {

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
        mRequestQueue.add(request);

    }


}
