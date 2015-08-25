package com.bluetooth.schedule;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bluetooth.R;
import com.bluetooth.db.DBConstant;
import com.bluetooth.db.DBManager;

public class Schedule_Detail_Edit extends Activity {


	private Button add;
	private TextView name;
	private DBManager dbManager;

	private String schedule_name,action,
		srtTime_text,stpTime_text,d1_intensity_text,
		d2_intensity_text,d3_intensity_text,
		d4_intensity_text,d1_status_text = "0",
		d2_status_text = "0",d3_status_text = "0",
		d4_status_text = "0";
	private EditText srtTime,stpTime;
	private Spinner d1_intensity,
		d2_intensity,
		d3_intensity,
		d4_intensity;
	private ToggleButton d1_status,
		d2_status,
		d3_status,
		d4_status;

	private ActionBar bar;
	private Intent intent;

	private TimePickerDialog startTimePickerDialog;
	private TimePickerDialog endTimePickerDialog;
	
	private ArrayList<ScheduleDetailBean> schedule_detail_bean = new ArrayList<ScheduleDetailBean>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_detail_edit);

		setUp();
		setSpinnerData();

		bar.setTitle("Schedule Detail");
		bar.setIcon(getResources().getDrawable(R.drawable.schedule));

		schedule_name = intent.getExtras().getString("name");
		action = intent.getExtras().getString("action");

		name.setText(schedule_name);
		if(action.equals("add")){
			add.setText("Add");			
		}else if(action.equals("update")){
			add.setText("Update");
		}

		setTimeField();
		
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getToggleState();
				
				Toast.makeText(Schedule_Detail_Edit.this, srtTime.getText().toString()+"\n"+
						stpTime.getText().toString()+"\n"+
						d1_intensity.getSelectedItem().toString()+"\n"+
						d1_status_text+"\n"+
						d2_intensity.getSelectedItem().toString()+"\n"+
						d2_status_text+"\n"+
						d3_intensity.getSelectedItem().toString()+"\n"+
						d3_status_text+"\n"+
						d4_intensity.getSelectedItem().toString()+"\n"+
						d4_status_text
						, Toast.LENGTH_SHORT).show();
				if(!srtTime.getText().toString().equals("") && !stpTime.getText().toString().equals("")){
					String start = srtTime.getText().toString();
					String stop = stpTime.getText().toString();

					ScheduleDetailBean bean = new ScheduleDetailBean();
					
					
					bean.setSch_Name(schedule_name);
					bean.setSrt_Time(start);
					bean.setStp_Time(stop);
					bean.setF1_Intensity(d1_intensity_text);
					bean.setF1_Status(d1_status_text);
					bean.setF2_Intensity(d2_intensity_text);
					bean.setF2_Status(d2_status_text);
					bean.setF3_Intensity(d3_intensity_text);
					bean.setF3_Status(d3_status_text);
					bean.setF4_Intensity(d4_intensity_text);
					bean.setF4_Status(d4_status_text);


					dbManager.open();
					dbManager.insertIntoScheduleDetail(bean);
					dbManager.close();

					/*Cursor cursor = dbManager.getAllDataFromTable(DBConstant.TBL_SCHEDULE);
					if(cursor != null && cursor.getCount()>0) {
					
					}
*/
				}else{
					Toast.makeText(Schedule_Detail_Edit.this, "Please Enter Schedule Name", Toast.LENGTH_LONG).show();
				}

			}
		});

	}

	public void setUp(){
		name = (TextView) findViewById(R.id.schedule_name_on_detail);
		add = (Button) findViewById(R.id.add_schedule_detail);
		srtTime = (EditText)findViewById(R.id.srtTime_detail);
		stpTime = (EditText) findViewById(R.id.stpTime_detail);
		d1_intensity = (Spinner) findViewById(R.id.d1_intensity);
		d2_intensity = (Spinner) findViewById(R.id.d2_intensity);
		d3_intensity = (Spinner) findViewById(R.id.d3_intensity);
		d4_intensity = (Spinner) findViewById(R.id.d4_intensity);
		d1_status = (ToggleButton) findViewById(R.id.d1_status);
		d2_status = (ToggleButton) findViewById(R.id.d2_status);
		d3_status = (ToggleButton) findViewById(R.id.d3_status);
		d4_status = (ToggleButton) findViewById(R.id.d4_status);

		bar= getActionBar();
		intent = getIntent();
		dbManager = new DBManager(this);
	}

	public void setSpinnerData(){
		ArrayAdapter<String> spin_data = new ArrayAdapter<String>(Schedule_Detail_Edit.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.intensity));
		spin_data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		d1_intensity.setAdapter(spin_data);
		d2_intensity.setAdapter(spin_data);
		d3_intensity.setAdapter(spin_data);
		d4_intensity.setAdapter(spin_data);
	}

	// Set Time Field
	private void setTimeField() {
		srtTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startTimePickerDialog.show();

			}
		});
		stpTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				endTimePickerDialog.show();

			}
		});

		startTimePickerDialog = new TimePickerDialog(this, new OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				srtTime.setText(hourOfDay + ":" + minute + ":00");
			}
		}, 00, 00, true);

		endTimePickerDialog = new TimePickerDialog(this, new OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				stpTime.setText(hourOfDay + ":" + minute + ":00");
			}
		}, 00, 00, true);

	}
	
	public void getToggleState(){
		if(d1_status.isChecked()){
			d1_status_text = "1";
		}
		if(!d1_status.isChecked()){
			d1_status_text = "0";
		}
		if(d2_status.isChecked()){
			d2_status_text = "1";
		}
		if(!d2_status.isChecked()){
			d2_status_text = "0";
		}
		if(d3_status.isChecked()){
			d3_status_text = "1";
		}
		if(!d3_status.isChecked()){
			d3_status_text = "0";
		}
		if(d4_status.isChecked()){
			d4_status_text = "1";
		}
		if(!d4_status.isChecked()){
			d4_status_text = "0";
		}
		
	}

	/*	








		

	    add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				

			}
		});



	 */




	/*  private void setDataFromDB() {
		dbManager.open();
		Cursor cursor = dbManager.getAllDataFromTable(DBConstant.TBL_SCHEDULE);
		if(cursor != null && cursor.getCount()>0) {
			schedule_bean.clear();
			if(cursor.moveToFirst()){
				do{
					ScheduleBean bean = new ScheduleBean();

					String sch_name = cursor.getString(cursor.getColumnIndex(DBConstant.SCHEDULE_NAME));

					bean.setSch_Name(sch_name);


					schedule_bean.add(bean);
				}while(cursor.moveToNext());
			}
		}
		dbManager.close();

		if (schedule_bean.size()>0) {
			list.setAdapter(new Schedule_Adapter(this, schedule_bean));
		}

	}
	 */



}