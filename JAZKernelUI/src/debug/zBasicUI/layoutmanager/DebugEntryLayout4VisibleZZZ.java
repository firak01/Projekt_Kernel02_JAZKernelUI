package debug.zBasicUI.layoutmanager;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import basic.zBasicUI.adapter.AdapterWindowCloser;
import basic.zBasicUI.layoutmanager.EntryLayout;
import basic.zBasicUI.layoutmanager.EntryLayout4VisibleZZZ;


/** Testbed for EntryLayout layout manager.
 * @author	Ian Darwin, ian@darwinsys.com
 * @version $Id: EntryLayoutTest.java,v 1.2 2007/01/12 09:40:47 lindhauer Exp $
 */
public class DebugEntryLayout4VisibleZZZ {

	/** "main program" method - construct and show */
	public static void main(String[] av) {
		final JFrame f = new JFrame("EntryLayout Demonstration");
		final Container cp = f.getContentPane();
		double widths[] = { .33, .66 };
		cp.setLayout(new EntryLayout4VisibleZZZ(widths));
		cp.add(new JLabel("Login:", SwingConstants.RIGHT));
		cp.add(new JTextField(10));
		cp.add(new JLabel("Password:", SwingConstants.RIGHT));
		cp.add(new JPasswordField(20));
		cp.add(new JLabel("Security Domain:", SwingConstants.RIGHT));
		cp.add(new JTextField(20));
		
		//WICHTIG: DIE ANZAHL DER KOMPONENTEN MUSS IMMER DURCH DIE ANZAHL DER SPALTEN OHNE REST TEILBAR SEIN
		cp.add(new JLabel("Monkey wrench"));		
		cp.add(new JLabel("...in works...andere Labels sind per Timer verborgen."));
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//f.addWindowListener(new AdapterWindowCloser(f, true));
		f.setLocation(200, 200);
		f.setVisible(true);
		
		
		javax.swing.Timer timer = new javax.swing.Timer(2000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	int length=cp.getComponentCount();
                for (int i = 0; i < length; i++)
                {
                    if (i%2==0)
                    	if(cp.getComponent(i)!=null) cp.getComponent(i).setVisible(false);
                }

            }
        });
        timer.setRepeats(false);
        timer.start();
		
	}
}
