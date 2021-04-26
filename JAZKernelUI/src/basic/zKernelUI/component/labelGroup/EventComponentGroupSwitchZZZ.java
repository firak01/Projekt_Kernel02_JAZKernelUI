package basic.zKernelUI.component.labelGroup;

import java.awt.AWTEvent;
import java.awt.Event;
import java.util.EventObject;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;

public final class EventComponentGroupSwitchZZZ extends EventObject{
	private int iId;
	private boolean bVisible;
	
	
	/** In dem Konstruktor wird neben der ID dieses Events auch der zu setztende Status (visable true/false) uebergeben
	 *  und die Gruppe, die dies betrifft. 
	 * @param source
	 * @param iID
	 * @param bVisable,  
	 */
	public EventComponentGroupSwitchZZZ(Object source, int iID, boolean bVisible) {
		super(source);
		this.iId = iID;
		this.bVisible = bVisible;
	}
	
	public int getID(){
		return this.iId;
	}
	public boolean getVisableToSet(){
		return this.bVisible;
	}
}
