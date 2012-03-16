
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
public class SignInSuccessCmd
	extends SimpleCommand
{
    public void execute( INotification note )
    {
    	PhotoStatusVo.getInstance().userCurrent = PhotoStatusVo.getInstance().user;
    	PhotoStatusVo.getInstance().passCodeCurrent = PhotoStatusVo.getInstance().passCode;
    }
}