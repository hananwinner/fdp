package com.fractureof.demos.location.logic;

import android.app.SearchManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.fractureof.demos.location.R;
import com.fractureof.demos.location.SplashActivity;
import com.fractureof.demos.location.backend.DatingPartner;
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
    static class Consts {
        static int minimumQueryLengthForMemeberSearch = 3;
        static int maxNumRecentDatesInSearchMode = 3;
        public static String NEW_PARTNER = "Your new friend..";
        public static String RECENT_DATE_WITHOUT_DATETIME_FEMALE = "You planned a date with her.";
        public static String RECENT_DATE_WITHOUT_DATETIME_MALE = "You planned a date with him.";
        //String RECENT_DATE_WITH_DATETIME_PREFIX_FEMALE = "You planned a date with her ";
        public static String MEMBER = "Member of Kipud";
    }

    //private Uri providerRequestUri;
    private boolean isSearchMode = false;
    private int currentIndex = 0;
    private int numOfRecents = 0;
    private MatrixCursor mCursor;
    private String mCurrentQuery= null;
    Set<Integer> recent_date_member_ids = new HashSet<Integer>(Consts.maxNumRecentDatesInSearchMode);
    private JSONArray mDatesArr = null;

    private void reset() {
        this.recent_date_member_ids.clear();
        mCursor.close();
    }

    private PartnerSuggestionsServer() { }

    public static PartnerSuggestionsServer makePartnerSuggestionsServer() {
        return new PartnerSuggestionsServer();
    }


    public boolean checkChangeQuery(String query) {
        if (mCurrentQuery == null) {
            mCurrentQuery = query;
            mCursor = makeCursor();
            return true;
        } else {
            if (mCurrentQuery.equals(query)) {
                return false;
            } else {
                reset();
                mCurrentQuery = query;
                mCursor = makeCursor();
                return true;
            }
        }
    }

    private void determineMode() {
        int queryLength = mCurrentQuery.length();
        //either search mode or just showing a few recent dates.
        boolean searchMode = queryLength >= Consts.minimumQueryLengthForMemeberSearch;
        isSearchMode = searchMode;
    }

    public Cursor getCursor(String query) {
        if(checkChangeQuery(query)) {
            determineMode();
            appendSearchedItem();
            appendRecentDates();
            appendMembersSearch();
        }
        return mCursor;
    }

    private void appendRecentDates() {
        Log.d(String.format("appendRecentDates (T %l)",Thread.currentThread().getId()),"start");
        if (mDatesArr == null) {
            Log.d(String.format("appendRecentDates (T %l)",Thread.currentThread().getId()),"need reloading list");
            Log.d(this.getClass().getSimpleName(),"appendRecentDates start");
            JsonObject input = new JsonObject();
            input.addProperty("user_profile", 11);
            mDatesArr = SyncanoUtils.queryCodeBox(8, input, "GetDates");
            Log.d(String.format("appendRecentDates (T %l)",Thread.currentThread().getId()),
                    "got list. length: " + mDatesArr.length());
        }
        try {
            for (int i = 0; i < mDatesArr.length(); ++i) {
                JSONObject obj = mDatesArr.getJSONObject(i);
                String name = obj.getString("name");
                Log.d(String.format("appendRecentDates (T %l)",Thread.currentThread().getId()),
                        String.format("Inspecting recent date %d, %s..",i, name)
                );
                if ( !isSearchMode) { //just add a few recents
                    Log.d(String.format("appendRecentDates (T %l)",Thread.currentThread().getId()),"adding recent date bc string query toos short");
                    String dating_partner_id = obj.getString("id");
                    if (dating_partner_id != null) {//else this is unexpected problem..
                        addRecentDateToCursor(obj, name, dating_partner_id);
                    }
                    else {
                        Log.d(String.format("appendRecentDates (T %l)",Thread.currentThread().getId()),"failed to add recent date from missing id!");
                    }
                } else if ( name.startsWith(mCurrentQuery) ) {
                    //object id in syncano - in this case of the dating partner
                    String dating_partner_id = obj.getString("id");
                    if (dating_partner_id != null) {//else this is unexpected problem..
                        addRecentDateToCursor(obj, name, dating_partner_id);

                        Integer id = checkUpdateRecentDateMembersListIfMember(obj);
                        //todo load image uri if id is not null..
                    } else {
                        //TODO: log unecpetd missing id in sycano objects..
                    }
                }
            }
        } catch (JSONException ex) {}
    }

    private void addRecentDateToCursor(JSONObject recentDate, String name, String dating_partner_id) throws JSONException {
        Log.d(String.format("appendRecentDates (T %l)",Thread.currentThread().getId()),"Adding recent date to cursor..");
        Integer dating_partner_id_int = Integer.parseInt(dating_partner_id);
        Log.d(String.format("appendRecentDates (T %l)",Thread.currentThread().getId()),"Adding recent date to cursor. id :" + dating_partner_id_int);
        String suggestionFormattedName = makeFormattedSuggestionName(recentDate, name);
        mCursor.addRow(new Object[]{
                currentIndex++,
                suggestionFormattedName,
                Consts.RECENT_DATE_WITHOUT_DATETIME_FEMALE, //TODO
                dating_partner_id,
                R.drawable.partner_avatar_f
        });
        Log.d(String.format("appendRecentDates (T %l)",Thread.currentThread().getId()),"Adding recent date to cursor: success.");
    }

    private Integer checkUpdateRecentDateMembersListIfMember(JSONObject recentDate) {
        try {
            String user_member_id = recentDate.getString("member_user_id");
            if (user_member_id != null) {
                Integer user_member_id_int = Integer.parseInt(user_member_id);
                recent_date_member_ids.add(user_member_id_int);
                return user_member_id_int;
            } else {
                return null;
            }
        } catch (JSONException ex) {
            //TODO log ..
            return null;
        }
    }

    private String makeFormattedSuggestionName(JSONObject obj, String name) throws JSONException {
        Log.d(String.format("appendRecentDates (T %l)",Thread.currentThread().getId()),"formatting the suggested name");
        String nickname = obj.getString("nickname");
        String suggestionFormattedName = name;
        if ( nickname != null ) {
            suggestionFormattedName = String.format("%s (%s)", name, nickname);
        }
        Log.d(String.format("appendRecentDates (T %l)",Thread.currentThread().getId()),"formatting the suggested name: success.");
        return suggestionFormattedName;
    }

    private void appendMembersSearch() {
        //need a to query members by the query

    }


    private void appendSearchedItem() {
        if ( isSearchMode ) {
            mCursor.addRow(new Object[]{currentIndex++,
                    mCurrentQuery,
                    Consts.NEW_PARTNER,
                    SyncanoConsts.NOT_EXIST_ID,
                    R.drawable.partner_avatar_f});
        }
    }

    private static MatrixCursor makeCursor() {
        MatrixCursor cursor = new MatrixCursor(
                new String[] {
                        BaseColumns._ID,
                        SearchManager.SUGGEST_COLUMN_TEXT_1, //the full name with nickname if exist
                        SearchManager.SUGGEST_COLUMN_TEXT_2, //description: a recent date and/or a member or someone new.
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID,
                        SearchManager.SUGGEST_COLUMN_ICON_1
                }
        );
        return cursor;
    }
}
