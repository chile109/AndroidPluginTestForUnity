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
		showWifiStatus(state);
	}

	public void showWifiStatus(boolean state)
	{
		if(state)
		{
			WifiInfo connectionInfo = _wifi.getConnectionInfo();	//wifi強度值
			int numberOfLevels = 5;
			int level = WifiManager.calculateSignalLevel(connectionInfo.getRssi(), numberOfLevels);   //wifi強度分級

			callUnity("Main Camera","FromAndroid", getDetailsWifiInfo());

			showToast("wifi強度" + level);
		}
		else
			showToast("未連接wifi");
	}

	//顯示wifi詳細資訊
	public String getDetailsWifiInfo(){
		StringBuffer sInfo = new StringBuffer();
		WifiInfo mWifiInfo = _wifi.getConnectionInfo();

		int Ip = mWifiInfo.getIpAddress();
		String strIp = "" + (Ip & 0xFF) + "." + ((Ip >> 8) & 0xFF) + "." + ((Ip >> 16) & 0xFF) + "." + ((Ip >> 24) & 0xFF);

		sInfo.append("\n--BSSID : "+mWifiInfo.getBSSID());	//获取BSSID地址。
		sInfo.append("\n--SSID : "+mWifiInfo.getSSID());	// 获取SSID地址。  需要连接网络的ID
		sInfo.append("\n--nIpAddress : "+ strIp);	//获取IP地址。4字节Int, XXX.XXX.XXX.XXX 每个XXX为一个字节
		sInfo.append("\n--MacAddress : "+mWifiInfo.getMacAddress());	//获取MAC地址。
		sInfo.append("\n--NetworkId : "+mWifiInfo.getNetworkId());	//获取网络ID。
		sInfo.append("\n--LinkSpeed : "+mWifiInfo.getLinkSpeed()+"Mbps");	// 获取连接速度，可以让用户获知这一信息。
		sInfo.append("\n--Rssi : "+mWifiInfo.getRssi());	//获取RSSI，RSSI就是接受信号强度指示  
		sInfo.append("\n--SupplicantState : "+mWifiInfo.getSupplicantState());

		sInfo.append("\n\n\n\n");
		return sInfo.toString();
	}
}
