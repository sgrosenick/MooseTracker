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
    private TextView mLatitude;
    private TextView mLongitude;
    private Button mSubmitSighting;
    private Button mChooseLocation;
    private String latValue;
    private String lonValue;
    private String latValueClear;
    private String lonValueClear;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createsighting);
        myDB = new DatabaseHelper(this);

        final SharedPreferences mSharedPreferences = getSharedPreferences("LatLon", MODE_PRIVATE);

        latValue = mSharedPreferences.getString("lat", "Latitude");
        lonValue = mSharedPreferences.getString("lon", "Longitude");

        mMooseCount = (EditText) findViewById(R.id.moose_count);
        mDescription = (EditText) findViewById(R.id.description);
        mLatitude = (TextView) findViewById(R.id.latitude_text);
        mLongitude = (TextView) findViewById(R.id.longitude_text);



        mSubmitSighting = (Button) findViewById(R.id.submit_sighting_button);
        mSubmitSighting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear form
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.clear().commit();

                latValueClear = mSharedPreferences.getString("lat", "Latitude");
                lonValueClear = mSharedPreferences.getString("lon", "Longitude");
                setLatLon(latValueClear, lonValueClear);
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

        setLatLon(latValue, lonValue);
        //addData();
    }

    public void setLatLon(String lat, String lon) {
        if (lat.length() > 0) {
            mLatitude.setText("Latitude: " + lat);
        } else {
            mLatitude.setText("Set Location to add Latitude");
        }

        if (lon.length() > 0) {
            mLongitude.setText("Longitude: " + lon);
        } else {
            mLongitude.setText("Set Location to add Longitude");
        }
    }

    public void addData() {
        mSubmitSighting.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.addData(mMooseCount.getText().toString(),
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
