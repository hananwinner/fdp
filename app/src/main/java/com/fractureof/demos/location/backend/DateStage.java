package com.fractureof.demos.location.backend;

import com.syncano.library.annotation.SyncanoClass;
import com.syncano.library.annotation.SyncanoField;
import com.syncano.library.data.SyncanoObject;
import java.util.Date;
        
@SyncanoClass(name = "date_stage")
public class DateStage extends SyncanoObject  {

	public static final String FIELD_PLAN = "plan";
	public static final String FIELD_STAGE_NUM = "stage_num";
	public static final String FIELD_OPTION_RANK = "option_rank";
	public static final String FIELD_HANGOUT_TYPE = "hangout_type";
	public static final String FIELD_HANGOUT = "hangout";

	@SyncanoField(name = FIELD_PLAN, filterIndex = true, orderIndex = false)
	public DatePlan plan;

	@SyncanoField(name = FIELD_STAGE_NUM, filterIndex = true, orderIndex = true)
	public Integer stageNum;

	@SyncanoField(name = FIELD_OPTION_RANK, filterIndex = true, orderIndex = true)
	public Integer optionRank;

	@SyncanoField(name = FIELD_HANGOUT_TYPE, filterIndex = true, orderIndex = false)
	public HangoutType hangoutType;

	@SyncanoField(name = FIELD_HANGOUT, filterIndex = false, orderIndex = false)
	public Hangout hangout;

}