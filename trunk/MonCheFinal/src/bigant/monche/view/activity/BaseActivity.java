package bigant.monche.view.activity;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.ImageLoader;

public abstract class BaseActivity extends Activity{

	protected ObservableComposite observable;
	protected String nameActivity;
	protected Class nextActivity;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	public BaseActivity() {
		super();
		observable = new ObservableComposite();
	}
	
	public String getName()
	{
		return nameActivity;
	}
	
	public Class getNextActivity()
	{
		return nextActivity;
	}
	
	protected abstract void startApp();

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
