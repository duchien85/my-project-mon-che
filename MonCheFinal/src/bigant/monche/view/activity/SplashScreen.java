package bigant.monche.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import bigant.monche.common.Event;
import bigant.monche.common.MonCheActivityFacade;
import bigant.monche.names.ActivityNames;
import bigant.monche.names.MediatorNames;
import bigant.monche.names.NotificationNames;

import com.monche.app.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class SplashScreen extends BaseActivity {
	protected boolean _active = true;
	protected int _splashTime = 8000;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
//		 thread for displaying the SplashScreen
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (_active && (waited < _splashTime)) {
						sleep(100);
						if (_active) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					if(_active)
						networkDisconnect();
				}
			}
		};
		splashTread.start();
		if(!isOnline())
			networkDisconnect();
		else
			startApp();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		if (event.getAction() == MotionEvent.ACTION_DOWN) {
//			_active = false;
//		}
		return true;
	}

	@Override
	protected void startApp() {
		// TODO Auto-generated method stub
		nameActivity = ActivityNames.SPLASH_SCREEN;
		nextActivity = ImageGridActivity.class;
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.threadPoolSize(5)
		.threadPriority(Thread.NORM_PRIORITY )
		.memoryCacheSize(1500000)
		.discCacheSize(50000000)
		.httpReadTimeout(10000)
		.denyCacheImageMultipleSizesInMemory()
		.build();
	// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		
		MonCheActivityFacade facade = MonCheActivityFacade.getInstance();
		facade.startup(this);
	}

	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
	public void loginSuccess() {
		// TODO Auto-generated method stub
		_active = false;
		observable.setChanged();
		observable.notifyObservers(new Event(NotificationNames.CHANGE_ACTIVITY, this));
		observable.hasChanged();
		observable.deleteObservers();
		//finish();
	}
	
	public void networkDisconnect()
	{
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				AlertDialog alertDialog = new AlertDialog.Builder(SplashScreen.this).create();
				alertDialog.setTitle("Error...");
				alertDialog.setMessage("No internet...");
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int which) {
				      // here you can add functions
					   finish();
				   }
				});
				alertDialog.setIcon(R.drawable.icon);
				alertDialog.show();
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		MonCheActivityFacade.getInstance().removeMediator(MediatorNames.SPLASH);
		_active = false;
		deleteObservers();
		finish();
		super.onBackPressed();
	}
}