package com.example.assignment2_android;

//import static com.example.assignment2_android.databaseFirestore.SiteLocationDatabase.postSiteLocations;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.assignment2_android.databaseFirestore.SiteLocationDatabase;
import com.example.assignment2_android.model.User;
import com.example.assignment2_android.model.VolunteerSite;
import com.example.assignment2_android.site.AddSite;
import com.example.assignment2_android.site.DisplayInfo;
import com.example.assignment2_android.site.DistanceSorter;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.assignment2_android.databinding.ActivitySiteLocationBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SiteLocation extends FragmentActivity implements OnMapReadyCallback {
    VolunteerSite volunteerSite;
    RandomLocation randomLocation;
    FirebaseFirestore db;
    List<User> currentUser;
    List<Marker> markerList;
    List<Double> totalDistance;
    private Geocoder mGeocoder;
    boolean hasData = false;
    private boolean search = false;
    public static ArrayList<VolunteerSite> volunteerSiteList;
    private GoogleMap mMap;
    Marker userMarker;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    String userSelection;
    EditText site;
    ImageView searchSite;
    String[] filterSelection = {"Max Capacity", "Total Current Volunteers", "Total Tested Volunteers", "Location Type", "Distance to your location"};
    ArrayList<VolunteerSite> siteBySearchList;
    private ActivitySiteLocationBinding binding;
    private FusedLocationProviderClient client;
    private LocationRequest locationRequest;
    int userAge;
    List<LatLng> allLatLng;
    String locationName;
    SiteLocationDatabase siteLocationDatabase;
    Circle locationCircle;
    ImageView currentLocation;
    DistanceSorter distanceSorter;
    Button type1, type2, type3, type4;
    Button capacity1, capacity2, capacity3, capacity4;
    Button distance1, distance2, distance3, distance4;
    Button volunteers1, volunteers2, volunteers3, volunteers4;
    Button testedVolunteers1, testedVolunteers2, testedVolunteers3, testedVolunteers4;
    List<String> allEmails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySiteLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        site = findViewById(R.id.userInputSite);
        searchSite = findViewById(R.id.searchSite);
        currentUser = new ArrayList<>();
        totalDistance = new ArrayList<>();
//        currentLocation = (ImageView) findViewById(R.id.currentLocation);
        client = LocationServices.getFusedLocationProviderClient(this);
        randomLocation = new RandomLocation();
        autoCompleteTextView = findViewById(R.id.auto_complete_text_view2);
        siteBySearchList = new ArrayList<>();
        volunteerSiteList = new ArrayList<>();
        markerList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        mGeocoder = new Geocoder(this);
        distanceSorter = new DistanceSorter();
        List<Address> addresses;
        volunteerSite = new VolunteerSite(106.660172, 10.762622);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_site_selection, filterSelection);

        //Binding button for filters
        type1 = findViewById(R.id.typeOption1);
        type2 = findViewById(R.id.typeOption2);
        type3 = findViewById(R.id.typeOption3);
        type4 = findViewById(R.id.typeOption4);

        capacity1 = findViewById(R.id.capacityOption1);
        capacity2 = findViewById(R.id.capacityOption2);
        capacity3 = findViewById(R.id.capacityOption3);
        capacity4 = findViewById(R.id.capacityOption4);

        distance1 = findViewById(R.id.distanceOption1);
        distance2 = findViewById(R.id.distanceOption2);
        distance3 = findViewById(R.id.distanceOption3);
        distance4 = findViewById(R.id.distanceOption4);

        volunteers1 = findViewById(R.id.totalNumberOption1);
        volunteers2 = findViewById(R.id.totalNumberOption2);
        volunteers3 = findViewById(R.id.totalNumberOption3);
        volunteers4 = findViewById(R.id.totalNumberOption4);

        testedVolunteers1 = findViewById(R.id.totalTestedOption1);
        testedVolunteers2 = findViewById(R.id.totalTestedOption2);
        testedVolunteers3 = findViewById(R.id.totalTestedOption3);
        testedVolunteers4 = findViewById(R.id.totalTestedOption4);

        //Set visibility for filter selections to invisible

        capacity1.setVisibility(View.GONE);
        capacity2.setVisibility(View.GONE);
        capacity3.setVisibility(View.GONE);
        capacity4.setVisibility(View.GONE);

        volunteers1.setVisibility(View.GONE);
        volunteers2.setVisibility(View.GONE);
        volunteers3.setVisibility(View.GONE);
        volunteers4.setVisibility(View.GONE);

        type1.setVisibility(View.GONE);
        type2.setVisibility(View.GONE);
        type3.setVisibility(View.GONE);
        type4.setVisibility(View.GONE);

        distance1.setVisibility(View.GONE);
        distance2.setVisibility(View.GONE);
        distance3.setVisibility(View.GONE);
        distance4.setVisibility(View.GONE);

        testedVolunteers1.setVisibility(View.GONE);
        testedVolunteers2.setVisibility(View.GONE);
        testedVolunteers3.setVisibility(View.GONE);
        testedVolunteers4.setVisibility(View.GONE);


        allEmails = MainActivity.userEmails;
        volunteerSiteList = UserGuide.allSites;

        // Set dropdown list
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userSelection = parent.getItemAtPosition(position).toString();
                toggleFilterOptions(userSelection);
            }
        });


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
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        System.out.println("hello huy ne"+ allEmails);

        System.out.println("hello huy again ne" + volunteerSiteList);
        System.out.println("hello huy again cho coi size ne" + volunteerSiteList.size());
        System.out.println();
    //    volunteerSite.generateNewLocation(volunteerSite.getHOCHIMINH(), 25, 8000, volunteerSiteList, randomLocation,allEmails);
