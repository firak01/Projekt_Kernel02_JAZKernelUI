package basic.zBasicUI.component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

public class UIHelperTransparency {
	/* aus:
	 https://stackoverflow.com/questions/12020597/java-convert-image-to-icon-imageicon
	 https://tips4java.wordpress.com/2010/08/22/alpha-icons/
	 */
	public static Image makeColorTransparent(Image im, final Color color) {
	    ImageFilter filter = new RGBImageFilter() {
	      // the color we are looking for... Alpha bits are set to opaque
	      public int markerRGB = color.getRGB() | 0xFF000000;

	      @Override
	      public final int filterRGB(int x, int y, int rgb) {
	        if ((rgb | 0xFF000000) == markerRGB) {
	          // Mark the alpha bits as zero - transparent
	          return 0x00FFFFFF & rgb;
	        } else {
	          // nothing to do
	          return rgb;
	        }
	      }
	    };

	    ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
	    return Toolkit.getDefaultToolkit().createImage(ip);
	  }

	  public static BufferedImage makeImageTranslucent(BufferedImage source,
	      double alpha) {
	    BufferedImage target = new BufferedImage(source.getWidth(),
	        source.getHeight(), java.awt.Transparency.TRANSLUCENT);
	    // Get the images graphics
	    Graphics2D g = target.createGraphics();
	    // Set the Graphics composite to Alpha
	    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
	        (float) alpha));
	    // Draw the image into the prepared reciver image
	    g.drawImage(source, null, 0, 0);
	    // let go of all system resources in this Graphics
	    g.dispose();
	    // Return the image
	    return target;
	  }
	  
	  
	  ///##################### ANDERE VARIANTE
	  /* Hier soll die Farbe erhalten bleiben */
	  public static Image transformColorToTransparency(BufferedImage image, Color c1, Color c2)
	  {
	    // Primitive test, just an example
	    final int r1 = c1.getRed();
	    final int g1 = c1.getGreen();
	    final int b1 = c1.getBlue();
	    final int r2 = c2.getRed();
	    final int g2 = c2.getGreen();
	    final int b2 = c2.getBlue();
	    ImageFilter filter = new RGBImageFilter()
	    {
	      public final int filterRGB(int x, int y, int rgb)
	      {
	        int r = (rgb & 0xFF0000) >> 16;
	        int g = (rgb & 0xFF00) >> 8;
	        int b = rgb & 0xFF;
	        if (r >= r1 && r <= r2 &&
	            g >= g1 && g <= g2 &&
	            b >= b1 && b <= b2)
	        {
	          // Set fully transparent but keep color
	          return rgb & 0xFFFFFF;
	        }
	        return rgb;
	      }
	    };

	    ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
	      return Toolkit.getDefaultToolkit().createImage(ip);
	  }
	

	  /*ACHTUNG DIE FARBE BLEIBT NICHT ERHALTEN */
	  public static  Image transformGrayToTransparency(BufferedImage image)
	  {
	    ImageFilter filter = new RGBImageFilter()
	    {
	      public final int filterRGB(int x, int y, int rgb)
	      {
	        return (rgb << 8) & 0xFF000000;
	      }
	    };

	    ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
	    return Toolkit.getDefaultToolkit().createImage(ip);
	  }
	  
	  /*ACHTUNG DIE FARBE BLEIBT NICHT ERHALTEN */
	  public static  Image transformWhiteToTransparency(BufferedImage image)
	  {
	    ImageFilter filter = new RGBImageFilter()
	    {
	      public final int filterRGB(int x, int y, int rgb)
	      {
	        return (rgb << 8) & 0xFFFFFFFF;
	      }
	    };

	    ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
	    return Toolkit.getDefaultToolkit().createImage(ip);
	  }
}
