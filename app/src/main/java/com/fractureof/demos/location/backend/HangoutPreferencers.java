package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "hangout_preferencers")
public class HangoutPreferencers extends SyncanoObject  {

	public static final String FIELD_STAGE1_PRIVACY_LEVEL = "stage1_privacy_level";
	public static final String FIELD_STAGE1_LOUDNESS_LEVEL = "stage1_loudness_level";
	public static final String FIELD_STAGE2_PRIVACY_LEVEL = "stage2_privacy_level";
	public static final String FIELD_STAGE2_LOUDNESS_LEVEL = "stage2_loudness_level";
	public static final String FIELD_USER = "user";

	@SyncanoField(name = FIELD_STAGE1_PRIVACY_LEVEL, filterIndex = true, orderIndex = false)
	public Integer stage1PrivacyLevel;

	@SyncanoField(name = FIELD_STAGE1_LOUDNESS_LEVEL, filterIndex = true, orderIndex = false)
	public Integer stage1LoudnessLevel;

	@SyncanoField(name = FIELD_STAGE2_PRIVACY_LEVEL, filterIndex = true, orderIndex = false)
	public Integer stage2PrivacyLevel;

	@SyncanoField(name = FIELD_STAGE2_LOUDNESS_LEVEL, filterIndex = true, orderIndex = false)
	public Integer stage2LoudnessLevel;

	@SyncanoField(name = FIELD_USER, filterIndex = true, orderIndex = false)
	public UserProfile user;

}