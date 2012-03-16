package bigant.monche.controller.login;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import bigant.monche.names.MediatorNames;
import bigant.monche.view.activity.LoginActivity;
import bigant.monche.view.login.LoginActivityMediator;

public class PreViewLoginCmd

extends SimpleCommand {
	public void execute(INotification note) {

		LoginActivity activity = (LoginActivity) note.getBody();
		LoginActivityMediator LoginMed = new LoginActivityMediator(MediatorNames.LOG_IN, activity);
		facade.registerMediator(LoginMed);
		
		//sendNotification(NotificationNames.LOG_IN);
	}
}
