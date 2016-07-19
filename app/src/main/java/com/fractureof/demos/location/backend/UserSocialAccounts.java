package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "user_social_accounts")
public class UserSocialAccounts extends SyncanoObject  {

	public static final String FIELD_USER_ID = "user_id";
	public static final String FIELD_SOCIAL_ACCOUNT_ID = "social_account_id";
	public static final String FIELD_SOCIAL_ACCOUNT_TYPE = "social_account_type";

	@SyncanoField(name = FIELD_USER_ID, filterIndex = true, orderIndex = false)
	public UserProfile userId;

	@SyncanoField(name = FIELD_SOCIAL_ACCOUNT_ID, filterIndex = true, orderIndex = false)
	public String socialAccountId;

	@SyncanoField(name = FIELD_SOCIAL_ACCOUNT_TYPE, filterIndex = true, orderIndex = false)
	public SocialAccountType socialAccountType;

}