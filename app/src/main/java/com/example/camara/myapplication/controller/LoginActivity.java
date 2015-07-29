package com.example.camara.myapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.camara.myapplication.R;

/**
 * Created by Camara on 20/07/2015.
 */
public class LoginActivity extends AppCompatActivity{

    Button buttonLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindLoginButton();
    }

    private void bindLoginButton() {
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(LoginActivity.this, ClientPersistActivity.class);
                Intent intent = new Intent(LoginActivity.this, ClientListActivity.class);
                startActivity(intent);

            }
        });
    }
}
