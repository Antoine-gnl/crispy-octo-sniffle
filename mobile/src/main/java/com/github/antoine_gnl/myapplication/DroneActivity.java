package com.github.antoine_gnl.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.antoine_gnl.myapplication.SensorTagAdapter.SensorTagAdapterOnClickHandler;
import com.github.antoine_gnl.myapplication.WatchAdapter.WatchAdapterOnClickHandler;

import java.util.ArrayList;
import java.util.List;

public class DroneActivity extends AppCompatActivity implements SensorTagAdapterOnClickHandler, WatchAdapterOnClickHandler {
    private List<String> fakeSensorValues = new ArrayList<String>();
    private SensorTagAdapter mSensorTagAdapter;
    private WatchAdapter mWatchAdapter;

    private RecyclerView mRecyclerViewSensorTag;
    private RecyclerView mRecyclerViewWatch;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    void createFakeData() {
        for (int i = 100; i >0; i--)
        {
            fakeSensorValues.add(0,String.valueOf(i));
        }
        fakeSensorValues.add(0,"SensorTag");
        String[] simple = new String[fakeSensorValues.size()];
        fakeSensorValues.toArray(simple);
        mSensorTagAdapter.setSensorTagData(simple);
        String[] simple2 = new String[fakeSensorValues.size()];
        fakeSensorValues.set(0,"Watch");
        fakeSensorValues.toArray(simple2);
        mWatchAdapter.setmWatchData(simple2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drone);

        mRecyclerViewSensorTag =  findViewById(R.id.recyclerview_sensorTag);
        mRecyclerViewWatch =  findViewById(R.id.recyclerview_watch);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager layoutManager
                = new GridLayoutManager(this,1, GridLayoutManager.VERTICAL,false);

        mRecyclerViewSensorTag.setLayoutManager(layoutManager);
        mRecyclerViewWatch.setLayoutManager(layoutManager2);
        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerViewSensorTag.setHasFixedSize(true);
        mRecyclerViewWatch.setHasFixedSize(true);

        // COMPLETED (11) Pass in 'this' as the ForecastAdapterOnClickHandler
        /*
         * The ForecastAdapter is responsible for linking our weather data with the Views that
         * will end up displaying our weather data.
         */
        mSensorTagAdapter = new SensorTagAdapter(this);
        mWatchAdapter = new WatchAdapter(this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerViewSensorTag.setAdapter(mSensorTagAdapter);
        mRecyclerViewWatch.setAdapter(mWatchAdapter);
        createFakeData();

    }

    @Override
    public void onClick(String weatherForDay) {
        Context context = this;
        Toast.makeText(context, weatherForDay, Toast.LENGTH_SHORT)
                .show();
    }
}
