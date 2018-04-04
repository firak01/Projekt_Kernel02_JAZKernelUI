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

public class UIHelperTransparencyRange {
	

	 
	  
	  
	  ///##################### ANDERE VARIANTE
	  /* Hier soll die Farbe erhalten bleiben */
	  public static Image transformColorRangeToTransparency(BufferedImage image, Color cStart, int iMinus, int iPlus, Color cTarget){
		 Image objImageReturn = null;
		 main:{ 
			 check:{
			 	if(image==null) break main;
		 	}
		 		
		    final int rStart = cStart.getRed();
		    final int gStart = cStart.getGreen();
		    final int bStart = cStart.getBlue();
		    
		    int rLow = rStart - iMinus;
		    if(rLow < 0) rLow = 0;		    
		    int rHigh = rStart + iPlus;
		    if(rHigh >255) rHigh = 255;
		    int gLow = gStart - iMinus;
		    if(gLow < 0) gLow = 0;
		    int gHigh = gStart + iPlus;
		    if(gHigh > 255) gHigh = 255;		    		    		    
		    int bLow = bStart - iMinus;
		    if(bLow <0) bLow = 0;
		    int bHigh = bStart + iPlus;
		    if(bHigh > 255) bHigh = 255;
		    
		    Image objImageTemp=null;
		    BufferedImage objBufferedImageTemp=image;
		    for(int r = rLow; r <= rHigh; r++){
		    	for(int g = gLow; g <= gHigh; g++){
		    		for(int b = bLow; b <= bHigh; b++){
		    			
		    			Color colorTemp = new Color(r,g,b);
		    			objImageTemp = UIHelperTransparency.transformColorToTransparency(objBufferedImageTemp, colorTemp, cTarget);
		    			objBufferedImageTemp = UIHelper.toBufferedImage(objImageTemp);
		    		}		    	
		    	}		    	
		    }
		    objImageReturn = objImageTemp;		   
		 }
		 return objImageReturn;
	  }		  
}
