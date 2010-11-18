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
 * Dummy implementation of the Growl API for situations where the native code
 * cannot be loaded (non-Mac systems).
 * 
 * @author Michael Stringer
 * @version 0.1
 */
class DummyGrowl implements Growl {

	/**
	 * {@inheritDoc}
	 */
	public void addNotification(final String name,
			final boolean enabledByDefault) {
		// does nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void register() throws GrowlException {
		// does nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void sendNotification(final String name, final String title,
			final String body) throws GrowlException {
		// does nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void sendNotification(final String name, final String title,
			final String body, final RenderedImage icon) throws GrowlException {
		// does nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void setIcon(final RenderedImage icon) throws GrowlException {
		// does nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void sendNotification(final String name, final String title,
			final String body, final String callbackContext)
			throws GrowlException {
		// does nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void sendNotification(final String name, final String title,
			final String body, final String callbackContext,
			final RenderedImage icon) throws GrowlException {
		// does nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void addCallbackListener(final GrowlCallbackListener listener) {
		// does nothing
	}
}
