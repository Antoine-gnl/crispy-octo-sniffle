package com.github.antoine_gnl.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.android.sunshine.ForecastAdapter.ForecastAdapterOnClickHandler;
import com.github.antoine_gnl.myapplication.SensorTagAdapter.SensorTagAdapterOnClickHandler;

public class DroneActivity extends AppCompatActivity implements SensorTagAdapterOnClickHandler {

    private SensorTagAdapter mSensorTagAdapter;

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

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerViewSensorTag.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerViewSensorTag.setHasFixedSize(true);

        // COMPLETED (11) Pass in 'this' as the ForecastAdapterOnClickHandler
        /*
         * The ForecastAdapter is responsible for linking our weather data with the Views that
         * will end up displaying our weather data.
         */
        mSensorTagAdapter = new SensorTagAdapter(this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerViewSensorTag.setAdapter(mSensorTagAdapter);
    }

    @Override
    public void onClick(String weatherForDay) {
        Context context = this;
        Toast.makeText(context, weatherForDay, Toast.LENGTH_SHORT)
                .show();
    }
}
