package com.example.smartparkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookingFormServiceStation extends AppCompatActivity {


    Button makebooking;
    EditText hoursE;
    String key;
    DatabaseReference mgetname,mgetpname,mbooking;
    FirebaseAuth firebaseAuth;
    String uid;
    String price;
    String hour;
    EditText estime;
    String stime;
    String pname,coname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form_service_station);
        firebaseAuth=FirebaseAuth.getInstance();
        uid=firebaseAuth.getCurrentUser().getUid();
        hoursE = findViewById(R.id.estime);
        estime=findViewById(R.id.H);
        Intent intent= getIntent();
        key=intent.getStringExtra("key");
        getpname();
        getuname();
        makebooking = findViewById(R.id.makebooking);
        makebooking.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                final Dialog dialog = new Dialog(BookingFormServiceStation.this);
                dialog.setContentView(R.layout.billdialouge);
                Button dialogButton = (Button) dialog.findViewById(R.id.applydioloudefilter);
                TextView Bill = (TextView) dialog.findViewById(R.id.Bill);
                price= String.valueOf((500)+50) ;
                Bill.setText(""+price);
                book();
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    public void book(){
        mbooking=FirebaseDatabase.getInstance().getReference("Bookings");
        String bid=mbooking.push().getKey();
        hour=hoursE.getText().toString();
        stime=estime.getText().toString();
        Model_Booking model_booking= new Model_Booking(key,uid,price,hour,stime,bid,coname,pname);
        mbooking.child(bid).setValue(model_booking).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(BookingFormServiceStation.this, "Successfully Booked", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getpname(){
        mgetpname=FirebaseDatabase.getInstance().getReference("NearBy");
        mgetpname.child("ServiceStations").child(key).child("p_name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    pname=dataSnapshot.getValue(String.class);
                    Toast.makeText(BookingFormServiceStation.this, ""+pname, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
            //

        });
    }
    public void getuname(){
        mgetname=FirebaseDatabase.getInstance().getReference("Users");
        mgetname.child(uid).child("Profile").child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    coname=dataSnapshot.getValue(String.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}