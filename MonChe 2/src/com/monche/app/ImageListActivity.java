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

package com.monche.app;


import com.monche.logic.PhotoLibs;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;

public class ImageListActivity extends Activity   {

	public static final String APP_TAG = "Monche";
	
	public Button newBtn;
	public Button randomBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.view_option);
        newBtn = (Button) findViewById(R.id.button1);
        randomBtn = (Button) findViewById(R.id.button2);
        newBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PhotoLibs.getInstances().setCategory("New");
				newBtn.setClickable(false);
				randomBtn.setClickable(false);
			}
		});
        randomBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PhotoLibs.getInstances().setCategory("Random");
				newBtn.setClickable(false);
				randomBtn.setClickable(false);
			}
		});
        PhotoLibs.getInstances().listViewActivity = this;
        PhotoLibs.getInstances().login();
    }

	@Override
	protected void onResume() {
		super.onResume();
		newBtn.setClickable(true);
		randomBtn.setClickable(true);
	}
    public void showDialog(String mess){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(mess)
	       .setCancelable(false)
	       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                ImageListActivity.this.finish();
	           }
	       });
		AlertDialog alert = builder.create();
		alert.show();
    }
    
    public void updatePhotos(){
    	startActivity(new Intent(this, ViewPhotoActivity.class));
    }
}
