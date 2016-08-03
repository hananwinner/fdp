package com.fractureof.demos.location;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener, HangoutsAdapter.ViewHolder.IMyViewHolderClicks {

    private GoogleMap mMap;

    private Map<Marker, Integer> hangoutMarkers = new HashMap<Marker, Integer>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    int selected_hangout_index = -1;
    Marker prevMarker = null;

    DistanceDraw distanceDraw;

    private void initMarkers() {
        initOurMarkers();
        initHangoutMarkers();
    }

    private void initOurMarkers() {
        BitmapDescriptor bitmapDesc = BitmapDescriptorFactory.fromBitmap(SplashActivity.partnerMarkerBitmap);
        MarkerOptions opts = new MarkerOptions().position(SplashActivity.temp_part_latLng)
                .icon(bitmapDesc);
        mMap.addMarker(opts);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(SplashActivity.temp_part_latLng,
                12.0f));
        BitmapDescriptor bitmapDescMe = BitmapDescriptorFactory.fromBitmap(SplashActivity.meMarkerBitmap);
        MarkerOptions optsMe = new MarkerOptions().position(SplashActivity.temp_me_latLng)
                .icon(bitmapDescMe);
        mMap.addMarker(optsMe);
    }

    private void initHangoutMarkers() {
        for (int i = 0; i < SplashActivity.hangout_arr.length(); ++i) {
            try {
                JSONObject obj = SplashActivity.hangout_arr.getJSONObject(i);
                String lat = obj.getString("lat");
                Float lat_f = Float.parseFloat(lat);
                String lon = obj.getString("lon");
                Float lon_f = Float.parseFloat(lon);
                LatLng latLng = new LatLng(lat_f,lon_f);
                MarkerOptions opts = new MarkerOptions().position(latLng);
                opts.title(obj.getString("name"));
                if ( i == selected_hangout_index) {
                    opts.icon(
                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)
                    );
                }
                Marker marker = mMap.addMarker(opts);
                hangoutMarkers.put(marker,i);
                //new PutMarkerTask(mMap,opts,i).execute(null, null, null);
            } catch (JSONException ex) {}
        }
    }

    @Override /*OnMarkerClickListener*/
    public boolean onMarkerClick(Marker marker) {
        if (hangoutMarkers.containsKey(marker)) {
            markSelectedIfChanged(marker);
        }
        return false;
    }

    private void markSelectedIfChanged(Marker marker) {
        if (marker != prevMarker) {
            int sel = hangoutMarkers.get(marker);
            mAdapter = new HangoutsAdapter(SplashActivity.hangout_arr, sel, this);
            mRecyclerView.swapAdapter(mAdapter, false);
            mRecyclerView.smoothScrollToPosition(sel);
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            if (prevMarker != null) {
                prevMarker.setIcon(BitmapDescriptorFactory.defaultMarker());
            }
            distanceDraw.setDateLatLng(marker.getPosition());
            prevMarker = marker;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(
                        R.id.map
                );
        mapFragment.getMapAsync(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.hangout_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new HangoutsAdapter(SplashActivity.hangout_arr,-1, this);
        mRecyclerView.setAdapter(mAdapter);

        ViewGroup distanceFragment = (ViewGroup) findViewById(R.id.distance_view);
        distanceDraw= (DistanceDraw) distanceFragment.findViewById(R.id.distance_draw);
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
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        initMarkers();
    }

    @Override /*IHangoutViewHolderClicks*/
    public void onHangoutSelected(int idx) {
        //go to the key of the marker..
        for ( Marker marker : hangoutMarkers.keySet()) {
            Integer marker_idx = hangoutMarkers.get(marker);
            if (marker_idx == idx) {
                markSelectedIfChanged(marker);
            }
        }
    }
}

