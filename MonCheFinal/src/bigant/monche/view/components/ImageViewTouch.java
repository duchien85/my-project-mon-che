package bigant.monche.view.components;

import bigant.monche.view.activity.ImageGalleryActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;
import android.widget.Toast;

public class ImageViewTouch
	extends ImageViewTouchBase
{
	static final float				MIN_ZOOM	= 0.5f;
	static final float				MAX_ZOOM	= 5.0f;
	protected ScaleGestureDetector	mScaleDetector;
	protected GestureDetector		mGestureDetector;
	protected float					mTouchSlop;
	protected float					mCurrentScaleFactor;
	protected float					mScaleFactor;
	protected boolean				mIsZooming;
	protected GestureListener		mGestureListener;
	protected ScaleListener			mScaleListener;
	
	public static final int GROW = 0;
	public static final int SHRINK = 1;
	
	
	private PointF f1= new PointF(0f,0f);
	private PointF f2= new PointF(0f,0f);
	
	float xCur, yCur, 
	xPre, yPre,
	xSec, ySec,
	distDelta,
	distCur, distPre = -1;
	private boolean isDrag = false;
	private float lastZoom  = 1;
	private float currentZoom = 1;
	public ImageGalleryActivity parent;
	public boolean dragging = false;
	public boolean loading = true;

	public boolean defaultState = true;
	public float increaseRoom = 0.5f;
	public ImageViewTouch(Context context, AttributeSet attrs)
	{
		super( context, attrs );
		
	}
	
	public ImageViewTouch(Context context, AttributeSet attrs, ImageGalleryActivity _parent)
	{
		super( context, attrs );
		parent = _parent;
	}
	
	public void setParent(ImageGalleryActivity _parent)
	{
		parent = _parent;
	}

	@Override
	protected void init()
	{
		super.init();
		mTouchSlop = ViewConfiguration.getTouchSlop();
		mGestureListener = new GestureListener();
		mScaleListener = new ScaleListener();

		mScaleDetector = new ScaleGestureDetector( getContext(), mScaleListener );
		mGestureDetector = new GestureDetector( getContext(), mGestureListener, null, true );
		mCurrentScaleFactor = 1f;
		mTouchSlop = 0.1f;
	}

	@Override
	public void setImageBitmapReset(Bitmap bitmap, boolean reset)
	{
		super.setImageBitmapReset( bitmap, reset );
		mScaleFactor = getMaxZoom() / 2.0f;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		
		//if(loading)
		//{
		//	Toast.makeText(parent.getContext(), "Dang Load", Toast.LENGTH_SHORT).show();
			//return true;
		//}
		
		try{
			mScaleDetector.onTouchEvent( event );
			if ( !mScaleDetector.isInProgress())
				mGestureDetector.onTouchEvent( event );
			//cancelScroll();
			int action = event.getAction(),p_count = event.getPointerCount();
			float zoom = 0;
			switch (action & MotionEvent.ACTION_MASK)
			{
			case MotionEvent.ACTION_MOVE:
				
			//	parent.beginFakeDrag();
			//	parent.setMIsBegingDragged(true);
		    	f1.set(event.getX(0), event.getY(0));
		    	if (p_count > 1) {
		    		Log.i("Double ", "Move ");
		    		isDrag = false;
		    		f2.set(event.getX(1), event.getY(1));
		    		distCur = (float) Math.sqrt(Math.pow(f1.x - f2.x, 2) + Math.pow(f1.y - f2.y, 2));	    		
		    		//distDelta = distPre > 0 ? distCur - distPre : 0;
		    		//Log.i("Delta " + distDelta, "distCur " + distCur + "distPre " + distPre);
		    		distDelta =distCur - distPre;
		    		if(Math.abs(distDelta) > mTouchSlop)
		    		{
		    		
			    		if (distPre>0){
			    			
				    		zoom = distCur/distPre;
				    	//	Log.i("Zoom : " , "  " + (zoom*currentZoom));
				    		if(zoom*currentZoom <= MAX_ZOOM && zoom*currentZoom >= MIN_ZOOM)
				    			zoomTo(zoom*currentZoom);
				    		else if(zoom*currentZoom >= MAX_ZOOM)
				    		{
				    			zoomTo(MAX_ZOOM);
				    		}
				    		else
				    		{
				    			zoomTo(MIN_ZOOM);
				    		}
				    		
				    		
				    	//	currentRoom = zoom*lastZoom;
				    		
			    		}
			    		else
			    		{
			    			//distPre = distCur;
			    		//	currentRoom = lastZoom;
			    		}
			    		//mBitmapDisplayed.setPixels(EMPTY_STATE_SET, 0, 0, 0, 0, 1000, 1000);
			    		distPre = distCur;
		    		}
		    		
		    		
		    			
		    		
		    	}
		    	else
		    	{
		    		System.out.println("Long " + Math.abs(xPre - event.getX(0)));
		    		//if(Math.abs(xPre - event.getX(0));
		    		Log.i("Single ", "Move ");
		    	}
		    	dragging = true;
		    	//isDrag = true;
		    	
		    break;
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
	//			mIsZooming = false;
	//			cancelScroll();
				
				if (event.getPointerCount()==1){
		    		xPre = event.getX(0);
		    		yPre = event.getY(0);
		    		isDrag = true;
		       	}else isDrag = false;
				parent.gallery.first = true;
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				
				//Log.i("UP    ***************  UP ", " " );
				/*if (getScale() < 1f)
					zoomTo( 1f, 500 );
				if (getScale() > getMaxZoom())
					zoomTo( getMaxZoom(), 500 );
				center( true, true, 500 );
				*/
				xPre = 0;
				yPre = 0;
				distPre = 0;
				lastZoom = currentZoom;
				isDrag = false;
				//mSuppMatrix.setTranslate(10, 10);
				if(dragging)
				{
					//parent.endFakeDrag();
					dragging = false;
				}
				break;
			 	
			}
			return true;
		}
		catch(Exception e)
		{
			return true;
		}
	}

	public synchronized void postTranslate(float dx, float dy) {
		mSuppMatrix.postTranslate(dx, dy);
		setImageMatrix(getImageViewMatrix());

		// Debug position
	//	Matrix m = getImageViewMatrix();
		//RectF rect = new RectF(0, 0,
			//	mBitmapDisplayed.getWidth(),
			//	mBitmapDisplayed.getHeight());
		//m.mapRect(rect);
		//Log.i("Translate: "+dx+" "+dy+"  Result: "+ rect.left+" "+rect.top +"SIze: "+ rect.width()+" "+ rect.height(), " " );
		//center(true, true);
	}
	@Override
	protected void onZoom(float scale)
	{
		super.onZoom( scale );
		currentZoom = scale;
		if ( !mScaleDetector.isInProgress())
			mCurrentScaleFactor = scale;
		defaultState = false;
	}

	class GestureListener
		extends GestureDetector.SimpleOnGestureListener
	{
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e)
		{
			if(parent!=null)
				parent.hideMenu();
			if (performClick())
				return true;
			return super.onSingleTapConfirmed( e );
		}

		@Override
		public boolean onDoubleTap(MotionEvent e)
		{
			float scale = getScale();
			float targetScale;
			if(scale > 1)
				targetScale = scale + 3*increaseRoom;
			else
				targetScale = scale + increaseRoom;
			//targetScale = (scale >= mScaleFactor) ? 1f : scale + mScaleFactor;
			//targetScale = Math.min( getMaxZoom(), Math.max( targetScale, MIN_ZOOM ) );
			//targetScale = Math.min( MAX_ZOOM, Math.max( targetScale, MIN_ZOOM ) );
			//mCurrentScaleFactor = targetScale;
			if(targetScale>=MAX_ZOOM)
			{
				targetScale = MAX_ZOOM;
				increaseRoom = -increaseRoom;
			}
			else if(targetScale<=MIN_ZOOM)
			{
				targetScale = MIN_ZOOM;
				increaseRoom = -increaseRoom;
			}
			zoomTo( targetScale, e.getX(), e.getY(), 500 );
			invalidate();
			return super.onDoubleTap( e );
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
		{
			Log.i("Scroll  ", " ******  ");
			if (e1 == null || e2 == null)
				return false;
			if (e1.getPointerCount() > 1 || e2.getPointerCount() > 1)
				return false;
			if (mScaleDetector.isInProgress())
				return false;

			scrollBy( -distanceX, -distanceY );
			invalidate();
			
			return super.onScroll( e1, e2, distanceX, distanceY );
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
		{
			Log.i("Fling  ", " ******  ");
			if (e1.getPointerCount() > 1 || e2.getPointerCount() > 1)
				return false;
			if (mScaleDetector.isInProgress())
				return false;

			scrollBy( velocityX / 3, velocityY / 3, 500 );
			invalidate();
			return super.onFling( e1, e2, velocityX, velocityY );
		}

		@Override
		public void onLongPress(MotionEvent e)
		{
			Log.i("Long Press  ", " ******  ");
			super.onLongPress( e );
			if ( !mIsZooming)
				performLongClick();
		}
	}

	class ScaleListener
		extends ScaleGestureDetector.SimpleOnScaleGestureListener
	{
		@Override
		public boolean onScale(ScaleGestureDetector detector)
		{
//			mIsZooming = true;
//			mCurrentScaleFactor = Math.min( getMaxZoom() * MAX_ZOOM, Math.max( mCurrentScaleFactor * detector.getScaleFactor(), MIN_ZOOM ) );
//			zoomTo( mCurrentScaleFactor, detector.getFocusX(), detector.getFocusY() );
//			invalidate();
			return true;
		}
	}
	
	public void reset()
	{
		zoomTo(1f);
		defaultState = true;
		center(true, true);
	}
	
	public boolean checkIsViewing()
	{
//		PointF point = getCenter(true, true);
//		System.out.println(" XXX   " + point.x);
//		if(Math.abs(point.x) < 50) return true;
//		else return false;
		if(getScale() > 1) return true;
		else return false;
	}
	
}