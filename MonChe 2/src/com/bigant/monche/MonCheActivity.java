/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bigant.monche;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.monche.logic.PhotoLibs;

public class MonCheActivity extends ListActivity   {

	public static final String APP_TAG = "ImageDownloader";
	
	public ListView categoriesListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.category_list);

//        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
//        radioGroup.setOnCheckedChangeListener(this);
//        
//        setListAdapter(new ImageAdapter());
        PhotoLibs.getInstances().listViewActivity = this;
        PhotoLibs.getInstances().login();
    }

    public void updateCategories(){
    	 setContentView(R.layout.category_list);
    	 
    	 setListAdapter(new ArrayAdapter<String>(this, R.layout.text_view,PhotoLibs.getInstances().categoriesName));
    	 //categoriesListView = (ListView) findViewById(R.id.);
    	 getListView().setOnItemClickListener(new OnItemClickListener() {
    		 @Override
    		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    				long arg3) {
    			 PhotoLibs.getInstances().selectCategory(arg2);
    		}
    	 });
    }
    public void showDialog(String mess){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(mess)
	       .setCancelable(false)
	       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                MonCheActivity.this.finish();
	           }
	       });
		AlertDialog alert = builder.create();
		alert.show();
    }
    
    public void updatePhotos(){
    	startActivity(new Intent(this, ViewPhotoActivity.class));
    	
//    	 setContentView(R.layout.imagelist);
//    	 setListAdapter(new ImageAdapter(lib.photos));
//    	 //categoriesListView = (ListView) findViewById(R.id.);
//    	 getListView().setOnItemClickListener(new OnItemClickListener() {
//    		 @Override
//    		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//    				long arg3) {
//    			 lib.selectPhoto(arg2);
//    		}
//    	 });
    	
    	
    }
}
