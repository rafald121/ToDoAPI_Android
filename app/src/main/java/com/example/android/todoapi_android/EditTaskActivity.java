package com.example.android.todoapi_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Rafaello on 2017-01-29.
 */

public class EditTaskActivity extends AppCompatActivity{

    public static final String EditTaskURL = "http://10.0.2.2:5000/tasks"; // + id !
    private static final String TAG = EditTaskActivity.class.getSimpleName();
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

//        Intent i = getIntent();
//        SerializabledTask task = (SerializabledTask) i.getSerializableExtra("task");
        Task task = getIntent().getParcelableExtra("task");

        Log.i(TAG, "onCreate: TASK SERIALIZABLED: " + task.toString());

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



    }



}
