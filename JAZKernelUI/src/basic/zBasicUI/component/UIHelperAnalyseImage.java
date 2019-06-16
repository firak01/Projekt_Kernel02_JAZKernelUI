package basic.zBasicUI.component;

import java.awt.image.BufferedImage;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class UIHelperAnalyseImage {
	/**Analyse des übergebenen Bildes. Hier werden alle Pixel (nach Zeile und Spalte) in Schleifen durchlaufen.
	 * Neben der RGB Angabe wird auch die Transparenz ausgegeben. 
	 * @throws ExceptionZZZ */
	  
	public static void debugPrintImagePixelData(BufferedImage img, boolean bIncludeTransparency) throws ExceptionZZZ{
		
		main:{
		check:{
			if(img==null){
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Kein ImageObjekt übergeben.");
				break main;				
			}
		}//end check:

		int[][] iaaPixelData = iaaPixelData = UIHelperAnalyseImage.getImagePixelDataRGB(img, bIncludeTransparency);//Den Unterschied macht das zurückgegbene interne Array aus.
		int iCounter = 0;
		int iRGB; String sRGB;
		if(bIncludeTransparency){
		     for(int x = 0; x < img.getWidth(); x++){
			     for(int y = 0; y < img.getHeight(); y++){
			    	 iRGB = iaaPixelData[iCounter][0];			    	 
			    	 String sT = StringZZZ.right("00" + iRGB,3);
			    	 
			    	 iRGB = iaaPixelData[iCounter][1];			    	 
			    	 String sR = StringZZZ.right("00" + iRGB,3);
			    	 
			    	 iRGB = iaaPixelData[iCounter][2];			    	 
			    	 String sG = StringZZZ.right("00" + iRGB,3);
			    	
			    	 iRGB = iaaPixelData[iCounter][3];			    	 
			    	 String sB = StringZZZ.right("00" + iRGB,3);
			    	 
			 		sRGB = "Transparenz: " + sT + " | RGB: " + sR + " " + sG + " " + sB;		    	 	
		         	System.out.println("Breite/Höhe gesamt:"  + img.getWidth() + "/" + img.getHeight() + " | X/Y = " + x + "/ " + y + " hat " + sRGB);		         					           
		         }
			     iCounter++;
		     }
		}else{
			 for(int x = 0; x < img.getWidth(); x++){
			     for(int y = 0; y < img.getHeight(); y++){			    	
			    	 iRGB = iaaPixelData[iCounter][0];			    	 
			    	 String sR = StringZZZ.right("00" + iRGB,3);
			    	 
			    	 iRGB = iaaPixelData[iCounter][1];			    	 
			    	 String sG = StringZZZ.right("00" + iRGB,3);
			    	
			    	 iRGB = iaaPixelData[iCounter][2];			    	 
			    	 String sB = StringZZZ.right("00" + iRGB,3);
			    	 
			 		sRGB = "RGB: " + sR + " " + sG + " " + sB;		    	 	
		         	System.out.println("Breite/Höhe gesamt:"  + img.getWidth() + "/" + img.getHeight() + " | X/Y = " + x + "/ " + y + " hat " + sRGB);		         					           
		         }
			     iCounter++;
		     }
		}		 			
		}//end main:
	
	}
	
	public static int[][] getImagePixelDataRGB(BufferedImage img, boolean bIncludeTransparency) throws ExceptionZZZ{
		int[][] iaaReturn = null;
		main:{
		check:{
			if(img==null){
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Kein ImageObjekt übergeben.");
				break main;				
			}
		}//end check:
		
		int[] rgb;
		int counter = 0;
		if(bIncludeTransparency){
			iaaReturn = new int[img.getHeight() * img.getWidth()][4];//4 weil jetzt noch der ALPHA Wert für die Transparenz dazugekommen ist.
			for(int x = 0; x < img.getWidth(); x++){
		    	 for(int y = 0; y < img.getHeight(); y++){	         
		         	 //System.out.println("Höhe " + img.getHeight() + " | Breite "  + img.getWidth() + "( X = " + x + " | y= " + y +  ")");
		             rgb = getPixelDataRGBWithTransparency(img, x, y);		
		             for(int k = 0; k < rgb.length; k++){
		            	 iaaReturn[counter][k] = rgb[k];
		             }		
		             counter++;
		         }
		     }
		}else{
			iaaReturn = new int[img.getHeight() * img.getWidth()][3];//4 weil jetzt noch der ALPHA Wert für die Transparenz dazugekommen ist.		
		     for(int x = 0; x < img.getWidth(); x++){
		    	 for(int y = 0; y < img.getHeight(); y++){	         
		         	 //System.out.println("Höhe " + img.getHeight() + " | Breite "  + img.getWidth() + "( X = " + x + " | y= " + y +  ")");
		             rgb = getPixelDataRGB(img, x, y);		
		             for(int k = 0; k < rgb.length; k++){
		            	 iaaReturn[counter][k] = rgb[k];
		             }		
		             counter++;
		         }
		     }
		}	     		    			
		}//end main:
		return iaaReturn;		
	}
	
	public static int[] getPixelDataRGBWithTransparency(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);


		//Das Original der im Interet gefundenen Lösung erweitert um ALPHA gemäß:
		//https://gamedev.stackexchange.com/questions/88305/get-color-array-with-alpha-for-an-image-in-java
		int rgb[] = new int[] {
				(argb >> 24) & 0xFF,//alpha (d.h. für die Transparenz)
			    (argb >> 16) & 0xff, //red
			    (argb >>  8) & 0xff, //green
			    (argb      ) & 0xff  //blue			     			   
			};

		String sT = StringZZZ.right("00" + rgb[0],3);		
		//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Transparenz: " + sT + " | rgb: " + rgb[1] + " " + rgb[2] + " " + rgb[3]);
		return rgb;
		}
	
	public static int[] getPixelDataRGB(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);

		// Das Original der im Internet gefundenen Lösung, basierend auf Bitoperation:
		int rgb[] = new int[] {
		    (argb >> 16) & 0xff, //red
		    (argb >>  8) & 0xff, //green
		    (argb      ) & 0xff  //blue
		};
			
		//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": rgb: " + rgb[0] + " " + rgb[1] + " " + rgb[2]);
		return rgb;
		}
}
