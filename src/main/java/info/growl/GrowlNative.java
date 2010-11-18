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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Growl notification implementation. This uses JNI to send messages to Growl.
 * 
 * @author Michael Stringer
 * @version 0.1
 */
class GrowlNative implements Growl {
	private String appName;
	private List<NotificationType> notifications;
	private List<GrowlCallbackListener> callbackListeners;
	private byte[] imageData;

	private native void sendNotification(String appName, String name,
			String title, String message, String callbackContext, byte[] icon);

	private native void registerApp(String appName, byte[] image,
			List<NotificationType> notifications);

	/**
	 * Creates a new <code>GrowlNative</code> instance for the specified
	 * application name.
	 * 
	 * @param appName
	 *            The name of the application sending notifications.
	 */
	GrowlNative(final String appName) {
		notifications = new ArrayList<NotificationType>();
		callbackListeners = new ArrayList<GrowlCallbackListener>();
		this.appName = appName;
	}

	void fireCallbacks(final String callbackContext) {
		for (GrowlCallbackListener listener : callbackListeners) {
			listener.notificationWasClicked(callbackContext);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void register() throws GrowlException {
		registerApp(appName, imageData, notifications);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addNotification(final String name,
			final boolean enabledByDefault) {
		notifications.add(new NotificationType(name, enabledByDefault));
	}

	/**
	 * {@inheritDoc}
	 */
	public void addCallbackListener(final GrowlCallbackListener listener) {
		callbackListeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setIcon(final RenderedImage icon) throws GrowlException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(icon, "png", baos);

			imageData = baos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void sendNotification(final String name, final String title,
			final String body) throws GrowlException {
		if (!notifications.contains(new NotificationType(name, false))) {
			System.out.println("contains: " + notifications);
			throw new GrowlException("Unregistered notification name [" + name
					+ "]");
		}
		sendNotification(appName, name, title, body, null, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public void sendNotification(final String name, final String title,
			final String body, final RenderedImage icon) throws GrowlException {
		if (!notifications.contains(new NotificationType(name, false))) {
			System.out.println("contains: " + notifications);
			throw new GrowlException("Unregistered notification name [" + name
					+ "]");
		}

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(icon, "png", baos);

			byte[] image = baos.toByteArray();

			sendNotification(appName, name, title, body, null, image);
		} catch (IOException ioe) {
			throw new GrowlException("Failed to convert Image", ioe);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void sendNotification(final String name, final String title,
			final String body, final String callbackContext)
			throws GrowlException {
		if (!notifications.contains(new NotificationType(name, false))) {
			System.out.println("contains: " + notifications);
			throw new GrowlException("Unregistered notification name [" + name
					+ "]");
		}

		sendNotification(appName, name, title, body, callbackContext, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public void sendNotification(final String name, final String title,
			final String body, final String callbackContext,
			final RenderedImage icon) throws GrowlException {
		if (!notifications.contains(new NotificationType(name, false))) {
			System.out.println("contains: " + notifications);
			throw new GrowlException("Unregistered notification name [" + name
					+ "]");
		}

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(icon, "png", baos);

			byte[] image = baos.toByteArray();

			sendNotification(appName, name, title, body, callbackContext, image);
		} catch (IOException ioe) {
			throw new GrowlException("Failed to convert Image", ioe);
		}
	}

	public class NotificationType {
		private String name;
		private boolean enabledByDefault;

		public NotificationType(final String name,
				final boolean enabledByDefault) {
			this.name = name;
			this.enabledByDefault = enabledByDefault;
		}

		public String getName() {
			return name;
		}

		public boolean isEnabledByDefault() {
			return enabledByDefault;
		}

		@Override
		public boolean equals(final Object other) {
			if (!(other instanceof NotificationType)) {
				return false;
			}

			NotificationType otherType = (NotificationType) other;

			return name.equals(otherType.name);
		}
	}
}
