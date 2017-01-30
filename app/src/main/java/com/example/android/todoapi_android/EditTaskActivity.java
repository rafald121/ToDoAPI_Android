package com.example.android.todoapi_android;

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

import java.util.HashMap;

/**
 * Created by Rafaello on 2017-01-29.
 */

public class EditTaskActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EditTaskURL = "http://10.0.2.2:5000/tasks"; // + id !
    private static final String TAG = EditTaskActivity.class.getSimpleName();
    EditText editTextTitle,editTextDetails,editTextTimeToDo;
    TextView textViewError;
    RadioButton RBSchool, RBWork, RBHome;
    RadioGroup radioGroup;
    Button buttonAddTask;

    SerializabledTask sTask;

    HashMap<String, String> map;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SESSIONINFO,
                Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
//        String login = getIntent().getStringExtra("login");

//        Intent i = getIntent();
//        SerializabledTask task = (SerializabledTask) i.getSerializableExtra("task");
//        String s = getIntent().getStringExtra("title");
//        Log.i(TAG, "onCreate: odczytany string" + s);

        sTask = (SerializabledTask) getIntent().getSerializableExtra("task");
        Log.i(TAG, "onCreate: sTask to String: " + sTask.toString());

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


//        fillInputWithData(sTask);

//        buttonAddTask.setOnClickListener(this);

    }

    private void fillInputWithData(SerializabledTask sTask) {
        editTextTitle.setText(sTask.getTitle());
        editTextDetails.setText(sTask.getDetails());
        editTextTimeToDo.setText(sTask.getTimeToDo());
        if(sTask.getTag().equals("school"))
            RBSchool.setChecked(true);
        else if(sTask.getTag().equals("work"))
            RBWork.setChecked(true);
        else if(sTask.getTag().equals("home"))
            RBHome.setChecked(true);
        else
            Log.e(TAG, "fillInputWithData: OTRZYMALEM OBIEKT BEZ ZAZNACZONEGO TAGU" + sTask.toString());

        Intent refresh = new Intent(this, EditTaskActivity.class);
        startActivity(refresh);
        this.finish();
    }

    @Override
    public void onClick(View v) {
//        if(v.getId() == buttonAddTask.getId())
//            sendEditRequest()
    }



//    private void sendEditRequest(HashMap<String, String> map, final VolleyCallback volleyCallback) {
//
//        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SESSIONINFO,
//                Context.MODE_PRIVATE);
//        final String token = sharedPreferences.getString("token", "");
//
//        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
//
//        JsonObjectRequest request = new JsonObjectRequest(AddTaskURL, new JSONObject(map),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(final JSONObject response) {
//                        Log.i(TAG, "onResponse: work? " + response.toString());
//                        JSONObject result = response;
//                        if(result!=null) {
//                            Log.i(TAG, "onResponse: PRZED CALLBACK");
//                            try {
//                                volleyCallback.onSuccess(result);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        else
//                            Log.i(TAG, "onResponse: RESULT IS NULL");
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        VolleyLog.e("Error tukej ", error.getMessage());
//                        volleyCallback.onFailure(error);
//                    }
//                }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("token", token);
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//        };
//        mRequestQueue.add(request);
//    }

//    private void fillInputWithData() {
//        editTextTitle.setText();
//    }


}
