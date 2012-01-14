package com.example.android.imagedownloader;

import com.components.pinchzoom.PinchImageView;
import com.monche.logic.PhotoLibs;
import com.monche.logic.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPhotoActivity extends Activity {
	PinchImageView img;
	Button preBtn;
	Button nextBtn;
	
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.photo_view_layout);
	        TextView t = (TextView) findViewById(R.id.textView1);
	        t.setText("LOADiNG");
	        //t.setVisibility(View.VISIBLE);
	        img = (PinchImageView) findViewById(R.id.imageView1);
	        //img.setVisibility(View.INVISIBLE);
	        preBtn = (Button) findViewById(R.id.button1);
	        nextBtn = (Button) findViewById(R.id.button2);
	        preBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Util.Trace("Pre click!");
					PhotoLibs.getInstances().prePhoto();
					PhotoLibs.getInstances().loadPhotoTo(img);
					img.center(true, true);
				}
			});
	        nextBtn.setOnClickListener(new View.OnClickListener() {
	    				@Override
	    				public void onClick(View v) {
	    					Util.Trace("Next click!");
	    					PhotoLibs.getInstances().nextPhoto();
	    					PhotoLibs.getInstances().loadPhotoTo(img);
	    					img.center(true, true);
	    				}
	    			});
	 }
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Util.Trace("Touch view: "+event.getPointerCount()+" "+ event.getAction());
		img.onTouchEvent(event);
		return false;
	}
	 @Override
	protected void onStart() {
		super.onStart();
		PhotoLibs.getInstances().loadPhotoTo(img);
		img.center(true, true);
	}
}
