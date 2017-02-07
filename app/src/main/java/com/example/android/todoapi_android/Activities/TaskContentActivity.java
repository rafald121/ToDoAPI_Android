package com.example.android.todoapi_android.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.todoapi_android.DTO.Task;
import com.example.android.todoapi_android.Helpers.ParcelabledTask;
import com.example.android.todoapi_android.Interfaces.VolleyCallbackDelete;
import com.example.android.todoapi_android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.android.todoapi_android.Activities.ListOfTasksActivity.getTasksListURL;

/**
 * Created by Rafaello on 2017-02-04.
 */

public class TaskContentActivity  extends Dialog implements View.OnClickListener{

    public static final String TAG= TaskContentActivity.class.getSimpleName();

    TextView title,details,timeToDo, tag, done;
    Task task = null;
    Button buttonDelete, buttonEdit;

    public Activity taskContentActivity;
    public Context taskContentContext;

    public Dialog mDialog;

    public TaskContentActivity(Context a, Task task){
        super(a);
        this.taskContentContext = a;
        this.task=task;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.task_content);
        title = (TextView) findViewById(R.id.taskContentTitle);
        details = (TextView) findViewById(R.id.taskContentDetails);
        timeToDo = (TextView) findViewById(R.id.taskContentTimeToDo);
        tag = (TextView) findViewById(R.id.taskContentTAG);
        done = (TextView) findViewById(R.id.taskContentDone);
        buttonDelete = (Button) findViewById(R.id.taskContentButtonDelete);
        buttonEdit = (Button) findViewById(R.id.taskContentButtonEdit);

        Log.i(TAG, "onCreate: passed to " + TAG + " data: " + task.toString());

        title.setText(task.getTitle());
        details.setText(task.getDetails());
        timeToDo.setText(task.getTimeToDo());
        tag.setText(task.getTag());
        done.setText(task.isDone()? "Yes" : "No");


        buttonDelete.setOnClickListener(this);
        buttonEdit.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if(v.getId() == buttonDelete.getId()){
            String taskID = String.valueOf(task.getId());
            try {

                deleteRequest(taskID, new VolleyCallbackDelete() {
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {
                        Log.i(TAG, "onSuccess: " + result.toString());
//                            list.notifyDataSetChanged();
                        Log.i(TAG, "onSuccess: after listOfTaskAdapter.notifyDataSetChanged()");
                    }

                    @Override
                    public void onFailure(VolleyError error) {
                        Log.i(TAG, "onFailure: " + error.toString());

                    }
                });
            } catch (AuthFailureError authFailureError) {
                authFailureError.printStackTrace();
            }

            Intent editIntent = new Intent(taskContentContext, ListOfTasksActivity
                    .class);
            editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            taskContentContext.startActivity(editIntent);

            Log.i(TAG, "onClick: deleted");
        } else if(v.getId() == buttonEdit.getId()){
            ParcelabledTask pTask = new ParcelabledTask();
            pTask.setTitle(task.getTitle());
            pTask.setDetails(task.getDetails());
            pTask.setTimeToDo(task.getTimeToDo());
            pTask.setTag(task.getTag());
            pTask.setId(task.getId());
            pTask.setDone(task.isDone());
            Intent editIntent = new Intent(taskContentContext, EditTaskActivity
                    .class);
            editIntent.putExtra("task", pTask);
            editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            taskContentContext.startActivity(editIntent);
            Log.i(TAG, "onClick: edited");
        } else
            Log.e(TAG, "onClick: there isn't other button than delete or edit ");
    }

    @Override
    public void onBackPressed() {
        return;
    }


    private void deleteRequest(String id, final VolleyCallbackDelete volleyCallbackDelete) throws AuthFailureError {
        SharedPreferences sharedPreferences = taskContentContext.getSharedPreferences(LoginActivity
                        .SESSIONINFO,
                Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token", "");

        RequestQueue mRequestQueue = Volley.newRequestQueue(taskContentContext);
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
//            Log.i(TAG, "getListOfTasks: headers: " + request.getHeaders().toString());
        mRequestQueue.add(request);
    }

}

