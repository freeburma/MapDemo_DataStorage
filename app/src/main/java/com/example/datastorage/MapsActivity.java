package com.example.datastorage;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener
{
    LocationManager locationManager;
    LocationListener locationListener;

    private GoogleMap mMap;

    public void centerMapOnLocation(Location location, String title)
    {
        if (location != null)
        {
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();

            mMap.addMarker(new MarkerOptions()
                               .position(userLocation)
                               .title(title)
                            );

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12));

        }// end if


    }// end centerMapOnLocation()

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centerMapOnLocation(lastKnownLocation, "Your Location");

            }// end if

        }// end if

    }// end onRequestPermissionsResult()

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }// end onCreate()


    @Override
    public void onMapReady (GoogleMap googleMap)
    {

        mMap = googleMap;

        //// Setting long press on Google Map
        mMap.setOnMapLongClickListener(this);

        Intent getMemorableIntent = getIntent();

        if (getMemorableIntent.getIntExtra("placeNumber", 0) == 0)
        {
            // Zoom in on user location
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener()
            {

                @Override
                public void onLocationChanged (Location location)
                {
                    centerMapOnLocation(location, "Your Location");

                }// end onLocationChanged()

                @Override
                public void onStatusChanged (String provider, int status, Bundle extras)
                {

                }

                @Override
                public void onProviderEnabled (String provider)
                {

                }

                @Override
                public void onProviderDisabled (String provider)
                {

                }
            };

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centerMapOnLocation(lastKnownLocation, "Your Location");
            }
            else
            {

                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }// end if

        }
        else
        {
            Location plaeLocation = new Location(LocationManager.GPS_PROVIDER);
            plaeLocation.setLatitude(MemorablePlaceMain.locations.get(getMemorableIntent.getIntExtra("placeNumber", 0)).latitude);
            plaeLocation.setLongitude(MemorablePlaceMain.locations.get(getMemorableIntent.getIntExtra("placeNumber", 0)).longitude);

            centerMapOnLocation(plaeLocation, MemorablePlaceMain.places.get(getMemorableIntent.getIntExtra("placeNumber", 0)));

        }// end if

        Toast.makeText(this, Integer.toString(getMemorableIntent.getIntExtra("placeNumber", -1)), Toast.LENGTH_LONG).show();

    }// end onMapReady()

    @Override
    public void onMapLongClick (LatLng latLng)
    {

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String address = "";

        try
        {
            List<Address> listAddresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if (listAddresses != null && listAddresses.size() > 0)
            {
                if (listAddresses.get(0).getThoroughfare() != null)
                {
                    if (listAddresses.get(0).getSubThoroughfare() != null)
                    {
                        address += listAddresses.get(0).getSubThoroughfare() + " ";

                    }// end if

                    address += listAddresses.get(0).getThoroughfare() + " ";

                }// end if

            }// end if
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }// end try

        if (address.equals(""))
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm");
            address += simpleDateFormat.format(new Date());

        }// end if


        mMap.addMarker(new MarkerOptions()
                       .position(latLng)
                       .title(address)
                    );

        MemorablePlaceMain.places.add(address);
        MemorablePlaceMain.locations.add(latLng);

        MemorablePlaceMain.arrayAdapter.notifyDataSetChanged();

        Toast.makeText(this, "Location Save ...", Toast.LENGTH_LONG).show();

        // Storing the Data
        SharedPreferences sharedPreferences = this.getSharedPreferences("package com.example.datastorage", Context.MODE_PRIVATE);

        try
        {
            ArrayList<String> latitudes = new ArrayList<>();
            ArrayList<String> longitudes = new ArrayList<>();

            for (LatLng coord : MemorablePlaceMain.locations)
            {
                latitudes.add(Double.toString(coord.latitude));
                longitudes.add(Double.toString(coord.longitude));

            }// end for

            sharedPreferences.edit().putString("places", ObjectSerializer.serialize(MemorablePlaceMain.places)).apply();
            sharedPreferences.edit().putString("lats", ObjectSerializer.serialize(latitudes)).apply();
            sharedPreferences.edit().putString("lons", ObjectSerializer.serialize(longitudes)).apply();
        }
        catch (Exception e)
        {

        }

        Log.i("LongClick", "Long click");

    }// end onMapLongClick()

}// end class