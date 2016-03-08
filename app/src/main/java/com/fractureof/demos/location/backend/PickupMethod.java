package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;

/**
 * Created by hanan on 29/02/2016.
 */
@SyncanoClass(name = "pickup_method")
public class PickupMethod extends SyncanoObject {
    private static final String FIELD_NAME = "name";
    @SyncanoField(name = FIELD_NAME , filterIndex = false, orderIndex = false)
    public String name;
}
