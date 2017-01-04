/**
 * 
 */
package basic.zBasicUI.adapter;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**Klasse erweitert den ComponentAdapter und kann in JFrame und JDialog - Klassen als 'ComponentListener' eingebunden werden (z.B. im Kernel JDialogExtended, im Konstruktor).
 *  Wenn dieser Adapter eingebunden wird, dann wird jedes Verschieben auf dem Bilschirm "analyisiert" und es wird beim Überschreiten der Bildschirmgrenzen
 *  die Component wieder nach innerhalb der Bildschirmgrenzen verschoben.
 * @author 0823
 *
 */
public class AdapterJComponent4ScreenSnapperZZZ extends ComponentAdapter {
	private boolean locked = false;
	private int snap_distance = 50;
	
	public void componentMoved(ComponentEvent evt){
		//System.out.println("Event component moved happend.");
		if(locked) return;  //dadurch kann dieser event zwichen der "Bewegung duch den Anwender" und der "Bewegung duch diesen event selbst" unterscheiden
		
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int nx = evt.getComponent().getX();
		int ny = evt.getComponent().getY();
		
		//top
		if(ny < 0 + snap_distance){
			ny=0;
		}
		
		//left
		if(nx < 0 + snap_distance){
			nx = 0;
		}
				
		//right
		if(nx > size.getWidth() - evt.getComponent().getWidth() - snap_distance){
			nx = (int) size.getWidth()-evt.getComponent().getWidth();
			
		}
		
		//bottom
		if(ny > size.getHeight() -  evt.getComponent().getHeight() - snap_distance){
			ny = (int) size.getHeight()-evt.getComponent().getHeight();				
		}
		
		//make sure we don´t get into a recursive loop when "set location" generates more events
		locked = true;
		
		evt.getComponent().setLocation(nx, ny);
		
		locked = false;
		
	}
}
