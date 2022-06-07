package app.wasimappbd.softxmagic.mdspbdnew;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.HashMap;

public class FormStep1Activity extends NavigationActivity  implements LocationListener,ConnectivityReceiver.ConnectivityReceiverListener {

    View view;
    Button nextbtn,prevbtn;
    Context context;
    String editstatus;
    private LocationManager locationManager;
    private Location location;
    private final int REQUEST_LOCATION = 200;
    private GoogleApiClient googleApiClient;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isLogin() && read_memory() != 0) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_form_step1, null,false);
            drawer.addView(view,0);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Send Reports");

            Intent intv = getIntent();
            final String statusval = intv.getStringExtra("action");
            final int sid = intv.getIntExtra("subid",0);

            nextbtn = (Button) findViewById(R.id.nextbtn);
            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentnext = new Intent(getApplicationContext(), FormStep2Activity.class);
                    intentnext.putExtra("subid", sid);
                    intentnext.putExtra("action", statusval);
                    startActivity(intentnext);
                }
            });

            prevbtn = (Button) findViewById(R.id.prevbtn);
            prevbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent inteprev = new Intent(getApplicationContext(), GridViewActivity.class);
                    startActivity(inteprev);
                }
            });
        }
        else {
            Intent i = new Intent(this.getBaseContext().getApplicationContext(), LoginActivity.class);
            this.startActivity(i);
        }

        locationMethod();
    }


    public void locationMethod(){
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(FormStep1Activity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, (LocationListener) this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (location != null) {
                sharedPref = this.getSharedPreferences("GPSLOCATION", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("lat", Double.toString(location.getLatitude()));
                editor.putString("long", Double.toString(location.getLongitude()));
                editor.commit();

                //Toast.makeText(getApplicationContext(),"Latitude: "+Double.toString(location.getLatitude())+"Longtitude: "+Double.toString(location.getLongitude()),Toast.LENGTH_SHORT).show();
            }
        } else {
            // showGPSDisabledAlertToUser();
            enableLoc();
        }
    }

    public void onLocationChanged(Location location) {
        sharedPref = this.getSharedPreferences("GPSLOCATION", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("lat", Double.toString(location.getLatitude()));
        editor.putString("long", Double.toString(location.getLongitude()));
        editor.commit();
        /*Toast.makeText(getApplicationContext(), "Latitude Value"+location.getLatitude()+
                " Lontitude Value"+location.getLongitude(), Toast.LENGTH_LONG).show();*/
        // getAddressFromLocation(location, getApplicationContext());
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            //showGPSDisabledAlertToUser();
            enableLoc();
        }
    }


    public Boolean isLogin() {
        sharedPref = this.getSharedPreferences("LOGININFO", Context.MODE_PRIVATE);
        Boolean s = sharedPref.getBoolean("loggedin", true);
        return s;
    }

    public int read_memory() {
        sharedPref = this.getSharedPreferences("LOGININFO", Context.MODE_PRIVATE);
        int s = sharedPref.getInt("userid", 0);
        return s;
    }

    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(FormStep1Activity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(false);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(FormStep1Activity.this, REQUEST_LOCATION);
                                // finish();
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }

}
