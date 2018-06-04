using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

/// <summary>
/// https://www.jianshu.com/p/86b275da600e
/// </summary>
public class NoJarAndroidCall : MonoBehaviour {

	public Text _text;
	public Button _WifiBtn;
	public Button _WifiStatusBtn;
	private bool _IsWifiOpen = true;
	AndroidJavaObject _ajc;
	// Use this for initialization
	void Start () {
		_ajc = new AndroidJavaObject("com.kevin.unity.NojarAndroid");
		_WifiBtn.onClick.AddListener(SetWifi);
		_WifiStatusBtn.onClick.AddListener(delegate {
		
			_ajc.Call("showWifiStatus", _IsWifiOpen);
		});
	}

	public void SetWifi()
	{
		_IsWifiOpen = !_IsWifiOpen;
		_ajc.Call("setWifistatus", _IsWifiOpen);
		Debug.Log("set wifi");
	}

	public void FromAndroid(string content)
	{
		_text.text = content;
	}
}
