package bigant.monche.view.login;

import java.util.Observable;
import java.util.Observer;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import bigant.monche.common.Event;
import bigant.monche.names.NotificationNames;
import bigant.monche.view.activity.SignInActivity;

	public class SignInActivityMediator extends Mediator implements Observer {
		/**
		 * Construct the <code>Mediator</code> instance.
		 * 
		 * @param mediatorName
		 *            Name of the <code>Mediator</code> object.
		 * 
		 * @param activity
		 *            The activity associated with the <em>Converter</em> activity
		 *            view component.
		 */
		

		public SignInActivityMediator(String mediatorName, SignInActivity activity) {
			super(mediatorName, activity);

			activity.addObserver(this);
		}

		protected SignInActivity getSignInActivity() {
			return (SignInActivity) viewComponent;
		}

		public Context getContext() {
			return (Context) getSignInActivity();
		}

		@Override
		public String[] listNotificationInterests() {
			return new String[] { NotificationNames.SIGN_IN_SUCCESS, NotificationNames.SIGN_IN_FAIL};
		}

		@Override
		public void handleNotification(INotification note) {
			
			if (note.getName().equals(NotificationNames.SIGN_IN_SUCCESS)) {
				Log.i("Sign in ", " success ");
				getSignInActivity().siginInSuccess();
			}
			else if (note.getName().equals(NotificationNames.SIGN_IN_FAIL)) {
				
				getSignInActivity().siginInFail();
				
			}
		}

		// OptionsMenu handling

		/**
		 * Called when the application options menu opens.
		 */
		public void optionsMenuOpenedHandler() {

		}

		/**
		 * Called when an item of the application menu is chosen.
		 * 
		 * @param id
		 *            The Android options menu item selected id.
		 */
		public void onOptionsMenuItemSelected(int id) {

		}

		// Activities management

		/**
		 * Called when the item of the application menu is chosen.
		 * 
		 * @param result
		 *            The result data sent by the <em>Preferences</em> activity when
		 *            it closes.
		 */
		public void onPreferencesActivityClosed(Intent result) {

		}

		// Observer implementation

		// @Override
		public void update(Observable observable, Object data) {
			Event event = (Event) data;
			if(event.name.equals(NotificationNames.SIGN_IN))
			{
				sendNotification(NotificationNames.SIGN_IN, event.data);
			}
		}
}
