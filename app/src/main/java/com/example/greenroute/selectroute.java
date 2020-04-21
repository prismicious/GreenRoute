package com.example.greenroute;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class selectroute extends AppCompatActivity {

    String startAddressString;
    String startCityString;

    String destAddressString;
    String destCityString;

    TextView r1_distance;
    TextView r2_distance;
    TextView r3_distance;

    ImageView r1_star1, r1_star2, r1_star3, r1_star4, r1_star5;
    ImageView r2_star1, r2_star2, r2_star3, r2_star4, r2_star5;
    ImageView r3_star1, r3_star2, r3_star3, r3_star4, r3_star5;



    String mergeAddress1;

    ViaPoint[] data;
    ArrayList<String> CoreIds;
    ArrayList<ViaPoint> viaPointAvgList;
    ViaPoint newViaPoint;
    private String mergeAddress2;
    private TextView r1_duration, r2_duration, r3_duration;
    private String probeData;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectroute);


        data = ViaPointData.loadJSONFromAsset(this);
        Log.i("Data", data[14].toString());

        CoreIds = new ArrayList<String>();
        viaPointAvgList = new ArrayList<ViaPoint>();
        for (ViaPoint v : data) {
            if (!CoreIds.contains(v.core_id)) {
                CoreIds.add(v.core_id);
                ArrayList<Integer> pm25s = new ArrayList<>();
                for (ViaPoint v2 : data) {
                    if (v2.core_id.equals(v.core_id)) {
                        if (Integer.parseInt(v2.pm25) < 100) {
                            pm25s.add(Integer.parseInt(v2.pm25));
                        }
                    }
                }

                newViaPoint = v;
                int[] newIntArray = ViaPointData.convertIntegers(pm25s);
                // newViaPoint.pm25 = Double.toString(ViaPointData.findAverageUsingStream(newIntArray));
                viaPointAvgList.add(newViaPoint);
                Log.i("DataAvg", "AVGS => " + newViaPoint.pm25 + " Desc => " + newViaPoint.desc + " LatLng => " + newViaPoint.lat + "," + newViaPoint.lon);


                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    startAddressString = extras.getString("startAddressString");
                    startCityString = extras.getString("startCityString");

                    destAddressString = extras.getString("destAddressString");
                    destCityString = extras.getString("destCityString");

                    mergeAddress1 = startAddressString + ", " + startCityString;
                    mergeAddress2 = destAddressString + ", " + destCityString;
                    Log.i("TextTest", "SELECTROUTE: Start: Address => " + startAddressString + " City => " + startCityString);
                    Log.i("TextTest", "SELECTROUTE: Dest: Address => " + destAddressString + " City => " + destCityString);
                } else {
                    Log.i("TextTest", "No extras found.");
                }
            }



        }


        if(mergeAddress1 == null || mergeAddress1 == "Address, City")
        {
            mergeAddress1 = "Brandevej 8c, Aalborg";
            Log.i("DistanceMatrix","Mergeaddress1 => " + mergeAddress1);
        }
        if(mergeAddress2 == null || mergeAddress2 == "Address, City")
        {
            mergeAddress2 = "Hadsundvej 180a, Aalborg";
            Log.i("DistanceMatrix","Mergeaddress2 => " + mergeAddress2);
        }


        List<Probe> probes = new ArrayList<Probe>();
        AddProbesTolist(probes);

        //Probe data
        String probeLat = viaPointAvgList.get(0).lat;
        String probeLon = viaPointAvgList.get(0).lon;
        probeData = probeLat + "," + probeLon;
        Log.i("DistanceMatrix", probeData);

        String[] origin = {mergeAddress1, probeData};
        String[] destinations = {probeData, mergeAddress2};

        String[] origin2 = {mergeAddress1, probeData};
        String[] destinations2 = {probeData, mergeAddress2};

        String[] origin3 = {mergeAddress1, probeData};
        String[] destinations3 = {probeData, mergeAddress2};

       GetDistanceAndDuration(origin,destinations, 1);
       GetDistanceAndDuration(origin,destinations, 2);
       GetDistanceAndDuration(origin,destinations, 3);
    }

    private void AddProbesTolist(List<Probe> probes) {
        
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    public void GetDistanceAndDuration(String[] origin, String[] destinations, int panelNumber)
    {
        GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyAt_YF-0dcSocGGxznWNBx60sAfEbPUc2Q").build();
        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
        try
        {
                    DistanceMatrix result = req
                            .origins(origin)
                            .destinations(destinations)
                            .mode(TravelMode.WALKING)
                            .await();

            long dist1 = result.rows[0].elements[0].distance.inMeters;
            long dist2 = result.rows[1].elements[1].distance.inMeters;

            long sec1 = result.rows[0].elements[0].duration.inSeconds;
            long sec2 = result.rows[1].elements[1].duration.inSeconds;
            double totalDurationInMin = (sec1 + sec2)/60;
            double totalDistInKM = ((double)dist1+(double)dist2)/1000;
            double formattedDistInKM = Math.floor(totalDistInKM * 100) / 100;
            Log.i("DistanceMatrix", "dist 1 => " + dist1 + " dist2 => " + dist2 + "total KM => " + totalDistInKM);
            Log.i("DistanceMatrix", "Dur 1 => " + sec1 + " Dur 2 => " + sec2 + "total Dur in Min => " + totalDurationInMin);

            handleUI(formattedDistInKM, totalDurationInMin, panelNumber);

        } catch (ApiException e) { e.printStackTrace(); }
          catch (InterruptedException e) { e.printStackTrace(); }
          catch (IOException e) { e.printStackTrace(); }
    }

    public void handleUI(double formattedDistInKM, double totalDurationInMin, int panelNumber)
    {
        switch (panelNumber)
        {
            case 1:
            r1_distance = findViewById(R.id.r1_distancefromAPI);
            r1_distance.setText(String.valueOf(formattedDistInKM));

            r1_duration = findViewById(R.id.r1_ETAfromAPI);
            r1_duration.setText(String.valueOf((int)totalDurationInMin));

            int pm25_1 = Integer.parseInt(viaPointAvgList.get(0).pm25);
            setRating(panelNumber, pm25_1);
                Log.i("Viapointlist", "pm25_1 => " + pm25_1);
            break;
            case 2:
            r2_distance = findViewById(R.id.r2_distancefromAPI);
            r2_distance.setText(String.valueOf(formattedDistInKM));

            r2_duration = findViewById(R.id.r2_ETAfromAPI);
            r2_duration.setText(String.valueOf((int)totalDurationInMin));

            int pm25_2 = Integer.parseInt(viaPointAvgList.get(1).pm25);
            setRating(panelNumber, pm25_2);
                Log.i("Viapointlist", "pm25_2 => " + pm25_2);
            break;

            case 3:
            r3_distance = findViewById(R.id.r3_distancefromAPI);
            r3_distance.setText(String.valueOf(formattedDistInKM));

            r3_duration = findViewById(R.id.r3_ETAfromAPI);
            r3_duration.setText(String.valueOf((int)totalDurationInMin));

            int pm25_3 = Integer.parseInt(viaPointAvgList.get(2).pm25);
            setRating(panelNumber, pm25_3);
                Log.i("Viapointlist", "pm25_3 => " + pm25_3);
            break;
        }

    }

    public void setRating(int panelNumber, int pm25)
    {
        switch (panelNumber)
        {
            case 1:
                r1_star2 = findViewById(R.id.r1_star2);
                r1_star3 = findViewById(R.id.r1_star3);
                r1_star4 = findViewById(R.id.r1_star4);
                r1_star5 = findViewById(R.id.r1_star5);
                if (pm25 > 25)
                {
                    r1_star2.setImageAlpha(0);
                    r1_star3.setImageAlpha(0);
                    r1_star4.setImageAlpha(0);
                    r1_star5.setImageAlpha(0);
                }
                else if (pm25 > 12)
                {
                    Log.i("Viapointlist", "Do we reach this?");
                    r1_star4.setImageAlpha(0);
                    r1_star5.setImageAlpha(0);
                }
                break;
            case 2:
                r2_star2 = findViewById(R.id.r2_star2);
                r2_star3 = findViewById(R.id.r2_star3);
                r2_star4 = findViewById(R.id.r2_star4);
                r2_star5 = findViewById(R.id.r2_star5);
                if (pm25 > 25)
                {
                    r2_star2.setImageAlpha(0);
                    r2_star3.setImageAlpha(0);
                    r2_star4.setImageAlpha(0);
                    r2_star5.setImageAlpha(0);

                }
                else if (pm25 > 12)
                {
                    Log.i("Viapointlist", "Do we reach this?");
                    r2_star4.setImageAlpha(0);
                    r2_star5.setImageAlpha(0);
                }
                break;

            case 3:
                r3_star3 = findViewById(R.id.r3_star3);
                r3_star3 = findViewById(R.id.r3_star3);
                r3_star4 = findViewById(R.id.r3_star4);
                r3_star5 = findViewById(R.id.r3_star5);
                if (pm25 > 25)
                {
                    r3_star2.setImageAlpha(0);
                    r3_star3.setImageAlpha(0);
                    r3_star4.setImageAlpha(0);
                    r3_star5.setImageAlpha(0);

                }
                else if (pm25 > 12)
                {
                    Log.i("Viapointlist", "Do we reach this?");
                    r3_star4.setImageAlpha(0);
                    r3_star5.setImageAlpha(0);
                }
                break;
        }

    }


    public void LaunchRoute1(View view)
    {
        String formattedUrl = "https://www.google.com/maps/dir/?api=1&" + "origin=" + mergeAddress1 + "&destination=" + mergeAddress2 + "&waypoints=" + probeData + "&travelmode=walking";
        Log.i("DistanceMatrix", "URL => " + formattedUrl);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(formattedUrl));
        startActivity(intent);
    }

    public void onButtonShowPopupWindowClick(View view) {


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.info_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

}
