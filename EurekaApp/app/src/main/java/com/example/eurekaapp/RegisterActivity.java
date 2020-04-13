package com.example.eurekaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    TextView tvLoginHere;
    EditText etFirstName, etLastName, etRegisterEmail, etNumber, etRegisterPassword;
    CheckBox chkObservation, chkPositive;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstName = (EditText) findViewById(R.id.firstNameField);
        etLastName = (EditText) findViewById(R.id.lastNameField);
        etRegisterEmail = (EditText) findViewById(R.id.registerEmailField);
        etNumber = (EditText) findViewById(R.id.numberField);
        etRegisterPassword = (EditText) findViewById(R.id.registerPasswordField);
        chkObservation = (CheckBox) findViewById(R.id.observationField);
        chkPositive = (CheckBox) findViewById(R.id.positiveCheck);
        btnRegister = (Button) findViewById(R.id.registerButton);
        tvLoginHere = (TextView) findViewById(R.id.loginHereButton);

        tvLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
