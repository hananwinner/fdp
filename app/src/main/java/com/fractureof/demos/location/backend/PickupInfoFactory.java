package com.fractureof.demos.location.backend;

import com.fractureof.demos.location.SplashActivity;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by hanan on 15/03/2016.
 */
public class PickupInfoFactory {
    public static Pickup getPickupInfo() {
        LatLng meetupLatLng = null;
        LatLng dateLatLng  = null;
        LatLng meLatLng = SplashActivity.temp_me_latLng;
        LatLng partnerLatLng = SplashActivity.temp_part_latLng;
        boolean mePickup = false;
        boolean partnerPickup = false;
        TransportationMeans meTrans = TransportationMeans.WALK;
        TransportationMeans partnerTrans = TransportationMeans.WALK;
        HangoutType hangoutType = new HangoutType( "drink");
        return new Pickup(
                meetupLatLng,
                dateLatLng,
                meLatLng,
                partnerLatLng,
                mePickup,
                partnerPickup,
                meTrans,
                partnerTrans,
                hangoutType
        );
    }
}
