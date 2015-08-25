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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluetooth.R;

public class PairedListActivity extends Activity {
	private ListView mListView;
	private DeviceAdapter mAdapter;
	private ArrayList<BluetoothDevice> mDeviceList;
	private EditText edit;
	
	private String mac ="",activity_name= "";
	
	private Button add;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.devices_list);
		
		mDeviceList		= getIntent().getExtras().getParcelableArrayList("device.list");
		mListView		= (ListView) findViewById(R.id.list);
		edit     		= (EditText) findViewById(R.id.list_edit);
		mAdapter		= new DeviceAdapter(this);
		
		add = (Button) findViewById(R.id.list_add);
		
		edit.setVisibility(View.GONE);
		add.setVisibility(View.GONE);
		Intent intent = getIntent();
		activity_name = intent.getExtras().getString("ativity_name");
		mAdapter.setData(mDeviceList);
		
		mListView.setAdapter(mAdapter);
	
		if(activity_name!=null){
			if(activity_name.equals("main_activity")){
				ActionBar bar = getActionBar();
				bar.setTitle("Connected Device");
				
			}else if(activity_name.equals("config_activity")){
				ActionBar bar = getActionBar();
				bar.setTitle("Paired Device");
			}
		
		}
			
		
		
		
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

    private void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
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
	
	
	
	public class DeviceAdapter extends BaseAdapter{
		Context context;
		private LayoutInflater mInflater;	
		private List<BluetoothDevice> mData;
		
		public DeviceAdapter(Context context) { 
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
				convertView			=  mInflater.inflate(R.layout.paired_item_device, null);
				
				holder 				= new ViewHolder();
				
				holder.nameTv		= (TextView) convertView.findViewById(R.id.device_name);
				holder.addressTv 	= (TextView) convertView.findViewById(R.id.device_address);
				holder.pairBtn		= (Button) convertView.findViewById(R.id.btn_pair);
				holder.pairSch		= (Button) convertView.findViewById(R.id.btn_schedule);
				holder.go		    = (ImageButton) convertView.findViewById(R.id.go);
				holder.layout		= (LinearLayout) convertView.findViewById(R.id.paired_button);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			BluetoothDevice device	= mData.get(position);
			
			if(activity_name!=null){
				if(activity_name.equals("main_activity")){
					holder.layout.setVisibility(View.GONE);
				}else if(activity_name.equals("config_activity")){
					holder.go.setVisibility(View.INVISIBLE);
				}
			}
			
			
			holder.nameTv.setText(device.getName());
			holder.addressTv.setText(device.getAddress());
			holder.pairBtn.setText((device.getBondState() == BluetoothDevice.BOND_BONDED) ? "Unpair" : "Pair");
			
			if(device.getBondState() != BluetoothDevice.BOND_BONDED){
				holder.go.setVisibility(View.INVISIBLE);
			}
			holder.pairBtn.setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					BluetoothDevice device = mDeviceList.get(position);
					
					if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
						unpairDevice(device);
						notifyDataSetChanged();
					} 
				}
			});
			holder.go.setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					
						startBlueToothAct(mData, position);
					
				}
			});
			
			
	        return convertView;
		}

		class ViewHolder {
			TextView nameTv;
			TextView addressTv;
			Button pairBtn;
			Button pairSch;
			ImageButton go;
			LinearLayout layout;
		}
	
	}
	
	public void startBlueToothAct( List<BluetoothDevice> Data,int position){
		
		Intent intent = new Intent(PairedListActivity.this,BluetoothService.class);
		mac = Data.get(position).getAddress();
		intent.putExtra("device.address", mac);
		startActivity(intent);
		
	}

}