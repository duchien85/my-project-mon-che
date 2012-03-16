package bigant.monche.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import bigant.monche.view.components.HandleViewPager;
import bigant.monche.view.components.ImageViewTouchBase;

public class aImageViewTouch extends ImageViewTouchBase {

	float defaultX = 0;
	float defaultY = 0;
	float defaultZoom = 1f;
	PointF fingerOne = new PointF();
	int fingerOnePointer;
	PointF fingerTwo = new PointF();
	int fingerTwoPointer;
	boolean isDragged = false;
	boolean isPinched = false;
	float lastDistance = 0;
	float maxX = 50;

	float maxY = 100;

	float maxZoom = 5f;

	float minX = -50;

	float minY = -100;
	float minZoom = 0.2f;
	int numberOfFingers = 0;

	float oldDownX, oldDownY;
	float oldX, oldY;
	// / simulator multi touch
	private final int POINTER = 2;

	private boolean pressAlt = false;
	float tolerant = 7;
	public HandleViewPager parent;
	public boolean loading = true;
	public aImageViewTouch(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public aImageViewTouch(Context context, AttributeSet attrs,
			HandleViewPager _parent) {
		super(context, attrs);
		parent = _parent;
	}

	public void setParent(HandleViewPager _parent) {
		parent = _parent;
	}

	@Override
	protected void init() {
		super.init();
	
	}

	@Override
	public void setImageBitmapReset(Bitmap bitmap, boolean reset) {
		super.setImageBitmapReset(bitmap, reset);
	//	mScaleFactor = getMaxZoom() / 2.0f;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (loading) {
			// Toast.makeText(parent.getContext(), "Dang Load",
			// Toast.LENGTH_SHORT).show();
			return true;
		}
		int action = event.getAction(),p_count = event.getPointerCount();
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_MOVE:
			
			
			if (numberOfFingers > 1) {
				fingerOne.set(event.getX(0), event.getY(0));
				fingerTwo.set(event.getX(1), event.getY(1));
				float distance = (float) Math.sqrt(Math.pow(fingerOne.x - fingerTwo.x, 2) + Math.pow(fingerOne.y - fingerTwo.y, 2));
				if (lastDistance != 0 && distance != 0) {
					zoomTo(getScale() * distance / lastDistance);
				}
				lastDistance = distance;
				isPinched = true;
				isDragged = false;
			}
			else 
			{
				isDragged = true;
			}

			Log.i("isDraged  " ," " +  isDragged);
			if (isDragged) {
				// System.out.println("keo camera");
				float dx = event.getX(0) - oldX;
				float dy = event.getY(0) - oldY;
				//translateCamera(dx * camera.zoom, -dy * camera.zoom);
				postTranslate(dx, dy);
			}
			oldX = event.getX(0);
			oldY = event.getY(0);
			isDragged = true;
			break;
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			numberOfFingers++;
			Log.i("num finger " , " " + numberOfFingers);
			if (numberOfFingers > 2)
				reset();
			if (numberOfFingers == 1) {
				
				fingerOne.set(event.getX(0), event.getY(0));
			} else if (numberOfFingers == 2) {
				fingerOne.set(event.getX(0), event.getY(0));
				fingerTwo.set(event.getX(1), event.getY(1));
				lastDistance = (float) Math.sqrt(Math.pow(fingerOne.x - fingerTwo.x, 2) + Math.pow(fingerOne.y - fingerTwo.y, 2));
			}
			isPinched = numberOfFingers >= 2;
			oldX = event.getX(0);
			oldY = event.getY(0);
			
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			numberOfFingers--;

			// just some error prevention... clamping number of fingers (ouch! :-)
			if (numberOfFingers < 0) {
				numberOfFingers = 0;
			}

			if (numberOfFingers == 0) {
				isPinched = false;
				lastDistance = 0;
			}
			isDragged = false;
			
			break;

		}
		return true;
	}

	public synchronized void postTranslate(float dx, float dy) {
		mSuppMatrix.postTranslate(dx, dy);
		setImageMatrix(getImageViewMatrix());
	}

	@Override
	protected void onZoom(float scale) {
	
	}
	
	public void reset() {
		numberOfFingers = 0;
		lastDistance = 0;
		isPinched = false;
		isDragged = false;
	}

}