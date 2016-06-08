package com.fractureof.demos.location;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class SearchableActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static class Consts {
        public static String datingPartnerId =  "com.fractureof.demos.location.datingPartnerId";
        public static int datingPartnerIdNotExists = -1;
    }

    private String mQuery = "";
    SearchView mSearchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);


        MenuItem searchItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        // Assumes current activity is the searchable activity
        mSearchView.setSearchableInfo( searchManager.getSearchableInfo(
                new ComponentName(this, SearchableActivity.class)
                ));
        mSearchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        mSearchView.setIconified(false);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        Intent intent = getIntent();
        if ( Intent.ACTION_SEARCH.equals( intent.getAction() ) ) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            Log.d("OnCreate",String.format("SEARCH : query: ",mQuery == null ? "none" : mQuery));
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Log.d("OnCreate","VIEW");
            String dataString = intent.getDataString();
            Uri uri = Uri.parse(dataString);
            String syncanoId = uri.getLastPathSegment();
            Integer syncanoId_int = Integer.parseInt(syncanoId);


            if (syncanoId_int == -1) {
                //new Dating Partner Object shall be created.
                // step back to partner name entry with the name set
            }
            Log.d("OnCreate",String.format("VIEW: ID : %d",syncanoId_int));
            Intent partnerEntryNameIntent = new Intent(
                    getApplicationContext(),
                    PartnerNameEntryActivity.class);
            partnerEntryNameIntent.putExtra(Consts.datingPartnerId,syncanoId_int);
            partnerEntryNameIntent.setAction(Intent.ACTION_VIEW);
            Log.d("OnCreate","starts partnerEntryNameIntent");
            startActivity(partnerEntryNameIntent);
            Log.d("OnCreate","started partnerEntryNameIntent");
            finish();
            Log.d("OnCreate","finished searchable activity");

        }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
