package com.fractureof.demos.location;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.fractureof.demos.location.backend.UserProfile;
import com.fractureof.demos.location.backend.codebox.HangoutsResponse;
import com.fractureof.demos.location.dummy.DatePlansContent;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.syncano.library.Syncano;
import com.syncano.library.api.Response;
import com.syncano.library.choice.TraceStatus;
import com.syncano.library.data.CodeBox;
import com.syncano.library.data.Trace;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.Collection;

import static junit.framework.Assert.assertTrue;

/*
retrieve the user_profile
retrieve pubs within 50 km from partner
 */

public class SplashActivity extends AppCompatActivity {

    public static Syncano syncano =
//            Syncano.init("83d5b6d706b3584108ac8c543a0b4809c9a4a8a5", "polished-night-6282");
            Syncano.init("3019540e5c2a045ad745b4e52741041adeea3a10", "polished-night-6282");
    public static Bitmap avatarBitmap = null;

    public static float temp_hangout_distance = 50.0f;
    public static String temp_hangout_vicinity = "partner";
    public static String temp_me_geo_id = "Ei9TZGVyb3QgRGF2aWQgSGFNZWxlY2ggMzUsIFRlbCBBdml2LVlhZm8sIElzcmFlbA";
    public static LatLng temp_me_latLng = new LatLng(32.0800473f,34.78528850000001f);
    public static LatLng temp_part_latLng = new LatLng(32.09353299999999f, 34.783258f);
    public static String temp_partner_geo_id = "ChIJL1AsW_JLHRURaRVnUxu6HSg";
    public static String list_str = "";
    //public static Collection<HangoutsResponse> hangoutsList;
    public static JSONArray hangout_arr;
    public static JSONArray date_plans_arr;
    public static float temp_me_lat = 32.0800473f;
    public static float temp_me_lng = 34.78528850000001f;
    public static Bitmap partnerMarkerBitmap;
    public static Bitmap meMarkerBitmap;
    public static Bitmap partnerAvatarBitmap;
    public static Bitmap fPartnerQm;
    //public static AccessToken fb_acc_token;

    class RetrieveInfoTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(getApplicationContext(), FacebookLoginActivity.class);
//            intent.putExtra(PlansDetailFragment.ARG_ITEM_ID,"1");
            startActivity(intent);
            finish();
        }

        @Override
        protected Void doInBackground(Void...params) {

            Response<UserProfile> result = syncano.getObject(UserProfile.class, Integer.decode("11").intValue()).send();
            if (result.isSuccess()) {
                Uri url1 = Uri.parse(result.getData().avatar.getLink());
                try {
                    avatarBitmap = Picasso.with(getApplicationContext()).load(url1).get();
                } catch (IOException ex) { }
            } else {
                try {
                    avatarBitmap = Picasso.with(getApplicationContext()).load(R.drawable.me_avatar_default).get();
                } catch (IOException ex) { }
            }
            try {
                SplashActivity.partnerMarkerBitmap = Picasso.with(getApplicationContext()).load(R.drawable.partner_marker).get();
            } catch (IOException ex) {}
            try {
                SplashActivity.fPartnerQm= Picasso.with(getApplicationContext()).load(R.drawable.f_partner_qm).get();
            } catch (IOException ex) {}
            try {
                SplashActivity.meMarkerBitmap = Picasso.with(getApplicationContext()).load(R.drawable.me_marker).get();
            } catch (IOException ex) {}
            try {
                SplashActivity.partnerAvatarBitmap= Picasso.with(getApplicationContext()).load(R.drawable.partner_avatar_f).get();
            } catch (IOException ex) {}
            retrieveNearbyHangouts();
            retrieveDatePlansSum();
            DatePlansContent.loadFromJson(SplashActivity.date_plans_arr);


            return null;
        }

        private void retrieveNearbyHangouts() {
            //load locations list
            JsonObject obj = new JsonObject();
            obj.addProperty("lat", "32.0800473");
            obj.addProperty("radius", ((Float)temp_hangout_distance).toString());
            obj.addProperty("lon", "34.78528850000001");

            CodeBox cbx = new CodeBox(3);
            Response<Trace> response2 = cbx.run(obj);
            if (response2.isSuccess()) {
                Trace trace = cbx.getTrace();
                long start = System.currentTimeMillis();
                // wait until codebox finishes execution
                while (System.currentTimeMillis() - start < 5000 && trace.getStatus() != TraceStatus.SUCCESS) {
                    assertTrue(trace.fetch().isSuccess());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    hangout_arr = new JSONArray(trace.getOutput());
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private void retrieveDatePlansSum() {
            //load locations list
            JsonObject obj = new JsonObject();
            obj.addProperty("user_profile", "11");

            CodeBox cbx = new CodeBox(7);
            Response<Trace> response2 = cbx.run(obj);
            if (response2.isSuccess()) {
                Trace trace = cbx.getTrace();
                long start = System.currentTimeMillis();
                // wait until codebox finishes execution
                while (System.currentTimeMillis() - start < 5000 && trace.getStatus() != TraceStatus.SUCCESS) {
                    assertTrue(trace.fetch().isSuccess());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    date_plans_arr = new JSONArray(trace.getOutput());
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }





    }

    //    public static Bitmap scaledAvatarBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.fractureof.demos.location",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        //FacebookSdk.sdkInitialize(getApplicationContext());
//        String keyhash = com.facebook.FacebookSdk.getApplicationSignature(getApplicationContext());
        //fb_acc_token =  AccessToken.getCurrentAccessToken();

        new RetrieveInfoTask().execute(null,null,null);
    }
}
