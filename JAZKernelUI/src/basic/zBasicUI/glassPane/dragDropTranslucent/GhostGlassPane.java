package basic.zBasicUI.glassPane.dragDropTranslucent;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

/** Aus dem Buch "Swing Hacks" , Nr. 69 "Translucent Drag and drop"
 *  Etwas, das GEDRAGGED wird, wird hieherh kopiert.
 * @author lindhaueradmin
 *
 */
public class GhostGlassPane extends JPanel
{
	private AlphaComposite composite;
    private BufferedImage dragged = null;
    private Point location = new Point(0, 0);
    
    //FGL 20130627: Versuch dem GhostGlassPane etwas Info über den ElternFrame mitzugeben.
    private JFrame frameParent = null;

    public GhostGlassPane()
    {
        setOpaque(false);
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
    }
    
    /**FGL 20130627: Versuch dem GlassPane das Elternframe mitzugeben.
     * @param frameParent
     */
    public GhostGlassPane(JFrame frameParent){
    	this();
    	this.setParentFrame(frameParent);
    	frameParent.setGlassPane(this);
    }
     

    public void setImage(BufferedImage dragged)
    {
        this.dragged = dragged;
    }

    public void setPoint(Point location)
    {
        this.location = location;
    }

    public void paintComponent(Graphics g)
    {
        if (dragged == null)
            return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(composite);
        g2.drawImage(dragged,
                     (int) (location.getX() - (dragged.getWidth(this)  / 2)),
                     (int) (location.getY() - (dragged.getHeight(this) / 2)),
                     null);
    }
    
    //##### GETTER / SETTER 
    public void setParentFrame(JFrame frameParent){
    	this.frameParent = frameParent;
    }
    public JFrame getParentFrame(){
    	return this.frameParent;
    }
}