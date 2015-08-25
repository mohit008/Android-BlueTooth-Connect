package com.bluetooth.communication;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.bluetooth.R;

public class MainActivity extends Activity {

	private GridView grid;
	private BluetoothAdapter mBluetoothAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);

		//grid view
		grid=(GridView)findViewById(R.id.home_grid);
		grid.setAdapter(new Home_Adapter(this));

		mBluetoothAdapter	= BluetoothAdapter.getDefaultAdapter();

		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, 0);
		}



		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				openOption(position);
			}

		});



	}

	public void openOption(int position){

		Intent intent  = null;
		switch (position) {
		case 0:
			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

			ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();
			list.addAll(pairedDevices);
			intent = new Intent(MainActivity.this, PairedListActivity.class);
			intent.putParcelableArrayListExtra("device.list", list);
			intent.putExtra("ativity_name", "main_activity");

			break;
		case 1:
			intent = new Intent(MainActivity.this, Setting.class);	
			break;

		}
		startActivity(intent);		
	}
	
	@Override
	public void onBackPressed() {
		finish();
		
	}



}