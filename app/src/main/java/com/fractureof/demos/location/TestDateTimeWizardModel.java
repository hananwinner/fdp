package com.fractureof.demos.location;

import android.content.Context;

import com.fractureof.demos.location.wizard.model.AbstractWizardModel;
import com.fractureof.demos.location.wizard.model.PageList;
import com.fractureof.demos.location.wizard.model.DateTimePage;

/**
 * Created by tyler on 19/06/2016.
 */
public class TestDateTimeWizardModel extends AbstractWizardModel {
    public TestDateTimeWizardModel(Context context) {
        super(context);
    }
    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new DateTimePage(this,"Date's Time").setRequired(true)
        );
    }
}
