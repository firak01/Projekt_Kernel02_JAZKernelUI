package basic.zKernelUI.component;

import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelLogZZZ;
import custom.zKernel.LogZZZ;


import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernelUI.component.model.EventComponentSelectionResetZZZ;
import basic.zKernelUI.component.model.IListenerSelectionResetZZZ;

public abstract class KernelJListListening4ComponentSelectionResetZZZ extends JList implements IConstantZZZ, IObjectZZZ, IKernelUserZZZ, IListenerSelectionResetZZZ{
	IKernelZZZ objKernel = null;
	LogZZZ objLog = null;
	
	private EventComponentSelectionResetZZZ eventPrevious;
	
	public KernelJListListening4ComponentSelectionResetZZZ(){
		super();
	}
	public KernelJListListening4ComponentSelectionResetZZZ(ListModel model){
		super(model);
	}
	public KernelJListListening4ComponentSelectionResetZZZ(IKernelZZZ objKernel, DefaultListModel model){
		super(model);
		this.objKernel = objKernel;
		this.objLog = objKernel.getLogObject();
	}
	
	public final void doReset(EventComponentSelectionResetZZZ eventNew){
		if(! eventNew.equals(this.getEventPrevious())){
			DefaultListModel modelList = (DefaultListModel)  this.getModel();  //Das muss der JList im Konstruktor mitgegeben worden sein.
			modelList.clear();	
			
			this.doResetCustom(eventNew); //Hier wird dann ggf. ein Worker Thread gestartet, der aufwendige Listen f�llen soll
			
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
	
	//aus IKernelLogObjectUserZZZ, analog zu KernelKernelZZZ
	@Override
	public void logLineDate(String sLog) throws ExceptionZZZ {
		LogZZZ objLog = this.getLogObject();
		if(objLog==null) {
			String sTemp = AbstractKernelLogZZZ.computeLineDate(sLog);
			System.out.println(sTemp);
		}else {
			objLog.WriteLineDate(sLog);
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
	public abstract void doResetCustom(EventComponentSelectionResetZZZ eventSelectionResetNew);
	
	public EventComponentSelectionResetZZZ getEventPrevious() {	
		return this.eventPrevious;
	}
	public void setEventPrevious(EventComponentSelectionResetZZZ eventSelectionResetNew) {
		this.eventPrevious = eventSelectionResetNew;
	}

}
