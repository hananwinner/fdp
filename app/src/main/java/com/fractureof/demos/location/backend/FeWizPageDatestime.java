package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "fe_wiz_page_datestime")
public class FeWizPageDatestime extends SyncanoObject  {

	public static final String FIELD_DATE_PLAN = "date_plan";
	public static final String FIELD_DATESTIME = "datestime";

	@SyncanoField(name = FIELD_DATE_PLAN, filterIndex = false, orderIndex = false)
	public DatePlan datePlan;

	@SyncanoField(name = FIELD_DATESTIME, filterIndex = false, orderIndex = false)
	public Date datestime;

}