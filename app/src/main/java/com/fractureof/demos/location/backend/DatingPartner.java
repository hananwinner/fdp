package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "dating_partner")
public class DatingPartner extends SyncanoObject  {

	public static final String FIELD_MY_USER = "my_user";
	public static final String FIELD_PARTNER_NAME = "partner_name";
	public static final String FIELD_PARTNER_FACEBOOK_ID = "partner_facebook_id";
	public static final String FIELD_PARTNER_NICKNAME = "partner_nickname";
	public static final String FIELD_MEMBER_USER = "member_user";

	@SyncanoField(name = FIELD_MY_USER, filterIndex = true, orderIndex = false)
	public UserProfile myUser;

	@SyncanoField(name = FIELD_PARTNER_NAME, filterIndex = true, orderIndex = false)
	public String partnerName;

	@SyncanoField(name = FIELD_PARTNER_FACEBOOK_ID, filterIndex = false, orderIndex = false)
	public String partnerFacebookId;

	@SyncanoField(name = FIELD_PARTNER_NICKNAME, filterIndex = true, orderIndex = false)
	public String partnerNickname;

	@SyncanoField(name = FIELD_MEMBER_USER, filterIndex = false, orderIndex = false)
	public UserProfile memberUser;

}