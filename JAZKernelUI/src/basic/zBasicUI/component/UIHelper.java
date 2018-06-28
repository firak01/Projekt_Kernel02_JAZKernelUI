package basic.zBasicUI.component;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileCacheImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Aus den Beispielen zum Buch "Swing Hacks", Beispiel Nr. 69 "Translucent Drag And Drop".
 *
 */
public final class UIHelper
{
    public static JButton createButton(String text)
    {
        JButton button = new JButton(text);
        button.setFocusPainted(true);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    public static JButton createButton(String text, String icon)
    {
        return createButton(text, icon, false);
    }

    public static JButton createButton(String text, String icon, boolean flat)
    {
        ImageIcon iconNormal = readImageIcon(icon + ".png");
        ImageIcon iconHighlight = readImageIcon(icon + "_highlight.png");
        ImageIcon iconPressed = readImageIcon(icon + "_pressed.png");

        JButton button = new JButton(text, iconNormal);
        button.setFocusPainted(!flat);
        button.setBorderPainted(!flat);
        button.setContentAreaFilled(!flat);
        if (iconHighlight != null) 
        {
            button.setRolloverEnabled(true);
            button.setRolloverIcon(iconHighlight);
        }
        if (iconPressed != null)
            button.setPressedIcon(iconPressed);
        return button;
    }

    /**FGL 20130624: Erweitert und Generalisiert aus Buch "Swing Hacks", Code zu Beispiel 69
     * @param text
     * @param icon
     * @return
     */
    public static JLabel createLabelWithIcon(String text, String icon)
    {
        ImageIcon iconNormal = readImageIcon(icon);
        JLabel label = new JLabel(text, iconNormal, JLabel.LEFT);
        return label;
    }
    
    /**FGL 20180330: Erweitert um die Möglichkeit die Größe zu ändern. Idee aus Buch "Swing Hacks", Code zu Beispiel 69*/
    public static JLabel createLabelWithIconResized(String text, String icon, int iWidth, int iHeight )
    {
        ImageIcon iconNormal = readImageIconResized(icon, iWidth, iHeight);
        JLabel label = new JLabel(text, iconNormal, JLabel.LEFT);
        return label;
    }

    /**FGL 20130624: Erweitert und Generalisiert aus Buch "Swing Hacks", Code zu Beispiel 69*/
    public static ImageIcon readImageIcon(String filename)
    {
    	//System.out.println("Pfad zu den Images: " + filename );
    	
    	//FGL 20130624: Original, funktioniert aber wohl nur innerhalb des gleichen Projekt / Packages
    	//System.out.println("Get Resource; " + UIHelper.class.getResource(filename));
        //return new ImageIcon(Toolkit.getDefaultToolkit().getImage(UIHelper.class.getResource(filename)));
    	return new ImageIcon(Toolkit.getDefaultToolkit().getImage(filename));
    }
    
    public static ImageIcon readImageIconResized(String sFilename, int iNewWidth, int iNewHeight){
    	ImageIcon objImageIconReturn = null;
    	try {
    	 File objFile = new File(sFilename);		   
		 BufferedImage objBufferdImageTemp = ImageIO.read(objFile);
		
		   
		 //Die Größe verändern
		   BufferedImage objBufferedImageResized = UIHelper.resizeImage(objBufferdImageTemp, iNewWidth, iNewHeight);

		   objImageIconReturn = new ImageIcon(objBufferedImageResized);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return objImageIconReturn;
    }
    
    public static BufferedImage resizeImage(BufferedImage objImageToResize, int iNewWidth, int iNewHeight){
    	BufferedImage objBufferedImageReturn=null;
    	main:{
    		//Erst ein Größenverändetes Image aus dem BufferedImage machen
    		 Image objImageTemp = objImageToResize.getScaledInstance(iNewWidth, iNewHeight, Image.SCALE_SMOOTH);
   		  
    		  //... und wieder zu einem BufferedImage machen
    		objBufferedImageReturn = new BufferedImage(iNewWidth, iNewHeight, BufferedImage.TYPE_INT_ARGB);
    		Graphics2D g2d = objBufferedImageReturn.createGraphics();
  		   	g2d.drawImage(objImageTemp, 0, 0, null);//FGL: Hierdurch wird wohl das Image wieder in das neue, zurückzugebend BufferedImage gepackt.
  		   	g2d.dispose();
  		   	
    	}
    	return objBufferedImageReturn;
    }
    
    /*D.h. hier werden die Ränder vom Bild abgeschnitten. Mit O ist die obere Ecke gekennzeichnet. Entspricht der unteren Ecke.
    
    +------------------+    
    |                    |
    |  O-----------+   |
    |  |            |    |
    |  |            |    |
    |  |            |    |
    |  |            |    |
    |  +-----------O   |
    |                    |
    ---------------------
  */    
    public static BufferedImage cropImageCentral(BufferedImage objImageToCrop, int iBorderWidth, int iBorderHeight){
    	BufferedImage objBufferedImageReturn=null;
    	main:{
    		//Erst ein Größenverändetes Image aus dem BufferedImage machen
    		 int iImageWidth = objImageToCrop.getWidth();
    		 int iImageHeight = objImageToCrop.getHeight();
    		 objBufferedImageReturn = objImageToCrop.getSubimage(iBorderWidth, iBorderHeight,  iImageWidth-2*iBorderWidth, iImageHeight-2*iBorderHeight);  		   	
    	}
    	return objBufferedImageReturn;
    }
    
    /*D.h. hier werden die Ränder vom Bild abgeschnitten. Mit O sind die obere und untere Ecke gekennzeichnet.
    
    +------------------+    
    |                    |
    |  O-----------+   |
    |  |            |    |
    |  |            |    |
    |  |            |    |
    |  |            |    |
    |  +-----------O   |
    |                    |
    ---------------------
  */    
    //ACHTUNG FEHLER: 
    //Exception in thread "AWT-EventQueue-0" java.awt.image.RasterFormatException: (y + height) is outside of Raster
    public static BufferedImage cropImageByPoints(BufferedImage objImageToCrop, int iUpperBorderHeight,int iUpperBorderWidth, int iLowerBorderHeight, int iLowerBorderWidth){
    	BufferedImage objBufferedImageReturn=null;
    	main:{
    		//Erst ein Größenverändetes Image aus dem BufferedImage machen
    		 int iImageWidth = objImageToCrop.getWidth();
    		 int iImageHeight = objImageToCrop.getHeight();
    		 objBufferedImageReturn = objImageToCrop.getSubimage(iUpperBorderWidth, iUpperBorderHeight,  iImageWidth-iUpperBorderWidth-iLowerBorderWidth, iImageHeight-iUpperBorderHeight-iLowerBorderHeight);  		   	
    	}
    	return objBufferedImageReturn;
    }
    
    
    //TODO: NOCH NICHT ANGEWENDET
  //Diese Methoden in den Kernel übernehmen. Sie dienen hier nur zur Verdeutlichung wie mit ImagInputStream und den BLOBs gearbeitet werden kann
  	public File addImageExtension(File incoming) throws IOException {
  		
  		String format = null;
  		ImageInputStream iis = ImageIO.createImageInputStream(incoming);
  		Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
  		while (imageReaders.hasNext()) {
  		    ImageReader reader = (ImageReader) imageReaders.next();
  		    format = reader.getFormatName().toLowerCase();
  		   // log.debug("filetype is: " + format);

  		    File newfile = new File("imageimage." + format);
  			if (newfile.exists()) {
  				newfile.delete();    	
  			}
  			
  		    Files.copy(incoming.toPath(), newfile.toPath());
  		    incoming.delete();

  		    return newfile;
  		}
  		return null;
  		
  	}
  	
  	/**     //TODO: NOCH NICHT ANGEWENDET
  	 * Extracts the picture size of a given Image.
  	 *
  	 * @param path Path to the image
  	 * @return Image-size in pixel
  	 * @throws IOException Throws exception when file-access fails
  	 */
  	public static Dimension getPictureSize(final String path) throws IOException {
  	   //FGL: DAS IST J2SE 1.7 Syntax:  try (ImageInputStream in = ImageIO.createImageInputStream(path)) {
  	//	try{ //FGL: Abändern in Java 1.6 Syntax
  			ImageInputStream in = ImageIO.createImageInputStream(path);
  			//FGL: Aber so fehlt eine Abfrage, auf NULL		
  					
  	        final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
  	        if (readers.hasNext()) {
  	            ImageReader reader = readers.next();
  	            try {
  	                reader.setInput(in);
  	                return new Dimension(reader.getWidth(0), reader.getHeight(0));
  	            } finally {
  	                reader.dispose();
  	            }
  	        }
  	        return null;  	
  	}
  	
  	
    //TODO: NOCH NICHT ANGEWENDET
  	public ImageInputStream createInputStreamInstance(Object input, boolean useCache, File cacheDir) throws IOException {
  		if (input instanceof InputStream) {
  			InputStream is = (InputStream)input;

  			if (useCache) {
  				return new FileCacheImageInputStream(is, cacheDir);
  			} else {
  				return new MemoryCacheImageInputStream(is);
  			}
  		} else {
  			throw new IllegalArgumentException();
  		}
  	}

}
