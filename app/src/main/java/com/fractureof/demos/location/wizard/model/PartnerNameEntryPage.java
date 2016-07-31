package com.fractureof.demos.location.wizard.model;

import android.support.v4.app.Fragment;

import com.fractureof.demos.location.PartnerNameEntryFragment;
import com.fractureof.demos.location.backend.DatePlan;

import java.util.ArrayList;

/**
 * Created by tyler on 16/06/2016.
 */
public class PartnerNameEntryPage extends Page {
    public static final String NAME_DATA_KEY = "name";

    public PartnerNameEntryPage(ModelCallbacks callbacks, String title, DatePlan datePlan) {
        super(callbacks, title, datePlan);
    }

    @Override
    public Fragment createFragment() {
        return PartnerNameEntryFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {

    }

    @Override
    protected void doSetBackendData() {

    }
}
