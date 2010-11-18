/*
 * Copyright (c) 2010, Michael Stringer
 * 
 * This file is part of Growl Java.
 * 
 * Growl Java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * Growl Java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Growl Java. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package info.growl;

/**
 * Callback interface for handling users clicking on notifications.
 * 
 * @author Michael Stringer
 * @version 0.1
 */
public interface GrowlCallbackListener {

	/**
	 * Signals that the user has clicked on a notification with an identifier of
	 * <code>clickContext</code>.
	 * 
	 * @param clickContext
	 *            The ID of the notification clicked.
	 */
	public void notificationWasClicked(String clickContext);
}
