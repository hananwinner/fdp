package com.fractureof.demos.location.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by tyler on 03/08/2016.
 */
public class MapUtils {
    public static String geocodeAddressDescription(Context context, LatLng latLng) {
        Geocoder geocoder = new Geocoder(context);
        String addressDescription = String.format("%s,%s",latLng.latitude,latLng.longitude);
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addressList.size() > 0) {
                Address address = addressList.get(0);
                if (address.getMaxAddressLineIndex() > 0)
                {
                    addressDescription = address.getAddressLine(0);
                } else if (address.getFeatureName() != null) {
                    addressDescription = address.getFeatureName();
                } else if (address.getAdminArea() != null) {
                    addressDescription = address.getAdminArea();
                }
            }
        } catch (IOException ex) {}
        return addressDescription;
    }
}
