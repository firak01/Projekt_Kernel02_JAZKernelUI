package basic.zKernelUI.component.labelGroup;

import java.util.ArrayList;

import javax.swing.JComponent;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import debug.zKernelUI.component.buttonSwitchLabelGroup.EventComponentGroupSwitchZZZ;

public class JComponentGroupZZZ extends KernelUseObjectZZZ implements IListenerComponentGroupSwitchZZZ { //,IEventBrokerSwitchComponentUserZZZ { //, IEventBrokerSwitchComponentUserZZZ{
	private ArrayList<JComponent>listaComponent=null;
	private String sAlias=null;
	private EventComponentGroupSwitchZZZ eventPrevious=null;
	
	public JComponentGroupZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);		
	}
	public JComponentGroupZZZ(IKernelZZZ objKernel,String sAlias) throws ExceptionZZZ {
		super(objKernel);
		JComponentGroupNew_(sAlias);
	}
	
	private boolean JComponentGroupNew_(String sAlias) {
		boolean bReturn = false;
		main:{
			this.setGroupAlias(sAlias);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public String getGroupAlias() {
		return this.sAlias;
	}
	public void setGroupAlias(String sAlias) {
		this.sAlias = sAlias;
	}
	
	public ArrayList<JComponent> getComponents(){
		if(this.listaComponent==null) {
			this.listaComponent=new ArrayList<JComponent>();
		}
		return this.listaComponent;
	}
	public void setComponents(ArrayList<JComponent>listaComponent) {
		this.listaComponent = listaComponent;
	}
	
	public void addComponent(JComponent component) {
		ArrayList<JComponent>listaComponent = this.getComponents();
		if(listaComponent!=null) {
			if(listaComponent.contains(component)) {
				//mach nix;
			}else {
				listaComponent.add(component);
			}
		}
	}
	
	
	//########## INTERFACES
	//+++ IListenerComponentGroupSwitchZZZ
	@Override
	public void doSwitch(EventComponentGroupSwitchZZZ eventComponentGroupSwitchNew) {
		//TODOGOON; //20210430 hole aus  dem Event die aktiv zu schaltende Gruppe.		
		//Für alle Komponenten der ArrayList:
		//Wenn der Gruppenalias dem hier verwendeten entspricht
		//schalte aktiv
		//ansonsten
		//schalte inaktiv
					
		System.out.println("XXXXXXXXXXX doSwitch der ComponentGroup '" + this.getGroupAlias() + "'");
		
		IListenerComponentGroupSwitchZZZ group4activeState = eventComponentGroupSwitchNew.getGroup();
		boolean bActiveState = eventComponentGroupSwitchNew.getComponentActiveState();
		String sGroupAlias = group4activeState.getGroupAlias();
		System.out.println("für Gruppe '" + sGroupAlias + "' gilt...");
		System.out.println("... der activeState= '" + bActiveState + "'");
		for(JComponent component : listaComponent) {
			component.setVisible(bActiveState);
		}				
		this.setEventPrevious(eventComponentGroupSwitchNew);
	}
	
	
	
	
	//### Interface IListener	
	@Override
	public EventComponentGroupSwitchZZZ getEventPrevious() {
		return this.eventPrevious;
	}
	@Override
	public void setEventPrevious(EventComponentGroupSwitchZZZ eventComponentGroupSwitchNew) {
		this.eventPrevious = eventComponentGroupSwitchNew;
	}
	@Override
	public void doSwitchCustom(EventComponentGroupSwitchZZZ eventComponentGroupSwitchNew) {
		// TODO Auto-generated method stub		
	}
	
	//### Interface IEventBrokerSwitchComponentUserZZZ
//	@Override
//	public KernelSenderComponentSwitchZZZ getSenderUsed() {
//		return this.sender;
//	}
//	@Override
//	public void setSenderUsed(KernelSenderComponentSwitchZZZ objEventSender) {
//		this.sender = objEventSender;
//	}
			
}
