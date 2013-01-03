package com.sopeng.desireflash;

public class DesireLED
{
	public DesireLED()
	{
	}
	
	boolean	ExecSuCommand(String strCommandLine)
	{
		try
		{
			Process	process = Runtime.getRuntime().exec("su");
			java.io.DataOutputStream os = new java.io.DataOutputStream(process.getOutputStream());
			os.writeBytes(strCommandLine + "\nexit\n");
			os.flush();
			process.waitFor();
			return	true;
		}
		catch(java.io.IOException e)
		{
		}
		catch (InterruptedException e)
		{
		}
		return	false;
	}
	
	int		_nCurrentMode = MSM_CAMERA_LED_OFF;	
	
	final	static	int	MSM_CAMERA_LED_OFF = 0;
	final	static	int	MSM_CAMERA_LED_LOW = 1;
	final	static	int	MSM_CAMERA_LED_HIGH = 2;
	final	static	int	MSM_CAMERA_LED_LOW_FOR_SNAPSHOT = 3;

	public native boolean  NativeIsSupportCameraLED();
	public native boolean  NativeCtrlCameraLED(int nMode);
	public native String  NativeGetSensorName();
	
	static
	{
		System.loadLibrary("desireflash");
	}
}


/*
public class Test132Act extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ExecSuCommand("chmod 666 /dev/msm_camera/config0");

		NativeCtrlCameraLED(_nCurrentMode);

		TextView	tv = new TextView(this);
		tv.setText( NativeGetSensorName() );
		setContentView(tv);
	}

	boolean	ExecSuCommand(String strCommandLine)
	{
		try
		{
			Process	process = Runtime.getRuntime().exec("su");
			java.io.DataOutputStream os = new java.io.DataOutputStream(process.getOutputStream());
			os.writeBytes(strCommandLine + "\nexit\n");
			os.flush();
			process.waitFor();
			return	true;
		}
		catch(java.io.IOException e)
		{
		}
		catch (InterruptedException e)
		{
		}
		return	false;
	}

	int		_nCurrentMode = MSM_CAMERA_LED_OFF;
	
	final	static	int	MSM_CAMERA_LED_OFF = 0;
	final	static	int	MSM_CAMERA_LED_LOW = 1;
	final	static	int	MSM_CAMERA_LED_HIGH = 2;
	final	static	int	MSM_CAMERA_LED_LOW_FOR_SNAPSHOT = 3;


	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch(event.getAction() & MotionEvent.ACTION_MASK)
		{
		case	MotionEvent.ACTION_DOWN:
			switch(_nCurrentMode)
			{
			case	MSM_CAMERA_LED_OFF:
				_nCurrentMode = MSM_CAMERA_LED_LOW;
				break;

			case	MSM_CAMERA_LED_LOW:
				_nCurrentMode = MSM_CAMERA_LED_HIGH;
				break;

			case	MSM_CAMERA_LED_HIGH:
				_nCurrentMode = MSM_CAMERA_LED_LOW_FOR_SNAPSHOT;
				break;

			case	MSM_CAMERA_LED_LOW_FOR_SNAPSHOT:
				_nCurrentMode = MSM_CAMERA_LED_OFF;
				break;
			}

			NativeCtrlCameraLED(_nCurrentMode);
			break;
		}

		return true;
	}

	
	public native boolean  NativeIsSupportCameraLED();
	public native boolean  NativeCtrlCameraLED(int nMode);
	public native String  NativeGetSensorName();

	static
	{
		System.loadLibrary("Test132");
	}
}
*/