package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "dating_type")
public class DatingType extends SyncanoObject  {

	public static final String FIELD_NAME = "name";
	public static final String FIELD_DESCRIPTION = "description";

	@SyncanoField(name = FIELD_NAME, filterIndex = false, orderIndex = false)
	public String name;

	@SyncanoField(name = FIELD_DESCRIPTION, filterIndex = false, orderIndex = false)
	public String description;

}