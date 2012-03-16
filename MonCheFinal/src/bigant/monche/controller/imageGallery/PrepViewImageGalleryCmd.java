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

package bigant.monche.controller.imageGallery;

import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import bigant.monche.names.MediatorNames;
import bigant.monche.names.NotificationNames;
import bigant.monche.view.activity.ImageGalleryActivity;
import bigant.monche.view.mediator.ImageGalleryMediator;
import bigant.monche.view.mediator.ImageGridMediator;

/**
 * Initialize <code>Mediator</code>s with the view they are responsible for.
 */
public class PrepViewImageGalleryCmd extends SimpleCommand implements ICommand {
	public void execute(INotification note) {

		ImageGalleryActivity activity = (ImageGalleryActivity) note.getBody();
		
		ImageGalleryMediator moncheMed = new ImageGalleryMediator(MediatorNames.IMAGE_GALLERY, activity);
		
		facade.registerMediator(moncheMed);
		//sendNotification(NotificationNames.LOAD_FIRST_RANDOM_PAGE);
	}
}