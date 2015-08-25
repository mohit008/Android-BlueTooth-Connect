package com.bluetooth.communication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluetooth.R;

public class Home_Adapter extends BaseAdapter {
	
	
	private Context context;

	public Home_Adapter(Context context) {
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
			grid = inflater.inflate(R.layout.home_grid_item, null);
			ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
			TextView textview = (TextView)grid.findViewById(R.id.grid_caption);

			imageView.setImageResource(images[position]);
			textview.setText(caption[position]);
		

		} else {
			grid = (View) convertView;
		}
		return grid;
	}
	
	private int[] images = {
			R.drawable.device,
			R.drawable.setting
	};
	
	private String[] caption = {
		"Device",
		"Settings"
	};
	



}
