package com.fractureof.demos.location.interactions;

import com.fractureof.demos.location.backend.PickupMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanan on 29/02/2016.
 */
public class DistanceDrawInteraction implements HangoutChangeListener {
    private PickupMethod pickupMethod;
    private String meGeoId;
    private String partnerGeoId;
    private String hangoutGeoId;
    private double distanceBetweenUs = -1;
    private double distanceHangoutMe = -1;
    private double distanceHangoutPartner = -1;
    private String meAvatarURL;
    private String partnerAvatarURL;

    private DistanceDrawInteraction(PickupMethod pickupMethod,
                                    String meGeoId,
                                    String partnerGeoId,
                                    String hangoutGeoId,
                                    String meAvatarURL,
                                    String partnerAvatarURL) {
        this.pickupMethod = pickupMethod;
        this.meGeoId = meGeoId;
        this.partnerGeoId = partnerGeoId;
        this.hangoutGeoId = hangoutGeoId;
        this.meAvatarURL = meAvatarURL;
        this.partnerAvatarURL = partnerAvatarURL;
    }
    public static DistanceDrawInteraction createDistanceDrawInteraction
            (PickupMethod pickupMethod,
             String meGeoId,
             String partnerGeoId,
             String hangoutGeoId,
             String meAvatarURL,
             String partnerAvatarURL) {
        return new DistanceDrawInteraction(
                pickupMethod,
                meGeoId,
                partnerGeoId,
                hangoutGeoId,
                meAvatarURL,
                partnerAvatarURL);
    }
//    public void changeHangout( String hangoutGeoId) {
//        this.hangoutGeoId = hangoutGeoId;
//        for (HangoutChangeListener l : hangoutChangeListeners) {
//            l.hangoutChange(hangoutGeoId);
//        }
//    }
//
//    private List<HangoutChangeListener> hangoutChangeListeners = new ArrayList<HangoutChangeListener>();
//
//    public void addListener(HangoutChangeListener toAdd) {
//        hangoutChangeListeners.add(toAdd);
//    }

    private void calcDistanceBetweenUs() {

    }

    private void calcDistanceHangoutMe() {

    }

    private void calcDistanceHangoutPartner() {

    }

    private double getDistanceBetweenUs() {
        if (distanceBetweenUs < 0) {
            calcDistanceBetweenUs();
        }
        return distanceBetweenUs;
    }

    private double getDistanceHangoutMe() {
        if (distanceHangoutMe< 0) {
            calcDistanceBetweenUs();
        }
        return distanceHangoutMe;
    }

    private double getDistanceHangoutPartner() {
        if (distanceHangoutPartner < 0) {
            calcDistanceBetweenUs();
        }
        return distanceHangoutPartner;
    }

    @Override
    public void hangoutChange(String hangoutGeoId) {
        this.hangoutGeoId = hangoutGeoId;
        calcDistanceHangoutMe();
        calcDistanceHangoutPartner();
    }
}
