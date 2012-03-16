package bigant.monche.controller.login;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import bigant.monche.names.MediatorNames;
import bigant.monche.view.activity.LoginActivity;
import bigant.monche.view.activity.SignInActivity;
import bigant.monche.view.login.LoginActivityMediator;
import bigant.monche.view.login.SignInActivityMediator;

public class PreViewSignInCmd

extends SimpleCommand {
	public void execute(INotification note) {

		SignInActivity activity = (SignInActivity) note.getBody();
		SignInActivityMediator LoginMed = new SignInActivityMediator(MediatorNames.SIGN_IN, activity);
		facade.registerMediator(LoginMed);
		
		//sendNotification(NotificationNames.LOG_IN);
	}
}
