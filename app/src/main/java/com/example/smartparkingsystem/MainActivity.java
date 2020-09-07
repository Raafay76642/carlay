package com.example.smartparkingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //for checking either the user allready logged in or not
        mAuth = FirebaseAuth.getInstance();
        if( mAuth.getInstance().getCurrentUser()==null){
           Intent intent = new Intent(MainActivity.this, Signup.class);
          startActivity(intent);
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);



    }

    /////////////////////////////////////////////////////
    public void OpenProfile(View view){
        Intent intent=new Intent(this, Profile.class);
        startActivity(intent);};
    public void OpenNearBy(View view){
        Intent intent=new Intent(this, NearBy.class);
        startActivity(intent);};
    public void OpenNearByService(View view){
        Intent intent=new Intent(this, NearByServiceStation.class);
        startActivity(intent);};
    public void OpenNearByWorkshop(View view){
        Intent intent=new Intent(this, NearByWorkshop.class);
        startActivity(intent);};
    public void OpenVehicalinfo(View view){
        Intent intent=new Intent(this, VehicalInfo.class);
        startActivity(intent);};
    public void OpenAbout(View view){
        Intent intent=new Intent(this, About.class);
        startActivity(intent);};
    public void OpenParkingPin(View view){
        Intent intent=new Intent(this, ParkingPin.class);
        startActivity(intent);};
    public void OpenCustomerSupport(View view){
        Intent intent=new Intent(this, CustomerSupport.class);
        startActivity(intent);};




}
