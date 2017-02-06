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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.todoapi_android.Adapters.ListOfTaskAdapter;
import com.example.android.todoapi_android.DTO.Task;
import com.example.android.todoapi_android.Interfaces.RecyclerViewClickListener;
import com.example.android.todoapi_android.Interfaces.VolleyCallbackArray;
import com.example.android.todoapi_android.Interfaces.VolleyCallbackDelete;
import com.example.android.todoapi_android.R;
import com.example.android.todoapi_android.Utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rafaello on 2017-01-26.
 */

public class ListOfTasksActivity extends AppCompatActivity implements RecyclerViewClickListener {

    public final static String getTasksListURL = "http://10.0.2.2:5000/tasks";
    private static final String TAG = ListOfTasksActivity.class.getSimpleName();

    String taskTag;
    List<Task> listOfTask;
    RecyclerView recyclerView;
    ListOfTaskAdapter listOfTaskAdapter;
//    Intent editIntent;
//    Intent deleteIntent;
//    Intent undoneIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_task);
        Context context = this.getApplicationContext();

//        editIntent = new Intent(this, EditTaskActivity.class);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

//        recyclerView.addOnItemTouchListener();
        if(getIntent().hasExtra("tag")) {
            Log.i(TAG, "onCreate: 1");
            final String tag = getIntent().getStringExtra("tag");
            Log.e(TAG, "onCreate: tag from intent: " + tag );
            if (tag.equals("school"))
                taskTag = "/school";
            else if (tag.equals("work"))
                taskTag = "/work";
            else if(tag.equals("home"))
                taskTag = "/home";
            else
                Log.e(TAG, "onCreate: LOL" );
            Log.i(TAG, "onCreate: taskTagInIf: " + taskTag);
        } else {
            Log.i(TAG, "onCreate: 2");
            taskTag = "";
        }


        Log.i(TAG, "onCreate: TASKTAG: " + taskTag);

        try {
            getListOfTasks(new VolleyCallbackArray(){
                @Override
                public void onSuccess(JSONArray result) throws JSONException {
                    Log.i(TAG, "onSuccess: tukkeeej" + result.toString());
                    listOfTask = HttpUtils.getListOfTask(result);
                    Log.i(TAG, "onSuccess: list : " + listOfTask.toString());

                    listOfTaskAdapter = new ListOfTaskAdapter(getApplicationContext(), listOfTask,
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

//                            ,
//                            new RecyclerViewItemActions() {
//                                @Override
//                                public void recyclerViewEditTask(View v, int position) {
////                                    Task task = listOfTask.get(position);
////                                    Log.i(TAG, "recyclerViewEditTask: edit clicked for title: " +
////                                            task.getTitle());
////                                    //TODO da sie skrocic \/?
////                                    ParcelabledTask pTask = new ParcelabledTask();
////                                    pTask.setTitle(task.getTitle());
////                                    pTask.setDetails(task.getDetails());
////                                    pTask.setTimeToDo(task.getTimeToDo());
////                                    pTask.setTag(task.getTag());
////                                    pTask.setId(task.getId());
////                                    pTask.setDone(task.isDone());
////
////                                    Intent editIntent = new Intent(ListOfTasksActivity.this, EditTaskActivity.class);
////                                    editIntent.putExtra("task", pTask);
////                                    startActivity(editIntent);
//
//
//                                }
//
//                                @Override
//                                public void recyclerViewDeleteTask(View v, int position) {
//                                    Task task = listOfTask.get(position);
//                                    Log.i(TAG, "recyclerViewDeletetTask: delete clicked for " +
//                                            "title: " +
//                                            task.getTitle());
//
//                                    String taskID = String.valueOf(task.getId());
//                                    try {
//
//                                        deleteRequest(taskID, new VolleyCallbackDelete() {
//                                            @Override
//                                            public void onSuccess(JSONObject result) throws JSONException {
//                                                Log.i(TAG, "onSuccess: " + result.toString());
//                                                listOfTaskAdapter.notifyDataSetChanged();
//                                                Log.i(TAG, "onSuccess: after listOfTaskAdapter.notifyDataSetChanged()");
//                                            }
//
//                                            @Override
//                                            public void onFailure(VolleyError error) {
//                                                Log.i(TAG, "onFailure: " + error.toString());
//
//                                            }
//                                        });
//                                    } catch (AuthFailureError authFailureError) {
//                                        authFailureError.printStackTrace();
//                                    }
//
//
//                                }
//
//                                @Override
//                                public void recyclerViewUnOrDoneTask(View v, int position) {
//                                    Task task = listOfTask.get(position);
//                                    Log.i(TAG, "recyclerViewUnDoneTask: undone clicked for title:" +
//                                            " " +
//                                            task.getTitle());
//                                    ParcelabledTask editTask = new ParcelabledTask();
//                                    editTask.setTitle(task.getTitle());
//                                    editTask.setDetails(task.getDetails());
//                                    editTask.setTimeToDo(task.getTimeToDo());
//                                    editTask.setTag(task.getTag());
//                                    editTask.setId(task.getId());
//                                    editTask.setDone(task.isDone());
//
//                                    //TODO zedytować aby od razu wracało do listy i ją aktualizowalo
//
//
//                                }
//                            });
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

    private void deleteRequest(String id, final VolleyCallbackDelete volleyCallbackDelete) throws AuthFailureError {

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SESSIONINFO,
                Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token", "");

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        Log.i(TAG, "getListOfTasks: URL: " + getTasksListURL + "/" + id);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE,
                getTasksListURL + "/" + id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            volleyCallbackDelete.onSuccess(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyCallbackDelete.onFailure(error);
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
        Log.i(TAG, "getListOfTasks: headers: " + request.getHeaders().toString());
        mRequestQueue.add(request);

    }


}
