using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

/// <summary>
/// https://www.jianshu.com/p/86b275da600e
/// </summary>
public class NoJarAndroidCall : MonoBehaviour {

	public Text _text;

	AndroidJavaObject _ajc;
	// Use this for initialization
	void Start () {
		_ajc = new AndroidJavaObject("com.kevin.unity.NojarAndroid");
	}

	public void SetToast()
	{
		bool success = _ajc.Call<bool>("showToast", "this is kevin unity");

		if(success == true)
		{
			Debug.Log("requsest success!!");
		}
	}

	public void disableWifi()
	{
		_ajc.Call("setWifistatus");
		Debug.Log("disable wifi");
	}

	public void FromAndroid(string content)
	{
		_text.text = content;
	}
}
