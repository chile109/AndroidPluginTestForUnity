using System.Collections;
using System.Collections.Generic;
using UnityEngine;
/// <summary>
/// https://www.youtube.com/watch?v=bmNMugkOQBI
/// </summary>
public class PluginTest : MonoBehaviour {

	const string pluginName = "com.example.unity.MyPlugin";


	class AlertViewCallback : AndroidJavaProxy
	{
		private System.Action<int> alertHandler;

		public AlertViewCallback(System.Action<int> alertHandlerIn): base (pluginName + "$AlertViewCallback")
		{
			alertHandler = alertHandlerIn;
		}

		public void onButtonTapped(int index)
		{
			Debug.Log("Button Tapped:" + index);
			if (alertHandler != null)
				alertHandler(index);
		}
	}

	// Android的Java接口 
	static AndroidJavaClass _pluginClass;
	static AndroidJavaObject _pluginInstance;

	public static AndroidJavaClass pluginClass
	{
		get {
			if(_pluginClass == null)
			{
				_pluginClass = new AndroidJavaClass(pluginName);
				AndroidJavaClass playerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");	//UnityPlayer是FrameLayout的一个子类
				AndroidJavaObject activity = playerClass.GetStatic<AndroidJavaObject>("currentActivity");	//currentActivity则是UnityPlayer类中的静态对象。
				_pluginClass.SetStatic<AndroidJavaObject>("mainActivity", activity);	//將UnityPlayer的currentActivity傳給java端的mainActivity
			}
			return _pluginClass;
		}
	}

	public static AndroidJavaObject PluginInstance{
		get{
			if(_pluginInstance == null)
			{
				_pluginInstance = pluginClass.CallStatic<AndroidJavaObject>("getInstance");		//呼叫靜態方法getInstance取得方法實例
			}
			return _pluginInstance;
		}
	}
	// Use this for initialization
	void Start () {
		Debug.Log("Time:" + getElapsedTime());
	}


	float elapsedTime = 0;

	void Update () {
		elapsedTime += Time.deltaTime;
		if(elapsedTime >= 5)
		{//	每五秒發送一次手機計時
			elapsedTime -= 5;
			Debug.Log("Tick: " + getElapsedTime());
		}

		if(Input.GetMouseButtonDown(0))
		{
			showAlertDialog(new string[] { "Alert Title", "Alert Message", "Button 1", "Button 2" }, (int obj) => {
				Debug.Log("Local Handle called: " + obj);			
			});
		}
	} 

	//返回java端getElapseTime的值
	double getElapsedTime()
	{
		if (Application.platform == RuntimePlatform.Android)
		{
			return PluginInstance.Call<double>("getElapseTime");
		}

		Debug.LogWarning("Wrong Platform");

		return 0;
	}

	void showAlertDialog(string[] strings, System.Action<int> handler = null)
	{
		if(strings.Length < 3)
		{
			Debug.Log("AlerrtView require at least 3 strings");
			return;
		}

		if (Application.platform == RuntimePlatform.Android)
			PluginInstance.Call("showAlertView", new object[] { strings, new AlertViewCallback(handler) });	  //調用java端的showAlertView方法
		else
			Debug.LogWarning("AlertView not supportted on this platform");
	}
}
