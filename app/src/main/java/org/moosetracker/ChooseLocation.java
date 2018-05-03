package org.moosetracker;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;

import org.w3c.dom.Text;

public class ChooseLocation extends AppCompatActivity implements OnMapReadyCallback {

    public String latStringFormat;
    public String lonStringFormat;
    private TextView mSetLocationInstruct;
    private GoogleMap mMap;
    private Marker marker;
    private Button mSetLocationButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_location);


        mSetLocationInstruct = (TextView) findViewById(R.id.set_location_instruct);

        mSetLocationButton = (Button) findViewById(R.id.set_location_button);
        mSetLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Store latitude and longitude for use in CreateSighting Activity
                SharedPreferences mSharedPreferences = getSharedPreferences("LatLon", MODE_PRIVATE);
                SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                mEditor.putString("lat", latStringFormat);
                mEditor.putString("lon", lonStringFormat);
                mEditor.apply();

                Intent intent = new Intent(ChooseLocation.this, CreateSighting.class);
                startActivity(intent);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Get permission to access user's location
        if (ActivityCompat.checkSelfPermission(ChooseLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ChooseLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            if (!mMap.isMyLocationEnabled()){
                mMap.setMyLocationEnabled(true);
            }
        }

        mMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker != null) {
                    marker.remove();
                }
                //Get latitude and longitude
                Double lat = latLng.latitude;
                Double lon = latLng.longitude;

                // Format for display in snippet
                latStringFormat = String.format("%.2f", lat);
                lonStringFormat = String.format("%.2f", lon);

                //This creates a marker
                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .title("Moose Location")
                        .snippet("Latitude: " + latStringFormat + ", Longitude: " + lonStringFormat);
                marker = mMap.addMarker(options);
                mSetLocationInstruct.setVisibility(View.GONE);
                mSetLocationButton.setVisibility(View.VISIBLE);
            }
        });

        // Move the camera to Anchorage
        LatLng anchorage = new LatLng(61.165374, -149.904544);
        //mMap.addMarker(new MarkerOptions().position(anchorage).title("Anchorage!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(anchorage, 11));
    }

}
