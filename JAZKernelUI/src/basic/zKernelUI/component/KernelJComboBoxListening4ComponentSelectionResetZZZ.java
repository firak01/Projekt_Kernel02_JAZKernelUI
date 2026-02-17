package basic.zKernelUI.component;

import javax.swing.JComboBox;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectLogZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.AbstractKernelLogZZZ;
import basic.zKernelUI.component.model.EventComponentSelectionResetZZZ;
import basic.zKernelUI.component.model.IListenerSelectionResetZZZ;
import basic.zKernel.IKernelZZZ;
import custom.zKernel.LogZZZ;

public abstract class KernelJComboBoxListening4ComponentSelectionResetZZZ  extends JComboBox implements IConstantZZZ, IObjectZZZ, IObjectLogZZZ, IKernelUserZZZ, IListenerSelectionResetZZZ{
	private IKernelZZZ objKernel;
	private LogZZZ objLog;
	private Object objItemInitial;
	boolean bObjectInitialSet=false;
	
	private EventComponentSelectionResetZZZ eventPrevious;
	
	public KernelJComboBoxListening4ComponentSelectionResetZZZ(IKernelZZZ objKernel, Object objItemInitial){
		super();
		this.objKernel = objKernel;
		this.objLog = objKernel.getLogObject();
		this.objItemInitial = objItemInitial; //Merke: Das kann noch nicht gesetzt werden, da noch gar keine Items vorhanden sind.
		
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComboBox#addItem(java.lang.Object)
	 * 
	 * Weil im Konstruktor schon mitzugeben ist, welches item markiert werden soll, wird hier nach dem eigentlichen Hinzuf�gen ggf. die Markierung durchgef�hrt.
	 */
	public void addItem(Object objItem){
		super.addItem(objItem);
		if(bObjectInitialSet==false){
			if(this.containsItem(this.getItemInitial())==true){
				this.setSelectedItem(this.getItemInitial());
				bObjectInitialSet=true;
			}			
		}
	}
	public Object getItemInitial(){
		return this.objItemInitial;
	}
	public boolean isItemInitialSetYet(){
		return this.bObjectInitialSet;
	}
	
	
	public final void doReset(EventComponentSelectionResetZZZ eventNew) {
		if(! eventNew.equals(this.getEventPrevious())){			
			String stemp = eventNew.getComponentText();
			this.setSelectedItem(stemp);
			
			this.doResetCustom(eventNew);
			
			this.setEventPrevious(eventNew);			
		}
	}
	
	/** Gibt true zur�ck, wenn in der ITemListe der JComboBox das gesuchte Objekt vorhanden ist
	* @param objItem
	* @return
	* 
	* lindhaueradmin; 06.02.2007 13:35:26
	 */
	public boolean containsItem(Object objItem){
		boolean bReturn = false;
		main:{
			int iItem = this.getItemCount();
			for(int icount = 0; icount <= iItem-1; icount++){
				if(this.getItemAt(icount).equals(this.getItemInitial())){
					bReturn = true;
					break main;
				}
			}		
		}//End main
		return bReturn;
	}
	

	public ExceptionZZZ getExceptionObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setExceptionObject(ExceptionZZZ objException) {
		// TODO Auto-generated method stub		
	}
	
	//aus IObjectLogZZZIKernelLogObjectUserZZZ, analog zu KernelKernelZZZ
	@Override
	public void logLineDate(String sLog) throws ExceptionZZZ {
		LogZZZ objLog = this.getLogObject();
		if(objLog==null) {
			String sTemp = LogZZZ.computeLineDate(this, sLog);
			System.out.println(sTemp);
		}else {
			objLog.writeLineDate(sLog);	
		}		
	}	

	public IKernelZZZ getKernelObject() {	
		return this.objKernel;
	}

	public void setKernelObject(IKernelZZZ objKernel) {
		this.objKernel = objKernel;
	}

	public LogZZZ getLogObject() {	
		return this.objLog;
	}

	public void setLogObject(LogZZZ objLog) {
		this.objLog = objLog;
	}
	public EventComponentSelectionResetZZZ getEventPrevious() {		
		return this.eventPrevious;
	}
	public void setEventPrevious(EventComponentSelectionResetZZZ eventSelectionResetNew) {
		this.eventPrevious = eventSelectionResetNew;
	}
	
	public abstract void doResetCustom(EventComponentSelectionResetZZZ eventSelectionResetNew);
}//END class

