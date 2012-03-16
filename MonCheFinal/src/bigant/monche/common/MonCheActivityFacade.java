/*
 * PPureMVC Java Currency Converter for Android
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

package bigant.monche.common;



import org.puremvc.java.patterns.facade.Facade;

import bigant.monche.controller.common.ChangeActivityCmd;
import bigant.monche.controller.imageGallery.PrepViewImageGalleryCmd;
import bigant.monche.controller.imageGrid.PrepViewImageGridCmd;
import bigant.monche.controller.login.AutoLoginFailCmd;
import bigant.monche.controller.login.AutoLoginSuccessCmd;
import bigant.monche.controller.login.LogOutCmd;
import bigant.monche.controller.login.LogOutSuccessCmd;
import bigant.monche.controller.login.LoginCmd;
import bigant.monche.controller.login.LoginFailCmd;
import bigant.monche.controller.login.LoginSuccessCmd;
import bigant.monche.controller.login.PreViewLoginCmd;
import bigant.monche.controller.login.PreViewSignInCmd;
import bigant.monche.controller.login.SignInCmd;
import bigant.monche.controller.login.SignInFailCmd;
import bigant.monche.controller.login.SignInSuccessCmd;
import bigant.monche.controller.photo.GetLinkImageCmd;
import bigant.monche.controller.photo.LikePhotoCmd;
import bigant.monche.controller.photo.LoadFirstHotPageCmd;
import bigant.monche.controller.photo.LoadFirstNewPageCmd;
import bigant.monche.controller.photo.LoadFirstRandomPageCmd;
import bigant.monche.controller.photo.LoadPageCmd;
import bigant.monche.controller.photo.PrepViewMonCheCmd;
import bigant.monche.controller.splash.StartupSplashCmd;
import bigant.monche.names.NotificationNames;
import bigant.monche.view.activity.BaseActivity;
import bigant.monche.view.activity.ImageGalleryActivity;
import bigant.monche.view.activity.ImageGridActivity;
import bigant.monche.view.activity.LoginActivity;
import bigant.monche.view.activity.MonCheActivity;
import bigant.monche.view.activity.SignInActivity;
import bigant.monche.view.activity.SplashScreen;

public class MonCheActivityFacade
	extends Facade
{
	/**
	 * Construct the <code>Facade</code> multiton instance for this
	 * <code>Activity</code>.
	 * 
	 * @param multitonKey
	 * 		Multiton key for this <code>Facade</code> multiton instance.
	 */
	public MonCheActivityFacade()
	{
		super();
		instance = this;
	}

	/**
	 * <code>ApplicationFacade</code> singleton fabrication method
	 * implementation.
	 *
	 * @param multitonKey
	 * 		Multiton key of the facade instance to get.
	 */
	
	public synchronized static MonCheActivityFacade getInstance()
	{
		if(instance == null) return new MonCheActivityFacade();

		else return (MonCheActivityFacade) instance;
	}

	@Override
	protected void initializeController()
	{
		super.initializeController();
		registerCommand( NotificationNames.START_UP, new StartupSplashCmd() );
		registerCommand( NotificationNames.START_UP_LOGIN, new PreViewLoginCmd() );
		registerCommand( NotificationNames.LOG_IN, new LoginCmd());
		registerCommand( NotificationNames.LOG_IN_FAIL, new LoginFailCmd());
		registerCommand( NotificationNames.LOG_IN_SUCCESS, new LoginSuccessCmd());
		registerCommand( NotificationNames.AUTO_LOGIN_SUCCESS, new AutoLoginSuccessCmd());
		registerCommand( NotificationNames.AUTO_LOGIN_FAIL, new AutoLoginFailCmd());
		registerCommand( NotificationNames.LOAD_PAGE, new LoadPageCmd());
		registerCommand( NotificationNames.LOAD_FIRST_NEW_PAGE, new LoadFirstNewPageCmd());
		registerCommand( NotificationNames.LOAD_FIRST_RANDOM_PAGE, new LoadFirstRandomPageCmd());
		registerCommand( NotificationNames.LOAD_FIRST_HOT_PAGE, new LoadFirstHotPageCmd());
		
		registerCommand( NotificationNames.SIGN_IN, new SignInCmd());
		registerCommand( NotificationNames.SIGN_IN_FAIL, new SignInFailCmd());
		registerCommand( NotificationNames.SIGN_IN_SUCCESS, new SignInSuccessCmd());
		registerCommand( NotificationNames.START_UP_SIGN_IN, new PreViewSignInCmd());
		registerCommand( NotificationNames.CHANGE_ACTIVITY, new ChangeActivityCmd());
		registerCommand( NotificationNames.START_UP_MONCHE, new PrepViewMonCheCmd());
		registerCommand( NotificationNames.START_UP_IMAGE_GRID, new PrepViewImageGridCmd());
		registerCommand( NotificationNames.START_UP_IMAGE_GALLERY, new PrepViewImageGalleryCmd());
		registerCommand( NotificationNames.LIKE_PHOTO, new LikePhotoCmd());
		registerCommand( NotificationNames.LOG_OUT, new LogOutCmd());
		//registerCommand( NotificationNames.LOG_OUT_FAIL, new LogOutFailCmd());
		registerCommand( NotificationNames.LOG_OUT_SUCCESS, new LogOutSuccessCmd());
		registerCommand( NotificationNames.GET_LINK_IMAGE, new GetLinkImageCmd());
	}

	/**
	 * Start the <code>Activity</code> initialization sequence.
	 * 
	 * @param activity
	 * 		A reference to the <code>Activity</code> to initialize.
	 */
	public void startup( SplashScreen activity )
	{
		sendNotification(  NotificationNames.START_UP, activity  );
		System.out.println("NotificationNames.STARTUP");
	}
	
	public void startUpLogin(LoginActivity activity)
	{
		sendNotification(NotificationNames.START_UP_LOGIN, activity);
	}
	
	public void startLogin(LoginActivity activity)
	{
		sendNotification(NotificationNames.LOG_IN, activity);
	}

//	public void startUpSignIn(SignInActivity signInActivity) {
//		sendNotification(NotificationNames.SIGN_IN, signInActivity);
//		
//	}
	public void changeActivity(BaseActivity activity) {
		// TODO Auto-generated method stub
		sendNotification(NotificationNames.CHANGE_ACTIVITY, activity);
	}

	public void startupMonche(MonCheActivity monCheActivity) {
		// TODO Auto-generated method stub
		sendNotification(NotificationNames.START_UP_MONCHE, monCheActivity);
	}

	public void startUpImageGrid(ImageGridActivity imageGridActivity) {
		// TODO Auto-generated method stub
		sendNotification(NotificationNames.START_UP_IMAGE_GRID, imageGridActivity);
	}

	public void startUpImageGallery(ImageGalleryActivity imageGalleryActivity) {
		// TODO Auto-generated method stub
		sendNotification(NotificationNames.START_UP_IMAGE_GALLERY, imageGalleryActivity);
	}

	public void startUpSignIn(SignInActivity signInActivity) {
		// TODO Auto-generated method stub
		sendNotification(NotificationNames.START_UP_SIGN_IN, signInActivity);
	}
	
}