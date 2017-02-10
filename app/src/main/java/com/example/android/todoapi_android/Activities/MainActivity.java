package com.example.android.todoapi_android.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.todoapi_android.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    Button buttonNewTask, buttonListOfTasks, buttonSchoolTasks, buttonWorkTasks, buttonHomeTasks,
            buttonLogout;
    ImageButton buttonDoneList, buttonUndoneList;
    TextView infoAboutLogin;
    SharedPreferences sharedPreferences;

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
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonDoneList = (ImageButton) findViewById(R.id.buttonDoneTask);
        buttonUndoneList = (ImageButton) findViewById(R.id.buttonUndoneTask);

        sharedPreferences = getSharedPreferences(LoginActivity.SESSIONINFO,
                Context.MODE_PRIVATE);
        String login = sharedPreferences.getString("login","");
        String token = sharedPreferences.getString("token", "");

        infoAboutLogin.setText(infoAboutLogin.getText() + " " + login);

        initButtons();

    }

    private void initButtons() {
        buttonNewTask.setOnClickListener(this);
        buttonListOfTasks.setOnClickListener(this);
        buttonSchoolTasks.setOnClickListener(this);
        buttonWorkTasks.setOnClickListener(this);
        buttonHomeTasks.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        buttonDoneList.setOnClickListener(this);
        buttonUndoneList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int clickedAction = v.getId();
        switch (clickedAction){

            case R.id.buttonNewTask:
                Intent nextIntent = new Intent(this, NewTaskActivity.class);
                startActivity(nextIntent);
                break;
            case R.id.buttonListOfTasks: //all
                Intent listOfTasks = new Intent(this, ListOfTasksActivity.class);
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
            case R.id.buttonLogout:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Log.i(TAG, "onClick: czy wyczyszczono preferences? : " + sharedPreferences.getString("login",""));
                Intent toLogin = new Intent(this, LoginActivity.class);
                startActivity(toLogin);
                break;
            case R.id.buttonDoneTask:
                Intent done = new Intent(this, ListOfTasksActivity.class);
                done.putExtra("done","done");
                startActivity(done);
                break;
            case R.id.buttonUndoneTask:
                Intent undone = new Intent(this, ListOfTasksActivity.class);
                undone.putExtra("done","undone");
                startActivity(undone);
                break;
            default:
                Log.e(TAG, "onClick: other option");

        }


    }
}

