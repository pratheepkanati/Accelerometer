package com.lirctek.ics;

import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.location.LocationRequest;
import com.yayandroid.locationmanager.base.LocationBaseService;
import com.yayandroid.locationmanager.configuration.DefaultProviderConfiguration;
import com.yayandroid.locationmanager.configuration.GooglePlayServicesConfiguration;
import com.yayandroid.locationmanager.configuration.LocationConfiguration;
import com.yayandroid.locationmanager.constants.FailType;
import com.yayandroid.locationmanager.constants.ProcessType;


public class LocationService extends LocationBaseService {
    public static  float speed;
    public static  int speedinMiles;
    public  static  Location location;
    private boolean isLocationRequested = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public LocationConfiguration getLocationConfiguration() {
        LocationRequest locationrequest = LocationRequest.create();
        locationrequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationrequest.setInterval(100);
        return new LocationConfiguration.Builder()
                .keepTracking(true)
                .useGooglePlayServices(new GooglePlayServicesConfiguration.Builder().askForSettingsApi(true).locationRequest(locationrequest).build())
                .useDefaultProviders(new DefaultProviderConfiguration.Builder().requiredTimeInterval(100).build())
                .build();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // calling super is required when extending from LocationBaseService
        super.onStartCommand(intent, flags, startId);
        if (!isLocationRequested) {
            isLocationRequested = true;
            getLocation();
        }
        // Return type is depends on your requirements
        return START_STICKY;
    }

    @Override
    public void onLocationChanged(Location location) {
      speed=location.getSpeed();
      speedinMiles= (int) (location.getSpeed() * 2.2369);
      LocationService.location=location;
        Log.e("SPEED",speed+"");
    }

    @Override
    public void onLocationFailed(@FailType int failType) {


        switch (failType) {
            case FailType.TIMEOUT: {
                Log.e("LOCATION_SERVICE", "Couldn't get location, and timeout!");
                break;
            }
            case FailType.PERMISSION_DENIED: {
                Log.e("LOCATION_SERVICE", "Couldn't get location, because user didn't give permission!");
                stopSelf();
                break;
            }
            case FailType.NETWORK_NOT_AVAILABLE: {
                Log.e("LOCATION_SERVICE", "Couldn't get location, because network is not accessible!");
                break;
            }
            case FailType.GOOGLE_PLAY_SERVICES_NOT_AVAILABLE: {
                Log.e("LOCATION_SERVICE", "Couldn't get location, because Google Play Services not available!");
                break;
            }
            case FailType.GOOGLE_PLAY_SERVICES_CONNECTION_FAIL: {
                Log.e("LOCATION_SERVICE", "Couldn't get location, because Google Play Services connection failed!");
                break;
            }
            case FailType.GOOGLE_PLAY_SERVICES_SETTINGS_DIALOG: {
                Log.e("LOCATION_SERVICE", "Couldn't display settingsApi dialog!");
                break;
            }
            case FailType.GOOGLE_PLAY_SERVICES_SETTINGS_DENIED: {
                Log.e("LOCATION_SERVICE", "Couldn't get location, because user didn't activate providers via settingsApi!");
                break;
            }
            case FailType.VIEW_DETACHED: {
                Log.e("LOCATION_SERVICE", "Couldn't get location, because in the process view was detached!");
                break;
            }
            case FailType.VIEW_NOT_REQUIRED_TYPE: {
                Log.e("LOCATION_SERVICE", "Couldn't get location, "
                        + "because view wasn't sufficient enough to fulfill given configuration!");
                break;
            }
            case FailType.UNKNOWN: {
                Log.e("LOCATION_SERVICE", "Ops! Something went wrong!");
                break;
            }
        }
    }

    @Override
    public void onProcessTypeChanged(@ProcessType int newProcess) {
        switch (newProcess) {
            case ProcessType.GETTING_LOCATION_FROM_GOOGLE_PLAY_SERVICES: {
                Log.v("LOCATION_SERVICE", "Getting Location from Google Play Services...");
                break;
            }
            case ProcessType.GETTING_LOCATION_FROM_GPS_PROVIDER: {
                Log.v("LOCATION_SERVICE", "Getting Location from GPS...");
                break;
            }
            case ProcessType.GETTING_LOCATION_FROM_NETWORK_PROVIDER: {
                Log.v("LOCATION_SERVICE", "Getting Location from Network...");
                break;
            }
            case ProcessType.ASKING_PERMISSIONS:
            case ProcessType.GETTING_LOCATION_FROM_CUSTOM_PROVIDER:
                // Ignored
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}