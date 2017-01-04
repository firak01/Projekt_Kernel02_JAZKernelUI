package basic.zBasicUI.glassPane.dragDropTranslucent;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernelUI.util.JScrollPaneHelperZZZ;


public abstract class AbstractGhostDropManager implements GhostDropListener {
	protected JComponent component;

	public AbstractGhostDropManager() {
		this(null);
	}
	
	public AbstractGhostDropManager(JComponent component) {
		this.component = component;
	}
	
	public JComponent getDropTargetComponent(){
		return this.component;
	}

	protected Point getTranslatedPoint(Point point) {
        Point p = (Point) point.clone();
        SwingUtilities.convertPointFromScreen(p, component);
		return p;
	}
	
	protected abstract boolean isInTarget(Point point);
		
	public void ghostDropped(GhostDropEvent e) {
	}
}