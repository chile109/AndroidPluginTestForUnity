using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using com.grepgame.android.plugin;
using SimpleJSON;

public class PhotoPlugin : MonoBehaviour {
	public Image mImage;

	void Start()
	{
		//handle Image data returned from Plugin
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

		Texture2D xx = new Texture2D(w, h, TextureFormat.BGRA32, false);
		xx.LoadImage(data);
		Sprite newSprite = Sprite.Create(xx as Texture2D, new Rect(0f, 0f, xx.width, xx.height), Vector2.zero);
		mImage.sprite = newSprite;
	}
}
