package org.moosetracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
    private Button mViewData;
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
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();

        latValue = mSharedPreferences.getString("lat", "Latitude");
        lonValue = mSharedPreferences.getString("lon", "Longitude");

        mMooseCount = (EditText) findViewById(R.id.moose_count);
        mDescription = (EditText) findViewById(R.id.description);
        mLatitude = (TextView) findViewById(R.id.latitude_text);
        mLongitude = (TextView) findViewById(R.id.longitude_text);

        mViewData = (Button) findViewById(R.id.view_data);


        mSubmitSighting = (Button) findViewById(R.id.submit_sighting_button);
        mSubmitSighting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Submit the data
                addData();

                // Clear form
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.clear().commit();

                mMooseCount.setText("");
                mDescription.setText("");

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
        ViewData();
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

            String count = mMooseCount.getText().toString();
            String description = mDescription.getText().toString();
            String latString = latValue.toString();
            String lonString = lonValue.toString();

            Long tsLong = System.currentTimeMillis()/1000;
            String timestamp = tsLong.toString();

            boolean insertData = myDB.addData(count, description, latString, lonString, timestamp);


            if (insertData == true) {
                Toast.makeText(CreateSighting.this, "Sighting Submitted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(CreateSighting.this, "Error Submitting", Toast.LENGTH_LONG).show();
            }
    }

    public void ViewData() {
        mViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = myDB.viewData();
                int dataCount = data.getCount();

                if(dataCount == 0) {
                    Display("Error", "No Data Found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();

                while (data.moveToNext()) {
                    buffer.append("Moose Seen:" + data.getString(1) + "\n");
                    buffer.append("Description: " + data.getString(2) + "\n");
                    //buffer.append("Timestamp: " + data.getString(6) + "\n");
                    buffer.append("\n");
                }

                Display("Data:", buffer.toString());
            }
        });
    }

    public void Display (String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
