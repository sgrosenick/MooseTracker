package org.moosetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class CreateSighting extends Activity {
    //private EditText mMooseCount;
    //private EditText mDescription;
    private Button mChooseLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createsighting);

        mChooseLocation = (Button) findViewById(R.id.choose_location_button);
        mChooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateSighting.this, ChooseLocation.class);
                startActivity(intent);
            }
        });


    }

}
