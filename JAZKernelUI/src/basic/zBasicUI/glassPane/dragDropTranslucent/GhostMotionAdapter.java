package basic.zBasicUI.glassPane.dragDropTranslucent;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GhostMotionAdapter extends MouseMotionAdapter
{
    private GhostGlassPane glassPane;
	//private IGhostGlassPaneFrame parentFrame;

	/*public GhostMotionAdapter(GhostGlassPane glassPane) {
		this.glassPane = glassPane;
	}*/
	public GhostMotionAdapter(GhostGlassPane glassPane){
		//this.parentFrame = glassPane.getParentFrame();
		this.glassPane = glassPane;
	}
	
	/* FGL 20130627:
	 * Aber: Wenn man einen JScrollPane verwendet, dann muss man den glassPane neu erstellen. Nach dem Weiterscrollen funktioniert der alte n�mlich nicht mehr richtig.
	 * Darum im ersten Wurf beim Clicken der Mouse-Taset den glassPane aktualisieren (Klasse GhostPictureAdapter)*/
	
	public void mouseDragged(MouseEvent e)
    {
        Component c = e.getComponent();

        Point p = (Point) e.getPoint().clone();
        SwingUtilities.convertPointToScreen(p, c);
        
        //Neue: Glass Pane aus dem Frame holen. Dann besteht Hoffnung, dass er ggf. nach dem Scrollen aktualisiert wurde.
        //GhostGlassPane glassPane = this.parentFrame.getGhostGlassPane();
        GhostGlassPane glassPane = this.glassPane;        
        SwingUtilities.convertPointFromScreen(p, glassPane);
        glassPane.setPoint(p);

        glassPane.validate();
        glassPane.repaint();
    }
}