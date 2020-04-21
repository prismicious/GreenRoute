package com.example.greenroute;

public class ViaPoint {

    public String id;
    public String core_id;
    public String serial;
    public String fw_version;
    public String start;
    public String end;
    public String loc_id;
    public String lat;
    public String lon;
    public String desc;
    public String record_date;
    public String battery_level;
    public String rain_median;
    public String rain_avg;
    public String rain_max;
    public String rain_min;
    public String noise_median;
    public String noise_avg;
    public String noise_max;
    public String noise_min;
    public String luminosity;
    public String temperature;
    public String humidity;
    public String pm10;
    public String pm25;
    public String no2;
    public String co;
    public String pressure_;

    @Override
    public String toString() {
        return "ViaPoint{" +
                "id='" + id + '\'' +
                ", core_id='" + core_id + '\'' +
                ", serial='" + serial + '\'' +
                ", fw_version='" + fw_version + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", loc_id='" + loc_id + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", desc='" + desc + '\'' +
                ", record_date='" + record_date + '\'' +
                ", battery_level='" + battery_level + '\'' +
                ", rain_median='" + rain_median + '\'' +
                ", rain_avg='" + rain_avg + '\'' +
                ", rain_max='" + rain_max + '\'' +
                ", rain_min='" + rain_min + '\'' +
                ", noise_median='" + noise_median + '\'' +
                ", noise_avg='" + noise_avg + '\'' +
                ", noise_max='" + noise_max + '\'' +
                ", noise_min='" + noise_min + '\'' +
                ", luminosity='" + luminosity + '\'' +
                ", temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pm10='" + pm10 + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", no2='" + no2 + '\'' +
                ", co='" + co + '\'' +
                ", pressure_='" + pressure_ + '\'' +
                '}';
    }
}