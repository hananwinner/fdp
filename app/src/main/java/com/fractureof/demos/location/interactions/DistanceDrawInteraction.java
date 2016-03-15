package com.fractureof.demos.location.interactions;

import com.fractureof.demos.location.backend.PickupMethod;
import com.fractureof.demos.location.logic.DistanceCalculator;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanan on 29/02/2016.
 */
public class DistanceDrawInteraction implements HangoutLocationChangeListener {
    private DistanceDrawInteraction(LatLng meLatLng, LatLng hangoutLatLng, LatLng partnerLatLng) {
        this.meLatLng = meLatLng;
        this.hangoutLatLng = hangoutLatLng;
        this.partnerLatLng = partnerLatLng;
    }

    private LatLng meLatLng;
    private LatLng partnerLatLng;
    private LatLng hangoutLatLng;
    private double distanceBetweenUs = -1;
    private double distanceHangoutMe = -1;
    private double distanceHangoutPartner = -1;

    private void calcDistanceBetweenUs() {
        this.distanceBetweenUs = DistanceCalculator.distance(meLatLng.latitude,meLatLng.longitude,partnerLatLng.latitude,
                partnerLatLng.longitude, "K");
    }

    private void calcDistanceHangoutMe() {
        this.distanceHangoutMe = DistanceCalculator.distance(
                meLatLng.latitude,meLatLng.longitude,
                hangoutLatLng.latitude,
                hangoutLatLng.longitude, "K");
    }

    private void calcDistanceHangoutPartner() {
        this.distanceHangoutPartner = DistanceCalculator.distance(
                partnerLatLng.latitude,
                partnerLatLng.longitude,
                hangoutLatLng.latitude,
                hangoutLatLng.longitude, "K");
    }

    public double getDistanceBetweenUs() {
        if (distanceBetweenUs < 0) {
            calcDistanceBetweenUs();
        }
        return distanceBetweenUs;
    }

    public double getDistanceHangoutMe() {
        if (distanceHangoutMe< 0) {
            calcDistanceBetweenUs();
        }
        return distanceHangoutMe;
    }

    public double getDistanceHangoutPartner() {
        if (distanceHangoutPartner < 0) {
            calcDistanceBetweenUs();
        }
        return distanceHangoutPartner;
    }

    @Override
    public void hangoutChange(LatLng latLng) {
        this.hangoutLatLng = latLng;
        calcDistanceHangoutMe();
        calcDistanceHangoutPartner();
    }


}
