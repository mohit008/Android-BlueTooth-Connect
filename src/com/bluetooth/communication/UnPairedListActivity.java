package com.bluetooth.communication;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluetooth.R;

public class UnPairedListActivity extends Activity {
	private ListView mListView;
	private UnpairDeviceAdapter mAdapter;
	private ArrayList<BluetoothDevice> mDeviceList;
	private EditText edit;

	private String mac ="";

	private Button add;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.devices_list);

		mDeviceList		= getIntent().getExtras().getParcelableArrayList("device.list");
		mListView		= (ListView) findViewById(R.id.list);
		edit    		= (EditText) findViewById(R.id.list_edit);
		add				= (Button) findViewById(R.id.list_add);
		mAdapter		= new UnpairDeviceAdapter(this);

		mAdapter.setData(mDeviceList);


		ActionBar bar = getActionBar();
		bar.setTitle("UnPaired Device");
		edit.setVisibility(View.GONE);
		add.setVisibility(View.GONE);

		mListView.setAdapter(mAdapter);



		registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)); 
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(mPairReceiver);

		super.onDestroy();
	}


	private void showToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	private void pairDevice(BluetoothDevice device) {
		try {
			Method method = device.getClass().getMethod("createBond", (Class[]) null);
			method.invoke(device, (Object[]) null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {	        	
				final int state 		= intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
				final int prevState	= intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

				if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
					showToast("Paired");
				} else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED){
					showToast("Unpaired");
				}

				mAdapter.notifyDataSetChanged();
			}
		}
	};



	public class UnpairDeviceAdapter extends BaseAdapter{
		Context context;
		private LayoutInflater mInflater;	
		private List<BluetoothDevice> mData;

		public UnpairDeviceAdapter(Context context) { 
			this.context = context;
			mInflater = LayoutInflater.from(context);        
		}

		public void setData(List<BluetoothDevice> data) {
			mData = data;
		}


		public int getCount() {
			return (mData == null) ? 0 : mData.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {			
				convertView			=  mInflater.inflate(R.layout.unpair_item_device, null);

				holder 				= new ViewHolder();

				holder.unpair_name		= (TextView) convertView.findViewById(R.id.unpair_device_name);
				holder.unpair_add 	= (TextView) convertView.findViewById(R.id.unpair_device_address);
				holder.unpair		= (Button) convertView.findViewById(R.id.unpair_btn);


				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			BluetoothDevice device	= mData.get(position);



			holder.unpair_name.setText(device.getName());
			holder.unpair_add.setText(device.getAddress());
			holder.unpair.setText((device.getBondState() == BluetoothDevice.BOND_BONDED) ? "Unpair" : "Pair");


			holder.unpair.setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					BluetoothDevice device = mDeviceList.get(position);

					showToast("Pairing...");

					pairDevice(device);

					notifyDataSetChanged();

				}
			});



			return convertView;
		}

		class ViewHolder {
			TextView unpair_name;
			TextView unpair_add;
			Button unpair;
		}

	}


}