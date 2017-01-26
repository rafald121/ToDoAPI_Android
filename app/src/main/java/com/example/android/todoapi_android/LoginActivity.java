package com.example.android.todoapi_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, VolleyCallback {

    private static final String TAG = LoginActivity.class.getSimpleName();
    public final String URLSTRING = "http://10.0.2.2:5000/login";
    EditText editTextLogin;
    EditText editTextPassword;

    Button buttonLogin;
    String login, password;

    JSONObject responseJSON;
    HashMap<String, String> responseMAP;

    VolleyCallback callback;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLogin = (EditText) findViewById(R.id.login_edittext);
        editTextPassword = (EditText) findViewById(R.id.password_edittext);
        buttonLogin = (Button) findViewById(R.id.login_button);

        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        login = editTextLogin.getText().toString();
        password = editTextPassword.getText().toString();

        if ( login.equals("") || password.equals("")){
            Log.e(TAG, "onClick: LOGIN OR DATA MUSTN'T BE EMPTY");
        } else {
            Log.i(TAG, "onClick: before postRequest");
            postRequest(login,password);
        }

        if (responseJSON!=null){

            Log.i(TAG, "onClick: responseJSON != null ");
            try {
                responseMAP = HttpUtils.parseJSONLogin(responseJSON);
            } catch (JSONException e){
                e.printStackTrace();
            }

            if(responseMAP!=null){
                Log.i(TAG, "onClick: responseMAP IS NOT NULL, DATA: "  + responseMAP.toString());

            } else {
                Log.e(TAG, "onClick: RESPONSE MAP IS NULL");
            }


        } else {
            Log.e(TAG, "onClick: responseJSON IS NULL");
        }



    }


    public void postRequest(String login, String password){

        HashMap<String,String> params = new HashMap<String, String>();
        params.put("login", login);
        params.put("password", password);

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(URLSTRING, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(final JSONObject response) {
//                        Log.i(TAG, "onResponse: dziala? " + response.toString());
//                        Log.i(TAG, "onResponse: szybsze2");
                        JSONObject result = response;
                        new VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                responseJSON = result;
                            }
                        }.onSuccess(result);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error tukej ", error.getMessage());
                        responseJSON = null;
                    }
                });

        mRequestQueue.add(request);


    }


    @Override
    public void onSuccess(JSONObject result) {
        responseJSON = result;
    }
}