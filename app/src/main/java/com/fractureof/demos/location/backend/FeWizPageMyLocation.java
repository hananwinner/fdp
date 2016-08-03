package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "fe_wiz_page_my_location")
public class FeWizPageMyLocation extends SyncanoObject  {

	public static final String FIELD_DATE_PLAN_ID = "date_plan_id";
	public static final String FIELD_MY_GEO_ID = "my_geo_id";
	public static final String FIELD_MY_LAT = "my_lat";
	public static final String FIELD_MY_LNG = "my_lng";

	@SyncanoField(name = FIELD_DATE_PLAN_ID, filterIndex = false, orderIndex = false)
	public Integer datePlanId;

	@SyncanoField(name = FIELD_MY_GEO_ID, filterIndex = false, orderIndex = false)
	public String myGeoId;

	@SyncanoField(name = FIELD_MY_LAT, filterIndex = false, orderIndex = false)
	public Float myLat;

	@SyncanoField(name = FIELD_MY_LNG, filterIndex = false, orderIndex = false)
	public Float myLng;

}