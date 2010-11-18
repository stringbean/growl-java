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

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for sending notifications using Growl.
 * 
 * @author Michael Stringer
 * @version 0.1
 */
public final class GrowlUtils {
	private static final boolean GROWL_LOADED;
	private static Map<String, Growl> instances = new HashMap<String, Growl>();

	static {
		boolean loaded = false;
		try {
			System.loadLibrary("growl");
			loaded = true;
		} catch (UnsatisfiedLinkError ule) {
			System.out.println("Failed to load Growl library");
		}

		GROWL_LOADED = loaded;
	}

	/**
	 * Utility method - should not be instantiated.
	 */
	private GrowlUtils() {

	}

	/**
	 * Gets a <code>Growl</code> instance to use for the specified application
	 * name. Multiple calls to this method will return the same instance.
	 * 
	 * @param appName
	 *            The name of the application.
	 * @return The <code>Growl</code> instance to use.
	 */
	public static Growl getGrowlInstance(final String appName) {
		Growl instance = instances.get(appName);

		if (instance == null) {
			if (GROWL_LOADED) {
				instance = new GrowlNative(appName);
			} else {
				instance = new DummyGrowl();
			}

			instances.put(appName, instance);
		}

		return instance;
	}

	/**
	 * Gets whether messages can be sent to Growl. If this returns
	 * <code>false</code> then {@link #getGrowlInstance(String)} will return a
	 * dummy object.
	 * 
	 * @return <code>true</code> if messages can be sent to Growl.
	 */
	public static boolean isGrowlLoaded() {
		return GROWL_LOADED;
	}
}
