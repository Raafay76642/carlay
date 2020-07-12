package com.example.smartparkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Signup extends AppCompatActivity {
    private EditText editTextMobile;
    private Button cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        cont = findViewById(R.id.buttonContinue);

        editTextMobile = findViewById(R.id.phoneno);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobileno = editTextMobile.getText().toString();

                if(mobileno.isEmpty() || mobileno.length() < 10){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }

                Intent intent = new Intent(Signup.this, VerifyCode.class);
                intent.putExtra("mobile", mobileno);
                startActivity(intent);
            }
        });
    }
}