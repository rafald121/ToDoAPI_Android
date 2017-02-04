package com.example.android.todoapi_android.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.example.android.todoapi_android.DTO.Task;
import com.example.android.todoapi_android.R;

/**
 * Created by Rafaello on 2017-02-04.
 */

public class TaskContentActivity  extends AppCompatActivity{

    TextView title,details,timeToDo, tag, done;
    Task task = null;

    public TaskContentActivity(Task task) {
        this.task = task;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.task_content);
        title = (TextView) findViewById(R.id.textViewTitle);
        details = (TextView) findViewById(R.id.taskContentDetails);
        timeToDo = (TextView) findViewById(R.id.taskContentTimeToDo);
        tag = (TextView) findViewById(R.id.taskContentTAG);

        title.setText(task.getTitle());
        details.setText(task.getDetails());
        timeToDo.setText(task.getTimeToDo());
        tag.setText(task.getTag());
        done.setText(task.isDone()? "Yes" : "No");

    }



}
