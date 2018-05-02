package org.moosetracker;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MapsActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private Button mCreateSighting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // This creates our database
        myDb = new DatabaseHelper(this);
<<<<<<< HEAD
=======


>>>>>>> ec1c05dd61908bd5f85e84f106241be6fffe7448

        mCreateSighting = (Button) findViewById(R.id.create_sighting_button);
        mCreateSighting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, CreateSighting.class);
                startActivity(intent);
            }
        });
    }


}
