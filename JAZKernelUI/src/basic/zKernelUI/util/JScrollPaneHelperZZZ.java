package basic.zKernelUI.util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class JScrollPaneHelperZZZ implements IConstantZZZ {
 
	/** Privatge Konstruktor, da nur static Methoden vorhanden sein sollen.
	 * 
	 * lindhaueradmin, 08.07.2013
	 */
	private JScrollPaneHelperZZZ(){
	}
	
	/**Ein ScrollPane zeigt nur einen Ausschnitt (den Viewport) an.
	 * Errechnet wird hier eine Koordinate, die das Scrollen und das Ändern der Fenstergröße berücksichtigt. 
	 * @param objScrollPane
	 * @param iX
	 * @return
	 * lindhaueradmin, 08.07.2013
	 */
	public static Point toWorldCoordinate(JScrollPane objScrollPane, Point objPointOnComponent)throws ExceptionZZZ{
		Point pointReturn = null;
		main:{
			   if(objScrollPane==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JScrollPane provided", iERROR_PROPERTY_MISSING, JScrollPaneHelperZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
			   }
			   if(objPointOnComponent==null){
					ExceptionZZZ ez = new ExceptionZZZ("No PointOnComponent provided", iERROR_PROPERTY_MISSING, JScrollPaneHelperZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
			   }
			   
			   //Wie stehen die ScrollBars?
			   int iHorizontalScrolled = objScrollPane.getHorizontalScrollBar().getValue();
			   int iVerticalScrolled = objScrollPane.getVerticalScrollBar().getValue();
			   
			   //Rechne den Point auf der Komponente um in einen Point auf dem Viewport.
			   JViewport objViewPort = objScrollPane.getViewport();
			   Point p4 = JScrollPaneHelperZZZ.getViewTranslatedPoint(objViewPort, objPointOnComponent);  //Punkt bezogen auf die View im JScrollPane.

			   //erweitere den umgerechneten Punkt um den gescrollten Wert
			   int iXNew = (int)p4.getX()+iHorizontalScrolled;
			   int iYNew = (int)p4.getY()+iVerticalScrolled;
			   p4.setLocation(iXNew, iYNew);
			   pointReturn = p4;	   
			   
		}//end main:
		return pointReturn;
	}
	
	/** Der Point ist schon in WorldCoordinates umgerechnet worden. 
	 * @param objScrollPane
	 * @param objPointOnComponent
	 * @return
	 * lindhaueradmin, 08.07.2013
	 */
	public static boolean isInTargetWorldCoordinate(JScrollPane objScrollPane, Point objPointInWorldCoordinate)throws ExceptionZZZ{
		main:{				
			 if(objScrollPane==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JScrollPane provided", iERROR_PROPERTY_MISSING, JScrollPaneHelperZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
			   }
			   if(objPointInWorldCoordinate==null){
					ExceptionZZZ ez = new ExceptionZZZ("No PointInWorldCoordinate provided", iERROR_PROPERTY_MISSING, JScrollPaneHelperZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
			   }
			
			   int iHorizontalMax = objScrollPane.getHorizontalScrollBar().getMaximum();
			   int iVerticalMax = objScrollPane.getVerticalScrollBar().getMaximum();

			   
			
			//Rectangle bounds = component.getBounds();
			//Rectangle bounds = objScrollPane.getViewport().getBounds(); //Kein großer Unterschied
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Scrollbar-Grenzen x: " + objPointInWorldCoordinate.getX() + "/ y: " + objPointInWorldCoordinate.getY());
		    System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Scrollbar-Grenzen x: " + iHorizontalMax + "/ y: " + iVerticalMax);   
			Rectangle bounds = new Rectangle(iHorizontalMax, iVerticalMax);
			
			return bounds.contains(objPointInWorldCoordinate);
		}//end main
	}
	
	
	/**Droppe in einen JScrollpane
	 * @param point
	 * @return
	 * lindhaueradmin, 08.07.2013
	 */
	public static Point getViewTranslatedPoint(JViewport objViewPort, Point point){
		Point p = (Point) point.clone();
		Point pReturn = objViewPort.toViewCoordinates(p);
		return pReturn;
	}
	
}
