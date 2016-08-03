package com.fractureof.demos.location.wizard.model;

import android.support.v4.app.Fragment;

import com.fractureof.demos.location.SplashActivity;
import com.fractureof.demos.location.backend.DatePlan;
import com.fractureof.demos.location.backend.FeWizPageMyLocation;
import com.fractureof.demos.location.wizard.ui.MyLocationFragment;

import java.util.ArrayList;

/**
 * Created by tyler on 31/07/2016.
 */
public class MyLocationPage extends Page{

    public static final String MY_LOCATION_GEO_ID_DATA_KEY = "MY_LOCATION_GEO_ID";
    public static final String MY_LOCATION_LAT_DATA_KEY = "MY_LOCATION_LAT";
    public static final String MY_LOCATION_LNG_DATA_KEY = "MY_LOCATION_LNG";
    public static final String MY_LOCATION_ADDRESS_DESC_DATA_KEY = "MY_LOCATION_ADDRESS_DESC";

    public MyLocationPage(ModelCallbacks callbacks, String title, DatePlan datePlan) {
        super(callbacks, title, datePlan);
        mBackendObject = new FeWizPageMyLocation();
        ((FeWizPageMyLocation) mBackendObject).datePlanId = datePlan.getId();
        String initialMyGeoId = SplashActivity.temp_me_geo_id;
        mData.putString(MY_LOCATION_GEO_ID_DATA_KEY,initialMyGeoId);
        ((FeWizPageMyLocation) mBackendObject).myGeoId = initialMyGeoId;
        float initialMyLat = SplashActivity.temp_me_lat;
        mData.putDouble(MY_LOCATION_LAT_DATA_KEY,initialMyLat);
        ((FeWizPageMyLocation) mBackendObject).myLat = initialMyLat;
        float initialMyLng = SplashActivity.temp_me_lng;
        mData.putDouble(MY_LOCATION_LNG_DATA_KEY,initialMyLng);
        ((FeWizPageMyLocation) mBackendObject).myLng = initialMyLng;
        mData.putString(MY_LOCATION_ADDRESS_DESC_DATA_KEY, SplashActivity.temp_me_geo_location_text);
    }

    @Override
    public Fragment createFragment() {

        return MyLocationFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {

    }

    @Override
    protected void doSetBackendData() {
        ((FeWizPageMyLocation) mBackendObject).myGeoId = mData.getString(MY_LOCATION_GEO_ID_DATA_KEY);
        ((FeWizPageMyLocation) mBackendObject).myLat = (float) mData.getDouble(MY_LOCATION_LAT_DATA_KEY);
        ((FeWizPageMyLocation) mBackendObject).myLng = (float) mData.getDouble(MY_LOCATION_LNG_DATA_KEY);
    }
}
