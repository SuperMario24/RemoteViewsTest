package com.example.saber.remoteviewsnotificationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DemoActivity_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_1);

        Toast.makeText(this, "this is DemoActivity_1", Toast.LENGTH_SHORT).show();

    }
}
