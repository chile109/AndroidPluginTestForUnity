package com.example.unity;

import java.util.Hashtable;

import com.unity3d.player.UnityPlayer;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import org.json.*;
public class Util {
	
		
	
	
	public static Bitmap decodeSampledBitmapFromFile(String path) {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		final int height = options.outHeight;
		final int width = options.outWidth;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		int inSampleSize = 1;

		options.inSampleSize = inSampleSize;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}
	
	public static Bitmap crop(Bitmap bm) {

		int w = bm.getWidth();
		int h = bm.getHeight();

		int r = w;
		if (r>h) r= h;
		return Bitmap.createBitmap(bm, (w-r)/2 ,(h-r)/2,r, r);
	}



}
