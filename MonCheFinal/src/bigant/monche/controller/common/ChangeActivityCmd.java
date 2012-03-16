
package bigant.monche.controller.common;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import android.content.Intent;

import bigant.monche.names.ActivityNames;
import bigant.monche.names.MediatorNames;
import bigant.monche.names.ProxyNames;
import bigant.monche.view.activity.BaseActivity;
import bigant.monche.view.activity.LoginActivity;
import bigant.monche.view.activity.MonCheActivity;
import bigant.monche.view.activity.SplashScreen;


/**
 * Initialize <code>Proxy</code>s with data they need to control.
 */
public class ChangeActivityCmd
	extends SimpleCommand
{
    public void execute( INotification note )
    {
    	BaseActivity activity = (BaseActivity) note.getBody();
    	Intent mainIntent = new Intent(activity, activity.getNextActivity());
    	activity.startActivity(mainIntent);
    	if(activity.getName().equals(ActivityNames.SPLASH_SCREEN))
    	{
    		facade.getInstance().removeMediator(MediatorNames.SPLASH);
    		activity.deleteObservers();
    		activity.finish();
    		
    	}
    	
    }
}