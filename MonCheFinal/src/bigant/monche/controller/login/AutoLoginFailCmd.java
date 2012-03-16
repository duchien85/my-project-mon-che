
package bigant.monche.controller.login;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import bigant.monche.model.proxy.PhotoServiceProxy;
import bigant.monche.model.vo.PhotoStatusVo;
import bigant.monche.names.ProxyNames;


/**
 * Initialize <code>Proxy</code>s with data they need to control.
 */
public class AutoLoginFailCmd
	extends SimpleCommand
{
    public void execute( INotification note )
    {
    	PhotoStatusVo.getInstance().user = PhotoStatusVo.getInstance().userDefault;
    	PhotoStatusVo.getInstance().passCode = PhotoStatusVo.getInstance().passCodeDefault;
    	PhotoStatusVo.getInstance().savePass = false;
    	PhotoStatusVo.getInstance().autoLogin = false;
    	PhotoStatusVo.getInstance().userCurrent = PhotoStatusVo.getInstance().userDefault;
    	PhotoStatusVo.getInstance().passCodeCurrent = PhotoStatusVo.getInstance().passCodeDefault;
    	PhotoStatusVo.getInstance().uid = "20";
    	
    	PhotoServiceProxy proxy = (PhotoServiceProxy)facade.retrieveProxy(ProxyNames.PHOTO_SERVICE_PROXY);
    	proxy.login((Activity)note.getBody());
		
    }
}