package com.bluetooth.communication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bluetooth.R;
import com.bluetooth.schedule.Schedule_Screen;

public class Setting extends Activity {

	private ListView list;
	private EditText edit;
	private Button add;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devices_list);
		
		ActionBar bar= getActionBar();
		bar.setTitle("Settings");
		bar.setIcon(getResources().getDrawable(R.drawable.setting));
		
		edit = (EditText) findViewById(R.id.list_edit);
		edit.setVisibility(View.GONE);
		add = (Button) findViewById(R.id.list_add);
		add.setVisibility(View.GONE);
		//list view
	    list=(ListView)findViewById(R.id.list);
	    list.setAdapter(new Setting_Adapter(this));
	   
	    
	    list.setOnItemClickListener(new OnItemClickListener() {
			   
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				 openOption(position);
			}
			
		});
	  
	   		
	
	}
	  
    public void openOption(int position){
    	Intent intent = null;
    	switch (position) {
		case 0:

				intent = new Intent(Setting.this, Device_Config.class);
				startActivity(intent);						
			
			break;
		case 1:
				intent = new Intent(Setting.this,Schedule_Screen.class);
			break;
			
		}
    	startActivity(intent);
    }
    
    @Override
    public void onBackPressed() {
    	finish();
		
    }
    
    
    public class Setting_Adapter extends BaseAdapter {
    	
    	
    	private Context context;

    	public Setting_Adapter(Context context) {
    		this.context = context;
    		
    	}
    	@Override
    	public int getCount() {
    		return images.length;
    	}
    	@Override
    	public Object getItem(int position) {
    		return images[position];
    	}
    	@Override
    	public long getItemId(int position) {
    		return 0;
    	}

    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		View grid;
    		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		if (convertView == null) {
    			grid = new View(context);
    			grid = inflater.inflate(R.layout.setting_list_item, null);
    			ImageView imageView = (ImageView)grid.findViewById(R.id.setting_list_image);
    			TextView textview = (TextView)grid.findViewById(R.id.setting_list_caption);

    			imageView.setImageResource(images[position]);
    			textview.setText(caption[position]);
    		

    		} else {
    			grid = (View) convertView;
    		}
    		return grid;
    	}
    	
    	private int[] images = {
    			R.drawable.config,
    			R.drawable.schedule
    	};
    	
    	private String[] caption = {
    		"Device Configuration",
    		"Schedule"
    	};
    	



    }

	
	

}