package com.wedev.test.mapsmodule;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/*
* Cette activité est chargé la position d'un point donnée par ses coordonnées sur la carte
* */

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    long latitude = 0;
    long longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        /*
        *RECUPERATION DE LA POSITION A PARTIR DE L'INTENT QUI A A MENE A CETTE ACTIVITE
         * */
        Intent i = getIntent();
        latitude = i.getLongExtra("latitude",0);
        longitude = i.getLongExtra("longitude",0);

        //OBTENTTION DU MAP FRAGMENT ET BRANCHEMENT DE L'ACTIVITE COMME LISTENER ATTENDANT QUE L'OBJECT MAPS SOIT PRÊT.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng coord = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(coord).title("L'utilisateur se trouve a cet endroit."));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coord));
    }
}
