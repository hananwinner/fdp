package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "hangout_type")
public class HangoutType extends SyncanoObject  {

	private static final String FIELD_NAME = "name";

	@SyncanoField(name = FIELD_NAME, filterIndex = false, orderIndex = false)
	public String name;

}