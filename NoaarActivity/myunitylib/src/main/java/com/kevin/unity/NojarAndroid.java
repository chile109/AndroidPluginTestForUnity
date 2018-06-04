package com.kevin.unity;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NojarAndroid {
	private Activity _unityActivity;
	private static Context unityContext;
	public static void init(Context paramContext){
		unityContext = paramContext.getApplicationContext();
	}

	Activity GetActivity()
	{
		if(null == _unityActivity)
		{
			//不知名型態class 外層需包try才不會報錯
			try {
				Class<?> classtype = Class.forName("com.unity3d.player.UnityPlayer");
				Activity activity = (Activity) classtype.getDeclaredField("currentActivity").get(classtype);
				_unityActivity = activity;
			} catch (ClassNotFoundException e) {

			} catch (IllegalAccessException e) {

			} catch (NoSuchFieldException e) {

			}
		}

		return _unityActivity;
	}

	/**
	 * 调用Unity的方法
	 * @param gameObjectName    调用的GameObject的名称
	 * @param functionName      方法名
	 * @param args              参数
	 * @return                  调用是否成功
	 */
	boolean callUnity(String gameObjectName, String functionName, String args){
		try {
			Class<?> classtype = Class.forName("com.unity3d.player.UnityPlayer");
			Method method =classtype.getMethod("UnitySendMessage", String.class,String.class,String.class);
			method.invoke(classtype,gameObjectName,functionName,args);
			return true;
		} catch (ClassNotFoundException e) {

		} catch (NoSuchMethodException e) {

		} catch (IllegalAccessException e) {

		} catch (InvocationTargetException e) {

		}
		return false;
	}

	/**
	 * Toast显示unity发送过来的内容
	 * @param content           消息的内容
	 * @return                  调用是否成功
	 */
	public boolean showToast(String content){
		Toast.makeText(GetActivity(),content,Toast.LENGTH_SHORT).show();
		//这里是主动调用Unity中的方法，该方法之后unity部分会讲到
		callUnity("Main Camera","FromAndroid", content);
		return true;
	}

	WifiManager _wifi = (WifiManager) GetActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

	public void setWifistatus (boolean state)
	{
		_wifi.setWifiEnabled(state);
	}

	public void showWifiStatus(boolean state)
	{
		if(state)
		{
			WifiInfo connectionInfo = _wifi.getConnectionInfo();	//wifi強度值
			int numberOfLevels = 5;
			int level = WifiManager.calculateSignalLevel(connectionInfo.getRssi(), numberOfLevels);   //wifi強度分級

			showToast("Wifi Level:" + level);
		}
		else
			showToast("未連接wifi");
	}
}
