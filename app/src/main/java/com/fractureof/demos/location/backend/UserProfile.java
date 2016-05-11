package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "user_profile")
public class UserProfile extends SyncanoObject  {

	private static final String FIELD_DEFAULT_GEO_LOCATION = "default_geo_location";
	private static final String FIELD_FACEBOOK_ID = "facebook_id";
	private static final String FIELD_FIRST_NAME = "first_name";
	private static final String FIELD_LAST_NAME = "last_name";
	private static final String FIELD_MIDDLE_NAME = "middle_name";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_GENDER = "gender";
	private static final String FIELD_PROFILE_PICTURE_URI = "profile_picture_uri";

	@SyncanoField(name = FIELD_DEFAULT_GEO_LOCATION, filterIndex = false, orderIndex = false)
	public String defaultGeoLocation;

	@SyncanoField(name = FIELD_FACEBOOK_ID, filterIndex = true, orderIndex = false)
	public String facebookId;

	@SyncanoField(name = FIELD_FIRST_NAME, filterIndex = false, orderIndex = false)
	public String firstName;

	@SyncanoField(name = FIELD_LAST_NAME, filterIndex = false, orderIndex = false)
	public String lastName;

	@SyncanoField(name = FIELD_MIDDLE_NAME, filterIndex = false, orderIndex = false)
	public String middleName;

	@SyncanoField(name = FIELD_NAME, filterIndex = true, orderIndex = false)
	public String name;

	@SyncanoField(name = FIELD_GENDER, filterIndex = true, orderIndex = false)
	public String gender;

	@SyncanoField(name = FIELD_PROFILE_PICTURE_URI, filterIndex = false, orderIndex = false)
	public String profilePictureUri;

}