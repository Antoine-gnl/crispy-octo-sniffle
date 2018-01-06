package com.github.antoine_gnl.myapplication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class testConnection extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final long SCAN_PERIOD = 10000;

    private Handler mHandler;
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<BluetoothDevice> mLeDevices;
    private boolean mScanning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_connection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context= getApplicationContext();
                Intent intent = new Intent(context,DroneActivity.class);
                startActivity(intent);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mLeDevices = new ArrayList<BluetoothDevice>();

        Button btnBLE = findViewById(R.id.bt_BLE_connect);
        btnBLE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanLeDevice(true);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* Bluetooth Declaration */
        setConnectionState(true);

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
            Log.i(BluetoothLeService.class.getSimpleName(),"J'ai pas de dents bleues");
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_connection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_connection) {
            setConnectionState(true);
            // Handle the camera action
            Toast.makeText(this, "Je suis un pingouin", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            setConnectionState(false);
            Toast.makeText(this, "Je suis une otarie", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            setConnectionState(false);
            Toast.makeText(this, "Je suis une baleine", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            setConnectionState(false);
            Toast.makeText(this, "Je suis trop grand pour l'ecran", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            setConnectionState(false);
            Toast.makeText(this, "Je suis en train de dormir", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            setConnectionState(false);
            Toast.makeText(this, "Je suis une tanche", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    void setConnectionState(boolean visibility)
    {
        Button btn1 = findViewById(R.id.bt_drone_connect);
        Button btn2 = findViewById(R.id.bt_BLE_connect);
        TextView tv1 = findViewById(R.id.tv_drone_state);
        TextView tv2 = findViewById(R.id.tv_BLE_state);
        if (visibility) {
            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
        }
        else
        {
            btn1.setVisibility(View.INVISIBLE);
            btn2.setVisibility(View.INVISIBLE);
            tv1.setVisibility(View.INVISIBLE);
            tv2.setVisibility(View.INVISIBLE);
        }
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
                        TextView tv = findViewById(R.id.tv_drone_state);
                        tv.setText(R.string.connected);
                        TextView tv2 = findViewById(R.id.tv_BLE_state);
                        tv2.setText("Scan stopped");

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
                    TextView tv2 = findViewById(R.id.tv_BLE_state);
                    tv2.setText("Scan stopped");
                }
            }, SCAN_PERIOD);
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
            TextView tv2 = findViewById(R.id.tv_BLE_state);
            tv2.setText("Scan in progress");
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            TextView tv2 = findViewById(R.id.tv_BLE_state);
            tv2.setText("Scan stopped");
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
