package com.example.smartparkingsystem.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartparkingsystem.About;
import com.example.smartparkingsystem.MainActivity;
import com.example.smartparkingsystem.Profile;
import com.example.smartparkingsystem.R;
import com.example.smartparkingsystem.SplashScreen;
import com.example.smartparkingsystem.VehicalInfo;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Layout layout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    public void OpenAbout(View view)
    {

        Intent intent=new Intent(getActivity(), About.class);
        startActivity(intent);
    }
    public void OpenVehicalinfo(View view)
    {

        Intent intent=new Intent(getActivity(), VehicalInfo.class);
        startActivity(intent);
    }
}