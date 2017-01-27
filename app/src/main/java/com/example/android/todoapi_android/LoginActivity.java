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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Rafaello on 2017-01-17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    public final String URLSTRING = "http://10.0.2.2:5000/login";
    public static final String SESSIONINFO = "Session";
    EditText editTextLogin;
    EditText editTextPassword;
    TextView textViewError;

    Button buttonLogin;
    String login, password;

    JSONObject responseJSON;
    HashMap<String, String> responseMAP;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLogin = (EditText) findViewById(R.id.login_edittext);
        editTextPassword = (EditText) findViewById(R.id.password_edittext);
        textViewError = (TextView) findViewById(R.id.textViewError);
        textViewError.setVisibility(TextView.INVISIBLE);

        buttonLogin = (Button) findViewById(R.id.login_button);

        buttonLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        login = editTextLogin.getText().toString();
        password = editTextPassword.getText().toString();

        final Intent toMain = new Intent(this, MainActivity.class);

        if ( login.equals("") || password.equals("")){
            Log.e(TAG, "onClick: LOGIN OR DATA MUSTN'T BE EMPTY");
            textViewError.setVisibility(TextView.VISIBLE);
            textViewError.setText("LOGIN OR DATA MUSTN'T BE EMPTY");
        } else {

            Log.i(TAG, "onClick: before postRequest");
            postRequest(login,password, new VolleyCallback(){

                @Override
                public void onSuccess(JSONObject result) {

                    Log.i(TAG, "onSuccess: result: " + result.toString());
                    responseJSON = result;

                    Log.i(TAG, "onClick: PRZED RESPONSE != NULL");
                    if (responseJSON!=null){

                        try {
                            Log.i(TAG, "onClick: BEFORE PARSED RESPONSE");
                            responseMAP = HttpUtils.parseJSONLogin(responseJSON);
                            Log.i(TAG, "onClick: PARSED RESPONSE");
                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                        if(responseMAP.get("info").equals("OK")) {

                            sharedPreferences = getSharedPreferences(SESSIONINFO, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("login", login);
                            editor.putString("token", responseMAP.get("token"));
                            editor.commit();

                            startActivity(toMain);
                        } else{
                            textViewError.setVisibility(TextView.VISIBLE);
                            textViewError.setText("Error in login");
                        }

                    } else {
                        Log.e(TAG, "onClick: responseJSON IS NULL");
                    }

                }

                @Override
                public void onFailure(VolleyError error) {
                    editTextLogin.setText("");
                    editTextPassword.setText("");
                    textViewError.setText("Invalid login or password");

                }


            });


        }





    }


    public void postRequest(String login, String password,final VolleyCallback volleyCallback){

        HashMap<String,String> params = new HashMap<String, String>();
        params.put("login", login);
        params.put("password", password);

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(URLSTRING, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.i(TAG, "onResponse: work? " + response.toString());
                        JSONObject result = response;
                        if(result!=null) {
                            Log.i(TAG, "onResponse: PRZED CALLBACK");
                            try {
                                volleyCallback.onSuccess(result);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Log.i(TAG, "onResponse: RESULT IS NULL");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error tukej ", error.getMessage());
                        volleyCallback.onFailure(error);
                    }
                });

        mRequestQueue.add(request);

    }



}