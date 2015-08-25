package com.bluetooth.db;

public class DBConstant {

	public static final int DB_VERSION = 1;
	public static String DATABASE_NAME = "scheduleManager";


	public static final String TBL_SCHEDULE = "tbl_schedule";
	public static final String TBL_SCHEDULE_DETAIL = "tbl_schedule_detail";

	//schedule table field
	public static final String SCHEDULE_NAME = "schedule_name";

	//schedule detail table fields
	public static final String START_TIME = "start_time";
	public static final String END_TIME = "end_time";
	public static final String FRAGRANCE_ONE_STATUS = "fragrance_one_status";
	public static final String FRAGRANCE_ONE_INTENSITY = "fragrance_one_intensity";
	public static final String FRAGRANCE_TWO_STATUS = "fragrance_two_status";
	public static final String FRAGRANCE_TWO_INTENSITY = "fragrance_two_intensity";
	public static final String FRAGRANCE_THREE_STATUS = "fragrance_three_status";
	public static final String FRAGRANCE_THREE_INTENSITY = "fragrance_three_intensity";
	public static final String FRAGRANCE_FOUR_STATUS = "fragrance_four_status";
	public static final String FRAGRANCE_FOUR_INTENSITY = "fragrance_four_intensity";
	


	
	public static final String CREATE_SCHEDULE_TABLE = "CREATE TABLE IF NOT EXISTS " + TBL_SCHEDULE + "( "
			+ SCHEDULE_NAME + " TEXT PRIMARY KEY"+ ")";

	public static final String CREATE_SCHEDULE_DETAIL_TABLE = "CREATE TABLE IF NOT EXISTS " + TBL_SCHEDULE_DETAIL + "( "
			+ SCHEDULE_NAME + " TEXT ,"
			+ START_TIME + " TEXT ,"
			+ END_TIME + " TEXT,"
			+ FRAGRANCE_ONE_STATUS + " TEXT,"
			+ FRAGRANCE_ONE_INTENSITY + " TEXT,"
			+ FRAGRANCE_TWO_STATUS + " TEXT,"
			+ FRAGRANCE_TWO_INTENSITY + " TEXT,"
			+ FRAGRANCE_THREE_STATUS + " TEXT,"
			+ FRAGRANCE_THREE_INTENSITY + " TEXT,"
			+ FRAGRANCE_FOUR_STATUS + " TEXT,"
			+ FRAGRANCE_FOUR_INTENSITY + " TEXT" + ")";

	
}
