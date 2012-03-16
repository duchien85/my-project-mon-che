package bigant.monche.view.activity;

import java.util.Observable;
import java.util.Observer;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import bigant.monche.common.Extra;
import bigant.monche.common.MonCheActivityFacade;

import com.monche.app.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MonCheActivity extends TabActivity
{
	TabHost tabHost;
	protected ObservableComposite observable;
	protected String nameActivity;
	protected Class nextActivity;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	public final String RANDOM_TAB = "randomTab";
	public final String NEW_TAB = "newTab";
	boolean visible = false;
	
	/**
	 * Constructor.
	 */
	public MonCheActivity()
	{
		super();
		observable = new ObservableComposite();
	}

    /**
     * Initialize our application with the Android <code>Activity</code>.
     */
	protected void startApp()
	{
		/*
		 * In some cases, the Activity instance could have been previously
		 * silently destroyed by the Android system we need to ensure that our
		 * Facade uses an existing Activity so we re-init it.
		 */
		
		MonCheActivityFacade facade = MonCheActivityFacade.getInstance();
		facade.startupMonche(this);
		
		 Resources res = getResources(); // Resource object to get Drawables
	        tabHost = getTabHost();  // The activity TabHost
	        tabHost.setOnTabChangedListener(new OnTabChangeListener(){
	        	@Override
	        	public void onTabChanged(String tabId) {
	        		Log.i("Tab **************   ", " fljdlkfj  ");
	        	    if(tabId.equals(RANDOM_TAB))
	        	    {
	        	    //	PhotoStatusVo.getInstance().type = TypeImageNames.RANDOM;
	        	    }
	        	    else if(tabId.equals(NEW_TAB))
	        	    {
	        	    	//PhotoStatusVo.getInstance().type = TypeImageNames.RANDOM_UNREAD;
	        	    }
	        	}
	        	public void onTabChanged(int tabId) {
	        		
	        	}
	        	
	        	
	        });
	        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	        Intent intent;  // Reusable Intent for each tab

	        // Create an Intent to launch an Activity for the tab (to be reused)
	        intent = new Intent().setClass(this, ImageGridActivity.class);

	        // Initialize a TabSpec for each tab and add it to the TabHost
	        spec = tabHost.newTabSpec(RANDOM_TAB).setIndicator(RANDOM_TAB,
	                          res.getDrawable(R.drawable.ic_tab_artists))
	                      .setContent(intent);
	        tabHost.addTab(spec);

	        // Do the same for the other tabs
	        intent = new Intent().setClass(this, ImageGridActivity.class);
	        spec = tabHost.newTabSpec(NEW_TAB).setIndicator(NEW_TAB,
	                          res.getDrawable(R.drawable.ic_tab_albums))
	                      .setContent(intent);
	        tabHost.addTab(spec);

	        tabHost.setCurrentTab(0);
	}
	
	public void startGallery(String[] list) 
	{
		
	    	//Intent intent = new Intent(this, ImageGalleryActivity.class);
	    Intent intent = new Intent(this, ImageGridActivity.class);
		intent.putExtra(Extra.IMAGES, list);
		startActivity(intent);
	    //setContentView(R.layout.test);
	}
	


    //-------------------------------------------------------------------------
    // Event listeners
    //-------------------------------------------------------------------------
	
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.monche);
        startApp();
    }
    
	/**
     * Called (or not called) when the activity is destroyed by the Android
     * system.
     */
    @Override   
    public void onDestroy()
    {
    	super.onDestroy();

    }

	public void startLogin()
	{
		 //Intent intent = new Intent(this, ImageGalleryActivity.class);
	    Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtra(Extra.LOGIN, "");
		startActivity(intent);
	}
	public String getName()
	{
		return nameActivity;
	}
	
	public Class getNextActivity()
	{
		return nextActivity;
	}
	

	class ObservableComposite extends Observable {
		@Override
		public void setChanged() {
			super.setChanged();
		}

		@Override
		public void clearChanged() {
			super.clearChanged();
		}
	}

	/**
	 * Adds the specified observer to the list of observers.
	 * 
	 * @param observer
	 *            The observer to add.
	 */
	public void addObserver(Observer observer) {
		observable.addObserver(observer);
	}

	/**
	 * Returns the number of observers registered to this Observable.
	 */
	public int countObservers() {
		return observable.countObservers();
	}

	/**
	 * Removes the specified observer from the list of observers.
	 * 
	 * @param observer
	 *            The observer to remove.
	 */
	public synchronized void deleteObserver(Observer observer) {
		observable.deleteObserver(observer);
	}

	/**
	 * Removes all observers from the list of observers.
	 */
	public synchronized void deleteObservers() {
		observable.deleteObservers();
	} 
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			
			default:
				return false;
		}
	}
}
