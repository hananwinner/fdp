package com.fractureof.demos.location;

import android.app.SearchManager;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fractureof.demos.location.backend.DatingPartner;
import com.syncano.library.Syncano;
import com.syncano.library.api.Response;

import java.util.List;

public class PartnerNameEntryActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    Syncano mBackend = SplashActivity.syncano;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_name_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView drawerHeaderIcon = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_icon);
        drawerHeaderIcon.setImageDrawable(new RoundedAvatarDrawable(SplashActivity.avatarBitmap));

        EditText nameEntry = (EditText) findViewById(R.id.name_entry);

        //get the id of dating partner if in the extra
        Log.d("OnCreate","checking Intent");
        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {

            int datingPartnerId = intent.getIntExtra(
                SearchableActivity.Consts.datingPartnerId,
                SearchableActivity.Consts.datingPartnerIdNotExists);
            Log.d("OnCreate",String.format("checking Intent. id : %d",datingPartnerId));

            nameEntry.setText("...");
            nameEntry.setEnabled(false);
        } else {
            nameEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SearchableActivity.class);
                    intent.putExtra(SearchManager.QUERY, "");
                    startActivity(intent);
                    finish();
                }
            });
            nameEntry.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent intent = new Intent(getApplicationContext(), SearchableActivity.class);
                    intent.putExtra(SearchManager.QUERY, "");
                    startActivity(intent);
                    finish();
                    return true;
                }
            });
            nameEntry.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Intent intent = new Intent(getApplicationContext(), SearchableActivity.class);
                    intent.putExtra(SearchManager.QUERY, s.toString());
                    startActivity(intent);
                    finish();
                }
            });
        }
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
}
