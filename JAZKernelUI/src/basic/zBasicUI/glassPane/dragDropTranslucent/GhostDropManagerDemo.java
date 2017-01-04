package basic.zBasicUI.glassPane.dragDropTranslucent;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class GhostDropManagerDemo extends AbstractGhostDropManager {
    private JComponent target;

    public GhostDropManagerDemo(JComponent target) {
        super(target);
    }

	public void ghostDropped(GhostDropEvent e) {
	   String action = e.getAction();
	   Point p = getTranslatedPoint(e.getDropLocation());

	   if (isInTarget(p)) {
	       //JOptionPane.showMessageDialog(this.component, "Action: " + action);
	   }
	}

	@Override
	protected boolean isInTarget(Point point) {
		Rectangle bounds = component.getBounds();
		return bounds.contains(point);
	}
}