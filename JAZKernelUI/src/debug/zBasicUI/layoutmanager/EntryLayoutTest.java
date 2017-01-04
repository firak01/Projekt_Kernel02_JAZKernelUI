package debug.zBasicUI.layoutmanager;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import basic.zBasicUI.adapter.AdapterWindowCloser;
import basic.zBasicUI.layoutmanager.EntryLayout;


/** Testbed for EntryLayout layout manager.
 * @author	Ian Darwin, ian@darwinsys.com
 * @version $Id: EntryLayoutTest.java,v 1.2 2007/01/12 09:40:47 lindhauer Exp $
 */
public class EntryLayoutTest {

	/** "main program" method - construct and show */
	public static void main(String[] av) {
		final JFrame f = new JFrame("EntryLayout Demonstration");
		Container cp = f.getContentPane();
		double widths[] = { .33, .66 };
		cp.setLayout(new EntryLayout(widths));
		cp.add(new JLabel("Login:", SwingConstants.RIGHT));
		cp.add(new JTextField(10));
		cp.add(new JLabel("Password:", SwingConstants.RIGHT));
		cp.add(new JPasswordField(20));
		cp.add(new JLabel("Security Domain:", SwingConstants.RIGHT));
		cp.add(new JTextField(20));
		// cp.add(new JLabel("Monkey wrench in works"));
		f.pack();
		f.addWindowListener(new AdapterWindowCloser(f, true));
		f.setLocation(200, 200);
		f.setVisible(true);
	}
}
