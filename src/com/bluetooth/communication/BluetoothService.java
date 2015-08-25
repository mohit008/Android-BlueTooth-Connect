package com.bluetooth.communication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bluetooth.R;

/**
 * This is the main Activity that displays the current chat session.
 */
public class BluetoothService extends Activity {
	// Debugging
	private static final String TAG = "BluetoothChat";
	private static final boolean D = true;

	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	private static final int REQUEST_CONNECT_DEVICE = 2;
	private static final int REQUEST_ENABLE_BT = 3;

	//toggle to on-off signal
	private ToggleButton tog1,tog2,tog3,tog4;
	
	//seek bar
	private SeekBar seek1,seek2,seek3,seek4;

	//massage
	TextView message;
	
	private String mConnectedDeviceName = null,mac="";
	

	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothChatService mChatService = null;

	//default intensity
	private int f1_intensity = 1,
			f2_intensity = 1,
			f3_intensity = 1,
			f4_intensity = 1;

	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");

		setContentView(R.layout.control_screen);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		Intent intent = getIntent();
		mac = intent.getStringExtra("device.address");

		getVariable();
		setupChat();
		connectDevice();
	}



	/*	@Override
	public synchronized void onResume() {
		super.onResume();
		if (D)
			Log.e(TAG, "+ ON RESUME +");

		if (mChatService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
				mChatService.start();
			}
		}
	}*/

	private void setupChat() {
		Log.d(TAG, "setupChat()");

		mChatService = new BluetoothChatService(this, mHandler);

	}


	private void sendMessage(String message) {

		byte[] send = message.getBytes();
		mChatService.write(send);

	}


	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				String writeMessage = new String(writeBuf);
				/*	mConversationArrayAdapter.add("Me:  " + writeMessage);*/
				break;
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				// construct a string from the valid bytes in the buffer
				String readMessage = new String(readBuf, 0, msg.arg1);
					message.setText(mConnectedDeviceName + ":  "
						+ readMessage);
				
				break;
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	private void connectDevice() {

		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(mac);
		mChatService.connect(device);
	}
	
	
	public void getVariable(){

		message = (TextView) findViewById(R.id.recieve);
		
		tog1 = (ToggleButton)findViewById(R.id.toggle1);
		tog2 = (ToggleButton)findViewById(R.id.toggle2);
		tog3 = (ToggleButton)findViewById(R.id.toggle3);
		tog4 = (ToggleButton)findViewById(R.id.toggle4);

		seek1 = (SeekBar)findViewById(R.id.seek1);
		seek2 = (SeekBar)findViewById(R.id.seek2);
		seek3 = (SeekBar)findViewById(R.id.seek3);
		seek4 = (SeekBar)findViewById(R.id.seek4);
		
		seek1.setMax(5);
		seek2.setMax(5);
		seek3.setMax(5);
		seek4.setMax(5);
		
		seek1.setProgress(1);
		seek2.setProgress(1);
		seek3.setProgress(1);
		seek4.setProgress(1);
		
		if(tog1.isChecked()){
			seek1.setEnabled(true);
		}else if(!tog1.isChecked()){
			seek1.setEnabled(false);
		}
		
		if(tog2.isChecked()){
			seek2.setEnabled(true);
		}else if(!tog2.isChecked()){
			seek2.setEnabled(false);
		}
		if(tog3.isChecked()){
			seek3.setEnabled(true);
		}else if(!tog3.isChecked()){
			seek3.setEnabled(false);
		}
		if(tog4.isChecked()){
			seek4.setEnabled(true);
		}else if(!tog4.isChecked()){
			seek4.setEnabled(false);
		}
		
		
		seek1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
					if(progress == 0){
						seek1.setProgress(1);
					}
					f1_intensity = progress;
					sendMessage("211"+f1_intensity+"3DA");
			}
		});
		seek2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if(progress == 0){
					seek4.setProgress(1);
				}
					f2_intensity = progress;
					sendMessage("221"+f2_intensity+"3DA");
			}
		});
		seek3.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if(progress == 0){
					seek4.setProgress(1);
				}
					f3_intensity = progress;
					sendMessage("231"+f3_intensity+"3DA");
			}
		});
		seek4.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if(progress == 0){
					seek4.setProgress(1);
				}
					f4_intensity = progress;
					sendMessage("241"+f4_intensity+"3DA");
			}
		});

		tog1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){

					sendMessage("211"+f1_intensity+"3DA");
					seek1.setEnabled(true);
				}else{

					sendMessage("210"+f1_intensity+"3DA");
					seek1.setEnabled(false);
				}
			}
		});
		tog2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){

					sendMessage("221"+f2_intensity+"3DA");
					seek2.setEnabled(true);
				}else{

					sendMessage("220"+f2_intensity+"3DA");
					seek2.setEnabled(false);
				}
			}
		});
		tog3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){

					sendMessage("231"+f3_intensity+"3DA");
					seek3.setEnabled(true);
				}else{

					sendMessage("230"+f3_intensity+"3DA");
					seek3.setEnabled(false);
				}
			}
		});
		tog4.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){

					sendMessage("241"+f4_intensity+"3DA");
					seek4.setEnabled(true);
				}else{

					sendMessage("240"+f4_intensity+"3DA");
					seek4.setEnabled(false);
				}
			}
		});

	}
	
	public void Finish(){
		startActivity(new Intent(BluetoothService.this,PairedListActivity.class));
	}

}
