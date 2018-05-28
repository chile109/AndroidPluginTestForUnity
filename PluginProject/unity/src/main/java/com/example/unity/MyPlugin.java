package com.example.unity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

public class MyPlugin {
	private static final MyPlugin ourInstance = new MyPlugin();

	private static final String LOGTAG = "CWTech";

	public static MyPlugin getInstance() {
		return ourInstance;
	}

	public static Activity mainActivity;

	public interface AlertViewCallback{
		public void onButtonTapped(int id);
	}

	private long startTime;

	private MyPlugin() {
		Log.i(LOGTAG, "Create plugin");
		startTime = System.currentTimeMillis();
	}

	public double getElapseTime()
	{
		return (System.currentTimeMillis() - startTime) / 1000.0f;
	}

	public void showAlertView(String[] strings, final AlertViewCallback callback)
	{
		if(strings.length < 3)
		{
			Log.i(LOGTAG, "Error - expect at least 3 strings, got " + strings.length);
			return;
		}

		DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				Log.i(LOGTAG, "Tapped: " + id);
				callback.onButtonTapped(id);
			}
		};

		AlertDialog _alertDialog = new AlertDialog.Builder(mainActivity)
				.setTitle(strings[0])
				.setMessage(strings[1])
				.setCancelable(false)
				.create();

		_alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, strings[2], myClickListener);

		if(strings.length > 3)
			_alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, strings[3], myClickListener);
		if(strings.length > 4)
			_alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, strings[4], myClickListener);

		_alertDialog.show();
	}
}

