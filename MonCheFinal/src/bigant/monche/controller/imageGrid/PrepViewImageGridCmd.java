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

package bigant.monche.controller.imageGrid;

import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import bigant.monche.model.proxy.PhotoServiceProxy;
import bigant.monche.names.MediatorNames;
import bigant.monche.names.NotificationNames;
import bigant.monche.names.ProxyNames;
import bigant.monche.view.activity.ImageGridActivity;
import bigant.monche.view.activity.SplashScreen;
import bigant.monche.view.mediator.ImageGridMediator;
import bigant.monche.view.mediator.SplashMediator;

/**
 * Initialize <code>Mediator</code>s with the view they are responsible for.
 */
public class PrepViewImageGridCmd extends SimpleCommand implements ICommand {
	public void execute(INotification note) {

		ImageGridActivity activity = (ImageGridActivity) note.getBody();
		
		ImageGridMediator moncheMed = new ImageGridMediator(MediatorNames.IMAGE_GRID, activity);
		
		facade.registerMediator(moncheMed);
		sendNotification(NotificationNames.LOAD_FIRST_RANDOM_PAGE);
	}
}