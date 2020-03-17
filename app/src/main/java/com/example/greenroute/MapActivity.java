package com.example.greenroute;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.greenroute.directionhelpers.FetchURL;
import com.example.greenroute.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.example.greenroute.directionhelpers.FetchURL;
import com.example.greenroute.directionhelpers.TaskLoadedCallback;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private GoogleMap mMap;
    private MarkerOptions place1, place2, place3;
    Button getDirection;
    private Polyline currentPolyline;
    public String url = "https://api.cityflow.live/users/login";
    public String devicesURL = "https://api.cityflow.live/devices";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        getDirection = findViewById(R.id.btnGetDirection);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchURL(MapActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "walking"), "walking");
            }
        });
        //27.658143,85.3199503
        //27.667491,85.3208583
        place1 = new MarkerOptions().position(new LatLng(57.012346, 9.993241)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(57.022170, 9.876142)).title("Location 2");
        place3 = new MarkerOptions().position(new LatLng(57.029320, 9.898425)).title("Location 3");
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);


        try {
            run();
            run2();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }




    void run() throws IOException {

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n\t\"email\": \"mikkel.sahlholdt@gmail.com\",\n\t\"password\": \"test123\"\n}");

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                MapActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("API ", myResponse);
                        Gson gson = new Gson();
                        ResponseParams params = gson.fromJson(myResponse, ResponseParams.class);
                        Log.i("API", params.toString());
                    }
                });

            }
        });
    }
    void run2() throws IOException {

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTc2LCJpYXQiOjE1ODQ0MTA5MjYsImV4cCI6MTU4NDQ5NzMyNn0.LQT48wb56xrWggqUHD0_quuAeudztPfd6-2mK2eAvZw";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(devicesURL)
                .get()
                .addHeader("authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                MapActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("API DEVICES", myResponse);
                        Gson gson = new Gson();
                  //      Device device = gson.fromJson(myResponse, Device.class);Log.i("API DEVICES", myResponse);
                      //  Log.i("API DEVICES", device.toString());
                 }
                });

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1);
        mMap.addMarker(place2);
        mMap.addMarker(place3);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        String via = "waypoints=via:57.025948,9.978815";
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + via + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        Log.i("URL", url);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}
