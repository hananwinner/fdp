package com.fractureof.demos.location.interactions;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanan on 14/03/2016.
 */
public class HangoutsMapInteraction implements HangoutChangeListener {
    public void hangoutChange(String hangoutGeoId) {
        for (int index = 0 ; index < hangoutList.size() ; ++index) {
            if (hangoutList.get(index).geoId == hangoutGeoId) {
                selected= hangoutList.get(index).latLng;
            }
        }
    }

    class HangoutsMapIntercationItem {
        private LatLng latLng;
        private String geoId;
    }

    private List<HangoutsMapIntercationItem> hangoutList;
    private LatLng selected;

    private HangoutsMapInteraction(
            //String meGeoId,
              //                     String partnerGeoId,
                                   List<HangoutsMapIntercationItem> hangoutList, int selected) {
        this.hangoutList = hangoutList;
        this.selected = hangoutList.get(selected).latLng;
    }
    private List<HangoutChangeListener> hangoutChangeListeners = new ArrayList<HangoutChangeListener>();

    public void addListener(HangoutChangeListener toAdd) {
        hangoutChangeListeners.add(toAdd);
    }

    private void onHangeoutChanged(String hangoutGeoId) {
        for (HangoutChangeListener l : hangoutChangeListeners) {
            l.hangoutChange(hangoutGeoId);
        }
    }

    public void selectHangout(LatLng latLng) {
        if (latLng != selected) {
            selected = latLng;
            for (int index = 0 ; index < hangoutList.size(); ++index) {
                if (hangoutList.get(index).latLng == selected) {
                    onHangeoutChanged(hangoutList.get(index).geoId);
                }
            }
        }
    }

}
