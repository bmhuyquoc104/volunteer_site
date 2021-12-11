package com.example.assignment2_android.site;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment2_android.LogIn;
import com.example.assignment2_android.R;
import com.example.assignment2_android.SiteLocation;
import com.example.assignment2_android.model.User;
import com.example.assignment2_android.model.VolunteerSite;

import java.util.ArrayList;
import java.util.List;

public class AddSite extends AppCompatActivity {
    //Declare textview for lat and lng
    TextView lat, lng;
    //Declare string array for location type
    String[] locationType = {"supermarket", "factory", "highSchool", "hospital", "secondary-school"};
    //Declare edittext for name and maxCapacity
    EditText name, maxCapacity;
    //Declare button for create site
    Button createSite;
    //Declare array adapter
    ArrayAdapter<String> adapterItems;
    VolunteerSite newVolunteerSite;
    String type;
    String lat1, lng1;

    AutoCompleteTextView autoCompleteTextView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);
        //Binding
        lat = findViewById(R.id.addLat);
        lng = findViewById(R.id.addLng);
        name = findViewById(R.id.addName);
        autoCompleteTextView = findViewById(R.id.auto_complete_text_view);
        maxCapacity = findViewById(R.id.maxCapacity);
        createSite = findViewById(R.id.createSite);

        newVolunteerSite = new VolunteerSite();
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_location_type, locationType);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("lat")) {
                lat1 = intent.getStringExtra("lat");
                lat.setText("Lat: " + lat1);
            }
            if (intent.hasExtra("lng")) {
                lng1 = intent.getStringExtra("lng");
                lng.setText("Lng: " + lng1);

            } else {
                lng.setText("nothing");
            }
        }

        createSite.setOnClickListener(view -> {
            String inputMaxCapacity = maxCapacity.getText().toString();
            System.out.println("hello" + inputMaxCapacity);
            String leaderName = name.getText().toString();
            System.out.println("hello leader " + leaderName);
            System.out.println(Integer.parseInt(inputMaxCapacity));
            VolunteerSite volunteerSite = newVolunteerSite.addNewLocation(newVolunteerSite.getHOCHIMINH(), leaderName, type,
                    Integer.parseInt(inputMaxCapacity), Double.parseDouble(lat1), Double.parseDouble(lng1));
            SiteLocation.volunteerSiteList.add(volunteerSite);
            System.out.println(SiteLocation.volunteerSiteList);


        });

        System.out.println("in ra lon gi ko" +type);

    }
}


