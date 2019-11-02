package com.example.slide6_anc2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private EditText edtSearch;
    private PlacesClient placesClient;

    public String name;
    public double latitute;
    public double longitute;
    private Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        edtSearch = findViewById(R.id.edtSearch);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDsDRA9SvQ_IzzUtRSKI1J8noZDETr06vM");
        }
        placesClient = Places.createClient(this);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                final LatLng latLng = place.getLatLng();

                // TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }

        });


    }


    public void init() {
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    geolocation();
                }
                return false;
            }
        });
        fideSoftKeyBroad();
    }

    private void geolocation() {
        String searchString = edtSearch.getText().toString().trim();

        Geocoder geocoder = new Geocoder(this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.size() > 0) {
            Address address = list.get(0);
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        name=getIntent().getStringExtra("name");
        longitute=getIntent().getDoubleExtra("longitute",-1);
        latitute=getIntent().getDoubleExtra("latitute",-1);

        moveCamera(new LatLng(latitute, longitute), DEFAULT_ZOOM, name);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Toast.makeText(MapsActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    888);

        } else {
            mMap.setMyLocationEnabled(true);
            init();
        }


    }

    private void moveCamera(LatLng latLng, float defaultZoom, String title) {
        if (!title.equalsIgnoreCase("My Location")) {

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, defaultZoom));
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        fideSoftKeyBroad();
    }

    private void fideSoftKeyBroad() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    public void addMarker(View view) {
        Intent intent = new Intent(this, AddMarker.class);
        startActivity(intent);
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//
//        MapAdapter addMarker = new MapAdapter();
//        Log.e("POSTION", addMarker.id + "");
//        mMap.clear();
//        Dao dao = new Dao(this);
//        List<Map> mapList = dao.getAllMap();
//        if (mapList.size() != 0) {
//            for (int i = 0; i < mapList.size(); i++) {
//                Map map = mapList.get(i);
//                LatLng cdfpt = new LatLng(map.getLatitute(), map.getLongitute());
//                mMap.addMarker(new MarkerOptions().position(cdfpt).title(map.getName()));
//            }
//        }
//        moveCamera(new LatLng(addMarker.latitute, addMarker.longitute), DEFAULT_ZOOM, addMarker.name);
//    }
}
