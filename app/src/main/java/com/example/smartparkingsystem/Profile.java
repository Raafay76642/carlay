package com.example.smartparkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    EditText name,age,licenseNo;
    String sname,sage,slicenseNo;

    private ProgressDialog mProgressBarsaving;
    DatabaseReference databaseProfileRef;
    FirebaseDatabase database;
    String id;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        //progressbar
        mProgressBarsaving = new ProgressDialog(Profile.this);
        //database
        database =FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        databaseProfileRef = database.getReference("Users");
        id =firebaseAuth.getCurrentUser().getUid();
        //For gatting user data
        getdata();
        //Getting Inputs from Xml
        name=findViewById(R.id.Uname);
        age=findViewById(R.id.Uage);
        licenseNo=findViewById(R.id.ULicence);



    }


    public void UpdateData(View  view)
    {
        slicenseNo=licenseNo.getText().toString();
        sage=age.getText().toString();
        sname=name.getText().toString();
        mProgressBarsaving.setMessage("Saving. . .!");
       mProgressBarsaving.show();

        profileModelClass ProfileModelCalss = new profileModelClass(sname,sage,slicenseNo);

        databaseProfileRef.child(id).child("Profile").setValue(ProfileModelCalss).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                mProgressBarsaving.cancel();
                final Toast toast = Toast.makeText(Profile.this, "Data is Saved", Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }
    public void getdata(){

        mProgressBarsaving.setMessage("Loading. . .!");
        mProgressBarsaving.show();
        databaseProfileRef.child(id).child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    profileModelClass model_class=dataSnapshot.getValue(profileModelClass.class);
                    name.setText(model_class.getName());
                    age.setText(model_class.getAge());
                    licenseNo.setText(model_class.getLno());
                    mProgressBarsaving.cancel();
                }
                else {
                    Toast.makeText(Profile.this, "Please Complete your Profile", Toast.LENGTH_SHORT).show();
                    mProgressBarsaving.cancel();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mProgressBarsaving.cancel();
            }
        });}}