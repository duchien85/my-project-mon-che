package bigant.monche.view.mediator;

import java.util.Observable;
import java.util.Observer;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import android.content.Context;
import android.util.Log;
import bigant.monche.common.Event;
import bigant.monche.names.NotificationNames;
import bigant.monche.view.activity.MonCheActivity;
import bigant.monche.view.activity.SplashScreen;

public class SplashMediator extends Mediator implements Observer{

	public SplashMediator(String mediatorName, SplashScreen viewComponent) {
		super(mediatorName, viewComponent);
		// TODO Auto-generated constructor stub
		viewComponent.addObserver(this);
	}


	protected SplashScreen getSplashActivity() {
		return (SplashScreen) viewComponent;
	}

	public Context getContext() {
		return (Context) getSplashActivity();
	}

	@Override
	public String[] listNotificationInterests() {
		return new String[] {NotificationNames.AUTO_LOGIN_SUCCESS, NotificationNames.DISCONNECT_NETWORK};
	}

	@Override
	public void handleNotification(INotification note) {
		if (note.getName().equals(NotificationNames.AUTO_LOGIN_SUCCESS)) {
			//String[] list = (String[]) note.getBody();
			getSplashActivity().loginSuccess();
		}
		if (note.getName().equals(NotificationNames.DISCONNECT_NETWORK)) {
			//String[] list = (String[]) note.getBody();
			getSplashActivity().networkDisconnect();
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
		
	}
	
}
