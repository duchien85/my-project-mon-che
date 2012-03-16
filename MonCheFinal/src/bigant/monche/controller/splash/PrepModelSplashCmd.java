/*
 * PureMVC Java Currency Converter for Android
 * Copyright (C) 2010  Frederic Saunier - www.tekool.net
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package bigant.monche.controller.splash;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import android.app.Activity;
import android.content.SharedPreferences;
import bigant.monche.model.proxy.PhotoServiceProxy;
import bigant.monche.model.vo.PhotoStatusVo;
import bigant.monche.names.NotificationNames;
import bigant.monche.names.ProxyNames;


/**
 * Initialize <code>Proxy</code>s with data they need to control.
 */
public class PrepModelSplashCmd
	extends SimpleCommand
{
    public void execute( INotification note )
    {	
    	Activity activity = (Activity)note.getBody();
    	final SharedPreferences shareData = activity.getSharedPreferences("Mon che", 0);
    	PhotoStatusVo.getInstance().load(shareData);
    	
     	PhotoServiceProxy networkPhotoProxy = new PhotoServiceProxy( ProxyNames.PHOTO_SERVICE_PROXY);
     	facade.registerProxy( networkPhotoProxy );
     	networkPhotoProxy.autoLogin((Activity)note.getBody());
    }
}