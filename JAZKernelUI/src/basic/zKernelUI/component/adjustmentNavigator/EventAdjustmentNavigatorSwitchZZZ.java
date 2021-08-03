package basic.zKernelUI.component.adjustmentNavigator;

import java.util.EventObject;


public final class EventAdjustmentNavigatorSwitchZZZ extends EventObject{	
	private int iId;
	private boolean bActiveState;
	private IListenerAdjustmentNavigatorSwitchZZZ group;
	private int iIndexInCollectionUsed;
	
	/** In dem Konstruktor wird neben der ID dieses Events auch der identifizierende Name der neu gew�hlten Komponente �bergeben.
	 * @param source
	 * @param iID
	 * @param sComponentItemText, z.B. f�r einen DirectoryJTree ist es der Pfad, f�r eine JCombobox der Name des ausgew�hlten Items 
	 */
	public EventAdjustmentNavigatorSwitchZZZ(Object source, int iID, IListenerAdjustmentNavigatorSwitchZZZ group, int iIndexInCollectionUsed, boolean bActiveState) {
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
	
	public IListenerAdjustmentNavigatorSwitchZZZ getGroup() {
		return this.group;
	}
	
	public int getIndexInCollection() {
		return this.iIndexInCollectionUsed;
	}
	
	
	
}
