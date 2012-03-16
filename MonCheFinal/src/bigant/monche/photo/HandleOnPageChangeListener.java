package bigant.monche.photo;

import bigant.monche.view.components.HandleViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

public class HandleOnPageChangeListener implements OnPageChangeListener {

	public HandleViewPager parent;
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		//Log.i("Scroll ********    ", " change stage ");
		if(parent.previousView!=null)
			parent.previousView.reset();
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		//Log.i("Scroll ********    ", " scrolled  ");
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
	//	Log.i("Scroll ********    ", " selected  ");
	}

}
