package com.github.antoine_gnl.myapplication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private BluetoothAdapter mBluetoothAdapter;
    private Handler mHandler;
    private ArrayList<BluetoothDevice> mLeDevices;
    private boolean mScanning;

    private static final int REQUEST_ENABLE_BT = 1;
    private static final long SCAN_PERIOD = 10000;

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

        mHandler = new Handler();
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Log.i(BluetoothLeService.class.getSimpleName(),"J'ai pas de ble");
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        Log.i(BluetoothLeService.class.getSimpleName(),"bluetooth initialized");
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Log.i(BluetoothLeService.class.getSimpleName(), "J'ai pas de dents bleues");
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mLeDevices = new ArrayList<BluetoothDevice>();
        createFakeData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(String weatherForDay) {
        Context context = this;
        Toast.makeText(context, weatherForDay, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
        mLeDevices.clear();
        // Initializes list view adapter.
        scanLeDevice(true);
    }
    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLeDevices.add(device);
                        }
                    });
                    if (device == null) return;
                    else
                    if(device.getAddress().equals("24:71:89:58:DA:80")) {
                        //final Intent intent = new Intent(getApplicationContext(), testConnection.class);
                        //intent.putExtra(testConnection.EXTRAS_DEVICE_NAME, device.getName());œ
                        //intent.putExtra(testConnection.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                        if (mScanning) {
                            mBluetoothAdapter.stopLeScan(mLeScanCallback);
                            mScanning = false;
                        }
                        Toast.makeText(getApplicationContext(),"Connected succesfully to "+ device.getName(),Toast.LENGTH_SHORT).show();
                        //startActivity(intent);
                    }
                }
            };

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }
    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
        mLeDevices.clear();
    }

}
