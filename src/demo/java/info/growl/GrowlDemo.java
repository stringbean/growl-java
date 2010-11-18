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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Simple demo application for Growl.
 * 
 * @author Michael Stringer
 * @version 0.1
 */
public class GrowlDemo extends JFrame {
	private static final long serialVersionUID = -3138383722905617712L;

	private static final String APP_NAME = "Test Java App";
	private static final String NOTIF_3_CALLBACK = "Notif3";

	public GrowlDemo() {
		super("Growl for Java");
		setSize(320, 290);
		setLocationRelativeTo(null);

		getContentPane().setLayout(new GridLayout(6, 1));

		getContentPane().add(new JButton(new RegisterAction()));
		getContentPane().add(new JButton(new Notification1Action()));
		getContentPane().add(new JButton(new Notification2Action()));
		getContentPane().add(new JButton(new Notification3Action()));
		getContentPane().add(new JButton(new App2NotificationAction()));
		getContentPane().add(new JButton(new ExitAction()));

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static final void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GrowlDemo();
			}
		});
	}

	private class RegisterAction extends AbstractAction {
		private static final long serialVersionUID = -8716214566439126691L;

		private RegisterAction() {
			super("Register Notifications");
		}

		public void actionPerformed(final ActionEvent event) {
			try {
				Growl growl = GrowlUtils.getGrowlInstance(APP_NAME);

				growl.addNotification("Test Notification 1", true);
				growl.addNotification("Test Notification 2", false);
				growl.addNotification("Test Notification 3", true);

				GrowlCallbackListener listener = new GrowlCallbackListener() {
					public void notificationWasClicked(final String clickContext) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								if (NOTIF_3_CALLBACK.equals(clickContext)) {
									JOptionPane
											.showMessageDialog(GrowlDemo.this,
													"User clicked on 'Test Notification 3'");
								}
							}
						});
					}
				};

				growl.addCallbackListener(listener);
				growl.register();
			} catch (GrowlException ge) {
				ge.printStackTrace();
			}
		}
	}

	private class Notification1Action extends AbstractAction {
		private static final long serialVersionUID = 2734492964502668174L;

		private Notification1Action() {
			super("Send 'Test Notification 1'");
		}

		public void actionPerformed(final ActionEvent ae) {
			try {
				Growl growl = GrowlUtils.getGrowlInstance(APP_NAME);

				growl.sendNotification("Test Notification 1", "Test Notif 1",
						"This is a test");
			} catch (GrowlException ge) {
				ge.printStackTrace();
			}
		}
	}

	private class Notification2Action extends AbstractAction {
		private static final long serialVersionUID = 54253683410246892L;

		private Notification2Action() {
			super("Send 'Test Notification 2'");
		}

		public void actionPerformed(final ActionEvent event) {
			try {
				Growl growl = GrowlUtils.getGrowlInstance(APP_NAME);
				BufferedImage image = ImageIO.read(GrowlDemo.class
						.getResource("/duke.gif"));
				growl.sendNotification("Test Notification 2", "Test Notif 2",
						"This is another test", image);
			} catch (GrowlException ge) {
				ge.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	private class Notification3Action extends AbstractAction {
		private static final long serialVersionUID = -5084897751729470472L;

		private Notification3Action() {
			super("Test Callback 'Notification 3'");
		}

		public void actionPerformed(final ActionEvent event) {
			try {
				Growl growl = GrowlUtils.getGrowlInstance(APP_NAME);

				growl.sendNotification("Test Notification 3", "Callback Test",
						"Click me - I dares you!", NOTIF_3_CALLBACK);
			} catch (GrowlException ge) {
				ge.printStackTrace();
			}
		}
	}

	private class App2NotificationAction extends AbstractAction {
		private static final long serialVersionUID = -4597531887234923897L;

		private App2NotificationAction() {
			super("Reg & Test App 2");
		}

		public void actionPerformed(final ActionEvent event) {
			try {
				Growl growl = GrowlUtils.getGrowlInstance("Other App");

				growl.addNotification("A Notification", true);

				BufferedImage image = ImageIO.read(GrowlDemo.class
						.getResource("/duke.gif"));
				growl.setIcon(image);

				growl.register();
				growl.sendNotification("A Notification", "Testin",
						"Blah de blah blah");
			} catch (GrowlException ge) {
				ge.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	private class ExitAction extends AbstractAction {
		private static final long serialVersionUID = 3101709085470822601L;

		private ExitAction() {
			super("Exit");
		}

		public void actionPerformed(final ActionEvent event) {
			System.exit(0);
		}
	}
}
