package fr.efrei.badtracker.fragments.create_match.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import fr.efrei.badtracker.R;

public class MatchInfoFragment extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView mapView;
    private GoogleMap map;
    private Geocoder geocoder;
    private FusedLocationProviderClient fusedLocationClient;
    private Location lastLocation;
    private Marker lastMarker;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_match_info, container, false);

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        mapView.getMapAsync(this);

        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!checkPermissions()) {
            requestPermissions();
        }
        else {
            registerLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterLocationUpdates();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            showSnackbar(R.string.required_location_permission, android.R.string.ok,
                    view -> ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_PERMISSIONS_REQUEST_CODE));

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(view.findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                //canceled
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                registerLocationUpdates();
            } else {
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        view -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        });
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void registerLocationUpdates() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setNumUpdates(1);

        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());

    }

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            lastLocation = locationResult.getLastLocation();
            System.out.println(lastLocation);
            updateCurrentLocation();
        }
    };


    private void unregisterLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void updateCurrentLocation() {
        if(map != null && geocoder != null) {

            LatLng latLng = new LatLng(lastLocation.getLatitude(),
                    lastLocation.getLongitude());
            String title = "Unknown";

            try {
                List<Address> addressList = geocoder.getFromLocation(lastLocation.getLatitude(),
                                lastLocation.getLongitude(), 1);
                if(addressList.size() > 0) {
                    title = addressList.get(0).getAddressLine(0);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            if(lastMarker != null) {
                lastMarker.remove();
            }

            lastMarker = map.addMarker(new MarkerOptions()
                    .title(title)
                    .position(latLng)
                    .visible(true));
            lastMarker.showInfoWindow();

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, map.getCameraPosition().zoom));
        }
    }
}