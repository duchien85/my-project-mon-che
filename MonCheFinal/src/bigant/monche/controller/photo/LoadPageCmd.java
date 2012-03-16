
package bigant.monche.controller.photo;

import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import bigant.monche.model.proxy.PhotoServiceProxy;
import bigant.monche.model.vo.PhotoStatusVo;
import bigant.monche.names.ProxyNames;
import bigant.monche.names.TypeImageNames;


/**
 * Initialize <code>Mediator</code>s with the view they are responsible for.
 */
public class LoadPageCmd
	extends SimpleCommand
	implements ICommand
{
    public void execute(INotification note )
    {
    	
    //	PhotoStatusVo.getInstance().type = TypeImageNames.RANDOM;
    	
		PhotoServiceProxy proxy = (PhotoServiceProxy) facade.retrieveProxy(ProxyNames.PHOTO_SERVICE_PROXY);
		proxy.getPhotoList();
    }
}
