package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "hangout")
public class Hangout extends SyncanoObject  {

	public static final String FIELD_TYPE = "type";
	public static final String FIELD_PRIVACY_LEVEL = "privacy_level";
	public static final String FIELD_LOUDNESS = "loudness";
	public static final String FIELD_GEO_ID = "geo_id";
	public static final String FIELD_LAT = "lat";
	public static final String FIELD_LON = "lon";

	@SyncanoField(name = FIELD_TYPE, filterIndex = true, orderIndex = false)
	public HangoutType type;

	@SyncanoField(name = FIELD_PRIVACY_LEVEL, filterIndex = true, orderIndex = false)
	public Integer privacyLevel;

	@SyncanoField(name = FIELD_LOUDNESS, filterIndex = true, orderIndex = false)
	public Integer loudness;

	@SyncanoField(name = FIELD_GEO_ID, filterIndex = true, orderIndex = false)
	public String geoId;

	@SyncanoField(name = FIELD_LAT, filterIndex = true, orderIndex = true)
	public Float lat;

	@SyncanoField(name = FIELD_LON, filterIndex = true, orderIndex = true)
	public Float lon;

}