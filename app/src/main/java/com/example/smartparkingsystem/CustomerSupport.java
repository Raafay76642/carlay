package com.example.smartparkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class CustomerSupport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_support);
    }
    public void openparkingpin(View view)
    {
        String mobileNumber = "+923157809850";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL); // Action for what intent called for
        intent.setData(Uri.parse("tel: " + mobileNumber)); // Data with intent respective action on intent
        startActivity(intent);
    }
    public void openfindmycar(View view)
    {
        try {
            String headerReceiver = "Hi KarLay team";// Replace with your message.
            String bodyMessageFormal = "";// Replace with your message.
            String whatsappContain = headerReceiver + bodyMessageFormal;
            String trimToNumner = "+910000000000"; //10 digit number
            Intent intent = new Intent ( Intent.ACTION_VIEW );
            intent.setData ( Uri.parse ( "https://wa.me/" + trimToNumner + "/?text=" + "" ) );
            startActivity ( intent );
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}