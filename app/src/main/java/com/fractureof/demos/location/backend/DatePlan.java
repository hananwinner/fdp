package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "date_plan")
public class DatePlan extends SyncanoObject  {

	public static final String FIELD_PARTNER = "partner";
	public static final String FIELD_DATE_TIME = "date_time";
	public static final String FIELD_MEETUP_LOCATION = "meetup_location";
	public static final String FIELD_MY_LOCATION = "my_location";
	public static final String FIELD_PARTNER_LOCATION = "partner_location";
	public static final String FIELD_ME_PICKUP = "me_pickup";
	public static final String FIELD_PARTNER_PICKUP = "partner_pickup";
	public static final String FIELD_ME_TRANSPOTATION = "me_transpotation";
	public static final String FIELD_PARTNER_TRANSPORTATION = "partner_transportation";
	public static final String FIELD_MEETUP_GEO_ID = "meetup_geo_id";
	public static final String FIELD_MY_GEO_ID = "my_geo_id";
	public static final String FIELD_PARTNER_GEO_ID = "partner_geo_id";

	@SyncanoField(name = FIELD_PARTNER, filterIndex = true, orderIndex = false)
	public DatingPartner partner;

	@SyncanoField(name = FIELD_DATE_TIME, filterIndex = true, orderIndex = true)
	public Date dateTime;

	@SyncanoField(name = FIELD_MEETUP_LOCATION, filterIndex = false, orderIndex = false)
	public Location meetupLocation;

	@SyncanoField(name = FIELD_MY_LOCATION, filterIndex = false, orderIndex = false)
	public Location myLocation;

	@SyncanoField(name = FIELD_PARTNER_LOCATION, filterIndex = false, orderIndex = false)
	public Location partnerLocation;

	@SyncanoField(name = FIELD_ME_PICKUP, filterIndex = false, orderIndex = false)
	public Boolean mePickup;

	@SyncanoField(name = FIELD_PARTNER_PICKUP, filterIndex = false, orderIndex = false)
	public Boolean partnerPickup;

	@SyncanoField(name = FIELD_ME_TRANSPOTATION, filterIndex = false, orderIndex = false)
	public PickupMethod meTranspotation;

	@SyncanoField(name = FIELD_PARTNER_TRANSPORTATION, filterIndex = false, orderIndex = false)
	public PickupMethod partnerTransportation;

	@SyncanoField(name = FIELD_MEETUP_GEO_ID, filterIndex = false, orderIndex = false)
	public String meetupGeoId;

	@SyncanoField(name = FIELD_MY_GEO_ID, filterIndex = false, orderIndex = false)
	public String myGeoId;

	@SyncanoField(name = FIELD_PARTNER_GEO_ID, filterIndex = false, orderIndex = false)
	public String partnerGeoId;

}