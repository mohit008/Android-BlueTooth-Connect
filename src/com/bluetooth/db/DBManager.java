package com.bluetooth.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bluetooth.schedule.ScheduleBean;
import com.bluetooth.schedule.ScheduleDetailBean;

public class DBManager {
	
	private final Context _context;
	private DatabaseHelper _databaseHelper;
	private SQLiteDatabase _sqliteDB;
	
	
	/**
	 * Constructor of class
	 * @param context of calling class
	 */
	public DBManager(Context context) {
		this._context = context;
		_databaseHelper = new DatabaseHelper(_context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DBConstant.DATABASE_NAME, null,DBConstant.DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try{
				
				db.execSQL(DBConstant.CREATE_SCHEDULE_TABLE);
				db.execSQL(DBConstant.CREATE_SCHEDULE_DETAIL_TABLE);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_SCHEDULE_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_SCHEDULE_DETAIL_TABLE);
			
		}

	}

	/**
	 * Open the database
	 */
	public DBManager open() throws SQLException {
		_sqliteDB = _databaseHelper.getWritableDatabase();
		return this;
	}
	
	/**
	 * Close the database
	 */
	public void close() {
		if(_databaseHelper != null)
			_databaseHelper.close();
	
	}
	
	
	/**
	 * Method to insert data in Schedule table
	 */
	public long insertIntoSchedule(ScheduleBean schedule) {

		ContentValues values = new ContentValues();

		values.put(DBConstant.SCHEDULE_NAME, schedule.getSch_Name());
		return _sqliteDB.replace(DBConstant.TBL_SCHEDULE, null, values);
	}

	
	/**
	 * Method to select all data of a table
	 * @param tableName
	 */
	public Cursor getAllDataFromTable(String tableName) {
		return _sqliteDB.rawQuery("select * from " + tableName, null);
	}

	/**
	 * Delete all data from table
	 * @param tableName
	 */
	public int deleteAllDataFromTable(String tableName) {
		return _sqliteDB.delete(tableName, null, null);
	}
	
	/**
	 * Method to insert data in Schedule Detail table
	 */
	public long insertIntoScheduleDetail(ScheduleDetailBean scheduleDetailBean) {

		ContentValues values = new ContentValues();

		values.put(DBConstant.SCHEDULE_NAME, scheduleDetailBean.getSch_Name());
		values.put(DBConstant.START_TIME, scheduleDetailBean.getSrt_Time());
		values.put(DBConstant.END_TIME, scheduleDetailBean.getStp_Time());
		values.put(DBConstant.FRAGRANCE_ONE_STATUS, scheduleDetailBean.getF1_Status());
		values.put(DBConstant.FRAGRANCE_ONE_INTENSITY, scheduleDetailBean.getF1_Intensity());
		values.put(DBConstant.FRAGRANCE_TWO_STATUS, scheduleDetailBean.getF2_Status());
		values.put(DBConstant.FRAGRANCE_TWO_INTENSITY, scheduleDetailBean.getF2_Intensity());
		values.put(DBConstant.FRAGRANCE_THREE_STATUS, scheduleDetailBean.getF3_Status());
		values.put(DBConstant.FRAGRANCE_THREE_INTENSITY, scheduleDetailBean.getF3_Intensity());
		values.put(DBConstant.FRAGRANCE_FOUR_STATUS, scheduleDetailBean.getF4_Status());
		values.put(DBConstant.FRAGRANCE_FOUR_INTENSITY, scheduleDetailBean.getF4_Intensity());
		
		return _sqliteDB.replace(DBConstant.TBL_SCHEDULE_DETAIL, null, values);
	}
	
