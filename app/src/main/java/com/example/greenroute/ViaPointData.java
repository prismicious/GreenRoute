package com.example.greenroute;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViaPointData
{


    @RequiresApi(api = Build.VERSION_CODES.O)


    public static ViaPoint[] loadJSONFromAsset(Context context) {
        String json = null;
        ViaPoint[] values = null;
        try {
            Gson gson = new Gson();
            InputStream is = context.getAssets().open("probeData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
             values = gson.fromJson(json, ViaPoint[].class);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return values;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static double findAverageUsingStream(int[] array) {
        return Arrays.stream(array).average().orElse(Double.NaN);
    }

    public static int[] convertIntegers(ArrayList<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }

}
