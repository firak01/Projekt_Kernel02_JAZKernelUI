package basic.zKernelUI.component.model;

import java.awt.AWTEvent;
import java.awt.Event;
import java.util.EventObject;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;

public final class EventComponentSelectionResetZZZ extends EventObject{
	private String sComponentText;
	private int iId;
	
	/** In dem Konstruktor wird neben der ID dieses Events auch der identifizierende Name der neu gewählten Komponente übergeben.
	 * @param source
	 * @param iID
	 * @param sComponentItemText, z.B. für einen DirectoryJTree ist es der Pfad, für eine JCombobox der Name des ausgewählten Items 
	 */
	public EventComponentSelectionResetZZZ(Object source, int iID,  String sComponentItemText) {
		super(source);
		this.sComponentText = sComponentItemText;
		this.iId = iID;
	}
	
	public int getID(){
		return this.iId;
	}
	public String getComponentText(){
		return this.sComponentText;
	}
}
