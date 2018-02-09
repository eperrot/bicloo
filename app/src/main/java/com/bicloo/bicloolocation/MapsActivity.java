package com.bicloo.bicloolocation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.bicloo.bicloolocation.events.StationsResponseEvent;
import com.bicloo.bicloolocation.modele.Station;
import com.bicloo.bicloolocation.network.StationsApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private static final int DEFAULT_ZOOM_LEVEL = 12;

    private Marker selectedMarker;

    private GoogleMap googleMap;

    @BindView(R.id.activity_maps_bottom_sheet) View bottomSheet;
    @BindView(R.id.activity_maps_station_address_textview) TextView stationAddressTextView;
    @BindView(R.id.activity_maps_status_textview) TextView stationStatusTextView;
    @BindView(R.id.activity_maps_availabilities_textview) TextView stationAvailabilitiesTextView;

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        mapFragment.getMapAsync(this);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    unselectedMarker();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset == 0) {
                    unselectedMarker();
                }
            }
        });
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        fetchData();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMarkerClickListener(this);
        this.googleMap.setOnMapClickListener(this);
        resetMapPosition();
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        showStation(marker);
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        unselectedMarker();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStationsResponseEvent(StationsResponseEvent event) {
        if (event.getStations() != null) {
            loadMap(event.getStations());
        } else {
            //TODO: something went wrong
        }
    }

    private void fetchData() {
        StationsApi.fetchStations(((BiclooApplication) getApplication()).getApiService(), getResources());
    }

    private void resetMapPosition() {
        // Move the camera to Nantes:
        LatLng location = new LatLng(47.2181, -1.5528);//TODO: remove hardcode
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, DEFAULT_ZOOM_LEVEL));

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void loadMap(List<Station> stations) {
        googleMap.clear();
        for (Station station : stations) {
            addStation(station);
        }
    }

    private void addStation(Station station) {
        LatLng loc = new LatLng(station.getPosition().getLat(), station.getPosition().getLng());
        MarkerOptions markerOptions = new MarkerOptions().position(loc).title(station.getAddress());
        Marker marker = googleMap.addMarker(markerOptions);
        marker.setTag(station);
    }

    private void showStation(Marker marker) {
        Station station = (Station) marker.getTag();

        selectMarker(marker);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        stationAddressTextView.setText(station.getAddress());
        stationStatusTextView.setText(station.getStatus());
        stationAvailabilitiesTextView.setText(getString(R.string.availabilities, station.getAvailableBikes(), station.getBikeStands()));
    }

    private void selectMarker(Marker marker) {
        Station station;

        if (selectedMarker != null) {
            selectedMarker.remove();
            station = (Station) selectedMarker.getTag();
            if (station != null) {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(selectedMarker.getPosition())
                        .title(station.getAddress());
                Marker revertedMarker = googleMap.addMarker(markerOptions);
                revertedMarker.setTag(station);
            }
        }

        if (marker != null) {
            station = (Station) marker.getTag();

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(marker.getPosition())
                    .title(station.getAddress())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            selectedMarker = googleMap.addMarker(markerOptions);
            selectedMarker.setTag(station);
        }
    }

    private void unselectedMarker() {
        if (selectedMarker == null) {
            return;
        }

        selectedMarker.remove();
        Station station = (Station) selectedMarker.getTag();
        if (station != null) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(selectedMarker.getPosition())
                    .title(station.getAddress());
            Marker revertedMarker = googleMap.addMarker(markerOptions);
            revertedMarker.setTag(station);
        }
    }
}
