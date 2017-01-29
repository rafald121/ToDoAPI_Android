package com.example.android.todoapi_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        infoAboutLogin = (TextView) findViewById(R.id.textViewloginInfo);
        buttonNewTask = (Button) findViewById(R.id.buttonNewTask);
        buttonListOfTasks = (Button) findViewById(R.id.buttonListOfTasks);
        buttonSchoolTasks = (Button) findViewById(R.id.buttonSchoolTasks);
        buttonWorkTasks = (Button) findViewById( R.id.buttonWorkTasks);
        buttonHomeTasks = (Button) findViewById(R.id.buttonHomeTasks);

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SESSIONINFO,
                Context.MODE_PRIVATE);
        String login = sharedPreferences.getString("login","");
        String token = sharedPreferences.getString("token", "");
        infoAboutLogin.setText(infoAboutLogin.getText() + " " + login);
//        String login = getIntent().getStringExtra("login");
        Log.i(TAG, "onCreate: TOKEN?" + token);


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
        Log.i(TAG, "onClick: START");
        int clickedAction = v.getId();
        switch (clickedAction){
            case R.id.buttonNewTask:
                Intent nextIntent = new Intent(this, NewTaskActivity.class);
                startActivity(nextIntent);
                break;
            case R.id.buttonListOfTasks: //all
                Intent listOfTasks = new Intent(this, ListOfTasksActivity.class);
                listOfTasks.putExtra("tag","all");
                startActivity(listOfTasks);
                break;
            case R.id.buttonSchoolTasks:
                Intent school = new Intent(this, ListOfTasksActivity.class);
                school.putExtra("tag","school");
                startActivity(school);
                break;
            case R.id.buttonWorkTasks:
                Intent work = new Intent(this, ListOfTasksActivity.class);
                work.putExtra("tag","work");
                startActivity(work);
                break;
            case R.id.buttonHomeTasks:
                Intent home = new Intent(this, ListOfTasksActivity.class);
                home.putExtra("tag","home");
                startActivity(home);
                break;
            default:
                Log.e(TAG, "onClick: cos nie tak");

        }


    }
}

