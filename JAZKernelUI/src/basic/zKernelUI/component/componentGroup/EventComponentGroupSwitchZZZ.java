package basic.zKernelUI.component.componentGroup;

import java.util.EventObject;

import basic.zKernelUI.component.navigator.EventNavigatorElementSwitchZZZ;


public final class EventComponentGroupSwitchZZZ extends EventObject implements Comparable<EventComponentGroupSwitchZZZ>{	
	private int iId;
	private boolean bActiveState;
	private IListenerComponentGroupSwitchZZZ group;
	private int iIndexInCollectionUsed;
	
	/** In dem Konstruktor wird neben der ID dieses Events auch der identifizierende Name der neu gew�hlten Komponente �bergeben.
	 * @param source
	 * @param iID
	 * @param sComponentItemText, z.B. f�r einen DirectoryJTree ist es der Pfad, f�r eine JCombobox der Name des ausgew�hlten Items 
	 */
	public EventComponentGroupSwitchZZZ(Object source, int iID, IListenerComponentGroupSwitchZZZ group, int iIndexInCollectionUsed, boolean bActiveState) {
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
	
	public IListenerComponentGroupSwitchZZZ getGroup() {
		return this.group;
	}
	
	public int getIndexInCollection() {
		return this.iIndexInCollectionUsed;
	}
	
	@Override
	public int compareTo(EventComponentGroupSwitchZZZ o) {
		//Das macht lediglich .sort funktionsfähig und wird nicht bei .equals(...) verwendet.
		int iReturn = 0;
		main:{
			if(o==null)break main;
			
			int itoCompare = o.getIndexInCollection();
			int i = this.getIndexInCollection();
			
			if(i==itoCompare) iReturn = 1;
		}
		return iReturn;
	}
	
	 /**
	   * Define equality of state.
	   */
	   @Override 
	   public boolean equals(Object aThat) {
	     if (this == aThat) return true;
	     if (!(aThat instanceof EventComponentGroupSwitchZZZ)) return false;
	     EventComponentGroupSwitchZZZ that = (EventComponentGroupSwitchZZZ)aThat;
	     //if(!that.getIndexInCollection().equals(((EventComponentGroupSwitchZZZ) aThat).getIndexInCollection())){
	     if(that.getIndexInCollection() ==((EventComponentGroupSwitchZZZ) aThat).getIndexInCollection()){
	    	 return false;
	     }
	     
//	     for(int i = 0; i < this.getSigFields().length; ++i){
//	       if (!Objects.equals(this.getSigFields()[i], that.getSigFields()[i])){
//	         return false;
//	       }
//	     }
	     return true;     
	   }

	   /**
	   * A class that overrides equals must also override hashCode.
	   */
	   @Override 
	   public int hashCode() {
		   int i = this.getIndexInCollection();
		   Integer inti = new Integer(i);				   
		   return inti.hashCode();     
	   }
}
