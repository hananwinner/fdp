package com.fractureof.demos.location.wizard.model;

import com.syncano.library.data.SyncanoObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanan on 20/07/2016.
 */
public abstract class BackendPage extends Page {
    protected BackendPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    public abstract List<SyncanoObject> getSyncanoBackendObjects();
}
