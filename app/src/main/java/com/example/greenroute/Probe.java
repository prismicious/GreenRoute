package com.example.greenroute;

public class Probe {

    int index;
    String description;
    String location;
    int pmAverage;
    Double distance;
    Double duration;

    public Probe(int index, String description, String location, int pmAverage) {
        this.index = index;
        this.description = description;
        this.location = location;
        this.pmAverage = pmAverage;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getDuration() {
        return duration;
    }

    public int getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getPmAverage() {
        return pmAverage;
    }
}
