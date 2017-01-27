package com.example.android.todoapi_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * Created by Rafaello on 2017-01-26.
 */

public class NewTaskActivity extends AppCompatActivity{

    EditText editTextTitle,editTextDetails,editTextTimeToDo;
    RadioButton RBSchool, RBWork, RBHome;
    Button buttonAddTask;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextDetails = (EditText) findViewById(R.id.editTextDetails);
        editTextTimeToDo = (EditText) findViewById(R.id.editTextTimeToDo);

        buttonAddTask = (Button) findViewById(R.id.buttonAddTask);



    }

}
