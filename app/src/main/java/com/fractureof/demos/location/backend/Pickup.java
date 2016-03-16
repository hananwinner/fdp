package com.fractureof.demos.location.backend;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by hanan on 15/03/2016.
 */
public class Pickup {
    public Pickup(LatLng meetupLatLng,
                  LatLng dateLatLng,
                  LatLng meLatLng,
                  LatLng partnerLatLng,
                  boolean mePickup,
                  boolean partnerPickup,
                  TransportationMeans meTrans,
                  TransportationMeans partnerTrans,
                  HangoutType hangoutType) {
        this.meetupLatLng = meetupLatLng;
        this.dateLatLng = dateLatLng;
        this.meLatLng = meLatLng;
        this.partnerLatLng = partnerLatLng;
        this.mePickup = mePickup;
        this.partnerPickup = partnerPickup;
        this.meTrans = meTrans;
        this.partnerTrans = partnerTrans;
        this.hangoutType = hangoutType;
    }

    public String meetupPoint; //can be null
    public LatLng meetupLatLng;
    public String dateLocation;
    public LatLng dateLatLng;
    public String meLocation;
    public LatLng meLatLng;
    public String partnerLocation;
    public LatLng partnerLatLng;
    public boolean mePickup;
    public boolean partnerPickup;
    public TransportationMeans meTrans;
    public TransportationMeans partnerTrans;
    public HangoutType hangoutType;

    public boolean dateLocationSet() {
        return dateLatLng != null;
    }
    public boolean areMeetingInMeetupPoint() {
        return meetupLatLng != null;
    }
    public boolean mePickingUp() {
        return this.mePickup;
    }
    public boolean partnerPickingUp() {
        return this.partnerPickup;
    }

}
