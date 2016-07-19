package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "location")
public class Location extends SyncanoObject  {

	public static final String FIELD_LAT = "lat";
	public static final String FIELD_LON = "lon";

	@SyncanoField(name = FIELD_LAT, filterIndex = true, orderIndex = true)
	public Float lat;

	@SyncanoField(name = FIELD_LON, filterIndex = true, orderIndex = true)
	public Float lon;

}