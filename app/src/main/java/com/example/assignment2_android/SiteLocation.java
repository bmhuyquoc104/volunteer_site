package com.example.assignment2_android;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.assignment2_android.model.VolunteerSite;
import com.example.assignment2_android.site.RandomLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.assignment2_android.databinding.ActivitySiteLocationBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SiteLocation extends FragmentActivity implements OnMapReadyCallback {
    VolunteerSite volunteerSite;
    RandomLocation randomLocations;
    private ArrayList<VolunteerSite> randomSitesList;
    private GoogleMap mMap;
    private ActivitySiteLocationBinding binding;
    private Geocoder geocoder ;
    private FusedLocationProviderClient client;
    private LocationRequest locationRequest;
    Circle locationCircle;
    ImageView currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySiteLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currentLocation = (ImageView) findViewById(R.id.currentLocation);
        client = LocationServices.getFusedLocationProviderClient(this);
        randomLocations = new RandomLocation();
        randomSitesList = new ArrayList<>();
        volunteerSite = new VolunteerSite(106.660172,10.762622);
        geocoder = new Geocoder(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (int i = 0; i < 40; i++){
            double [] result = randomLocations.getRandomLocation(volunteerSite.getLat(),volunteerSite.getLng(),5000);
            VolunteerSite temp = new VolunteerSite(result[0],result[1]) ;
            randomSitesList.add(temp);

        }
        System.out.println("huy ne !!!!!" + randomSitesList + "\n");
        for (int i = 0; i < randomSitesList.size(); i++){
            double lat = randomSitesList.get(i).getLat();
            double lng = randomSitesList.get(i).getLng();
            LatLng site = new LatLng(lat,lng);
            List<Address> addresses =
                    null;
            try {
                addresses = geocoder.getFromLocation(lat,lng,1);
                Address address = addresses.get(0);
                String locationName = address.getAddressLine(0);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                @SuppressLint("UseCompatLoadingForDrawables")
                BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.volunteer_site2);
                Bitmap b = bitmapDrawable.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                mMap.addMarker(new MarkerOptions().position(site).title(locationName)
                                                  .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site,14));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("lat + lng :" + lat + " :" + lng);
            System.out.println(site.toString());
        }

        // Add a marker in Sydney and move the camera
    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        Task<Location> locationTask = client.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng currentLocation =
                        new LatLng(location.getLatitude(), location.getLongitude());
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16));
                mMap.getUiSettings().setZoomControlsEnabled(true);
            }
        });
    }

    public void handleCurrentLocation(View view) {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                    , 44);
        }
    }



    private void getUpdateLocation() {
        locationRequest = LocationRequest.create()
                .setInterval(4000)
                .setFastestInterval(2000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44 ){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   getLocation();

            }
            else {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
}