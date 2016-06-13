package com.fractureof.demos.location;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.fractureof.demos.location.logic.PartnerSuggestionsServer;

import java.util.ArrayList;
import java.util.List;

public class SearchableActivity
        extends AppCompatActivity
        implements SearchView.OnQueryTextListener,
        PartnerSearchAdapter.ViewHolder.IMyViewHolderClicks {

    public static class Consts {
        public static String datingPartnerId =  "com.fractureof.demos.location.datingPartnerId";
        public static String datingPartnerFmtName =  "com.fractureof.demos.location.datingPartnerFmtName";
        public static int datingPartnerIdNotExists = -1;
    }

    PartnerSuggestionsServer mServer = PartnerSuggestionsServer.makePartnerSuggestionsServer();

    private String mQuery = "";
    SearchView mSearchView;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<PartnerSearchAdapter.PartnerSearchModel> mPartnerResults = new ArrayList<>();

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

        mRecyclerView = (RecyclerView) findViewById(R.id.partner_search_recycler_view);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PartnerSearchAdapter(this, mPartnerResults, this);
        mRecyclerView.setAdapter(mAdapter);
//        Intent intent = getIntent();
//        if ( Intent.ACTION_SEARCH.equals( intent.getAction() ) ) {
//            mQuery = intent.getStringExtra(SearchManager.QUERY);
//            Log.d("OnCreate",String.format("SEARCH : query: ",mQuery == null ? "none" : mQuery));
//        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
//            Log.d("OnCreate","VIEW");
//            String dataString = intent.getDataString();
//            Uri uri = Uri.parse(dataString);
//            String syncanoId = uri.getLastPathSegment();
//            Integer syncanoId_int = Integer.parseInt(syncanoId);
//
//
//            if (syncanoId_int == -1) {
//                //new Dating Partner Object shall be created.
//                // step back to partner name entry with the name set
//            }
//            Log.d("OnCreate",String.format("VIEW: ID : %d",syncanoId_int));
//            Intent partnerEntryNameIntent = new Intent(
//                    getApplicationContext(),
//                    PartnerNameEntryActivity.class);
//            partnerEntryNameIntent.putExtra(Consts.datingPartnerId,syncanoId_int);
//            partnerEntryNameIntent.setAction(Intent.ACTION_VIEW);
//            Log.d("OnCreate","starts partnerEntryNameIntent");
//            startActivity(partnerEntryNameIntent);
//            Log.d("OnCreate","started partnerEntryNameIntent");
//            finish();
//            Log.d("OnCreate","finished searchable activity");

        //}
    }

    @Override
    public void onPartnerSelected(int idx) {
        Log.d(this.getClass().getSimpleName(),
                String.format("Partner selected using index %d.",idx));
        Intent partnerEntryNameIntent = new Intent(
            getApplicationContext(),
            PartnerNameEntryActivity.class);
        partnerEntryNameIntent.setAction(Intent.ACTION_VIEW);
        PartnerSearchAdapter.PartnerSearchModel item = mPartnerResults.get(idx);
        int partnerBackendId = item.partnerBackendId;
        String formattedName = item.formattedName;

        //the task of creating the new partner record is deferred to a loader in.
        //A module responsible of creating things as we go.
        //an activity checks on loadfer
        //we need to create this item, because we would like to proceed with the
        //

        Log.d(this.getClass().getSimpleName(),
                String.format("Partner properties being put as Extra: syncano id: %d, fmtName: %s",
                        partnerBackendId, formattedName));
        partnerEntryNameIntent.putExtra(Consts.datingPartnerId,partnerBackendId);
        partnerEntryNameIntent.putExtra(Consts.datingPartnerFmtName,formattedName);
        startActivity(partnerEntryNameIntent);
        finish();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        new RetrieveInfoTask().execute(newText,null,null);
        return false;
    }

    public void swapAdapter() {
        mAdapter = new PartnerSearchAdapter(this, mPartnerResults, this);
        mRecyclerView.swapAdapter(mAdapter,false);
    }

    class RetrieveInfoTask extends AsyncTask<String, Void, Void> {

        List<PartnerSearchAdapter.PartnerSearchModel> lll;
        @Override
        protected void onPostExecute(Void aVoid) {
            mPartnerResults = lll;
            swapAdapter();
        }

        @Override
        protected Void doInBackground(String...params) {
           Cursor cursor = mServer.process(params[0]);
            lll = new ArrayList<>(cursor.getCount());
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                // The Cursor is now set to the right position
                lll.add( new PartnerSearchAdapter.PartnerSearchModel(
                        cursor.getString( cursor.getColumnIndex( SearchManager.SUGGEST_COLUMN_TEXT_1)),
                        cursor.getString(cursor.getColumnIndex( SearchManager.SUGGEST_COLUMN_TEXT_2)),
                        cursor.getInt(cursor.getColumnIndex( SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID)),
                        cursor.getString(cursor.getColumnIndex( SearchManager.SUGGEST_COLUMN_ICON_1)))
                        );
            }

            return null;
        }

    }
}
