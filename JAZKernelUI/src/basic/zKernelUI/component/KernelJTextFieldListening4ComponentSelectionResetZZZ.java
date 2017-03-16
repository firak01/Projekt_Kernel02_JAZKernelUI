package basic.zKernelUI.component;

import java.util.HashMap;

import javax.swing.JTextField;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IFlagZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernelUI.component.model.EventComponentSelectionResetZZZ;
import basic.zKernelUI.component.model.IListenerSelectionResetZZZ;
import custom.zKernel.LogZZZ;

public abstract class KernelJTextFieldListening4ComponentSelectionResetZZZ extends JTextField implements IObjectZZZ, IFlagZZZ, IKernelUserZZZ, IListenerSelectionResetZZZ{
	private KernelZZZ objKernel;
	private LogZZZ objLog;
	
	private EventComponentSelectionResetZZZ eventPrevious;
	private boolean bFlagUseEventResetDefault=false;
	private boolean bFlagDebug=false;
	private boolean bFlagInit=false;
	
	public KernelJTextFieldListening4ComponentSelectionResetZZZ(KernelZZZ objKernel, String sTextInitial){
		super(sTextInitial);
		this.objKernel = objKernel;
		this.objLog = objKernel.getLogObject();			
	}
	
	public final void doReset(EventComponentSelectionResetZZZ eventNew) {
		if(! eventNew.equals(this.getEventPrevious())){
			if(this.getFlag("useEventResetDefault")==true){
				String stemp = eventNew.getComponentText();
				this.setText(stemp);
			}
			
			this.doResetCustom(eventNew);
			
			this.setEventPrevious(eventNew);
		}
	}

	public ExceptionZZZ getExceptionObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setExceptionObject(ExceptionZZZ objException) {
		// TODO Auto-generated method stub		
	}

	public KernelZZZ getKernelObject() {		
		return this.objKernel;
	}

	public void setKernelObject(KernelZZZ objKernel) {
		this.objKernel = objKernel;
	}

	public LogZZZ getLogObject() {		
		return this.objLog;
	}

	public void setLogObject(LogZZZ objLog) {
		this.objLog = objLog;
	}

	public abstract void doResetCustom(EventComponentSelectionResetZZZ eventSelectionResetNew);

	public EventComponentSelectionResetZZZ getEventPrevious() {		
		return this.eventPrevious;
	}

	public void setEventPrevious(EventComponentSelectionResetZZZ eventSelectionResetNew) {
		this.eventPrevious = eventSelectionResetNew;
	}
	

	/* (non-Javadoc)
	 * @see basic.zBasic.IFunctionZZZ#getFlag(java.lang.String)
	 */
	public boolean getFlag(String sFlagName) {
		boolean bFunction = false;
	main:{
		if(sFlagName == null) break main;
		if(sFlagName.equals("")) break main;
		
		// hier keine Superclass aufrufen, ist ja nicht ObjectZZZ
		//bFunction = super.getFlag(sFlagName);
		//if(bFunction == true) break main;
		
		// Die Flags dieser Klasse setzen
		String stemp = sFlagName.toLowerCase();
		if(stemp.equals("debug")){
			bFunction = this.bFlagDebug;
			break main;
		}else if(stemp.equals("init")){
			bFunction = this.bFlagInit;
			break main;
		}else if(stemp.equals("useeventresetdefault")){
			bFunction = this.bFlagUseEventResetDefault;
			break main;
		}else{
			bFunction = false;
		}		
	}	// end main:
	
	return bFunction;	
	}

	/** Function can set the flags of this class or the super-class.
	 * The following new flags are supported:
	 * --- debug
 * @see basic.zBasic.IFunctionZZZ_loesch#setFlag(java.lang.String, boolean)
 */
public boolean setFlag(String sFlagName, boolean bFlagValue){
	boolean bFunction = false;
	main:{
		if(StringZZZ.isEmpty(sFlagName)) break main;
	
		// hier keine Superclass aufrufen, ist ja nicht von ObjectZZZ erbend
		// bFunction = super.setFlag(sFlagName, bFlagValue);
		// if(bFunction == true) break main;
		
		// Die Flags dieser Klasse setzen
		String stemp = sFlagName.toLowerCase();
		if(stemp.equals("debug")){
			this.bFlagDebug = bFlagValue;
			bFunction = true;                            //durch diesen return wert kann man "reflexiv" ermitteln, ob es in dem ganzen hierarchie-strang das flag �berhaupt gibt !!!
			break main;
		}else if(stemp.equals("init")){
			this.bFlagInit = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("useeventresetdefault")){
			this.bFlagUseEventResetDefault = bFlagValue;
			bFunction = true;
			break main;
		}else{
			bFunction = false;
		}	
		
	}	// end main:
	
	return bFunction;	
}

public final boolean proofFlagExists(String sFlagName){
	//TODO: ausformulieren, als gemeing�ltigen Algorithmus
	return false;
}

	//20170308: Enum FLAGZ nutzen
	@Override
	public HashMap<String, Boolean> getHashMapFlagZ() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean proofFlagZExists(String sFlagName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setFlagZ(String sFlagName, boolean bFlagValue)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getFlagZ(String sFlagName) {
		// TODO Auto-generated method stub
		return false;
	}

}
