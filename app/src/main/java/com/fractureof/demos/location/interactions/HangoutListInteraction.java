//package com.fractureof.demos.location.interactions;
//
//import com.google.android.gms.maps.model.LatLng;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by hanan on 01/03/2016.
// *
// * a list of hangout places and the way they are presented
// */
//
//public class HangoutListInteraction implements HangoutChangeListener {
//    @Override
//    public void hangoutChange(LatLng latLng) {
//        //do nothing
//    }
//
//    public void hangoutChange(String hangoutGeoId) {
//        for (int index = 0 ; index < list.size() ; ++index) {
//            if (list.get(index).geoId == hangoutGeoId) {
//                mCurSelectedHangout = index;
//            }
//        }
//    }
//    class HangoutListInteractionItem {
//        public String getName() {
//            return name;
//        }
//        public String getAddress() {
//            return address;
//        }
//        private String name;
//        private String address;
//        private String geoId;
//
//        private HangoutListInteractionItem(String name, String address, String geoId) {
//            this.name = name;
//            this.address = address;
//            this.geoId = geoId;
//        }
//        private HangoutListInteractionItem createHangoutListInteractionItem(String name, String address, String geoId) {
//            return new HangoutListInteractionItem(name, address, geoId);
//        }
//    }
//
//    private List<HangoutListInteractionItem> list = new ArrayList<>();
//    private int mCurSelectedHangout = -1;
//
//    private HangoutListInteraction( List<HangoutListInteractionItem> hangoutList) {
//        this.list = hangoutList;
//    }
//    public void selectHangout(int index) {
//        if (index < 0) {
//            throw new IllegalArgumentException();
//        }
//        if (index > list.size() -1 ) {
//            throw new IllegalArgumentException();
//        }
//        if (index != mCurSelectedHangout) {
//            mCurSelectedHangout = index;
//            onHangeoutChanged(list.get(index).geoId);
//        }
//    }
//
//    private List<HangoutChangeListener> hangoutChangeListeners = new ArrayList<HangoutChangeListener>();
//
//    public void addListener(HangoutChangeListener toAdd) {
//        hangoutChangeListeners.add(toAdd);
//    }
//
//    private void onHangeoutChanged(String hangoutGeoId) {
//        for (HangoutChangeListener l : hangoutChangeListeners) {
//            l.hangoutChange(hangoutGeoId);
//
//        }
//    }
//}
