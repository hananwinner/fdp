package com.fractureof.demos.location.backend;

/**
 * Created by hanan on 22/02/2016.
 */
import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.choice.FieldType;
import com.syncano.library.data.SyncanoFile;
import com.syncano.library.data.SyncanoObject;

import org.json.JSONObject;

import java.io.File;
import java.util.Date;

@SyncanoClass(name = "user_profile")
public class UserProfile extends SyncanoObject{
    private static final String FIELD_FIRST_NAME = "first_name";
    private static final String FIELD_LAST_NAME = "last_name";
    private static final String FIELD_BIRTHDATE = "birthdate";
    private static final String FIELD_ABOUT_ME = "about_me";
    private static final String FIELD_AVATAR = "avatar";

    @SyncanoField(name = FIELD_FIRST_NAME , filterIndex = false, orderIndex = false)
    public String firstName;
    @SyncanoField(name = FIELD_LAST_NAME , filterIndex = false, orderIndex = false)
    public String lastName;
    @SyncanoField(name = FIELD_BIRTHDATE, filterIndex = false, orderIndex = false)
    public Date birthdate;
    @SyncanoField(name = FIELD_ABOUT_ME, filterIndex = false, orderIndex = false)
    public String aboutMe;
    @SyncanoField(type = FieldType.FILE ,name = FIELD_AVATAR, filterIndex = false, orderIndex = false)
    public SyncanoFile avatar;
}
