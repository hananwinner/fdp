package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "meet_point")
public class MeetPoint extends SyncanoObject  {

	public static final String FIELD_GEO_ID = "geo_id";
	public static final String FIELD_WAITING_SLOGAN = "waiting_slogan";

	@SyncanoField(name = FIELD_GEO_ID, filterIndex = false, orderIndex = false)
	public String geoId;

	@SyncanoField(name = FIELD_WAITING_SLOGAN, filterIndex = false, orderIndex = false)
	public String waitingSlogan;

}