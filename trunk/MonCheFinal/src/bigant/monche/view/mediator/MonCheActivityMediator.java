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

package bigant.monche.view.mediator;

import java.util.Observable;
import java.util.Observer;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import android.content.Context;
import android.content.Intent;
import bigant.monche.common.Event;
import bigant.monche.names.NotificationNames;
import bigant.monche.view.activity.MonCheActivity;

/**
 * The <code>Mediator</code> object which is responsible for the application
 * activity.
 * 
 * It has for only role to give access to the application context without using
 * a direct reference to it, what might have broken the loose coupling rules.
 */
public class MonCheActivityMediator extends Mediator implements Observer {
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
	public MonCheActivityMediator(String mediatorName, MonCheActivity activity) {
		super(mediatorName, activity);

		activity.addObserver(this);
	}

	protected MonCheActivity getMoncheActivity() {
		return (MonCheActivity) viewComponent;
	}

	public Context getContext() {
		return (Context) getMoncheActivity();
	}

	@Override
	public String[] listNotificationInterests() {
		return new String[] { NotificationNames.START_GALLERY , NotificationNames.LOG_IN
				, NotificationNames.LOGIN_SUCCESS, NotificationNames.START_LOADPAGE};
	}

	@Override
	public void handleNotification(INotification note) {
		if (note.getName().equals(NotificationNames.START_GALLERY)) {
			String[] list = (String[]) note.getBody();
			getMoncheActivity().startGallery(list);
		}
		else if (note.getName().equals(NotificationNames.LOG_IN)) {
			//getMoncheActivity().startLogin();
		} 
		else if (note.getName().equals(NotificationNames.LOGIN_SUCCESS)) {
		//	sendNotification(NotificationNames.LOAD_PAGE, getMoncheActivity());
		}
		else if (note.getName().equals(NotificationNames.START_LOADPAGE)) {
			sendNotification(NotificationNames.LOAD_PAGE, getMoncheActivity());
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