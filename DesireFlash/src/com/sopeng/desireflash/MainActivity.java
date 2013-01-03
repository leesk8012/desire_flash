package com.sopeng.desireflash;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;

public class MainActivity extends Activity
{
	private static DesireLED desireLED;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		desireLED = new DesireLED();
		desireLED.ExecSuCommand("chmod 666 /dev/msm_camera/config0");
		desireLED.NativeCtrlCameraLED(desireLED._nCurrentMode);
		Log.i("LED",""+desireLED.NativeGetSensorName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO Auto-generated method stub
		switch(event.getAction() & MotionEvent.ACTION_MASK)
		{
			case	MotionEvent.ACTION_DOWN:
				switch(desireLED._nCurrentMode)
				{
				case	DesireLED.MSM_CAMERA_LED_OFF:
					desireLED._nCurrentMode = DesireLED.MSM_CAMERA_LED_LOW;
					break;
	
				case	DesireLED.MSM_CAMERA_LED_LOW:
					desireLED._nCurrentMode = DesireLED.MSM_CAMERA_LED_HIGH;
					break;
	
				case	DesireLED.MSM_CAMERA_LED_HIGH:
					desireLED._nCurrentMode = DesireLED.MSM_CAMERA_LED_LOW_FOR_SNAPSHOT;
					break;
	
				case	DesireLED.MSM_CAMERA_LED_LOW_FOR_SNAPSHOT:
					desireLED._nCurrentMode = DesireLED.MSM_CAMERA_LED_OFF;
					break;
				}

				desireLED.NativeCtrlCameraLED(desireLED._nCurrentMode);
			break;
		}
		
		return super.onTouchEvent(event);
	}
	
}
