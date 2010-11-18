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

import java.awt.image.RenderedImage;

/**
 * Interface for sending notifications to Growl.
 * 
 * @author Michael Stringer
 * @version 0.1
 */
public interface Growl {
	/**
	 * Registers this Growl object with the Growl service.
	 * 
	 * @throws GrowlException
	 *             If an error occurs during the registration.
	 */
	public void register() throws GrowlException;

	/**
	 * Sets the icon to display for notifications from this <code>Growl</code>.
	 * 
	 * This <b>must</b> be called before calling {@link #register()}. If the
	 * icon is changed after {@link #register()} has been called then another
	 * call to {@link #register()} must be made.
	 * 
	 * @param icon
	 *            The icon to display.
	 * @throws GrowlException
	 *             If an error occurs while setting the icon.
	 */
	public void setIcon(RenderedImage icon) throws GrowlException;

	/**
	 * Adds a notification type for this <code>Growl</code>. This <b>must</b> be
	 * called before calling {@link #register()}. If the another notification
	 * type is added after {@link #register()} has been called then another call
	 * to {@link #register()} must be made.
	 * 
	 * @param name
	 *            The name of the notification type. This is what appears in the
	 *            Growl settings.
	 * @param enabledByDefault
	 *            <code>true</code> if this notification type should be enabled
	 *            by default. This can be overridden by the user in the Growl
	 *            settings.
	 */
	public void addNotification(String name, boolean enabledByDefault);

	/**
	 * Adds a click callback listener.
	 * 
	 * @param listener
	 *            The callback listener to add.
	 */
	public void addCallbackListener(GrowlCallbackListener listener);

	/**
	 * Sends a notification to Growl for displaying. This <b>must</b> be called
	 * after calling {@link #register()}.
	 * 
	 * @param name
	 *            Name of the notification type that has been registered.
	 * @param title
	 *            The title of the notification.
	 * @param body
	 *            The body of the notification.
	 * @throws GrowlException
	 *             If the notification could not be sent.
	 */
	public void sendNotification(String name, String title, String body)
			throws GrowlException;

	/**
	 * Sends a notification to Growl for displaying. This <b>must</b> be called
	 * after calling {@link #register()}.
	 * 
	 * @param name
	 *            Name of the notification type that has been registered.
	 * @param title
	 *            The title of the notification.
	 * @param body
	 *            The body of the notification.
	 * @param icon
	 *            The icon to display with the notification.
	 * @throws GrowlException
	 *             If the notification could not be sent.
	 */
	public void sendNotification(String name, String title, String body,
			RenderedImage icon) throws GrowlException;

	/**
	 * Sends a notification to Growl for displaying. This <b>must</b> be called
	 * after calling {@link #register()}.
	 * 
	 * @param name
	 *            Name of the notification type that has been registered.
	 * @param title
	 *            The title of the notification.
	 * @param body
	 *            The body of the notification.
	 * @param callbackContext
	 *            A unique ID that will be sent to any registered
	 *            {@link GrowlCallbackListener}s. If this is <code>null</code>
	 *            then clicks will be ignored.
	 * @throws GrowlException
	 *             If the notification could not be sent.
	 */
	public void sendNotification(String name, String title, String body,
			String callbackContext) throws GrowlException;

	/**
	 * Sends a notification to Growl for displaying. This <b>must</b> be called
	 * after calling {@link #register()}.
	 * 
	 * @param name
	 *            Name of the notification type that has been registered.
	 * @param title
	 *            The title of the notification.
	 * @param body
	 *            The body of the notification.
	 * @param callbackContext
	 *            A unique ID that will be sent to any registered
	 *            {@link GrowlCallbackListener}s. If this is <code>null</code>
	 *            then clicks will be ignored.
	 * @param icon
	 *            The icon to display with the notification.
	 * @throws GrowlException
	 *             If the notification could not be sent.
	 */
	public void sendNotification(String name, String title, String body,
			String callbackContext, RenderedImage icon) throws GrowlException;
}
