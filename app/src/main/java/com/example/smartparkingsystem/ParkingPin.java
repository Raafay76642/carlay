package com.example.smartparkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ParkingPin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_pin);
    }
    public void openparkingpin(View view)
    {
        Intent i = new Intent(this,DropLocation.class);
        startActivity(i);
    }
    public void openfindmycar(View view)
    {
        String ParentActivity = "ParkingPin";
        Intent intent = new Intent(this, Navigation.class);
        intent.putExtra("Parent_Activity", ParentActivity);
        startActivity(intent);
    }
}