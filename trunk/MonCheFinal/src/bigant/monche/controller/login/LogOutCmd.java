
package bigant.monche.controller.login;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import android.app.Activity;
import bigant.monche.model.proxy.PhotoServiceProxy;
import bigant.monche.names.ProxyNames;


/**
 * Initialize <code>Proxy</code>s with data they need to control.
 */
public class LogOutCmd
	extends SimpleCommand
{
    public void execute( INotification note )
    {
    	PhotoServiceProxy proxy = (PhotoServiceProxy)facade.retrieveProxy(ProxyNames.PHOTO_SERVICE_PROXY);
    	proxy.logOut((Activity)note.getBody());
    }
}