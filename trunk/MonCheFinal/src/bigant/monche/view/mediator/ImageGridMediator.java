package bigant.monche.view.mediator;

import java.util.Observable;
import java.util.Observer;


import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import android.content.Context;
import android.util.Log;
import bigant.monche.common.Event;
import bigant.monche.names.NotificationNames;
import bigant.monche.view.activity.ImageGridActivity;
import bigant.monche.view.activity.SplashScreen;

public class ImageGridMediator extends Mediator implements Observer{
	public ImageGridMediator(String mediatorName, ImageGridActivity viewComponent) {
		super(mediatorName, viewComponent);
		// TODO Auto-generated constructor stub
		viewComponent.addObserver(this);
	}


	protected ImageGridActivity getImageGridActivity() {
		return (ImageGridActivity) viewComponent;
	}

	public Context getContext() {
		return (Context) getImageGridActivity();
	}

	@Override
	public String[] listNotificationInterests() {
		return new String[] {NotificationNames.LOAD_PAGE_SUCCESS, NotificationNames.LOGIN_SUCCESS};
	}

	@Override
	public void handleNotification(INotification note) {
	
		if (note.getName().equals(NotificationNames.LOAD_PAGE_SUCCESS)) {
			Log.i("Load page **  ", " success");
			getImageGridActivity().loadPageSuccess();
		}
		
		
	}
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
		Event event = (Event) arg1;
		if(event.name.equals(NotificationNames.CHANGE_ACTIVITY))
		{
			sendNotification(NotificationNames.CHANGE_ACTIVITY, event.data);
		}
		else if(event.name.equals(NotificationNames.LOAD_FIRST_NEW_PAGE))
		{
			sendNotification(NotificationNames.LOAD_FIRST_NEW_PAGE, event.data);
		} 
		else if(event.name.equals(NotificationNames.LOAD_FIRST_RANDOM_PAGE))
		{
			sendNotification(NotificationNames.LOAD_FIRST_RANDOM_PAGE, event.data);
		} 
		else if(event.name.equals(NotificationNames.LOAD_FIRST_HOT_PAGE))
		{
			sendNotification(NotificationNames.LOAD_FIRST_HOT_PAGE, event.data);
		}
		else if(event.name.equals(NotificationNames.LOG_OUT)) {
			Log.i("Log out ", " nha");
			sendNotification(NotificationNames.LOG_OUT, event.data);
		}
	}

}
