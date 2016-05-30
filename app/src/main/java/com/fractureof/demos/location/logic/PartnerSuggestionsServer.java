package com.fractureof.demos.location.logic;

import android.app.SearchManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.util.Log;

import com.fractureof.demos.location.R;
import com.fractureof.demos.location.util.SyncanoConsts;
import com.fractureof.demos.location.util.SyncanoUtils;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tyler on 22/05/2016.
 *
 * responsible for:
 * compiling a list of partners suggestion for the partner search provider.
 *
 */
public class PartnerSuggestionsServer {

    public Cursor process(String query) {
        PartnerSearchEvaluationUnit ev = new PartnerSearchEvaluationUnit(query);
        return ev.evaluate();
    }


    private PartnerSuggestionsServer() { }

    public static PartnerSuggestionsServer makePartnerSuggestionsServer() {
        return new PartnerSuggestionsServer();
    }



}
