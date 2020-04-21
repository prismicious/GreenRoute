package com.example.greenroute;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Routefinder extends AppCompatActivity {

    EditText addressText;
    EditText addressText2;
    String startAddressString;
    String startCityString;
    String destAddressString;
    String destCityString;


    ViaPoint[] data;
    ArrayList<String> CoreIds;
    ArrayList<ViaPoint> viaPointAvgList;
    ViaPoint newViaPoint;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routefinder);

    }



    public void testIntent(View view)
    {
        addressText = findViewById(R.id.routefinder_address);
        addressText2 = findViewById(R.id.routefinder_address2);
        String address = addressText.getText().toString();
        String[] startAddressArray = address.split(",");
        startAddressString = startAddressArray[0];
        startCityString = startAddressArray[1];
        Log.i("TextTest", "ROUTEFINDER: Start: Address => " + startAddressString + " City => " + startCityString);

        String address2 = addressText2.getText().toString();
        String[] destAddressArray = address2.split(",");
        destAddressString = destAddressArray[0];
        destCityString = destAddressArray[1];
        Log.i("TextTest", "ROUTEFINDER: Dest: Address => " + destAddressString + " City => " + destCityString);

            Intent i = new Intent(Routefinder.this, selectroute.class);
            i.putExtra("startAddressString", startAddressString);
            i.putExtra("startCityString", startCityString);
            i.putExtra("destAddressString", destAddressString);
            i.putExtra("destCityString", destCityString);
            Routefinder.this.startActivity(i);
    }
}
