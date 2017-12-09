package com.github.antoine_gnl.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DroneActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewSensorTag;
    private RecyclerView mRecyclerViewWatch;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drone);

        mRecyclerViewSensorTag =  findViewById(R.id.recyclerview_sensorTag);
        mRecyclerViewWatch =  findViewById(R.id.recyclerview_watch);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
    }
}
