package org.moosetracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class CreateSighting extends Activity {
    DatabaseHelper myDB;
    private EditText mMooseCount;
    private EditText mDescription;
    private LinearLayout mLatitudeLayout;
    private LinearLayout mLongitudeLayout;
    private TextView mLatitude;
    private TextView mLongitude;
    private Button mSubmitSighting;
    private Button mChooseLocation;
    private String latValue;
    private String lonValue;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createsighting);
        myDB = new DatabaseHelper(this);

        final SharedPreferences mSharedPreferences = getSharedPreferences("LatLon", MODE_PRIVATE);

        mMooseCount = (EditText) findViewById(R.id.moose_count);
        mDescription = (EditText) findViewById(R.id.description);
        mLatitudeLayout = (LinearLayout) findViewById(R.id.latitude);
        mLongitudeLayout = (LinearLayout) findViewById(R.id.longitude);
        mLatitude = (TextView) findViewById(R.id.latitude_text);
        mLongitude = (TextView) findViewById(R.id.longitude_text);

        mSubmitSighting = (Button) findViewById(R.id.submit_sighting_button);
        mSubmitSighting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear form

                mLatitudeLayout.setVisibility(View.GONE);
                mLongitudeLayout.setVisibility(View.GONE);

                SharedPreferences clearPref = getSharedPreferences("LatLon", MODE_PRIVATE);
                clearPref.edit().clear().commit();
            }
        });


        mChooseLocation = (Button) findViewById(R.id.choose_location_button);
        mChooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateSighting.this, ChooseLocation.class);
                startActivity(intent);
            }
        });

        latValue = mSharedPreferences.getString("lat", "Latitude");
        lonValue = mSharedPreferences.getString("lon", "Longitude");

        if (latValue.length() > 0) {
            mLatitudeLayout.setVisibility(View.VISIBLE);
            mLatitude.setText("Latitude: " + latValue);
        } else {
            mLatitude.setText("Latitude");
        }

        if (lonValue.length() > 0) {
            mLongitudeLayout.setVisibility(View.VISIBLE);
            mLongitude.setText("Longitude: " + lonValue);
        } else {
            mLongitude.setText("Longitude");
        }

        addData();
    }

    public void addData() {
        mSubmitSighting.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.insertData(mMooseCount.getText().toString(),
                                mDescription.getText().toString(),
                                latValue,
                                lonValue);
                        if (isInserted = true) {
                            Toast.makeText(CreateSighting.this, "Sighting Submitted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CreateSighting.this, "Error Submitting", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
}
