package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "defaults")
public class Defaults extends SyncanoObject  {

	public static final String FIELD_DEAFULT_PREFERENCES = "deafult_preferences";
	public static final String FIELD_DEAFULT_USER_PROFILE = "deafult_user_profile";
	public static final String FIELD_DEFAULT_DATE_HOUR = "default_date_hour";

	@SyncanoField(name = FIELD_DEAFULT_PREFERENCES, filterIndex = false, orderIndex = false)
	public HangoutPreferencers deafultPreferences;

	@SyncanoField(name = FIELD_DEAFULT_USER_PROFILE, filterIndex = false, orderIndex = false)
	public UserProfile deafultUserProfile;

	@SyncanoField(name = FIELD_DEFAULT_DATE_HOUR, filterIndex = false, orderIndex = false)
	public Date defaultDateHour;

}