//        volunteerSite.generateNewLocation(volunteerSite.getHANOI(), 5, 3000, volunteerSiteList, randomLocation,allEmails);
//        volunteerSite.generateNewLocation(volunteerSite.getDALAT(), 5, 2000, volunteerSiteList, randomLocation,allEmails);

        volunteerSiteList.sort(distanceSorter);
        System.out.println(volunteerSiteList);
        // Post generated site locations to database (only need to use 1 time, can change parameter to push more later)
        //SiteLocationDatabase.postSiteLocations(db, volunteerSiteList, this);


        for (VolunteerSite volunteerSite : volunteerSiteList
        ) {
            double lat = volunteerSite.getLat();
            double lng = volunteerSite.getLng();
            locationName = volunteerSite.getLocationName();
            LatLng site = new LatLng(lat, lng);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            @SuppressLint("UseCompatLoadingForDrawables")
            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
            Bitmap b = bitmapDrawable.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
            MarkerOptions mMarker = new MarkerOptions();
            mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            userMarker = mMap.addMarker(mMarker);
            assert userMarker != null;
            userMarker.setZIndex(1.0f);
            markerList.add(userMarker);
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 1));
//            mMap.getUiSettings().setZoomControlsEnabled(true);
        }


        toggleFilterOptions(userSelection);

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

            }
        });

        // Create new location in the map by click
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {

                User user = new User("Huy", "123", "err", 21, "2131");
                currentUser.add(user);
//                currentUser = LogIn.oneUserlist;

                for (User c : currentUser
                ) {
                    userAge = c.getAge();
                }

                if (userAge < 18) {
                    Toast.makeText(getApplicationContext(), "You must be at least 18 to become a leader.", Toast.LENGTH_LONG).show();
                } else {
                    double lat1 = latLng.latitude;
                    double lng1 = latLng.longitude;
                    try {
                        List<Address> addresses =
                                mGeocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        Address address = addresses.get(0);
                        String locationName = address.getAddressLine(0);
                        @SuppressLint("UseCompatLoadingForDrawables") BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                        Bitmap b = bitmapdraw.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(locationName)
                                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                        );

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(SiteLocation.this, AddSite.class);
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra("lat", Double.toString(lat1));
                    intent.putExtra("lng", Double.toString(lng1));
                    startActivity(intent);
                }
            }
        });

        //Display info by clicking the marker


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                LatLng currentMarker = marker.getPosition();
                double currentLat = currentMarker.latitude;
                double currentLng = currentMarker.longitude;
                for (VolunteerSite volunteerSite : volunteerSiteList
                ) {

                    if (currentLat == volunteerSite.getLat() && currentLng == volunteerSite.getLng()) {
                        locationName = volunteerSite.getLocationName();
                        Intent intent = new Intent(getApplicationContext(), DisplayInfo.class);
                        intent.putExtra("locationName", locationName);
                        startActivity(intent);
                    }
                }
                return false;
            }
        });


        //Search function
        searchSite.setOnClickListener(view -> {

            String inputSite = site.getText().toString();

            for (int i = 0; i < volunteerSiteList.size(); i++) {


                if (inputSite.equals(volunteerSiteList.get(i).getStatus())
                        || inputSite.equals(volunteerSiteList.get(i).getLocationName())
                        || inputSite.equals(volunteerSiteList.get(i).getLeaderName())
                        || inputSite.equals(Double.toString(volunteerSiteList.get(i).getLat()))
                        || inputSite.equals(Double.toString(volunteerSiteList.get(i).getLng()))
                ) {
                    siteBySearchList.add(volunteerSiteList.get(i));
                    hasData = true;


                } else {
                    for (VolunteerSite volunteerSite : volunteerSiteList
                    ) {
                        double lat = volunteerSite.getLat();
                        double lng = volunteerSite.getLng();
                        locationName = volunteerSite.getLocationName();
                        LatLng site = new LatLng(lat, lng);
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                        @SuppressLint("UseCompatLoadingForDrawables")
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                        Bitmap b = bitmapDrawable.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                        MarkerOptions mMarker = new MarkerOptions();
                        mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                        userMarker = mMap.addMarker(mMarker);
                        markerList.add(userMarker);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                    }
                }
                if (hasData) {
                    removeAllMarkers(markerList);
                    for (VolunteerSite volunteerSite : siteBySearchList
                    ) {
                        double lat = volunteerSite.getLat();
                        double lng = volunteerSite.getLng();
                        locationName = volunteerSite.getLocationName();
                        LatLng site = new LatLng(lat, lng);
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                        @SuppressLint("UseCompatLoadingForDrawables")
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                        Bitmap b = bitmapDrawable.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                        MarkerOptions mMarker = new MarkerOptions();
                        mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                        userMarker = mMap.addMarker(mMarker);
                        assert userMarker != null;
                        userMarker.setZIndex(1.0f);
                        markerList.add(userMarker);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                    }

                }
            }


        });


        // return marker if user selection is type1
        type1.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getLocationType().equals("Hospital")) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        type2.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getLocationType().equals("Stadium")) {
                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        type3.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getLocationType().equals("School")) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        type4.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getLocationType().equals("Factory")) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });


        capacity1.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getMaxCapacity() < 15) {
                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        capacity2.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getMaxCapacity() >= 15 && volunteerSite.getMaxCapacity() < 25) {
                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        capacity3.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getMaxCapacity() >= 25 && volunteerSite.getMaxCapacity() < 35) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        capacity4.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getMaxCapacity() > 35) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        volunteers1.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getTotalVolunteers() < 10) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        volunteers2.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getTotalVolunteers() >= 10 && volunteerSite.getTotalVolunteers() < 20) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        volunteers3.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getTotalVolunteers() >= 20 && volunteerSite.getTotalVolunteers() < 30) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        volunteers4.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getTotalVolunteers() > 30) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        testedVolunteers1.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getTotalTestedVolunteers() < 8) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        testedVolunteers2.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getTotalTestedVolunteers() >= 8 && volunteerSite.getTotalTestedVolunteers() < 18) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        testedVolunteers3.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getTotalTestedVolunteers() >= 18 && volunteerSite.getTotalTestedVolunteers() < 28) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        testedVolunteers4.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getTotalTestedVolunteers() > 28) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        distance1.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getDistanceFromCurrentLocation() < 1000) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        distance2.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getDistanceFromCurrentLocation() >= 1000 && volunteerSite.getDistanceFromCurrentLocation() < 2000) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });

        distance3.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getDistanceFromCurrentLocation() >= 2000 && volunteerSite.getDistanceFromCurrentLocation() < 3000) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });
        distance4.setOnClickListener(view -> {
            removeAllMarkers(markerList);
            for (VolunteerSite volunteerSite : volunteerSiteList
            ) {
                if (volunteerSite.getDistanceFromCurrentLocation() > 3000) {

                    double lat = volunteerSite.getLat();
                    double lng = volunteerSite.getLng();
                    locationName = volunteerSite.getLocationName();
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    @SuppressLint("UseCompatLoadingForDrawables")
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.volunteer_site2);
                    Bitmap b = bitmapDrawable.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 250, 200, true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    userMarker.setZIndex(1.0f);
                    markerList.add(userMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 14));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }
        });


    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        Task<Location> locationTask = client.getLastLocation();

        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                for (VolunteerSite volunteerSite : volunteerSiteList) {
                    double lat2 = volunteerSite.getLat();
                    double lng2 = volunteerSite.getLng();
                    volunteerSite.setDistanceFromCurrentLocation(volunteerSite.distance(lat, lng, lat2, lng2));
                    LatLng site = new LatLng(lat, lng);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    MarkerOptions mMarker = new MarkerOptions();
                    mMarker.position(site);
                    userMarker = mMap.addMarker(mMarker);
                    assert userMarker != null;
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(site));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(site, 10));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }
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


    public void removeAllMarkers(List<Marker> markerslist) {
        for (Marker marker : markerslist) {
            marker.setVisible(false);
        }
    }

    public void toggleFilterOptions(String str) {

        if (str == "Max Capacity") {
            capacity1.setVisibility(View.VISIBLE);
            capacity2.setVisibility(View.VISIBLE);
            capacity3.setVisibility(View.VISIBLE);
            capacity4.setVisibility(View.VISIBLE);
            volunteers1.setVisibility(View.GONE);
            volunteers2.setVisibility(View.GONE);
            volunteers3.setVisibility(View.GONE);
            volunteers4.setVisibility(View.GONE);
            type1.setVisibility(View.GONE);
            type2.setVisibility(View.GONE);
            type3.setVisibility(View.GONE);
            type4.setVisibility(View.GONE);
            testedVolunteers1.setVisibility(View.GONE);
            testedVolunteers2.setVisibility(View.GONE);
            testedVolunteers3.setVisibility(View.GONE);
            testedVolunteers4.setVisibility(View.GONE);
            distance1.setVisibility(View.GONE);
            distance2.setVisibility(View.GONE);
            distance3.setVisibility(View.GONE);
            distance4.setVisibility(View.GONE);
        } else if (str == "Total Current Volunteers") {
            volunteers1.setVisibility(View.VISIBLE);
            volunteers2.setVisibility(View.VISIBLE);
            volunteers3.setVisibility(View.VISIBLE);
            volunteers4.setVisibility(View.VISIBLE);
            type1.setVisibility(View.GONE);
            type2.setVisibility(View.GONE);
            type3.setVisibility(View.GONE);
            type4.setVisibility(View.GONE);
            testedVolunteers1.setVisibility(View.GONE);
            testedVolunteers2.setVisibility(View.GONE);
            testedVolunteers3.setVisibility(View.GONE);
            testedVolunteers4.setVisibility(View.GONE);
            distance1.setVisibility(View.GONE);
            distance2.setVisibility(View.GONE);
            distance3.setVisibility(View.GONE);
            distance4.setVisibility(View.GONE);
            capacity1.setVisibility(View.GONE);
            capacity2.setVisibility(View.GONE);
            capacity3.setVisibility(View.GONE);
            capacity4.setVisibility(View.GONE);
        } else if (str == "Location Type") {
            type1.setVisibility(View.VISIBLE);
            type2.setVisibility(View.VISIBLE);
            type3.setVisibility(View.VISIBLE);
            type4.setVisibility(View.VISIBLE);
            testedVolunteers1.setVisibility(View.GONE);
            testedVolunteers2.setVisibility(View.GONE);
            testedVolunteers3.setVisibility(View.GONE);
            testedVolunteers4.setVisibility(View.GONE);
            distance1.setVisibility(View.GONE);
            distance2.setVisibility(View.GONE);
            distance3.setVisibility(View.GONE);
            distance4.setVisibility(View.GONE);
            capacity1.setVisibility(View.GONE);
            capacity2.setVisibility(View.GONE);
            capacity3.setVisibility(View.GONE);
            capacity4.setVisibility(View.GONE);
            volunteers1.setVisibility(View.GONE);
            volunteers2.setVisibility(View.GONE);
            volunteers3.setVisibility(View.GONE);
            volunteers4.setVisibility(View.GONE);

        } else if (str == "Distance to your location") {
            distance1.setVisibility(View.VISIBLE);
            distance2.setVisibility(View.VISIBLE);
            distance3.setVisibility(View.VISIBLE);
            distance4.setVisibility(View.VISIBLE);
            volunteers1.setVisibility(View.GONE);
            volunteers2.setVisibility(View.GONE);
            volunteers3.setVisibility(View.GONE);
            volunteers4.setVisibility(View.GONE);
            type1.setVisibility(View.GONE);
            type2.setVisibility(View.GONE);
            type3.setVisibility(View.GONE);
            type4.setVisibility(View.GONE);
            testedVolunteers1.setVisibility(View.GONE);
            testedVolunteers2.setVisibility(View.GONE);
            testedVolunteers3.setVisibility(View.GONE);
            testedVolunteers4.setVisibility(View.GONE);
            capacity1.setVisibility(View.GONE);
            capacity2.setVisibility(View.GONE);
            capacity3.setVisibility(View.GONE);
            capacity4.setVisibility(View.GONE);
        } else if (str == "Total Tested Volunteers") {
            testedVolunteers1.setVisibility(View.VISIBLE);
            testedVolunteers2.setVisibility(View.VISIBLE);
            testedVolunteers3.setVisibility(View.VISIBLE);
            testedVolunteers4.setVisibility(View.VISIBLE);
            volunteers1.setVisibility(View.GONE);
            volunteers2.setVisibility(View.GONE);
            volunteers3.setVisibility(View.GONE);
            volunteers4.setVisibility(View.GONE);
            type1.setVisibility(View.GONE);
            type2.setVisibility(View.GONE);
            type3.setVisibility(View.GONE);
            type4.setVisibility(View.GONE);
            distance1.setVisibility(View.GONE);
            distance2.setVisibility(View.GONE);
            distance3.setVisibility(View.GONE);
            distance4.setVisibility(View.GONE);
            capacity1.setVisibility(View.GONE);
            capacity2.setVisibility(View.GONE);
            capacity3.setVisibility(View.GONE);
            capacity4.setVisibility(View.GONE);
        } else {
            System.out.println("Something wrong !!");
        }
    }

    public void getListOfDistance(List<Double> distance, double lat, double lng, List<VolunteerSite> list) {
        for (VolunteerSite volunteerSite : list) {
            double lat2 = volunteerSite.getLat();
            double lng2 = volunteerSite.getLng();
            volunteerSite.setDistanceFromCurrentLocation(volunteerSite.distance(lat, lng, lat2, lng2));
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();

            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}