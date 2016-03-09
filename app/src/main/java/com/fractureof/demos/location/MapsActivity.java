package com.fractureof.demos.location;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fractureof.demos.location.backend.codebox.PojoHangoutResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final float HUE_DIFF = 30.0f;
    private GoogleMap mMap;
    private float mCurHue;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        try {
//            JSONArray arr = new JSONArray(SplashActivity.list_str);
//            hangout_arr = new PojoHangoutResponse[arr.length()];//  new String[arr.length()];
//            for (int i = 0; i < arr.length(); i++)
//            {
//                String hangout_name = arr.getJSONObject(i).getString("name");
//                hangout_arr[i].name = hangout_name;
//                hangout_arr[i].address = hangout_name;
//            }
//            //ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, hangout_arr);
//            //ListView hangout_list = (ListView) findViewById(R.id.hangout_list);
//            //hangout_list.setAdapter(adapter);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        mRecyclerView = (RecyclerView) findViewById(R.id.hangout_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new HangoutsAdapter(SplashActivity.hangout_arr);
        mRecyclerView.setAdapter(mAdapter);
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //// TODO: 29/02/2016  load location of me and partner
        //show on map

    }

    private void createMarker(LatLng latLng, String name, String address) {
        mCurHue += HUE_DIFF; 
        
        Marker marker = mMap.addMarker(
                new MarkerOptions()
                        .position(latLng)
                        .icon(
                                BitmapDescriptorFactory.defaultMarker(
                                        mCurHue
                                )));
        
    }
}

