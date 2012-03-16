/*
 * PureMVC Java Currency Converter for Android
 * Copyright (C) 2010  Frederic Saunier - www.tekool.net
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package bigant.monche.view.login;

import java.util.Observable;
import java.util.Observer;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import bigant.monche.names.NotificationNames;
import bigant.monche.view.activity.LoginActivity;

/**
 * The <code>Mediator</code> object which is responsible for the application
 * activity.
 * 
 * It has for only role to give access to the application context without using
 * a direct reference to it, what might have broken the loose coupling rules.
 */
public class LoginActivityMediator extends Mediator implements Observer {
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
	

	public LoginActivityMediator(String mediatorName, LoginActivity activity) {
		super(mediatorName, activity);

		activity.addObserver(this);
	}

	protected LoginActivity getLogInActivity() {
		return (LoginActivity) viewComponent;
	}

	public Context getContext() {
		return (Context) getLogInActivity();
	}

	@Override
	public String[] listNotificationInterests() {
		return new String[] {NotificationNames.LOGIN_SUCCESS, NotificationNames.LOG_IN_FAIL};
	}

	@Override
	public void handleNotification(INotification note) {
//		if (note.getName().equals(NotificationNames.START_UP)) {
//			SharedPreferences shareData = getMonCheActivity().getSharedPreferences(PREFERENCE_NAME, 0);
//			SaveDataUser.load(shareData);
//			sendNotification(NotificationNames.SHOW_LOGIN);
//		}
		if (note.getName().equals(NotificationNames.LOG_IN_FAIL)) {
			Log.i("login  **  ", "  FAIl ");
			getLogInActivity().logInFail();
		}
		if (note.getName().equals(NotificationNames.LOGIN_SUCCESS)) {
		
			Log.i("relogin  **  ", " success");
				
			getLogInActivity().loginSuccess();
			//sendNotification(NotificationNames.START_LOADPAGE);
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
	}
}