package debug.zKernelUI.component.buttonSwitchLabelGroup;

import java.util.EventObject;

import basic.zKernelUI.component.labelGroup.IListenerComponentGroupSwitchZZZ;


public final class EventComponentGroupSwitchZZZ extends EventObject{	
	private int iId;
	private boolean bActiveState;
	private IListenerComponentGroupSwitchZZZ group;
	
	/** In dem Konstruktor wird neben der ID dieses Events auch der identifizierende Name der neu gew�hlten Komponente �bergeben.
	 * @param source
	 * @param iID
	 * @param sComponentItemText, z.B. f�r einen DirectoryJTree ist es der Pfad, f�r eine JCombobox der Name des ausgew�hlten Items 
	 */
	public EventComponentGroupSwitchZZZ(Object source, int iID, IListenerComponentGroupSwitchZZZ group, boolean bActiveState) {
		super(source);
		this.bActiveState = bActiveState;
		this.iId = iID;
		this.group = group;
	}
	
	public int getID(){
		return this.iId;
	}
	public boolean getComponentActiveState(){
		return this.bActiveState;
	}
	
	public IListenerComponentGroupSwitchZZZ getGroup() {
		return this.group;
	}
	
}
