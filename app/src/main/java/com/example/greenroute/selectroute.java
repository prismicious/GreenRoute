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
import java.util.Collections;
import java.util.Comparator;
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
    ArrayList durations = new ArrayList<Double>();

    List<Probe> probes;
    String mergeAddress1;
    int index = 0;
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


        if(mergeAddress1 == null || mergeAddress1 == "")
        {
            mergeAddress1 = "Brandevej 8c, Aalborg";
            Log.i("DistanceMatrix","Mergeaddress1 => " + mergeAddress1);
        }
        if(mergeAddress2 == null || mergeAddress2 == "")
        {
            mergeAddress2 = "Mispelvej 51, 9000 Aalborg";
            Log.i("DistanceMatrix","Mergeaddress2 => " + mergeAddress2);
        }


        probes = new ArrayList<Probe>();
        AddProbesTolist(probes);

        //Probe data
        String probeLat = viaPointAvgList.get(0).lat;
        String probeLon = viaPointAvgList.get(0).lon;
        probeData = probeLat + "," + probeLon;
        Log.i("DistanceMatrix", probeData);
        Log.i("ProbeList","Index 0 location => " + probes.get(0).getLocation());


        String[] origin = {mergeAddress1, probes.get(0).getLocation()};
        String[] destinations = {probes.get(0).getLocation(), mergeAddress2};

        String[] origin2 = {mergeAddress1, probes.get(1).getLocation()};
        String[] destinations2 = {probes.get(1).getLocation(), mergeAddress2};

        String[] origin3 = {mergeAddress1, probes.get(2).getLocation()};
        String[] destinations3 = {probes.get(2).getLocation(), mergeAddress2};

        String[] origin4 = {mergeAddress1, probes.get(3).getLocation()};
        String[] destinations4 = {probes.get(3).getLocation(), mergeAddress2};

        String[] origin5 = {mergeAddress1, probes.get(4).getLocation()};
        String[] destinations5 = {probes.get(4).getLocation(), mergeAddress2};

        String[] origin6 = {mergeAddress1, probes.get(5).getLocation()};
        String[] destinations6 = {probes.get(5).getLocation(), mergeAddress2};

        String[] origin7 = {mergeAddress1, probes.get(6).getLocation()};
        String[] destinations7 = {probes.get(6).getLocation(), mergeAddress2};

        String[] origin8 = {mergeAddress1, probes.get(7).getLocation()};
        String[] destinations8 = {probes.get(7).getLocation(), mergeAddress2};

        String[] origin9 = {mergeAddress1, probes.get(8).getLocation()};
        String[] destinations9 = {probes.get(8).getLocation(), mergeAddress2};

        String[] origin10 = {mergeAddress1, probes.get(9).getLocation()};
        String[] destinations10 = {probes.get(9).getLocation(), mergeAddress2};

        String[] origin11 = {mergeAddress1, probes.get(10).getLocation()};
        String[] destinations11 = {probes.get(10).getLocation(), mergeAddress2};

        String[] origin12 = {mergeAddress1, probes.get(11).getLocation()};
        String[] destinations12 = {probes.get(11).getLocation(), mergeAddress2};

       GetDistanceAndDuration(origin,destinations,0);
       GetDistanceAndDuration(origin2,destinations2,1);
       GetDistanceAndDuration(origin3,destinations3,0);
       GetDistanceAndDuration(origin4,destinations4,2);
       GetDistanceAndDuration(origin5,destinations5,0);
       GetDistanceAndDuration(origin6,destinations6,0);
       GetDistanceAndDuration(origin7,destinations7,0);
       GetDistanceAndDuration(origin8,destinations8,0);
       GetDistanceAndDuration(origin9,destinations9,0);
       GetDistanceAndDuration(origin10,destinations10,3);
       GetDistanceAndDuration(origin11,destinations11,0);
       GetDistanceAndDuration(origin12,destinations12,0);

       probes.sort((o1, o2) -> o1.getDistance().compareTo(o2.getDistance()));
       handleUI();
        for(int i=0;i<probes.size();i++)
        {
            Log.i("Calculations" , "(" + i + ") Probe " + probes.get(i).getIndex() + " (distance) " + probes.get(i).getDistance().toString() + "km (duration) " + probes.get(i).getDuration().toString() + " min");
        }
    }

    private void AddProbesTolist(List<Probe> probes) {
        Probe probe1 = new Probe(24, "Vestre Fjordvej/Kastetvej", "57.0557,9.8925", 10);
        Probe probe2 = new Probe(29, "Kong Christians Allé/Hasserisgade", "57.0454,9.9036", 12);
        Probe probe3 = new Probe(23, "Peder Skrams Gade/Olfert Fischers Gade", "57.0559,9.9015", 14);
        Probe probe4 = new Probe(26, "Vestbyen station", "57.0256,9.9089", 14);
        Probe probe5 = new Probe(15, "Studentgroup?", "57.0435,9.9202", 9);
        Probe probe6 = new Probe(14, "Vesterbro/Bispensgade", "57.0066639,9.701360", 25);
        Probe probe7 = new Probe(31, "Vesterbro/Limfjordsbroen", "57.0525,9.9176", 18);
        Probe probe8 = new Probe(12, "Nytorv midtfor Salling", "57.0483,9.9229", 13);
        Probe probe9 = new Probe(13, "CREATE", "57.048,9.9298", 9);
        Probe probe10 = new Probe(30, "DCE Baggrundstation 8158", "57.0466,9.9308", 8);
        Probe probe11 = new Probe(11, "Jyllandsgade/Sønderbro", "57.0424,9.9307", 12);
        Probe probe12 = new Probe(25, "Nyhavnsgade", "57.0468,9.9395", 10);

        probes.add(probe1);
        probes.add(probe2);
        probes.add(probe3);
        probes.add(probe4);
        probes.add(probe5);
        probes.add(probe6);
        probes.add(probe7);
        probes.add(probe8);
        probes.add(probe9);
        probes.add(probe10);
        probes.add(probe11);
        probes.add(probe12);

        int testAvg = probes.get(0).getPmAverage();

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
            //Calculates distance and time between A (start) -> B (probe) -> C (destination)
            //Calculate Distance between A and B
            long dist1 = result.rows[0].elements[0].distance.inMeters;
            //Calculate Distance between B and C
            long dist2 = result.rows[1].elements[1].distance.inMeters;
            //Calculate time between A and B
            long sec1 = result.rows[0].elements[0].duration.inSeconds;
            //Calculate time between B and C
            long sec2 = result.rows[1].elements[1].duration.inSeconds;
            //Convert to Min from seconds
            double totalDurationInMin = (sec1 + sec2)/60;
            //Convert to km and add dist from A => B and B => C for total route length
            double totalDistInKM = ((double)dist1+(double)dist2)/1000;
            double formattedDistInKM = Math.floor(totalDistInKM * 100) / 100;
            probes.get(index).setDistance(formattedDistInKM);
            probes.get(index).setDuration(totalDurationInMin);
            durations.add(totalDistInKM);
            index++;
            //Print in format #.##
            Log.i("DistanceMatrix", "dist 1 => " + dist1 + " dist2 => " + dist2 + "total KM => " + totalDistInKM);
            Log.i("DistanceMatrix", "Dur 1 => " + sec1 + " Dur 2 => " + sec2 + "total Dur in Min => " + totalDurationInMin);
            //int panelNumber = 1;
        } catch (ApiException e) { e.printStackTrace(); }
          catch (InterruptedException e) { e.printStackTrace(); }
          catch (IOException e) { e.printStackTrace(); }
    }

    public void handleUI()
    {

        // Panel 1 UI
            r1_distance = findViewById(R.id.r1_distancefromAPI);
            r1_distance.setText(String.valueOf(probes.get(0).getDistance()));

            r1_duration = findViewById(R.id.r1_ETAfromAPI);
            r1_duration.setText(String.valueOf(probes.get(0).getDuration().intValue()));

            int pm25_1 = probes.get(0).getPmAverage();
            setRating(1, pm25_1);
                Log.i("Viapointlist", "pm25_1 => " + pm25_1);

                //Panel 2 UI
            r2_distance = findViewById(R.id.r2_distancefromAPI);
            r2_distance.setText(String.valueOf(probes.get(1).getDistance()));

            r2_duration = findViewById(R.id.r2_ETAfromAPI);
            r2_duration.setText(String.valueOf(probes.get(1).getDuration().intValue()));

            int pm25_2 = probes.get(1).getPmAverage();
            setRating(2, pm25_2);
                Log.i("Viapointlist", "pm25_2 => " + pm25_2);

                //Panel 3 UI
            r3_distance = findViewById(R.id.r3_distancefromAPI);
            r3_distance.setText(String.valueOf(probes.get(2).getDistance()));

            r3_duration = findViewById(R.id.r3_ETAfromAPI);
            r3_duration.setText(String.valueOf(probes.get(2).getDuration().intValue()));

            int pm25_3 = probes.get(2).getPmAverage();
            setRating(3, pm25_3);
                Log.i("Viapointlist", "pm25_3 => " + pm25_3);

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
                if (pm25 > 18)
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
        String formattedUrl = "https://www.google.com/maps/dir/?api=1&"
                + "origin="
                + mergeAddress1
                + "&destination="
                + mergeAddress2
                + "&waypoints="
                + probes.get(0).getLocation()
                + "&travelmode=walking";
        Log.i("DistanceMatrix", "URL => " + formattedUrl);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(formattedUrl));
        startActivity(intent);
    }

    public void LaunchRoute2(View view)
    {
        String formattedUrl = "https://www.google.com/maps/dir/?api=1&" + "origin=" + mergeAddress1 + "&destination=" + mergeAddress2 + "&waypoints=" + probes.get(1).getLocation() + "&travelmode=walking";
        Log.i("DistanceMatrix", "URL => " + formattedUrl);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(formattedUrl));
        startActivity(intent);
    }

    public void LaunchRoute3(View view)
    {
        String formattedUrl = "https://www.google.com/maps/dir/?api=1&" + "origin=" + mergeAddress1 + "&destination=" + mergeAddress2 + "&waypoints=" + probes.get(2).getLocation() + "&travelmode=walking";
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
