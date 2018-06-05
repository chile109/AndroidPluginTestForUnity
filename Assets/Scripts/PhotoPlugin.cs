using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class PhotoPlugin : MonoBehaviour {

	AndroidJavaClass unityPlayer;
	AndroidJavaObject currentActivity;

	AndroidJavaObject _ajo;
	Texture texture;

	public RawImage Pic;
	public Button _CameraBtn;
	public Button _GalleyBtn;

	// Use this for initialization
	void Start () {

		unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");

		_ajo = new AndroidJavaObject("com.kevin.unity.NojarAndroid");
		_ajo.CallStatic("init", currentActivity);

		_CameraBtn.onClick.AddListener(delegate
		{
			PhotoGalley("takePhoto");
		});

		_GalleyBtn.onClick.AddListener(delegate
		{
			PhotoGalley("openGalley");
		});
	}
	
	void PhotoGalley(string target)
	{
		_ajo.Call("PhotoMethod", target);
	}

	void getTexture(string str)
	{
		//在Android插件中通知Unity开始去指定路径中找图片资源
		StartCoroutine(LoadTexture(str));

	}

	IEnumerator LoadTexture(string ImgName)
	{
		//注解1
		string path = "file://" + Application.persistentDataPath + "/" + ImgName;

		WWW www = new WWW(path);
		while (!www.isDone)
		{

		}
		yield return www;
		//为贴图赋值
		texture = www.texture;

		Pic.texture = texture;
	}
}
