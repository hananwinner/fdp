package com.fractureof.demos.location;

import android.app.DownloadManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.fractureof.demos.location.util.SyncanoUtils;
import com.google.gson.JsonObject;
import com.syncano.library.Syncano;
import com.syncano.library.data.CodeBox;

import org.json.JSONArray;

import okhttp3.OkHttpClient;

public class PartnerSuggestionProvider extends ContentProvider {
    public PartnerSuggestionProvider() {
    }

//    JSONArray dates = null;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        //make sure the date list is loaded
//        if ( dates == null ) {
//            OkHttpClient client = new OkHttpClient();
//            okhttp3.Request request = new okhttp3.Request.Builder().url()
//
//        }

        //need to get the user a cursor in members that match the query
        String query = uri.getLastPathSegment().toUpperCase();
        if ( query.length() >= 3) {
            //request members from server
            JSONArray result = getMembers(query, "female");
        }







        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private JSONArray getMembers(String query, String gender) {
        JsonObject obj = new JsonObject();
        obj.addProperty("query", query);
        obj.addProperty("gender", gender);

        JSONArray members = SyncanoUtils.queryCodeBox(15, obj, "getMembers");
        return members;
    }
}
