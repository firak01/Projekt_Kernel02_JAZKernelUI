package basic.zBasicUI.glassPane.dragDropTranslucent;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
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

import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasicUI.component.UIHelper;
import basic.zKernel.KernelZZZ;

public class GhostPictureAdapter extends GhostDropAdapter
{
	private BufferedImage image;
	
     public GhostPictureAdapter(GhostGlassPane glassPane, String action){
    	 super(glassPane, action);
     }
	public GhostPictureAdapter(GhostGlassPane glassPane, String action, String picture) {
	   super(glassPane, action);
	   try {
	       //Das funktioniert wohl nicht immer this.image = ImageIO.read(new BufferedInputStream(GhostPictureAdapter.class.getResourceAsStream(picture)));
		   File objFile = new File(picture);
		   
		   BufferedImage objBufferdImageTemp = ImageIO.read(objFile);		  
		   this.image = objBufferdImageTemp;
		   
		   
		   
	   } catch (MalformedURLException mue) {
	       throw new IllegalStateException("Invalid picture URL.");
	   } catch (IOException ioe) {
           throw new IllegalStateException("Invalid picture or picture URL.");
       }
	}
	
	/** Die Größe verändern beim Ziehen.
	 * Als ein Alternativer Konstuktor. Er wird angeboten, damit man in dieser Klasse kein Kernel - Objekt verwenden muss, um z.B. die Konfiguration für die IconGröße auszulesen.
	 * @param glassPane
	 * @param action
	 * @param picture
	 * @param iWidth
	 * @param iHeight
	 */
	public GhostPictureAdapter(GhostGlassPane glassPane, String action, String picture, int iWidth, int iHeight) {
		   super(glassPane, action);
		   GhostPictureAdapterNew_(glassPane, action, null, picture, iWidth, iHeight);
		   
		   try {
		       //Das funktioniert wohl nicht immer this.image = ImageIO.read(new BufferedInputStream(GhostPictureAdapter.class.getResourceAsStream(picture)));
			   File objFile = new File(picture);
			   
			   BufferedImage objBufferedImageTemp = ImageIO.read(objFile);
			   
			 //Die Größe verändern			   
			   if(iWidth>=1 && iHeight>=1){
				   BufferedImage objBufferedImageResized = UIHelper.resizeImage(objBufferedImageTemp, iWidth, iHeight); 
				   this.image = objBufferedImageResized;
			   }else{
				   this.image=objBufferedImageTemp;
			   }

		   } catch (MalformedURLException mue) {
		       throw new IllegalStateException("Invalid picture URL.");
		   } catch (IOException ioe) {
	           throw new IllegalStateException("Invalid picture or picture URL.");
	       }
		}
	
	/** Die Größe verändern beim Ziehen.
	 * Als ein Alternativer Konstuktor, nun direkt mit dem Bild in bytes.
	 * Er wird angeboten, damit man in dieser Klasse kein Kernel - Objekt verwenden muss, um z.B. die Konfiguration für die IconGröße auszulesen.
	 * @param glassPane
	 * @param action
	 * @param picture
	 * @param iWidth
	 * @param iHeight
	 */
	public GhostPictureAdapter(GhostGlassPane glassPane, String action, byte[] imageInByte, int iWidth, int iHeight) {
		   super(glassPane, action);
		   GhostPictureAdapterNew_(glassPane, action, imageInByte, null, iWidth, iHeight);		   
		}
	
	/** Das Bild direkt vewenden. Ggfs. wurde es daher schon Größentechnisch, etc. verändert in der Datenbank abgespeichert.
	 * Als ein Alternativer Konstuktor, nun direkt mit dem Bild in bytes.
	 * Er wird angeboten, damit man in dieser Klasse kein Kernel - Objekt verwenden muss, um z.B. die Konfiguration für die IconGröße auszulesen.
	 * @param glassPane
	 * @param action
	 * @param picture
	 */
	public GhostPictureAdapter(GhostGlassPane glassPane, String action, byte[] imageInByte) {
		   super(glassPane, action);
		   GhostPictureAdapterNew_(glassPane, action, imageInByte, null, 0, 0);
	}
	
	private boolean GhostPictureAdapterNew_(GhostGlassPane glassPane, String action, byte[] imageInByte, String sImagePath, int iWidth, int iHeight){
		boolean bReturn = false;
		String sImagePathUsed = null;
		try {
		if(imageInByte==null && StringZZZ.isEmpty(sImagePath)){
			   //sImagePathUsed = "Projekt_Kernel02_JAZKernel\\JAZKernel\\images\\quit.png"; //ALS DUMMY
			sImagePathUsed = "images\\quit.png"; //ALS DUMMY
		 }else{
			 sImagePathUsed = sImagePath;  
		 }
		
		BufferedImage objBufferedImageTemp = null;
		if(imageInByte==null){
			if(!StringZZZ.isEmpty(sImagePathUsed)){
				  File objFile = new File(sImagePathUsed);
				  objBufferedImageTemp = ImageIO.read(objFile);			   	
			}
		}else{
			objBufferedImageTemp = UIHelper.toBufferedImage(imageInByte);
		}
			   
		   //Die Größe verändern			   
		   if(iWidth>=1 && iHeight>=1){
			   BufferedImage objBufferedImageResized = UIHelper.resizeImage(objBufferedImageTemp, iWidth, iHeight); 
			   this.image = objBufferedImageResized;
		   }else{
			   this.image=objBufferedImageTemp;
		   }

			   bReturn = true;
		   } catch (MalformedURLException mue) {
		       throw new IllegalStateException("Invalid picture URL. (sImagePathUsed=" + sImagePathUsed + "')" );
		   } catch (IOException ioe) {
	           throw new IllegalStateException("Invalid picture or picture URL. (sImagePathUsed=" + sImagePathUsed + "')" );
	       }
		   
		
		return bReturn;
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
        if(image!=null) glassPane.setImage(image); //Verwende hier ein im Konstruktor zur Verfügung gestelltes Bild. Darum heisst es ja auch "Picture Adapter".                
        glassPane.validate();
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