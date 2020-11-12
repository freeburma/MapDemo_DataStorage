package com.example.datastorage;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class activity_googlemap extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                            .findFragmentById(R.id.memorableGoogleMapId);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady (GoogleMap googleMap)
    {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        LatLng tauranga = new LatLng(-37.6763, 176.166672);
        mMap.addMarker(new MarkerOptions()
                        .position(tauranga)
                        .title("Tauranga")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        );

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tauranga, 7));

    }// end onMapReady()

}