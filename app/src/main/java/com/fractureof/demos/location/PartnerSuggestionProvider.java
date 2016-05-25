package com.fractureof.demos.location;

import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.fractureof.demos.location.logic.PartnerSuggestionsServer;
import com.fractureof.demos.location.util.SyncanoConsts;
import com.fractureof.demos.location.util.SyncanoUtils;
import com.google.gson.JsonObject;
import com.syncano.library.Syncano;
import com.syncano.library.data.CodeBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import okhttp3.OkHttpClient;

public class PartnerSuggestionProvider extends ContentProvider {

    public PartnerSuggestionProvider() {
    }

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

    /*
    Supplies list of members.
    while searching, recent dates still appear even if last character dod not match (V2)
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        PartnerSuggestionsServer server = PartnerSuggestionsServer.makePartnerSuggestionsServer();
        return server.getCursor(uri.getLastPathSegment());
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
