package com.example.smartparkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VehicalInfo extends AppCompatActivity {

    EditText vehicalinfo;
    Spinner type;
    String svehicalinfo,stype;
    private ProgressDialog mProgressBarsaving;
    DatabaseReference databaseProfileRef;
    FirebaseDatabase database;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehical_info);
        getSupportActionBar().hide();
        vehicalinfo =findViewById(R.id.VehicalNo);
        type = findViewById(R.id.SpinnrVehicalType);
        mProgressBarsaving = new ProgressDialog(VehicalInfo.this);
        //database
        database = FirebaseDatabase.getInstance();
        databaseProfileRef = database.getReference("Users");
        id = "U12mu2QQAeUP6tx8XxLbTsW3rKX2";


        getdata();

    }
    public void UpdateData(View view)
    {

        stype=type.getSelectedItem().toString();
        svehicalinfo= vehicalinfo.getText().toString();
        mProgressBarsaving.setMessage("Saving. . .!");
        mProgressBarsaving.show();

        VehicalInfoModelClass vehicalInfoModelClass = new VehicalInfoModelClass(stype,svehicalinfo);

        databaseProfileRef.child(id).child("VehicalInfo").setValue(vehicalInfoModelClass).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                mProgressBarsaving.cancel();
                final Toast toast = Toast.makeText(VehicalInfo.this, "Data is Saved", Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }


    public void getdata(){

        mProgressBarsaving.setMessage("Loading. . .!");
        mProgressBarsaving.show();
        databaseProfileRef.child(id).child("VehicalInfo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    VehicalInfoModelClass vehicalInfoModelClass=dataSnapshot.getValue(VehicalInfoModelClass.class);
                    vehicalinfo.setText(vehicalInfoModelClass.getRegNo());
                    mProgressBarsaving.cancel();
                }
                else {
                    Toast.makeText(VehicalInfo.this, "Please Complete your Profile", Toast.LENGTH_SHORT).show();
                    mProgressBarsaving.cancel();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mProgressBarsaving.cancel();
            }
        });}
}