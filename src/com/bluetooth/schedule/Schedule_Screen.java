package com.bluetooth.schedule;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluetooth.R;
import com.bluetooth.db.DBConstant;
import com.bluetooth.db.DBManager;

public class Schedule_Screen extends Activity {

	private ListView list;
	private EditText edit;
	private Button add;
	private ArrayList<ScheduleBean>  schedule_bean = new ArrayList<ScheduleBean>();
	private DBManager dbManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devices_list);

		ActionBar bar= getActionBar();
		bar.setTitle("Schedule");
		bar.setIcon(getResources().getDrawable(R.drawable.schedule));


		edit = (EditText) findViewById(R.id.list_edit);
		add = (Button) findViewById(R.id.list_add);
		list=(ListView)findViewById(R.id.list);

		dbManager = new DBManager(this);

		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(!edit.getText().toString().equals("")){
					String name = edit.getText().toString();

					ScheduleBean bean = new ScheduleBean();
					bean.setSch_Name(name);

					dbManager.open();
					dbManager.insertIntoSchedule(bean);
					dbManager.close();

					schedule_bean.add(bean);
					list.setAdapter(new Schedule_Adapter(Schedule_Screen.this,schedule_bean));

					edit.setText("");
				}else{
					Toast.makeText(Schedule_Screen.this, "Please Enter Schedule Name", Toast.LENGTH_LONG).show();
				}


			}
		});


		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {

			}

		});



	}

	@Override
	protected void onResume() {

		setDataFromDB();
		super.onResume();
	}



	public class Schedule_Adapter extends BaseAdapter {


		private Context context;
		public ArrayList<ScheduleBean> schedule_bean = new ArrayList<ScheduleBean>();

		public Schedule_Adapter(Context context,ArrayList<ScheduleBean> schedule_bean) {
			this.context = context;
			this.schedule_bean  = schedule_bean;

		}
		@Override
		public int getCount() {
			return schedule_bean.size();
		}
		@Override
		public Object getItem(int position) {
			return schedule_bean.get(position);
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.schedule_screen_item, null);
				holder = new ViewHolder();
				holder.name = (TextView)convertView.findViewById(R.id.schedule_name);
				holder.layout = (RelativeLayout)convertView.findViewById(R.id.schedule_screen_item_layout);
				holder.edit_name = (ImageView)convertView.findViewById(R.id.schedule_edit_name);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			final String name = schedule_bean.get(position).getSch_Name();
			holder.name.setText(schedule_bean.get(position).getSch_Name());
			holder.layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Schedule_Screen.this,Schedule_Detail_Screen.class);
					intent.putExtra("name", name);
					startActivity(intent);
					
					
				}
			});
			
			return convertView;
		}



	}

	public class ViewHolder{
		TextView name;
		RelativeLayout layout;
		ImageView edit_name;
	}

	private void setDataFromDB() {
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




}