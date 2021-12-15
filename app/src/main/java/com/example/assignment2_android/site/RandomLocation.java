package com.example.assignment2_android.site;

import android.location.Location;

import java.util.Random;

// Get random location in the map (random lat and lng)
public class RandomLocation {
    public double[] getRandomLocation (double x0,double y0, int radius){
        Random random = new Random();
        // Convert radius from meters to degrees

        double radiusInDegrees = radius / 111000f;


            double u = random.nextDouble();
            double v = random.nextDouble();
            double w = radiusInDegrees * Math.sqrt(u);
            double t = 2 * Math.PI * v;
            double x = w * Math.cos(t);
            double y = w * Math.sin(t);

            double new_x = x / Math.cos(Math.toRadians(y0));
            double new_y = y / Math.sin(Math.toRadians(x0));

            double foundLatitude = new_y + y0;
            double foundLongitude = new_x + x0;


            // Set the adjusted location
        return new double[]{foundLatitude, foundLongitude};
    }

}
