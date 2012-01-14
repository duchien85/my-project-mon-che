package com.monche.logic.task;

import java.util.List;

import android.os.AsyncTask;

import com.monche.logic.PhotoLibs;
import com.monche.logic.Util;


public class StringGetTask extends AsyncTask<String, Void, List<String>>{
	PhotoLibs lib;
	String id;
	public StringGetTask(String taskID, PhotoLibs lib){
		this.lib =  lib;
		this.id = taskID;
	}
	
	@Override
	protected List<String> doInBackground(String... params) {
		return Util.getString(params[0]);
	}
	
	@Override
	protected void onPostExecute(List<String> result) {
		if (isCancelled()) {
            result = null;
        }
		if (lib!=null) lib.handleString(id, result);
	}
}

