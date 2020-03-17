package com.example.greenroute;

public class Probe {
    String id;
    int type;
    double location;
    double latitude;
    double longitude;
    int roles;
    String permissions;
    String tags;

    @Override
    public String toString() {
        return "Probe{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", location=" + location +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", roles=" + roles +
                ", permissions='" + permissions + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
