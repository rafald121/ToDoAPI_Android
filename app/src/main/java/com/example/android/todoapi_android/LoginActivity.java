package com.example.android.todoapi_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.R.attr.start;

/**
 * Created by Rafaello on 2017-01-17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = LoginActivity.class.getSimpleName();

    EditText editTextLogin;
    EditText editTextPassword;
    Button buttonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLogin = (EditText) findViewById(R.id.login_edittext);
        editTextPassword = (EditText) findViewById(R.id.password_edittext);
        buttonLogin = (Button) findViewById(R.id.login_button);

        initListeners();
    }

    private void initListeners(){
        findViewById(R.id.login_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: Login: "+editTextLogin.getText().toString() + " password: "
                + editTextPassword.getText().toString());
        Intent intentMainActivity = new Intent(this, MainActivity.class);
        startActivity(intentMainActivity);
    }
}


