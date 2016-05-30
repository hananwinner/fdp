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
 * Created by tyler on 30/05/2016.
 */
public class PartnerSearchEvaluationUnit {

    MatrixCursor mCursor = null;
    String mQuery = null;
    boolean mIsSearchMode = false;
    private int currentIndex = 0;
    private JSONArray mDatesArr = null;

    Set<Integer> recent_date_member_ids = new HashSet<Integer>(Consts.maxNumRecentDatesInSearchMode);


    static class Consts {
        static int minimumQueryLengthForMemeberSearch = 3;
        static int maxNumRecentDatesInSearchMode = 3;
        public static String NEW_PARTNER = "Your new friend..";
        public static String RECENT_DATE_WITHOUT_DATETIME_FEMALE = "You planned a date with her.";
        public static String RECENT_DATE_WITHOUT_DATETIME_MALE = "You planned a date with him.";
        //String RECENT_DATE_WITH_DATETIME_PREFIX_FEMALE = "You planned a date with her ";
        public static String MEMBER = "Member of Kipud";
    }

    private static boolean determineMode(String query) {
        int queryLength = query.length();
        //either search mode or just showing a few recent dates.
        boolean searchMode = queryLength >= Consts.minimumQueryLengthForMemeberSearch;
        return searchMode;
    }

    public PartnerSearchEvaluationUnit(String query) {
        mCursor = makeCursor();
        mQuery = query;
        mIsSearchMode = determineMode(query);
    }

    public Cursor evaluate() {
        appendSearchedItem();
        appendRecentDates();
        appendMembersSearch();
        return mCursor;
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

    private void appendSearchedItem() {
        if ( mIsSearchMode ) {
            mCursor.addRow(new Object[]{currentIndex++,
                    mQuery,
                    Consts.NEW_PARTNER,
                    SyncanoConsts.NOT_EXIST_ID,
                    R.drawable.partner_avatar_f});
        }
    }

    private void appendRecentDates() {
        Log.d(String.format("appendRecentDates (T %d)",Thread.currentThread().getId()),"start");
        Log.d(String.format("appendRecentDates (T %d)",Thread.currentThread().getId()),"need reloading list");
        Log.d(this.getClass().getSimpleName(),"appendRecentDates start");
        JsonObject input = new JsonObject();
        input.addProperty("user_profile", 11);
        mDatesArr = SyncanoUtils.queryCodeBox(8, input, "GetDates");
        Log.d(String.format("appendRecentDates (T %d)",Thread.currentThread().getId()),
                "got list. length: " + mDatesArr.length());

        try {
            for (int i = 0; i < mDatesArr.length(); ++i) {
                JSONObject obj = mDatesArr.getJSONObject(i);
                String name = obj.getString("name");
                Log.d(String.format("appendRecentDates (T %d)", Thread.currentThread().getId()),
                        String.format("Inspecting recent date %d, %s..", i, name)
                );
                if (!mIsSearchMode) { //just add a few recents
                    Log.d(String.format("appendRecentDates (T %d)", Thread.currentThread().getId()), "adding recent date bc string query toos short");
                    String dating_partner_id = obj.getString("id");
                    if (dating_partner_id != null) {//else this is unexpected problem..
                        addRecentDateToCursor(obj, name, dating_partner_id);
                    } else {
                        Log.d(String.format("appendRecentDates (T %d)", Thread.currentThread().getId()), "failed to add recent date from missing id!");
                    }
                } else {
                    String lowerName = name.toLowerCase();
                    if (lowerName.startsWith(mQuery)) {
                        //object id in syncano - in this case of the dating partner
                        String dating_partner_id = obj.getString("id");
                        if (dating_partner_id != null) {//else this is unexpected problem..
                            addRecentDateToCursor(obj, name, dating_partner_id);
                            Integer id = checkUpdateRecentDateMembersListIfMember(obj);
                            //todo load image uri if id is not null..
                        } else {
                            //TODO: log unecpetd missing id in sycano objects..
                        }
                    } else {
                        Log.d(String.format("appendRecentDates (T %d)",
                                Thread.currentThread().getId()),
                                String.format("date %s doe not match query %s", lowerName, mQuery));
                    }
                }
            }
        } catch (JSONException ex) {
            Log.d(String.format("appendRecentDates (T %d)",
                    Thread.currentThread().getId()),
                    String.format("JSONException : %s", ex.toString()));
        }
    }

    private void addRecentDateToCursor(JSONObject recentDate, String name, String dating_partner_id) throws JSONException {
        Log.d(String.format("appendRecentDates (T %d)",Thread.currentThread().getId()),"Adding recent date to cursor..");
        Integer dating_partner_id_int = Integer.parseInt(dating_partner_id);
        Log.d(String.format("appendRecentDates (T %d)",Thread.currentThread().getId()),"Adding recent date to cursor. id :" + dating_partner_id_int);
        String suggestionFormattedName = makeFormattedSuggestionName(recentDate, name);
        mCursor.addRow(new Object[]{
                currentIndex++,
                suggestionFormattedName,
                Consts.RECENT_DATE_WITHOUT_DATETIME_FEMALE, //TODO
                dating_partner_id,
                R.drawable.partner_avatar_f
        });
        Log.d(String.format("appendRecentDates (T %d)",Thread.currentThread().getId()),"Adding recent date to cursor: success.");
    }

    private void appendMembersSearch() {
        //need a to query members by the query

    }

    private static String makeFormattedSuggestionName(JSONObject obj, String name) throws JSONException {
        Log.d(String.format("appendRecentDates (T %d)",Thread.currentThread().getId()),"formatting the suggested name");
        String nickname = obj.getString("nickname");
        String suggestionFormattedName = name;
        if ( nickname != null ) {
            suggestionFormattedName = String.format("%s (%s)", name, nickname);
        }
        Log.d(String.format("appendRecentDates (T %d)",Thread.currentThread().getId()),"formatting the suggested name: success.");
        return suggestionFormattedName;
    }

    private Integer checkUpdateRecentDateMembersListIfMember(JSONObject recentDate) {
        try {
            Integer user_member_id_int = recentDate.getInt("member_user_id");
            recent_date_member_ids.add(user_member_id_int);
            return user_member_id_int;

        } catch (JSONException ex) {
            //TODO log ..
            return null;
        }
    }
}
