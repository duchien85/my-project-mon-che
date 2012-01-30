package com.monche.cache;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ExternalCache {
	public Context context;
	 
	
	public boolean isAvaiable(){
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		return mExternalStorageAvailable && mExternalStorageWriteable;
	}
	
	public void saveToCache(String filename, Bitmap b){
		if (b==null) return;
		if (isAvaiable()){
			 File path = context.getExternalCacheDir();
			 Log.d("ExternalStorage", path.toString()+filename.toString()+".jpg");
			 try {
				 	File file = new File(path,filename+".jpg");
				 	if (file.exists()) return;
			        OutputStream os = new FileOutputStream(file);
			        if (os!=null)
			        	b.compress(Bitmap.CompressFormat.JPEG, 90, os);
			        os.close();
			    } catch (IOException e) {
			        Log.d("ExternalStorage", "Error writing ", e);
			    }
		}
	}
	
	public Bitmap getFromCache(String filename){
		if (isAvaiable()){
			File path = context.getExternalCacheDir();
			 try {
				 	File file = new File(path,filename+".jpg");
				 	if (!file.exists()) return null;
				 	FileInputStream in = new FileInputStream(file);
				 	BufferedInputStream buf = new BufferedInputStream(in);
			        Bitmap bMap = BitmapFactory.decodeStream(buf);
			        in.close();
			        return bMap;
			     } catch (IOException e) {
			        Log.d("ExternalStorage", "Error reading ", e);
			        return null;
			    }
		}else return null;
	}
	
	public boolean havePhoto(String id){
		if (isAvaiable()){
			File path = context.getExternalCacheDir();
			 File file = new File(path,id+".jpg");
			if (file.exists()) return true;
			else return false;
		}else return false;
	}
}
