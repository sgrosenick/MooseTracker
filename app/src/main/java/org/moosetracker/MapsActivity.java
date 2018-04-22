package org.moosetracker;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MapsActivity extends AppCompatActivity {

    //DatabaseHelper myDb;
    private Button mCreateSighting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // This creates our database
        //myDb = new DatabaseHelper(this);

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
