package basic.zBasicUI.glassPane.dragDropTranslucent;
import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

public class GhostPictureAdapter extends GhostDropAdapter
{
	private BufferedImage image;

	public GhostPictureAdapter(GhostGlassPane glassPane, String action, String picture) {
	   super(glassPane, action);
	   try {
	       //Das funktioniert wohl nicht immer this.image = ImageIO.read(new BufferedInputStream(GhostPictureAdapter.class.getResourceAsStream(picture)));
		   File objFile = new File(picture);
		   this.image = ImageIO.read(objFile);
	
	   } catch (MalformedURLException mue) {
	       throw new IllegalStateException("Invalid picture URL.");
	   } catch (IOException ioe) {
           throw new IllegalStateException("Invalid picture or picture URL.");
       }
	}

    public void mousePressed(MouseEvent e)
    {
    	/* FGL 20130627: Klasse GhostMotionAdapter verwendet den GlassPane beim DRAGGEN.
    	 * Aber: Wenn man einen JScrollPane verwendet, funktioniert der alte GlassPane nicht mehr richtig.
    	 * 
    	 * Darum im ersten Wurf beim Clicken der Mouse-Taste den glassPane neu erstellen.*/
    	
    	
        Component c = e.getComponent();
        
        System.out.println("GhostPictureAdapter: MousePressed on component using GhostPictureAdapter");
        //Scheint so nicht zu funktionieren... this.refreshGhostGlassPane(); //FGL 20130627 neu, aktualisiere den GlassPane, weil beim Verschieben eines Panels mit ScrollBars es sonst nicht mehr funktioniert.
        glassPane.setVisible(true);

        Point p = (Point) e.getPoint().clone();
        SwingUtilities.convertPointToScreen(p, c);
        SwingUtilities.convertPointFromScreen(p, glassPane);

        glassPane.setPoint(p);
        glassPane.setImage(image);
        glassPane.repaint();
    }

    public void mouseReleased(MouseEvent e)
    {
        Component c = e.getComponent();

        Point p = (Point) e.getPoint().clone();
        SwingUtilities.convertPointToScreen(p, c);

        Point eventPoint = (Point) p.clone();
        SwingUtilities.convertPointFromScreen(p, glassPane);

        glassPane.setPoint(p);
        glassPane.setVisible(false);
        glassPane.setImage(null);

        fireGhostDropEvent(new GhostDropEvent(action, eventPoint));
    }
}