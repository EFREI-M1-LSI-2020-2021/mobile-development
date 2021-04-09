package fr.android.moi.sensors;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap map;
    private LocationManager locationManager;
    private String provider;
    private Marker lastMarker;
    private Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onResume();
                }
                else {
                    Toast.makeText(this, "Permission denied to access your location", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        if(checkLocationPermission()) {
            enableGPS();
            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);
            lastLocation = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }
        else {
            requestLocationPermission();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(checkLocationPermission()) {
            locationManager.removeUpdates(this);
        }
        else {
            requestLocationPermission();
        }
    }

    private boolean checkLocationPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        updateLastLocation();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        if(lastMarker != null) {
            lastMarker.remove();
        }

        lastLocation = location;
        updateLastLocation();
    }

    private void updateLastLocation() {
        if(lastLocation != null) {
            LatLng latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            lastMarker = map.addMarker(markerOptions);
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    private void enableGPS(){
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

    }
}