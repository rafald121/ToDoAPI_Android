package com.example.android.todoapi_android.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.todoapi_android.DTO.Task;
import com.example.android.todoapi_android.R;

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
        Log.i(TAG, "onClick: CLICKED IN DIALOG");
        if(v.getId() == buttonDelete.getId()){
            Log.i(TAG, "onClick: deleted");
        } else if(v.getId() == buttonEdit.getId()){
            Log.i(TAG, "onClick: edited");
        } else
            Log.e(TAG, "onClick: there isn't other button than delete or edit ");
    }

    @Override
    public void onBackPressed() {
        return;
    }
}

