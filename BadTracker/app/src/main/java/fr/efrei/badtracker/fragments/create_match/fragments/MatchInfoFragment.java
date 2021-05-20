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
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.Utils;
import fr.efrei.badtracker.fragments.create_match.CreateMatchFragment;
import fr.efrei.badtracker.models.MatchLocation;

public class MatchInfoFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private MapView mapView;
    private EditText editText;
    private FloatingActionButton lastLocationButton;
    private GoogleMap map;
    private Geocoder geocoder;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng location;
    private Marker marker;
    private View view;
    private CreateMatchFragment createMatchFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_match_info, container, false);

        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        createMatchFragment = (CreateMatchFragment) navHostFragment.getParentFragment();

        MatchInfoFragmentArgs args = MatchInfoFragmentArgs.fromBundle(getArguments());

        editText = view.findViewById(R.id.edit_text_match_name);

        String name = args.getName();
        if(name != null) {
            editText.setText(name);
        }

        MatchLocation location = args.getLocation();
        if(location != null) {
            this.location = new LatLng(location.getLatitude(), location.getLongitude());
        }

        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        lastLocationButton = view.findViewById(R.id.last_location);
        lastLocationButton.setOnClickListener(v -> findLastLocation());

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        map.setOnMapLongClickListener(this);
        if(!checkPermissions()) {
            requestPermissions();
        }
        else {
            if(location == null) {
                findLastLocation();
            }
            else {
                updateCurrentLocation(false);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        save();
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        location = latLng;
        updateCurrentLocation(true);
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
            Utils.showSnackbar(view, R.string.required_location_permission, android.R.string.ok,
                    view -> requestLocation());
        } else {
            requestLocation();
        }
    }

    private void requestLocation() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                //canceled
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                findLastLocation();
            } else {
                Utils.showSnackbar(view, R.string.permission_denied_explanation, R.string.settings,
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
    private void findLastLocation() {
        Log.d("Debug", "here");
        fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
            Location lastLocation = task.getResult();
            if(lastLocation != null) {
                this.location = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                updateCurrentLocation(false);
            }
        });
    }

    private void updateCurrentLocation(boolean defaultZoom) {
        if(map != null && geocoder != null && location != null) {

            String title = "Unknown";

            try {
                List<Address> addressList = geocoder.getFromLocation(location.latitude,
                                location.longitude, 1);
                if(addressList.size() > 0) {
                    title = addressList.get(0).getAddressLine(0);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            if(marker != null) {
                marker.remove();
            }

            marker = map.addMarker(new MarkerOptions()
                    .title(title)
                    .position(location)
                    .visible(true));
            marker.showInfoWindow();

            float zoom = defaultZoom ? map.getCameraPosition().zoom : 16;
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
        }
    }

    public boolean validate() {
        String text = editText.getText().toString();
        return !text.isEmpty() && location != null;
    }

    private void save() {

        String text = editText.getText().toString();
        if(!text.isEmpty()) {
            createMatchFragment.setMatchName(text);
        }

        if(location != null) {
            createMatchFragment.setMatchLocation(new MatchLocation(location.latitude,
                    location.longitude));
        }
    }
}