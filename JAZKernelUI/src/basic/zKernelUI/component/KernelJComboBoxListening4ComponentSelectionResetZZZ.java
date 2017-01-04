package basic.zKernelUI.component;

import javax.swing.JComboBox;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernelUI.component.model.EventComponentSelectionResetZZZ;
import basic.zKernelUI.component.model.IListenerSelectionResetZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;

public abstract class KernelJComboBoxListening4ComponentSelectionResetZZZ  extends JComboBox implements IConstantZZZ, IObjectZZZ, IKernelUserZZZ, IListenerSelectionResetZZZ{
	private KernelZZZ objKernel;
	private LogZZZ objLog;
	private Object objItemInitial;
	boolean bObjectInitialSet=false;
	
	private EventComponentSelectionResetZZZ eventPrevious;
	
	public KernelJComboBoxListening4ComponentSelectionResetZZZ(KernelZZZ objKernel, Object objItemInitial){
		super();
		this.objKernel = objKernel;
		this.objLog = objKernel.getLogObject();
		this.objItemInitial = objItemInitial; //Merke: Das kann noch nicht gesetzt werden, da noch gar keine Items vorhanden sind.
		
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComboBox#addItem(java.lang.Object)
	 * 
	 * Weil im Konstruktor schon mitzugeben ist, welches item markiert werden soll, wird hier nach dem eigentlichen Hinzufügen ggf. die Markierung durchgeführt.
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
	
	/** Gibt true zurück, wenn in der ITemListe der JComboBox das gesuchte Objekt vorhanden ist
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
	public EventComponentSelectionResetZZZ getEventPrevious() {		
		return this.eventPrevious;
	}
	public void setEventPrevious(EventComponentSelectionResetZZZ eventSelectionResetNew) {
		this.eventPrevious = eventSelectionResetNew;
	}
	
	public abstract void doResetCustom(EventComponentSelectionResetZZZ eventSelectionResetNew);
}//END class

