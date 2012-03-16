package bigant.monche.view.components;

import bigant.monche.view.activity.ImageGridActivity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class HandleGridView extends GridView {

	public HandleGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public HandleGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	ImageGridActivity parent;

	public HandleGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int action = ev.getAction();
		float zoom = 0;
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_UP:
			parent.hideMenu();
			break;
		}
		return super.onTouchEvent(ev);
	}

	public void setParent(ImageGridActivity _parent) {
		parent = _parent;
	}
}
