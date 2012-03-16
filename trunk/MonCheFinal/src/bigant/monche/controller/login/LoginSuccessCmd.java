
package bigant.monche.controller.login;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import bigant.monche.model.proxy.PhotoServiceProxy;
import bigant.monche.model.vo.PhotoStatusVo;


/**
 * Initialize <code>Proxy</code>s with data they need to control.
 */
public class LoginSuccessCmd
	extends SimpleCommand
{
    public void execute( INotification note )
    {
    	PhotoServiceProxy proxy = (PhotoServiceProxy) note.getBody();
    	Activity activity = (Activity)proxy.login;
    	
    	PhotoStatusVo.getInstance().userCurrent = proxy.userCurrent;
    	PhotoStatusVo.getInstance().passCodeCurrent = proxy.passCodeCurrent;
    	
    	Log.i("Cap nhat " , PhotoStatusVo.getInstance().userCurrent + "  " + PhotoStatusVo.getInstance().passCodeCurrent);
    	final SharedPreferences shareData = activity.getSharedPreferences("Mon che", 0);
    	
    	if(PhotoStatusVo.getInstance().savePass)
    	{
    		PhotoStatusVo.getInstance().save(shareData);
    	}
    	else
    	{
    		PhotoStatusVo.getInstance().remove(shareData);
    	}
    	
    }
}