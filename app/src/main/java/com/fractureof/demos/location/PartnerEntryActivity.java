package com.fractureof.demos.location;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

public class PartnerEntryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker mPartnerMarker = null;
    private Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


//        ImageView drawerHeaderIcon = (ImageView) navigationView.findViewById(R.id.imageView);
//        drawerHeaderIcon.setImageBitmap(SplashActivity.avatarBitmap);
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
        getMenuInflater().inflate(R.menu.partner_entry, menu);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ImageView drawerHeaderIcon = (ImageView) navigationView.findViewById(R.id.imageView);
        drawerHeaderIcon.setImageBitmap(SplashActivity.avatarBitmap);

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

        if (id == R.id.nav_date_plans) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(), PlansListActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        initOurMarkers();

    }
    private void initOurMarkers() {
        BitmapDescriptor bitmapDesc = BitmapDescriptorFactory.fromBitmap(SplashActivity.fPartnerQm);

        LatLng randomUnknownPartnerLoc = new LatLng(SplashActivity.temp_me_latLng.latitude + mRandom.nextFloat()/300,
                SplashActivity.temp_me_latLng.longitude + mRandom.nextFloat()/300);

        MarkerOptions opts = new MarkerOptions().position(randomUnknownPartnerLoc)
                .icon(bitmapDesc);
        mPartnerMarker =  mMap.addMarker(opts);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(SplashActivity.temp_part_latLng,
                12.0f));
        BitmapDescriptor bitmapDescMe = BitmapDescriptorFactory.fromBitmap(SplashActivity.meMarkerBitmap);
        MarkerOptions optsMe = new MarkerOptions().position(SplashActivity.temp_me_latLng)
                .icon(bitmapDescMe);
        mMap.addMarker(optsMe);
    }
}
