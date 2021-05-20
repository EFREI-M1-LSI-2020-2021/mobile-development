package fr.efrei.badtracker.fragments.main.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.api.ApiTask;
import fr.efrei.badtracker.api.MatchApi;
import fr.efrei.badtracker.api.dtos.LocationDto;
import fr.efrei.badtracker.api.dtos.MatchDto;
import fr.efrei.badtracker.models.Match;
import fr.efrei.badtracker.models.MatchLocation;
import fr.efrei.badtracker.services.ApiService;
import retrofit2.Response;

public class MapMatchesFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private MapView mapView;
    private GoogleMap map;
    private ApiService apiService;
    private MatchApi matchApi;
    private Map<String, Match> markers = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        apiService = ApiService.getInstance();
        matchApi = apiService.getMatchApi();

        View view = inflater.inflate(R.layout.fragment_map_matches, container, false);

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        apiService.execute(matchApi.getMatches(), response -> {
            if(response.isSuccessful()) {
                List<MatchDto> matches = response.body();
                for(MatchDto matchDto : matches) {
                    Match match = matchDto.toMatch();
                    MatchLocation location = match.getLocation();
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
                            .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_baseline_sports_tennis_24));
                    Marker marker = map.addMarker(markerOptions);
                    markers.put(marker.getId(), match);
                }
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Match match = markers.get(marker.getId());

        if(match != null) {
            MarkerBottomSheetFragment markerBottomSheetFragment = new MarkerBottomSheetFragment(match);
            markerBottomSheetFragment.show(getChildFragmentManager(), "marker");
        }

        return true;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes  int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_map_pin);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}