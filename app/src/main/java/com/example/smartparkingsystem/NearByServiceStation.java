package com.example.smartparkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NearByServiceStation extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressDialog mProgressBarsaving;
    private P_Adapter PAdapter;
    private List<P_Model> pList;
    DatabaseReference mref;
    Query query;
    ArrayList<P_Model> dataModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_service_station);
        mProgressBarsaving = new ProgressDialog(NearByServiceStation.this);
        mProgressBarsaving.setMessage("Loading. . .!");
        mProgressBarsaving.show();
        recyclerView = findViewById(R.id.nbservice_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pList = new ArrayList<>();
        PAdapter = new P_Adapter(this, pList);
        recyclerView.setAdapter(PAdapter);
        //for parent activity in booking
        SharedPreferences.Editor editor= getSharedPreferences("parent_booking", MODE_PRIVATE).edit();
        editor.putString("parentbooking","Service");
        editor.apply();



        query= FirebaseDatabase.getInstance().getReference().child("NearBy").child("ServiceStations").orderByChild("p_space");
        query.addListenerForSingleValueEvent(valueEventListener);
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            pList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    P_Model p_model = snapshot.getValue(P_Model.class);
                    pList.add(p_model);
                }
                PAdapter.notifyDataSetChanged();
                mProgressBarsaving.cancel();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}