package com.krislq.sliding;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;

//这个类为了防止图片太大会溢出
//自动进行缩放(代码源自AndroidAPI)
public class LargeImageUtil {
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) { // Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2; // Calculate the largest
												// inSampleSize value that is a
												// power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}
	public static Bitmap decodeSampledBitmapFromResource(ContentResolver resolver, Uri uri, int reqWidth, int reqHeight) throws FileNotFoundException {
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		
		Rect rect=new Rect();
		rect.left=0;rect.top=0;
		rect.bottom=reqHeight;rect.right=reqWidth;
		
		InputStream in=resolver.openInputStream(uri);
		BitmapFactory.decodeStream(in, null, options); // Calculate
															// inSampleSize
		
		options.inSampleSize = calculateInSampleSize(options, reqWidth,	reqHeight); // Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		
		in=resolver.openInputStream(uri);
		return BitmapFactory.decodeStream(in, null, options);
	}
	
}
