package com.example.unity;

import android.util.Log;

public class MyPlugin {
	private static final MyPlugin ourInstance = new MyPlugin();

	private static final String LOGTAG = "CWTech";

	public static MyPlugin getInstance() {
		return ourInstance;
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
}

