package basic.zKernelUI.component.navigator;

import java.util.EventObject;


public final class EventNavigatorElementSwitchZZZ extends EventObject{	
	private int iId;
	private boolean bActiveState;
	private IListenerNavigatorElementSwitchZZZ group;
	private int iIndexInCollectionUsed;
	
	/** In dem Konstruktor wird neben der ID dieses Events auch der identifizierende Name der neu gew�hlten Komponente �bergeben.
	 * @param source
	 * @param iID
	 * @param sComponentItemText, z.B. f�r einen DirectoryJTree ist es der Pfad, f�r eine JCombobox der Name des ausgew�hlten Items 
	 */
	public EventNavigatorElementSwitchZZZ(Object source, int iID, IListenerNavigatorElementSwitchZZZ group, int iIndexInCollectionUsed, boolean bActiveState) {
		super(source);
		this.bActiveState = bActiveState;
		this.iId = iID;
		this.group = group;
		this.iIndexInCollectionUsed = iIndexInCollectionUsed;
	}
	
	public int getID(){
		return this.iId;
	}
	public boolean getComponentActiveState(){
		return this.bActiveState;
	}
	
	public IListenerNavigatorElementSwitchZZZ getGroup() {
		return this.group;
	}
	
	public int getIndexInCollection() {
		return this.iIndexInCollectionUsed;
	}
	
	
	
}
