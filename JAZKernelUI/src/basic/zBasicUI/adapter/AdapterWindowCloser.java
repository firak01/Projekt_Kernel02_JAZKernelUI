package basic.zBasicUI.adapter;

import java.awt.Window;
import java.awt.event.*;

/** A WindowCloser - watch for Window Closing events, and
 * follow them up with setVisible(false) and dispose().
 * 
 * FGL: This class is used e.g. by debug.zKernelUI.layoutmanager.EntryLayoutTest.java
 *          f.addWindowListener(new WindowCloser(f, true));   //where f is the JFrame which should be closed.
 * 
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
public class AdapterWindowCloser extends WindowAdapter {
	/** The window we close */
	Window win;
	/** True if we are to exit as well. */
	boolean doExit = false;

	public AdapterWindowCloser(Window w) {
		this(w, false);
	}
	public AdapterWindowCloser(Window w, boolean exit) {
		win = w;
		doExit = exit;
	}
	/* FGL: Dies wäre dann meiner Meinung nach eine Methode, die man überschreiben könnte, z.B. mit einer Messagebox "... und schüss ..."
	 * (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent e) {
		win.setVisible(false);
		win.dispose();
		if (doExit)
			System.exit(0);
	}
}
