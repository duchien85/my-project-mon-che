package bigant.monche.controller.login;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import bigant.monche.names.ProxyNames;

public class PreModelSignInCmd extends SimpleCommand {
	public void execute(INotification note) {
		//SignInServiceProxy networkSignInProxy = new SignInServiceProxy( ProxyNames.SIGN_IN);
     	//facade.registerProxy( networkSignInProxy );
     }
}
