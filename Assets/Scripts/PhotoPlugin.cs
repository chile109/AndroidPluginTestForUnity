using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using com.grepgame.android.plugin;
using SimpleJSON;

public class PhotoPlugin : MonoBehaviour {
	public Image mImage;
	public AspectRatioFitter _aspectRatioFitter;

	void Start()
	{
		//通過委派將收到資料交給imageHandle生成spite
		Koi.getInstance().imageDelegate = imageHandle;
	}
	//The result image

	public void openGallery()
	{
		Koi.getInstance().openGallery();
	}

	public void openCamera()
	{
		Koi.getInstance().openCamera();
	}

	/**
     * Create Image with data from Plugin
     */
	void imageHandle(string message, byte[] data)
	{

		JSONArray jsa = (JSONArray)JSON.Parse(message);
		JSONNode jsn = jsa[0];
		int w = jsn["width"].AsInt;
		int h = jsn["height"].AsInt;

		Debug.Log("width: " + w);
		Debug.Log("height: " + h);

		Texture2D xx = new Texture2D(w, h, TextureFormat.BGRA32, false);
		xx.LoadImage(data);
		Sprite newSprite = Sprite.Create(xx as Texture2D, new Rect(0f, 0f, xx.width, xx.height), Vector2.zero);
		_aspectRatioFitter.aspectRatio = (float)newSprite.texture.width / (float)newSprite.texture.height;
		mImage.sprite = newSprite;

	}
}
