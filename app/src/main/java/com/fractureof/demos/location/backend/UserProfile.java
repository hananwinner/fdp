package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "user_profile")
public class UserProfile extends SyncanoObject  {

	public static final String FIELD_DEFAULT_GEO_LOCATION = "default_geo_location";
	public static final String FIELD_FACEBOOK_ID = "facebook_id";
	public static final String FIELD_FIRST_NAME = "first_name";
	public static final String FIELD_LAST_NAME = "last_name";
	public static final String FIELD_MIDDLE_NAME = "middle_name";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_GENDER = "gender";
	public static final String FIELD_PROFILE_PICTURE_URI = "profile_picture_uri";
	public static final String FIELD_NICKNAME = "nickname";

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

	@SyncanoField(name = FIELD_NAME, filterIndex = true, orderIndex = true)
	public String name;

	@SyncanoField(name = FIELD_GENDER, filterIndex = true, orderIndex = false)
	public String gender;

	@SyncanoField(name = FIELD_PROFILE_PICTURE_URI, filterIndex = false, orderIndex = false)
	public String profilePictureUri;

	@SyncanoField(name = FIELD_NICKNAME, filterIndex = true, orderIndex = false)
	public String nickname;

}