package com.fractureof.demos.location.interactions;

import com.fractureof.demos.location.backend.Hangout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanan on 01/03/2016.
 *
 * a list of hangout places and the way they are presented
 */



public class HangoutListInteraction {

    class HangoutListInteractionItem {
        public float mHue;
        public Hangout mHangout;

        public HangoutListInteractionItem(float mHue, Hangout mHangout) {
            this.mHue = mHue;
            this.mHangout = mHangout;
        }
    }

    private List<HangoutListInteractionItem> mList = new ArrayList<>();
    private float mDefaultCurHue = 0.0f;
    private final float hueDiff = 30.0f;
    private int mCurSelectedHangout = -1;
    private String hangoutGeoId;

    private HangoutListInteraction( List<Hangout> hangoutList) {
        mDefaultCurHue += hueDiff;
        for ( Hangout item : hangoutList)
        mList.add(
                new HangoutListInteractionItem(
                        mDefaultCurHue, item
                ));
    }
    public static HangoutListInteraction createHangoutListInteraction(List<Hangout> hangoutList) {
        return new HangoutListInteraction(hangoutList);
    }
    public void selectHangout(int index) {
        if (index < 0) {
            throw new IllegalArgumentException();
        }
        if (index > mList.size() -1 ) {
            throw new IllegalArgumentException();
        }
        mCurSelectedHangout = index;
        hangoutGeoId = mList.get(index).mHangout.geoId;
    }

    private List<HangoutChangeListener> hangoutChangeListeners = new ArrayList<HangoutChangeListener>();

    public void addListener(HangoutChangeListener toAdd) {
        hangoutChangeListeners.add(toAdd);
    }

    public void changeHangout( String hangoutGeoId) {
        this.hangoutGeoId = hangoutGeoId;
        for (HangoutChangeListener l : hangoutChangeListeners) {
            l.hangoutChange(hangoutGeoId);
        }
    }
}
