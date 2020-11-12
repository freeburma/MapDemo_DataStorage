package com.example.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MemorablePlaceMain extends AppCompatActivity
{

    /// Following static variables allow access from other activites
    static ArrayList<String> places = new ArrayList<String>();    // Place name
    static ArrayList<LatLng> locations = new ArrayList<LatLng>(); // Locations: lat and lng
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorable_place_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("package com.example.datastorage", Context.MODE_PRIVATE);

        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();

        /// Clearing the previous data
        places.clear();
        latitudes.clear();
        longitudes.clear();
        locations.clear();


        try
        {
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places", ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lats", ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lons", ObjectSerializer.serialize(new ArrayList<String>())));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (places.size() > 0 && latitudes.size() > 0 && longitudes.size() > 0)
        {
            if (places.size() == latitudes.size() && places.size() == longitudes.size())
            {
                for (int i = 0; i < latitudes.size(); i++)
                {
                    locations.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))));

                }// end for

            }// end if


        }
        else
        {
            places.add("Add a new place ...");
            locations.add(new LatLng(0, 0));
        }// end if

        ListView listView = (ListView) findViewById(R.id.listView);

//        places.add("Add a new place ...");      // Adding new place
//        locations.add(new LatLng(0, 0)); // Default Lat and Lng

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id)
            {

//                Toast.makeText(MemorablePlaceMain.this, Integer.toString(position), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("placeNumber", position);
                startActivity(intent);

            }// end onItemClick()
        });
    }

}