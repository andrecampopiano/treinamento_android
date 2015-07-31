package com.example.camara.myapplication.controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.camara.myapplication.R;
import com.example.camara.myapplication.model.entities.User;
import com.example.camara.myapplication.model.persistence.UserContract;
import com.example.camara.myapplication.util.FormHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Camara on 20/07/2015.
 */
public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    EditText usuario;
    EditText password;
    private User user;


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
                usuario = (EditText) findViewById(R.id.editTextUserName);
                password = (EditText) findViewById(R.id.editTextPassword);

                if(FormHelper.requiredValidade(LoginActivity.this, usuario, password)){
                    if(user == null){
                        user = new User();
                    }
                    user.setUsuario(usuario.getText().toString());
                    user.setPassword(password.getText().toString());
                    if(user.findUSer()){
                        Intent intent = new Intent(LoginActivity.this, ClientListActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, getString(R.string.userInvalid), Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
}
