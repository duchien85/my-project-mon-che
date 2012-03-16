package bigant.monche.view.components;

import bigant.monche.photo.HandleOnPageChangeListener;
import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class HandleViewPager extends ViewPager {
	private GestureDetector mGestureDetector;
	float distanceMove = 0;
	private PointF pre= new PointF(0f,0f);
	private PointF cur= new PointF(0f,0f);
	public boolean first = true;
	public ImageViewTouch currentView;
	public ImageViewTouch previousView;
	public HandleViewPager (Context context, AttributeSet attr)
	{
		super(context, attr);
		mGestureDetector = new GestureDetector(new YScrollDetector());
		HandleOnPageChangeListener handle = new HandleOnPageChangeListener();
		setOnPageChangeListener(handle);
		handle.parent = this;
	}
	
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		
		
		if(currentView != null && currentView.checkIsViewing())
			return false;
		else 
			return super.onInterceptTouchEvent(arg0);
	}
	
    View.OnTouchListener mGestureListener;


    // Return false if we're scrolling in the x direction  
    class YScrollDetector extends SimpleOnGestureListener {
    	
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        	//Log.i("distance X ", " " + distanceX );
        	int action = e2.getAction();
        	int id  = MotionEventCompat.getPointerId(e2, 0);
        	//Log.i("Diem cham : ", "" + id);
        	switch (action & MotionEvent.ACTION_MASK)
    		{
    			case MotionEvent.ACTION_UP:
    			case MotionEvent.ACTION_DOWN:
    			case MotionEvent.ACTION_POINTER_DOWN:
    			case MotionEvent.ACTION_POINTER_UP:
    				first = true;
    		}
        	if(first)
        	{
        		first = false;
        		Log.i("distance X ", " lan dau" );
        		return false;
        		
        	}
            if(Math.abs(distanceX) > 30) {
                return true;
            }
            return false;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
    	// TODO Auto-generated method stub
    	//return true;
    //	getC
    	try
    	{
    	//	if(currentView.checkIsViewing())
    		//	return true;
    		return super.onTouchEvent(arg0);
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		//Log.i("Crash ", "dcm");
    		return true;
		}
    }
   
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    	// TODO Auto-generated method stub
    //	if(!previousView.checkIsViewing())
    	//	super.onScrollChanged(l, t, oldl, oldt);
    	
    	
    	
    }
    
}


