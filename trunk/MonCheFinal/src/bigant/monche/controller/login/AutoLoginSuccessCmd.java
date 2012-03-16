
package bigant.monche.controller.login;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import android.app.Activity;
import android.content.SharedPreferences;
import bigant.monche.model.proxy.PhotoServiceProxy;
import bigant.monche.model.vo.PhotoStatusVo;
import bigant.monche.names.ProxyNames;


/**
 * Initialize <code>Proxy</code>s with data they need to control.
 */
public class AutoLoginSuccessCmd
	extends SimpleCommand
{
    public void execute( INotification note )
    {
    	PhotoServiceProxy proxy = (PhotoServiceProxy) note.getBody();
    	Activity activity = (Activity)proxy.login;
    	
    	PhotoStatusVo.getInstance().userCurrent = proxy.userCurrent;
    	PhotoStatusVo.getInstance().passCodeCurrent = proxy.passCodeCurrent;
    	final SharedPreferences shareData = activity.getSharedPreferences("Mon che", 0);
    	
    	
    	
    }
}