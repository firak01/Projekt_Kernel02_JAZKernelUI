package basic.zKernelUI.component.navigator;

import java.util.EventObject;


public final class EventNavigatorElementSwitchZZZ extends EventObject{	
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
	
	
	
}
