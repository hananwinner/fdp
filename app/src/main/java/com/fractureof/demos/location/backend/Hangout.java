package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;

/**
 * Created by hanan on 01/03/2016.
 */
@SyncanoClass(name = "hangout")
public class Hangout extends SyncanoObject {
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_PRIVACY_LEVEL = "privacy_level";
    private static final String FIELD_LOUDNESS = "loudness";
    private static final String FIELD_GEO_ID = "geo_id";

    @SyncanoField(name = FIELD_TYPE, filterIndex = true, orderIndex = true)
    public HangoutType hangoutType ;
    @SyncanoField(name = FIELD_PRIVACY_LEVEL, filterIndex = true, orderIndex = true)
    public Integer privacyLevel;
    @SyncanoField(name = FIELD_LOUDNESS, filterIndex = true, orderIndex = true)
    public Integer loudness;
    @SyncanoField(name = FIELD_GEO_ID, filterIndex = false, orderIndex = false)
    public String geoId;

}
