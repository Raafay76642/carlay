package com.example.smartparkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BookingMain extends AppCompatActivity {

    String key,longi,lati;
    TextView tbooknow,tnv;
    SharedPreferences readData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_main); Intent intent= getIntent();

        key=intent.getStringExtra("key");
        longi=intent.getStringExtra("longi");
        lati=intent.getStringExtra("lati");
        tbooknow=findViewById(R.id.tbooknowmain);
        tbooknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openbooknow();
            }
        });
        tnv=findViewById(R.id.tnav);
        tnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_navigate();
            }
        });

    }
    public void openbooknow(){
        readData = getSharedPreferences("parent_booking", MODE_PRIVATE);
        String parentbookinglocale = readData.getString("parentbooking", "");
        if (parentbookinglocale.equals("parking")){
        Intent intent= new Intent(this,BookingForm.class);
        intent.putExtra("key",key);
        startActivity(intent);}
        else if(parentbookinglocale.equals("Service")){
            Intent intent= new Intent(this,BookingFormServiceStation.class);
            intent.putExtra("key",key);
            startActivity(intent);
        }
        else if(parentbookinglocale.equals("workshop")){
            Intent intent= new Intent(this,BookingFormWorkshop.class);
            intent.putExtra("key",key);
            startActivity(intent);
        }
    }
    public void open_navigate(){
        Intent intent= new Intent(this,Navigation.class);
        intent.putExtra("Parent_Activity","Booking");
        intent.putExtra("longi",longi);
        intent.putExtra("lati",lati);
        startActivity(intent);
    }
}