	/**
	 * Method to select schedule detail data
	 * @param tableName
	 */
	/*public Cursor getScheduleDetailData(String srtTime,String endTime,String schedule_name) {
		return _sqliteDB.rawQuery(
				"select * from " + DBConstant.TBL_SCHEDULE_DETAIL 
				+ " where " + DBConstant.KEY_SCHEDULE_ID + "=?" ,
				new String[] {srtTime});
	}
	*/
	/*//**
	 * Update logic detail id in logic detail table..
	 * @param logicId
	 * @param logicDetailId
	 * @return
	 *//*
	public int UpadteLogicDetailId(String srtTime,String endTime,String logicDetailId,String logicId) {
		ContentValues value = new ContentValues();
		value.put(DBConstant.KEY_LOGIC_START_TIME,srtTime );
		value.put(DBConstant.KEY_LOGIC_STOP_TIME,endTime );
		return _sqliteDB.update(DBConstant.TBL_LOGIC_DETAIL ,
				value, DBConstant.KEY_LOGIC_DETAIL_ID +"=?"+" and " +DBConstant.KEY_LOGIC_ID +"=?",
				new String[]{logicId,logicDetailId});
	}

	*//**
	 * Get logic detail id from logic detail table
	 * @param startTime
	 * @param LogicId
	 * @return
	 *//*
	public Cursor getLogicDeatilID(String startTime,String LogicId) {
		return _sqliteDB.rawQuery(
				"select "+ DBConstant.KEY_LOGIC_DETAIL_ID
				+ " from " + DBConstant.TBL_LOGIC_DETAIL 
				+ " where "+DBConstant.KEY_LOGIC_ID+"='"+LogicId+"' and "+DBConstant.KEY_LOGIC_START_TIME+"='"+startTime+"';",null);
	}
	
	*/
	
	
	
	
	/*

	private final Context _context;
	private DatabaseHelper _databaseHelper;
	private SQLiteDatabase _sqliteDB;

	*//**
	 * Constructor of class
	 * @param context of calling class
	 *//*
	public DBManager(Context context) {
		this._context = context;
		_databaseHelper = new DatabaseHelper(_context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DBConstant.DATABASE_NAME, null,DBConstant.DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try{
				db.execSQL(DBConstant.CREATE_THERMOSTAT_SETUP_TABLE);
				db.execSQL(DBConstant.CREATE_DEPARTMENT_TABLE);
				db.execSQL(DBConstant.CREATE_FLOOR_TABLE);
				db.execSQL(DBConstant.CREATE_HOME_SETTING_TABLE);
				db.execSQL(DBConstant.CREATE_LEARNING_TABLE);
				db.execSQL(DBConstant.CREATE_SENSOR_TABLE);
				db.execSQL(DBConstant.CREATE_HVAC_TABLE);
				db.execSQL(DBConstant.CREATE_LOGIC_BOARD_LIGHT_TABLE);
				db.execSQL(DBConstant.CREATE_EEP_CASE_CONDITION_DATAFIELD_TABLE);
				db.execSQL(DBConstant.CREATE_AMENITIES_TABLE);
				db.execSQL(DBConstant.CREATE_AUDIT_TABLE);
				db.execSQL(DBConstant.CREATE_SENSOR_READING_HISTORY_TABLE);
				db.execSQL(DBConstant.CREATE_SCHEDULE_TABLE);
				db.execSQL(DBConstant.CREATE_SCHEDULE_DETAIL_TABLE);
				db.execSQL(DBConstant.CREATE_METER_TABLE);
				db.execSQL(DBConstant.CREATE_LOGIC_TABLE);
				db.execSQL(DBConstant.CREATE_LOGIC_DETAIL_TABLE);

			} catch (SQLException e) {
				e.printStackTrace();
			}
			//db.execSQL(DBConstant.CREATE_SUBCATEGORY_DATA_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_THERMOSTAT_SETUP_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_DEPARTMENT_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_FLOOR_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_HOME_SETTING_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_LEARNING_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_SENSOR_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_HVAC_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_LOGIC_BOARD_LIGHT_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_EEP_CASE_CONDITION_DATAFIELD_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_AMENITIES_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_AUDIT_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_SENSOR_READING_HISTORY_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_SCHEDULE_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_SCHEDULE_DETAIL_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_METER_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_LOGIC_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ DBConstant.CREATE_LOGIC_DETAIL_TABLE);
		}

	}

	*//**
	 * Open the database
	 *//*
	public DBManager open() throws SQLException {
		_sqliteDB = _databaseHelper.getWritableDatabase();
		return this;
	}

	*//**
	 * Close the database
	 *//*
	public void close() {
		if(_databaseHelper != null)
			_databaseHelper.close();
		//_databaseHelper = null;
	}

	
	 * 
	 

	public static final	String DB_FILEPATH = "/data/data/com.magnumthermostat/databases/"+DBConstant.DATABASE_NAME;

	*//**
	 * Copies the database file at the specified location over the current
	 * internal application database.
	 * *//*
	public boolean importDatabase(String dbPath) throws IOException {

		// Close the SQLiteOpenHelper so it will commit the created empty
		// database to internal storage.
		close();
		File newDb = new File(dbPath);
		File oldDb = new File(DB_FILEPATH);
		if (newDb.exists()) {
			FileUtils.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
			// Access the copied database so SQLiteHelper will cache it and mark
			// it as created.
			close();
			return true;
		}
		return false;
	}

	public long insertIntoDataFieldForHumidity() {

		ContentValues contentValues = new ContentValues();
		contentValues.put(DBConstant.KEY_RORG_ID, "3");
		contentValues.put(DBConstant.KEY_FUNC_ID, "8");
		contentValues.put(DBConstant.KEY_TYPE_ID, "38");
		contentValues.put(DBConstant.KEY_RORG_NUMBER, "A5");
		contentValues.put(DBConstant.KEY_FUNC_NUMBER, "04");
		contentValues.put(DBConstant.KEY_TYPE_NUMBER, "01");
		contentValues.put(DBConstant.KEY_BIT_OFFS, "8");
		contentValues.put(DBConstant.KEY_BIT_SIZE, "8");
		contentValues.put(DBConstant.KEY_VALUE, "");
		contentValues.put(DBConstant.KEY_DATA, "Humidity");
		contentValues.put(DBConstant.KEY_SHORTCUT, "Hum");
		contentValues.put(DBConstant.KEY_DESCRIPTION, "");
		contentValues.put(DBConstant.KEY_INFO, "DB_2: Rel. Humidity 0...100%, linear n=0...250");
		contentValues.put(DBConstant.KEY_RANGE_MIN, "0");
		contentValues.put(DBConstant.KEY_RANGE_MAX, "250");
		contentValues.put(DBConstant.KEY_SCALE_MIN, "0");
		contentValues.put(DBConstant.KEY_SCALE_MAX, "100");
		contentValues.put(DBConstant.KEY_UNIT, "%");

		return _sqliteDB.insert(DBConstant.TBL_EEP_CASE_CONDITION_DATAFIELD, null, contentValues);
	}

	public long insertIntoDataFieldForTemperature() {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBConstant.KEY_RORG_ID, "3");
		contentValues.put(DBConstant.KEY_FUNC_ID, "8");
		contentValues.put(DBConstant.KEY_TYPE_ID, "38");
		contentValues.put(DBConstant.KEY_RORG_NUMBER, "A5");
		contentValues.put(DBConstant.KEY_FUNC_NUMBER, "04");
		contentValues.put(DBConstant.KEY_TYPE_NUMBER, "01");
		contentValues.put(DBConstant.KEY_BIT_OFFS, "16");
		contentValues.put(DBConstant.KEY_BIT_SIZE, "8");
		contentValues.put(DBConstant.KEY_VALUE, "");
		contentValues.put(DBConstant.KEY_DATA, "Temperature");
		contentValues.put(DBConstant.KEY_SHORTCUT, "Tmp");
		contentValues.put(DBConstant.KEY_DESCRIPTION, "");
		contentValues.put(DBConstant.KEY_INFO, "DB_1 Temperature (8 bit) 0...40°C, linear n=0...250");
		contentValues.put(DBConstant.KEY_RANGE_MIN, "0");
		contentValues.put(DBConstant.KEY_RANGE_MAX, "250");
		contentValues.put(DBConstant.KEY_SCALE_MIN, "0");
		contentValues.put(DBConstant.KEY_SCALE_MAX, "+40");
		contentValues.put(DBConstant.KEY_UNIT, "°C");

		return _sqliteDB.insert(DBConstant.TBL_EEP_CASE_CONDITION_DATAFIELD, null, contentValues);
	}


	
	 * db.delete(TABLE_GROUP, KEY_TABLE_GROUP_NAME + " = ?", new String[] {
	 * groupName });
	 
	public Cursor getDataFromDataField(String rorg_number, String func_number,
			String type_number) {
		return _sqliteDB.rawQuery("select * from "
				+ DBConstant.TBL_EEP_CASE_CONDITION_DATAFIELD + " where "
				+ DBConstant.KEY_RORG_NUMBER + "=? and "
				+ DBConstant.KEY_FUNC_NUMBER + "=? and "
				+ DBConstant.KEY_TYPE_NUMBER + "=?", new String[] {
						rorg_number, func_number, type_number });
	}

	// add thermostat setup
	public long addThermostat(String thermostatId, String locationName,
			String securityPin, String securityQuestion, String securityAnswer,
			String registeredFlag, String validThermostatFlag) {

		ContentValues contentValues = new ContentValues();
		contentValues.put(DBConstant.KEY_THERMOSTAT_ID, thermostatId);
		contentValues.put(DBConstant.KEY_LOCATION_NAME, locationName);
		contentValues.put(DBConstant.KEY_SECURITY_PIN, securityPin);
		contentValues.put(DBConstant.KEY_SECURITY_QUESTION, securityQuestion);
		contentValues.put(DBConstant.KEY_SECURITY_ANSWER, securityAnswer);
		contentValues.put(DBConstant.KEY_REGISTERED_FLAG, registeredFlag);
		contentValues.put(DBConstant.KEY_VALID_THERMOSTAT_FLAG,
				validThermostatFlag);

		return _sqliteDB.replace(DBConstant.TBL_THERMOSTAT_SETUP, null,
				contentValues);
	}

	public HashMap<String,String> getThermostatData(){
		HashMap<String, String> data = new HashMap<String, String>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + DBConstant.TBL_THERMOSTAT_SETUP;

		Cursor cursor = _sqliteDB.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			data.put(DBConstant.KEY_THERMOSTAT_ID, cursor.getString(
					cursor.getColumnIndex(DBConstant.KEY_THERMOSTAT_ID)));
			data.put(DBConstant.KEY_LOCATION_NAME, cursor.getString(
					cursor.getColumnIndex(DBConstant.KEY_LOCATION_NAME)));
			data.put(DBConstant.KEY_SECURITY_PIN, cursor.getString(
					cursor.getColumnIndex(DBConstant.KEY_SECURITY_PIN)));
			data.put(DBConstant.KEY_SECURITY_QUESTION, cursor.getString(
					cursor.getColumnIndex(DBConstant.KEY_SECURITY_QUESTION)));
			data.put(DBConstant.KEY_SECURITY_ANSWER, cursor.getString(
					cursor.getColumnIndex(DBConstant.KEY_SECURITY_ANSWER)));
			data.put(DBConstant.KEY_REGISTERED_FLAG, cursor.getString(
					cursor.getColumnIndex(DBConstant.KEY_REGISTERED_FLAG)));
			data.put(DBConstant.KEY_VALID_THERMOSTAT_FLAG, cursor.getString(
					cursor.getColumnIndex(DBConstant.KEY_VALID_THERMOSTAT_FLAG)));
		}
		//cursor.close();
		return  data;
	}

	public Cursor getRoomData(){
		return _sqliteDB.rawQuery("select "+DBConstant.KEY_ROOM_NAME+" from "+DBConstant.TBL_ROOM, null);
	}

	public long insertIntoRoom(String roomName,String registeredFlag){

		ContentValues contentValues = new ContentValues();
		contentValues.put(DBConstant.KEY_THERMOSTAT_ID, Constant.THERMOSTAT_ID);
		contentValues.put(DBConstant.KEY_ROOM_NAME, roomName);
		contentValues.put(DBConstant.KEY_REGISTERED_FLAG, registeredFlag);
		return _sqliteDB.insert(DBConstant.TBL_ROOM, null, contentValues);

	}

	public long insertIntoFloor(String floorName,String registeredFlag){

		ContentValues contentValues = new ContentValues();
		contentValues.put(DBConstant.KEY_THERMOSTAT_ID, "abc");
		contentValues.put(DBConstant.KEY_FLOOR_NAME, floorName);
		contentValues.put(DBConstant.KEY_REGISTERED_FLAG, registeredFlag);
		return _sqliteDB.insert(DBConstant.TBL_FLOOR, null, contentValues);

	}
	public Cursor getFloorData(){
		return _sqliteDB.rawQuery("select "+DBConstant.KEY_FLOOR_NAME+" from "+DBConstant.TBL_FLOOR, null);
	}

	public boolean deleteFloor(String name)
	{
		return _sqliteDB.delete(DBConstant.TBL_FLOOR, DBConstant.KEY_FLOOR_NAME + "='" + name+"'", null) > 0;
	}

	public boolean deleteDept(String name)
	{
		return _sqliteDB.delete(DBConstant.TBL_ROOM, DBConstant.KEY_ROOM_NAME + "='" + name+"'", null) > 0;
	}

	public long insertIntoLearningTable(Device device){
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBConstant.KEY_THERMOSTAT_ID, Constant.THERMOSTAT_ID);
		contentValues.put(DBConstant.KEY_DEVICE_ID, device.getIdString());
		contentValues.put(DBConstant.KEY_STATUS,
				String.valueOf(device.getTypeValue()));
		contentValues.put(DBConstant.KEY_BASE_ID, Constant.ENOCEAN_4_BYTE);
		contentValues.put(DBConstant.KEY_LEARNING_DATA,
				device.getAllDataString());
		contentValues.put(DBConstant.KEY_IDENTIFICATION_TEXT, device.getType());
		contentValues.put(DBConstant.KEY_SCHEDULE_NAME, "abc");
		contentValues.put(DBConstant.KEY_FLAG, "0");
		return _sqliteDB.insert(DBConstant.TBL_LEARNING, null, contentValues);
	}

	public long insert3ChannelIntoLearningTable(Device device,
			String concatString) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBConstant.KEY_THERMOSTAT_ID, Constant.THERMOSTAT_ID);
		contentValues.put(DBConstant.KEY_DEVICE_ID,
				concatString + device.getIdString());
		contentValues.put(DBConstant.KEY_STATUS,
				String.valueOf(device.getTypeValue()));
		contentValues.put(DBConstant.KEY_BASE_ID, Constant.ENOCEAN_4_BYTE);
		contentValues.put(DBConstant.KEY_LEARNING_DATA,
				device.getAllDataString());
		contentValues.put(DBConstant.KEY_IDENTIFICATION_TEXT, device.getType());
		contentValues.put(DBConstant.KEY_SCHEDULE_NAME, "abc");
		contentValues.put(DBConstant.KEY_FLAG, "0");
		return _sqliteDB.replace(DBConstant.TBL_LEARNING, null, contentValues);
	}

	public Cursor getLearningTableData() {
		return _sqliteDB.rawQuery("select * from " + DBConstant.TBL_LEARNING,
				null);
	}

	public boolean deleteLearningTableData() {
		return _sqliteDB.delete(DBConstant.TBL_LEARNING,
				DBConstant.KEY_THERMOSTAT_ID + "='" + Constant.THERMOSTAT_ID
				+ "'", null) > 0;
	}

	public boolean deleteLearningTableDataAfterConfig(String deviceId) {
		return _sqliteDB.delete(DBConstant.TBL_LEARNING,
				DBConstant.KEY_DEVICE_ID + "='" + deviceId + "'", null) > 0;
	}
	public long insertIntoSensorTable(Device device){
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBConstant.KEY_THERMOSTAT_ID, Constant.THERMOSTAT_ID);
		contentValues.put(DBConstant.KEY_DEVICE_ID, device.getIdString());
		contentValues.put(DBConstant.KEY_DEVICE_TYPE, device.getType());
		contentValues.put(DBConstant.KEY_STATUS,
				String.valueOf(device.getTypeValue()));
		contentValues.put(DBConstant.KEY_BASE_ID, device.getSenderIdString());
		contentValues.put(DBConstant.KEY_ROOM_NAME, device.getGroup());
		contentValues.put(DBConstant.KEY_DEVICE_NAME, device.getName());
		contentValues.put(DBConstant.KEY_LEARNING_DATA,
				device.getAllDataString());
		contentValues.put(DBConstant.KEY_SCHEDULE_NAME, "abc");
		contentValues.put(DBConstant.KEY_BASE_ID_PRE, device.getBaseIdPre());
		contentValues.put(DBConstant.KEY_BASE_ID_POST, device.getBaseIdPost());
		contentValues.put(DBConstant.KEY_FLAG, "0");
		return _sqliteDB.insert(DBConstant.TBL_SENSOR, null, contentValues);
	}

	public Cursor getSensorTableData() {
		return _sqliteDB.rawQuery("select * from " + DBConstant.TBL_SENSOR,
				null);
	}

	public int getSensorTableDataRow(String deviceId) {
		// rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new
		// String[] {"David", "2"});
		Cursor cursor = _sqliteDB.rawQuery("select " + DBConstant.KEY_DEVICE_ID
				+ " from " + DBConstant.TBL_SENSOR + " where "
				+ DBConstant.KEY_DEVICE_ID + "=?", new String[] { deviceId });
		return cursor.getCount();
	}

	public Cursor getSensorTableBaseIdPost() {
		return _sqliteDB.rawQuery("select " + DBConstant.KEY_BASE_ID_POST
				+ " from " + DBConstant.TBL_SENSOR + " ORDER BY "
				+ DBConstant.KEY_BASE_ID_POST + " asc", null);
	}

	public Cursor getGroupFromSensorTableData() {
		return _sqliteDB.rawQuery("select " + DBConstant.KEY_ROOM_NAME
				+ " from " + DBConstant.TBL_SENSOR, null);
	}

	public boolean deleteAllSensorTableData() {
		return _sqliteDB.delete(DBConstant.TBL_SENSOR,
				DBConstant.KEY_THERMOSTAT_ID + "='" + Constant.THERMOSTAT_ID
				+ "'", null) > 0;
	}

	public boolean deleteSensorTableData(String deviceId) {
		return _sqliteDB.delete(DBConstant.TBL_SENSOR, DBConstant.KEY_DEVICE_ID
				+ "='" + deviceId + "'", null) > 0;
	}

	public long addHVAC(HVAC_Bean hvac_Bean) {

		ContentValues contentValues = new ContentValues();
		contentValues.put(DBConstant.KEY_EQUIPMENT_TYPE, hvac_Bean.getEquipmentTypeSpinner());
		contentValues.put(DBConstant.KEY_MODE, hvac_Bean.getModeSpinner());
		contentValues.put(DBConstant.KEY_UNIT, hvac_Bean.getUnitSpinner());
		contentValues.put(DBConstant.KEY_REPEATER, hvac_Bean.getRepeaterSpinner());
		contentValues.put(DBConstant.KEY_FAN_SPEED, hvac_Bean.getFanSpeedSpinner());
		contentValues.put(DBConstant.KEY_STAGE, hvac_Bean.getStageSpinner());
		contentValues.put(DBConstant.KEY_STAGE_TYPE, hvac_Bean.getStageTypeSpinner());
		contentValues.put(DBConstant.KEY_FAN_COIL, hvac_Bean.getFanCoilSpinner());
		contentValues.put(DBConstant.KEY_REVERSE_VALVE_TYPE, hvac_Bean.getReverseValveTypeSpinner());
		contentValues.put(DBConstant.KEY_RETURN_AFTER_UNOCCUPIED, hvac_Bean.getReturnAfterUnoccupiedSpinner());
		contentValues.put(DBConstant.KEY_RETURN_AFTER_WINDOW_OPEN, hvac_Bean.getReturnAfterWindowOpenSpinner());
		contentValues.put(DBConstant.KEY_TEMP_SENSOR, hvac_Bean.getTempSensorSpinner());
		contentValues.put(DBConstant.KEY_PIPE_SENSOR, hvac_Bean.getPipeSensorSpinner());
		contentValues.put(DBConstant.KEY_SETPOINT_MIN_LIMIT, hvac_Bean.getStandSetPointsMinLimit());
		contentValues.put(DBConstant.KEY_SETPOINT_MAX_LIMIT, hvac_Bean.getStandSetPointsMaxLimit());
		contentValues.put(DBConstant.KEY_COMFORT_SETPOINT, hvac_Bean.getComfortSetpoint());
		contentValues.put(DBConstant.KEY_FREEZE_PROTECTION, hvac_Bean.getFreezeProtection());
		contentValues.put(DBConstant.KEY_GREEN_MODE_COOLING, hvac_Bean.getGreenModeCooling());
		contentValues.put(DBConstant.KEY_GREEN_MODE_HEATING, hvac_Bean.getGreenModeHeating());
		contentValues.put(DBConstant.KEY_UNOCCUPIED_HEATING, hvac_Bean.getUnoccupiedHeating());
		contentValues.put(DBConstant.KEY_UNOCCUPIED_COOLING, hvac_Bean.getUnoccupiedCooling());
		contentValues.put(DBConstant.KEY_UNOCCUPIED_DELAY, hvac_Bean.getUnoccupiedDelay());
		contentValues.put(DBConstant.KEY_WINDOW_OPEN_HEATING, hvac_Bean.getWindowOpenHeating());
		contentValues.put(DBConstant.KEY_WINDOW_OPEN_COOLING, hvac_Bean.getWindowOpenCooling());
		contentValues.put(DBConstant.KEY_WINDOW_OPEN_DELAY, hvac_Bean.getWindowOpenDelay());

		return _sqliteDB.replace(DBConstant.TBL_HVAC_SETUP, null, contentValues);
	}

	public HVAC_Bean getHVAC_DATA() {
		HVAC_Bean hvacData = null;
		String selectQuery = "SELECT  * FROM " + DBConstant.TBL_HVAC_SETUP;
		Cursor cursor = _sqliteDB.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			hvacData = new HVAC_Bean();
			hvacData.setEquipmentTypeSpinner(cursor.getInt(cursor.getColumnIndex(DBConstant.KEY_EQUIPMENT_TYPE)));
			hvacData.setModeSpinner(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_MODE)));
			hvacData.setUnitSpinner(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_UNIT)));
			hvacData.setRepeaterSpinner(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_REPEATER)));
			hvacData.setFanSpeedSpinner(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_FAN_SPEED)));
			hvacData.setStageSpinner(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_STAGE)));
			hvacData.setStageTypeSpinner(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_STAGE_TYPE)));
			hvacData.setFanCoilSpinner(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_FAN_COIL)));
			hvacData.setReverseValveTypeSpinner(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_REVERSE_VALVE_TYPE)));
			hvacData.setReturnAfterUnoccupiedSpinner(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_RETURN_AFTER_UNOCCUPIED)));
			hvacData.setReturnAfterWindowOpenSpinner(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_RETURN_AFTER_WINDOW_OPEN)));
			hvacData.setTempSensorSpinner(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_TEMP_SENSOR)));
			hvacData.setPipeSensorSpinner(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_PIPE_SENSOR)));
			hvacData.setStandSetPointsMaxLimit(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_SETPOINT_MAX_LIMIT)));
			hvacData.setStandSetPointsMinLimit(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_SETPOINT_MIN_LIMIT)));
			hvacData.setComfortSetpoint(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_COMFORT_SETPOINT)));
			hvacData.setFreezeProtection(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_FREEZE_PROTECTION)));
			hvacData.setGreenModeCooling(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_GREEN_MODE_COOLING)));
			hvacData.setGreenModeHeating(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_GREEN_MODE_HEATING)));
			hvacData.setUnoccupiedHeating(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_UNOCCUPIED_HEATING)));
			hvacData.setUnoccupiedCooling(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_UNOCCUPIED_COOLING)));
			hvacData.setUnoccupiedDelay(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_UNOCCUPIED_DELAY)));
			hvacData.setWindowOpenHeating(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_WINDOW_OPEN_HEATING)));
			hvacData.setWindowOpenCooling(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_WINDOW_OPEN_COOLING)));
			hvacData.setWindowOpenDelay(cursor.getInt(
					cursor.getColumnIndex(DBConstant.KEY_WINDOW_OPEN_DELAY)));
		}

		return hvacData;
	}

	public long insertIntoLogicBoardLight(String startTime, String endTime){
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBConstant.KEY_THERMOSTAT_ID, Constant.THERMOSTAT_ID);
		contentValues.put(DBConstant.KEY_START_TIME, startTime);
		contentValues.put(DBConstant.KEY_END_TIME, endTime);
		return _sqliteDB.replace(DBConstant.TBL_LOGIC_BOARD_LIGHT, null,
				contentValues);
	}

	public Cursor getLogicBoardLightData() {
		return _sqliteDB.rawQuery("select * from "
				+ DBConstant.TBL_LOGIC_BOARD_LIGHT, null);
	}

	public Cursor getAmenities_DATA() {
		return _sqliteDB.rawQuery("select * from " + DBConstant.TBL_AMENITIES,
				null);
	}

	public long insertIntoAudit(String slocId, String orgId, String thermId,
			String objectName, String transFlag, String transId,
			String transProcessYN, String transJson, String auditTimestamp) {

		ContentValues contentValues = new ContentValues();

		contentValues.put(DBConstant.KEY_AUDIT_SLOC_ID, slocId);
		contentValues.put(DBConstant.KEY_AUDIT_ORG_ID, orgId);
		contentValues.put(DBConstant.KEY_AUDIT_THERM_ID, thermId);
		contentValues.put(DBConstant.KEY_AUDIT_OBJECT_NAME, objectName);
		contentValues.put(DBConstant.KEY_AUDIT_TRANS_FLAG, transFlag);
		contentValues.put(DBConstant.KEY_AUDIT_TRANS_ID, transId);
		contentValues.put(DBConstant.KEY_AUDIT_TRANS_PROCESS_YN, transProcessYN);
		contentValues.put(DBConstant.KEY_AUDIT_TRANS_JSON, transJson);
		contentValues.put(DBConstant.KEY_AUDIT_TIMESTAMP, auditTimestamp);

		return _sqliteDB.replace(DBConstant.TBL_AUDIT, null, contentValues);
	}

	public int checkObjectName(String objectName){
		Cursor cursor = _sqliteDB.rawQuery(
				"select " + DBConstant.KEY_AUDIT_OBJECT_NAME
				+ " from " + DBConstant.TBL_AUDIT 
				+ " where "+DBConstant.KEY_AUDIT_OBJECT_NAME+"=?",
				new String[] {objectName});
		return cursor.getCount(); 
	}

	public void updateRowInAudit(String objectName,String values){
		Cursor cursor = _sqliteDB.rawQuery(
				"select " + DBConstant.KEY_AUDIT_OBJECT_NAME
				+ " from " + DBConstant.TBL_AUDIT 
				+ " where " + DBConstant.KEY_AUDIT_OBJECT_NAME + "=?",
				new String[] {objectName});
		cursor.close();
	}

	public Cursor getAuditData() {
		return _sqliteDB.rawQuery("select * from "
				+ DBConstant.TBL_AUDIT, null);
	}
	//sensor reading history operations

	*//**
	 * Method to insert data in SensorReadingHistory table
	 * @param deviceId
	 * @param readValue
	 * @return
	 *//*
	public long insertIntoSensorReadingHistory(String deviceId, String readValue) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBConstant.KEY_THERMOSTAT_ID, Constant.THERMOSTAT_ID);
		contentValues.put(DBConstant.KEY_DEVICE_ID, deviceId);
		contentValues.put(DBConstant.KEY_READ_VALUE, readValue);
		return _sqliteDB.insert(DBConstant.TBL_SENSOR_READING_HISTORY, null, contentValues);

	}

	*//**
	 * Method to get data from SensorReadingHistory table
	 * @return
	 *//*
	public Cursor getSensorReadingHistory() {
		return _sqliteDB.rawQuery("select * from " + DBConstant.TBL_SENSOR_READING_HISTORY,
				null);
	}

	*//**
	 * Method to get data from SensorReadingHistory table
	 * @return
	 *//*
	public Cursor getSensorReadingData() {
		return _sqliteDB.rawQuery("select "+DBConstant.KEY_DEVICE_ID+","+DBConstant.KEY_READ_VALUE+" from " + DBConstant.TBL_SENSOR_READING_HISTORY,
				null);
	}

	*//**
	 * Method to insert data in Schedule table
	 *//*
	public long insertIntoSchedule(ScheduleBean schedule) {

		ContentValues values = new ContentValues();

		values.put(DBConstant.KEY_SLOC_ID, schedule.getSloc_id());
		values.put(DBConstant.KEY_ORG_ID, schedule.getOrg_id());
		values.put(DBConstant.KEY_SCHEDULE_ID, schedule.getSchedule_id());
		values.put(DBConstant.KEY_SCHEDULE_NAME, schedule.getSchedule_name());
		values.put(DBConstant.KEY_SCH_VAL_ACTIVE_YN, schedule.getSch_val_active_yn());
		values.put(DBConstant.KEY_USERNAME, schedule.getUsername());
		values.put(DBConstant.KEY_MOD_DT, schedule.getMod_dt());
		values.put(DBConstant.KEY_LOW_THREST_REST, schedule.getLow_thresh_rest());
		values.put(DBConstant.KEY_HIGH_THREST_REST, schedule.getHigh_thresh_rest());

		return _sqliteDB.replace(DBConstant.TBL_SCHEDULE, null, values);
	}

	
	
	
	*//**
	 * Insert into logic
	 * @param logicBean
	 *//*
	
	public long insertIntoLogic(LogicBean logicBean) {

		ContentValues values = new ContentValues();

		values.put(DBConstant.KEY_LOGIC_SLOC_ID, logicBean.getSloc_id());
		values.put(DBConstant.KEY_LOGIC_ORG_ID, logicBean.getOrg_id());
		values.put(DBConstant.KEY_LOGIC_ID, logicBean.getLogic_id());
		values.put(DBConstant.KEY_LOGIC_NAME, logicBean.getLogic_name());
		values.put(DBConstant.KEY_LOGIC_VAL_ACTIVE_YN, logicBean.getLog_val_active_yn());
		values.put(DBConstant.KEY_LOGIC_USERNAME, logicBean.getUsername());
		values.put(DBConstant.KEY_LOGIC_MOD_DT, logicBean.getMod_dt());
		values.put(DBConstant.KEY_LOGIC_STATUS, logicBean.getStatus());
		

		return _sqliteDB.replace(DBConstant.TBL_LOGIC, null, values);
	}

	*//**
	 * Get logic id from logic table..
	 * @param logicName
	 * @return
	 *//*
	public Cursor getLogicID(String logicName) {
		return _sqliteDB.rawQuery(
				"select "+ DBConstant.KEY_LOGIC_ID
				+ " from " + DBConstant.TBL_LOGIC 
				+ " where " + DBConstant.KEY_LOGIC_NAME + "= '" +logicName+"'",null);
	}
	
	*//**
	 * Update logic name in logic table..
	 * @param logicId
	 * @param logicName
	 * @return
	 *//*
	public int UpadteLogicName(String logicId,String logicName) {
		ContentValues value = new ContentValues();
		value.put(DBConstant.KEY_LOGIC_NAME,logicName );
		return _sqliteDB.update(DBConstant.TBL_LOGIC ,
				value, DBConstant.KEY_LOGIC_ID +"=?",
				new String[]{logicId});
	}

	*//**
	 * Insert into Logic Detail
	 * @param logicBean
	 *//*
	public long insertIntoLogicDeatil(LogicDetailBean logicBean) {

		ContentValues values = new ContentValues();
		values.put(DBConstant.KEY_LOGIC_DETAIL_ID, logicBean.getLogic_detailId());
		values.put(DBConstant.KEY_LOGIC_SLOC_ID, logicBean.getSloc_id());
		values.put(DBConstant.KEY_LOGIC_ORG_ID, logicBean.getOrg_id());
		values.put(DBConstant.KEY_LOGIC_ID, logicBean.getLogic_id());
		values.put(DBConstant.KEY_LOGIC_START_TIME, logicBean.getStrt_time_period());
		values.put(DBConstant.KEY_LOGIC_STOP_TIME, logicBean.getEnd_time_period());
		values.put(DBConstant.KEY_LOGIC_USERNAME, logicBean.getUsername());
		values.put(DBConstant.KEY_LOGIC_MOD_DT, logicBean.getMod_dt());
		values.put(DBConstant.KEY_LOGIC_STATUS, logicBean.getStatus());

		return _sqliteDB.replace(DBConstant.TBL_LOGIC_DETAIL, null, values);
	}
	
	*//**
	 * Get logic detail id from logic detail table
	 * @param startTime
	 * @param LogicId
	 * @return
	 *//*
	public Cursor getLogicDeatilID(String startTime,String LogicId) {
		return _sqliteDB.rawQuery(
				"select "+ DBConstant.KEY_LOGIC_DETAIL_ID
				+ " from " + DBConstant.TBL_LOGIC_DETAIL 
				+ " where "+DBConstant.KEY_LOGIC_ID+"='"+LogicId+"' and "+DBConstant.KEY_LOGIC_START_TIME+"='"+startTime+"';",null);
	}
	
	*//**
	 * Update logic detail id in logic detail table..
	 * @param logicId
	 * @param logicDetailId
	 * @return
	 *//*
	public int UpadteLogicDetailId(String srtTime,String endTime,String logicDetailId,String logicId) {
		ContentValues value = new ContentValues();
		value.put(DBConstant.KEY_LOGIC_START_TIME,srtTime );
		value.put(DBConstant.KEY_LOGIC_STOP_TIME,endTime );
		return _sqliteDB.update(DBConstant.TBL_LOGIC_DETAIL ,
				value, DBConstant.KEY_LOGIC_DETAIL_ID +"=?"+" and " +DBConstant.KEY_LOGIC_ID +"=?",
				new String[]{logicId,logicDetailId});
	}

	
	

	*//**
	 * Method to insert data in Schedule Detail table
	 *//*
	public long insertIntoScheduleDetail(ScheduleDetailBean scheduleDetail) {

		ContentValues values = new ContentValues();

		values.put(DBConstant.KEY_SLOC_ID, scheduleDetail.getSloc_id());
		values.put(DBConstant.KEY_ORG_ID, scheduleDetail.getOrg_id());
		values.put(DBConstant.KEY_SEQ_NO, scheduleDetail.getSeq_no());
		values.put(DBConstant.KEY_SCHEDULE_ID, scheduleDetail.getSchedule_id());
		values.put(DBConstant.KEY_START_TIME, scheduleDetail.getStrt_time_period());
		values.put(DBConstant.KEY_END_TIME, scheduleDetail.getEnd_time_period());
		values.put(DBConstant.KEY_USERNAME, scheduleDetail.getUsername());
		values.put(DBConstant.KEY_MOD_DT, scheduleDetail.getMod_dt());
		values.put(DBConstant.KEY_LOW_THREST_REST, scheduleDetail.getLow_thresh_period());
		values.put(DBConstant.KEY_HIGH_THREST_REST, scheduleDetail.getHigh_thresh_period());

		return _sqliteDB.replace(DBConstant.TBL_SCHEDULE_DETAIL, null, values);
	}

	*//**
	 * Method to select all data of a table
	 * @param tableName
	 *//*
	public Cursor getAllDataFromTable(String tableName) {
		return _sqliteDB.rawQuery("select * from " + tableName, null);
	}

	*//**
	 * Delete all data from table
	 * @param tableName
	 *//*
	public int deleteAllDataFromTable(String tableName) {
		return _sqliteDB.delete(tableName, null, null);
	}

	*//**
	 * Method to select schedule detail data
	 * @param tableName
	 *//*
	public Cursor getScheduleDetailData(String scheduleId) {
		return _sqliteDB.rawQuery(
				"select * from " + DBConstant.TBL_SCHEDULE_DETAIL 
				+ " where " + DBConstant.KEY_SCHEDULE_ID + "=?",
				new String[] {scheduleId});
	}
*/}
