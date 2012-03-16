package bigant.monche.view.mediator;

import java.util.Observable;
import java.util.Observer;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import android.content.Context;
import android.util.Log;
import bigant.monche.common.Event;
import bigant.monche.names.NotificationNames;
import bigant.monche.view.activity.ImageGalleryActivity;
import bigant.monche.view.activity.ImageGridActivity;

public class ImageGalleryMediator extends Mediator implements Observer{
	public ImageGalleryMediator(String mediatorName, ImageGalleryActivity viewComponent) {
		super(mediatorName, viewComponent);
		// TODO Auto-generated constructor stub
		viewComponent.addObserver(this);
	}


	protected ImageGalleryActivity getImageGalleryActivity() {
		return (ImageGalleryActivity) viewComponent;
	}

	public Context getContext() {
		return (Context) getImageGalleryActivity();
	}

	@Override
	public String[] listNotificationInterests() {
		return new String[] {NotificationNames.GET_LINK_SUCCESS, NotificationNames.LIKE_PHOTO_SUCCESS, NotificationNames.LOG_IN_SUCCESS};
	}

	@Override
	public void handleNotification(INotification note) {
	
		if(note.getName().equals(NotificationNames.LIKE_PHOTO_SUCCESS)) {
			//getImageGalleryActivity().likePhotoSuccess();
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
		else if(event.name.equals(NotificationNames.LIKE_PHOTO))
		{
			sendNotification(NotificationNames.LIKE_PHOTO, event.data);
		} 
		else if(event.name.equals(NotificationNames.GET_LINK_IMAGE)) {
			sendNotification(NotificationNames.GET_LINK_IMAGE);
		}
		else if(event.name.equals(NotificationNames.LOG_OUT))
		{
			sendNotification(NotificationNames.LOG_OUT, getImageGalleryActivity());
		}
				
		
	}

}
