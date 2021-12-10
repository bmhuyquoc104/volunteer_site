package com.example.assignment2_android.site;

import com.example.assignment2_android.model.VolunteerSite;

import java.util.Comparator;

public class DistanceSorter implements Comparator<VolunteerSite> {
    @Override
    public int compare(VolunteerSite o1, VolunteerSite o2) {

        return Double.compare(o1.getDistanceFromCurrentLocation(),(o2.getDistanceFromCurrentLocation()));
    }
}
