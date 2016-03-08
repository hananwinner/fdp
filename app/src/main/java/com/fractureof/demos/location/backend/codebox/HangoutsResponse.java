package com.fractureof.demos.location.backend.codebox;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;

/**
 * Created by hanan on 06/03/2016.
 */
@SyncanoClass(name = "hangout")
public class HangoutsResponse extends SyncanoObject {
    private static final String FIELD_ID = "id";
    private static final String FIELD_GEO_ID = "geo_id";
    private static final String FIELD_LAT= "lat";
    private static final String FIELD_LON = "lon";
    private static final String FIELD_NAME = "name";
    @SyncanoField(name = FIELD_ID, filterIndex = false, orderIndex = false)
    public String id;
    @SyncanoField(name = FIELD_GEO_ID, filterIndex = false, orderIndex = false)
    public String geoId;
    @SyncanoField(name = FIELD_LAT, filterIndex = false, orderIndex = false)
    public float lat;
    @SyncanoField(name = FIELD_LON, filterIndex = false, orderIndex = false)
    public float lon;
    @SyncanoField(name = FIELD_NAME, filterIndex = false, orderIndex = false)
    public String name;

}
