package com.example.android.todoapi_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    Button buttonNewTask, buttonListOfTasks, buttonSchoolTasks, buttonWorkTasks, buttonHomeTasks;
    TextView infoAboutLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String login = getIntent().getStringExtra("login");

        buttonNewTask = (Button) findViewById(R.id.buttonNewTask);
        buttonListOfTasks = (Button) findViewById(R.id.buttonListOfTasks);
        buttonSchoolTasks = (Button) findViewById(R.id.buttonSchoolTasks);
        buttonWorkTasks = (Button) findViewById( R.id.buttonWorkTasks);
        buttonHomeTasks = (Button) findViewById(R.id.buttonHomeTasks);

        initButtons();


    }

    private void initButtons() {
        buttonNewTask.setOnClickListener(this);
        buttonListOfTasks.setOnClickListener(this);
        buttonSchoolTasks.setOnClickListener(this);
        buttonWorkTasks.setOnClickListener(this);
        buttonHomeTasks.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int clickedAction = v.getId();
        switch (clickedAction){
            case R.id.buttonNewTask:
                Intent nextIntent = new Intent(this, newTaskActivity.class);
                startActivity(nextIntent);
                break;
            case R.id.buttonListOfTasks:
                Intent listOfTasks = new Intent(this, listOfTasksActivity.class);
                startActivity(listOfTasks);
                break;
            default:
                Log.i(TAG, "onClick: hmm?");

        }


    }
}

