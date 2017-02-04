package com.example.android.todoapi_android.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.todoapi_android.R;
import com.example.android.todoapi_android.Interfaces.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rafaello on 2017-01-26.
 */

public class NewTaskActivity extends AppCompatActivity {

    public static final String AddTaskURL = "http://10.0.2.2:5000/tasks";
    private static final String TAG = NewTaskActivity.class.getSimpleName();

    EditText editTextTitle,editTextDetails,editTextTimeToDo;
    TextView textViewError;
    RadioButton RBSchool, RBWork, RBHome;
    RadioGroup radioGroup;
    Button buttonAddTask;

    HashMap<String, String> map;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SESSIONINFO,
                Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
//        String login = getIntent().getStringExtra("login");
        Log.i(TAG, "onCreate: TOKEN?" + token);

        textViewError = (TextView) findViewById(R.id.textViewError);
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextDetails = (EditText) findViewById(R.id.editTextDetails);
        editTextTimeToDo = (EditText) findViewById(R.id.editTextTimeToDo);

        buttonAddTask = (Button) findViewById(R.id.buttonAddTask);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        RBSchool = (RadioButton) findViewById(R.id.radioButtonSchool);
        RBWork = (RadioButton) findViewById(R.id.radioButtonWork);
        RBHome = (RadioButton) findViewById(R.id.radioButtonHome);


        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                map = getDataFromActivityToMap();

                postRequest(map, new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        Log.i(TAG, "onSuccess: " + result.toString());
                        Intent intent = new Intent(NewTaskActivity.this.getBaseContext(),
                                MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(VolleyError error) {
                        Log.i(TAG, "onFailure: " + error.toString());
                        textViewError.setText("error while adding task");
                    }
                });


            }
        });


    }

    private void postRequest(HashMap<String, String> map, final VolleyCallback volleyCallback) {

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SESSIONINFO,
                Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token", "");

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(AddTaskURL, new JSONObject(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.i(TAG, "onResponse: work? " + response.toString());
                        JSONObject result = response;
                        if(result!=null) {
                            Log.i(TAG, "onResponse: PRZED CALLBACK");
                            try {
                                volleyCallback.onSuccess(result);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                            Log.i(TAG, "onResponse: RESULT IS NULL");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error tukej ", error.getMessage());
                        volleyCallback.onFailure(error);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("token", token);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        mRequestQueue.add(request);
    }


    private HashMap<String,String> getDataFromActivityToMap() {
        HashMap<String,String> map = new HashMap<>();

        map.put("title", editTextTitle.getText().toString());
        map.put("details", editTextDetails.getText().toString());
        map.put("timeToDo", editTextTimeToDo.getText().toString());

        int selectedButton = radioGroup.getCheckedRadioButtonId();
        String tag = "";
        switch (selectedButton){
            case R.id.radioButtonSchool:
                tag = "school";
                break;
            case R.id.radioButtonWork:
                tag = "work";
                break;
            case R.id.radioButtonHome:
                tag = "home";
                break;
            default:
                tag = "";
        }
        map.put("tag", tag);

        return map;
    }



}
