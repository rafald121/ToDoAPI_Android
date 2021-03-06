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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.todoapi_android.Helpers.ParcelabledTask;
import com.example.android.todoapi_android.Interfaces.VolleyCallback;
import com.example.android.todoapi_android.R;
import com.example.android.todoapi_android.Utils.HashMapUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.android.todoapi_android.Activities.NewTaskActivity.AddTaskURL;

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

    HashMap<String, Object> map;
    ParcelabledTask parcelabledTask;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);

        textViewError = (TextView) findViewById(R.id.textViewError);
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextDetails = (EditText) findViewById(R.id.editTextDetails);
        editTextTimeToDo = (EditText) findViewById(R.id.editTextTimeToDo);

        buttonAddTask = (Button) findViewById(R.id.buttonAddTask);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        RBSchool = (RadioButton) findViewById(R.id.radioButtonSchool);
        RBWork = (RadioButton) findViewById(R.id.radioButtonWork);
        RBHome = (RadioButton) findViewById(R.id.radioButtonHome);

        Intent i = getIntent();
        parcelabledTask = i.getParcelableExtra("task");

        if(parcelabledTask!=null) {
            Log.i(TAG, "onCreate: WCZYTANO PARCELABLED TASK");
            fillInputWithData(parcelabledTask);
        }
        else
            Log.e(TAG, "onCreate: PARCELABLE TASK IS NULL");

        buttonAddTask.setOnClickListener(this);

    }

    private void fillInputWithData(ParcelabledTask sTask) {


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

    }

    @Override
    public void onClick(View v) {

        String tag="";
        int checkedRadio = radioGroup.getCheckedRadioButtonId();
        if (checkedRadio == RBSchool.getId())
            tag="school";
        else if(checkedRadio == RBHome.getId())
            tag="home";
        else if(checkedRadio == RBWork.getId())
            tag="work";
        else
            Log.e(TAG, "onClick: DON'T CHECKED RADIOBUTTON?");

        map = HashMapUtils.createHashMapFromObject(
                editTextTitle.getText().toString(),
                editTextDetails.getText().toString(),
                editTextTimeToDo.getText().toString(),
                tag, parcelabledTask.isDone());

        if(v.getId() == buttonAddTask.getId())
            Log.i(TAG, "onClick: ID" + parcelabledTask.getId());

            if(checkIfFormWasFilled(editTextTitle)) {
                sendEditRequest(parcelabledTask.getId(), map, new VolleyCallback() {

                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {
                        result.toString();
                        Intent intent = new Intent(EditTaskActivity.this, ListOfTasksActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(VolleyError error) {
                        //TODO dodac, aby wyświetało kod odpowiedzi
                        textViewError.setText("Error occurred while send edit request" + error.getMessage());
                    }
                });
            }   else {
                textViewError.setText("You have to add title of task");
            }

    }

    private boolean checkIfFormWasFilled(EditText editTextTitle){

        if(editTextTitle.getText().toString().equals("")) {
            Log.i(TAG, "checkIfFormWasFilled: FALSE");
            return false;
        } else {
            Log.i(TAG, "checkIfFormWasFilled: TRUE");
            return true;
        }
    }

    private void sendEditRequest(int id, HashMap<String, Object> map, final VolleyCallback
            volleyCallback) {

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SESSIONINFO,
                Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token", "");

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        String url = AddTaskURL + "/" + id;
        Log.i(TAG, "sendEditRequest: URL IN PUT REQUEST: " + url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,url,
                new JSONObject (map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {

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
                            Log.e(TAG, "onResponse: RESULT IS NULL");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error tukej in put", error.getMessage());
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
}
