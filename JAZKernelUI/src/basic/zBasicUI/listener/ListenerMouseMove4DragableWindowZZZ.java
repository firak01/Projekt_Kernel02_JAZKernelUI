/**
 * 
 */
package basic.zBasicUI.listener;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** Dieser Listerner wird als Adapter in die JDialog/JFrame bzw. deren JPanel-Komponenten der Kernel Klassen gebracht (z.B. KernelJDialogExtendedZZZ, und zwar im Konstruktor).
 *   Ziel ist es Drag/drop der Maus auf dieser Komponente zu "bemerken", um dann die End-Position der Maus zu bestimmen. Der JDialog/JFrame wird dann entsprechend neu gesetzt.
 * @author 0823
 *
 */
public class ListenerMouseMove4DragableWindowZZZ  implements MouseListener, MouseMotionListener{
	JComponent target;
	JFrame frame;
	JDialog dialog;
	
	Point start_drag;
	Point start_loc;
	
	public ListenerMouseMove4DragableWindowZZZ(JComponent target, JFrame frame){
		this.target = target;
		this.frame = frame;
	}
	
	public ListenerMouseMove4DragableWindowZZZ(JPanel panel, JDialog dialog){
		this.target = (JComponent) panel;
		this.dialog = dialog;
	} 
	
	public JFrame getFrame(){
		return this.frame;
	}
	
	public JDialog getDialog(){
		return this.dialog;
	}
	
	//#######################################################
	public Point getScreenLocation(MouseEvent e){
		Point cursor = e.getPoint();
		Point target_location = this.target.getLocationOnScreen();
		return new Point((int)(target_location.getX() + cursor.getX()), (int)(target_location.getY() + cursor.getY()));
	}
	
	
	//#####  INTERFACE #########################################
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		this.start_drag = this.getScreenLocation(e);
		if(this.getFrame()!=null){
			this.start_loc = this.getFrame().getLocation();
		}else if(this.getDialog()!=null){
			this.start_loc = this.getDialog().getLocation(); 
		}
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) {
		Point current = this.getScreenLocation(e);
		Point offset = new Point((int) current.getX() - (int) start_drag.getX(), (int) current.getY() - (int) start_drag.getY());
		
		if(this.getFrame()!= null){
			JFrame frame = this.getFrame();
			
			Point new_location = new Point((int)(this.start_loc.getX()+offset.getX()), (int) (this.start_loc.getY() + offset.getY()));
			
			frame.setLocation(new_location);
		}else if(this.getDialog()!= null){
			JDialog dialog = this.getDialog();
			
			Point new_location = new Point((int)(this.start_loc.getX()+offset.getX()), (int) (this.start_loc.getY() + offset.getY()));
			
			dialog.setLocation(new_location);
		}
	}
	


	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}