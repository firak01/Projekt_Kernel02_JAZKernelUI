package basic.zKernelUI.component.navigator;

import java.util.EventObject;


public final class EventNavigatorElementSwitchZZZ extends EventObject implements Comparable<EventNavigatorElementSwitchZZZ>{	
	private int iId;
	private boolean bActiveState;
	private String sNavigatorElementAlias;
	
	/** In dem Konstruktor wird neben der ID dieses Events auch der identifizierende Name der neu gewaehlten Komponente uebergeben.
	 * @param source
	 * @param iID
	 */
	public EventNavigatorElementSwitchZZZ(Object source, int iID, String sNavigatorElementAlias, boolean bActiveState) {
		super(source);
		this.bActiveState = bActiveState;
		this.iId = iID;
		this.sNavigatorElementAlias = sNavigatorElementAlias;
	}
	
	public int getID(){
		return this.iId;
	}
	public boolean getComponentActiveState(){
		return this.bActiveState;
	}
	
	public String getNavigatorElementAlias() {
		return this.sNavigatorElementAlias;
	}

	
	@Override
	public int compareTo(EventNavigatorElementSwitchZZZ o) {
		//Das macht lediglich .sort funktionsfähig und wird nicht bei .equals(...) verwendet.
		int iReturn = 0;
		main:{
			if(o==null)break main;
			
			String sAliasToCompare = o.getNavigatorElementAlias();
			String sAlias = this.getNavigatorElementAlias();
			
			if(sAlias.equals(sAliasToCompare)) iReturn = 1;
		}
		return iReturn;
	}
	
	 /**
	   * Define equality of state.
	   */
	   @Override 
	   public boolean equals(Object aThat) {
	     if (this == aThat) return true;
	     if (!(aThat instanceof EventNavigatorElementSwitchZZZ)) return false;
	     EventNavigatorElementSwitchZZZ that = (EventNavigatorElementSwitchZZZ)aThat;
	     if(!that.getNavigatorElementAlias().equals(((EventNavigatorElementSwitchZZZ) aThat).getNavigatorElementAlias())){
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
	     return this.getNavigatorElementAlias().hashCode();     
	   }

	
	
	
}
