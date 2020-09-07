package com.example.smartparkingsystem.ui.dashboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartparkingsystem.BookingAdopterClass;
import com.example.smartparkingsystem.Model_Booking;
import com.example.smartparkingsystem.NearByServiceStation;
import com.example.smartparkingsystem.P_Adapter;
import com.example.smartparkingsystem.P_Model;
import com.example.smartparkingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private ProgressDialog mProgressBarsaving;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private BookingAdopterClass bookingAdopterClass;
    private List<Model_Booking> bookingList;
    DatabaseReference mref;
    Query query;
    ArrayList<Model_Booking> dataModels;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {





        // Add the following lines to create RecyclerView

        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mProgressBarsaving = new ProgressDialog(getActivity());
        mProgressBarsaving.setMessage("Loading. . .!");
        mProgressBarsaving.show();
        ///////////////////////////
        // 1. get a reference to recyclerView
        RecyclerView recyclerView = root.findViewById(R.id.list_recycler);

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookingList = new ArrayList<>();
        bookingAdopterClass= new BookingAdopterClass(getActivity(), bookingList);
        recyclerView.setAdapter(bookingAdopterClass);



        query= FirebaseDatabase.getInstance().getReference().child("Bookings").orderByChild("bid");
        query.addListenerForSingleValueEvent(valueEventListener);



        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            bookingList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Model_Booking b_model = snapshot.getValue(Model_Booking.class);
                    bookingList.add(b_model);
                }
                bookingAdopterClass.notifyDataSetChanged();
                mProgressBarsaving.cancel();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}