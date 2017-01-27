package com.example.android.todoapi_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;

/**
 * Created by Rafaello on 2017-01-26.
 */

public class NewTaskActivity extends AppCompatActivity {

    EditText editTextTitle,editTextDetails,editTextTimeToDo;
    RadioButton RBSchool, RBWork, RBHome;
    RadioGroup radioGroup;
    Button buttonAddTask;

    HashMap<String, String> map;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);

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

                


            }
        });


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
                tag = "School";
                break;
            case R.id.radioButtonWork:
                tag = "Work";
                break;
            case R.id.radioButtonHome:
                tag = "Home";
                break;
            default:
                tag = "";
        }
        map.put("tag", tag);

        return map;
    }



}
