package basic.zKernelUI.component.componentGroup;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;

public class JComponentGroupZZZ extends KernelUseObjectZZZ implements IListenerComponentGroupSwitchZZZ { //,IEventBrokerSwitchComponentUserZZZ { //, IEventBrokerSwitchComponentUserZZZ{
	private ArrayList<JComponent>listaComponent=null;
	private String sAlias=null;
	private EventComponentGroupSwitchZZZ eventPrevious=null;
	private boolean bAnyComponentAdded=false;
	
	public JComponentGroupZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);		
	}
	public JComponentGroupZZZ(IKernelZZZ objKernel,String sAlias) throws ExceptionZZZ {
		super(objKernel);
		JComponentGroupNew_(sAlias, null);
	}
	public JComponentGroupZZZ(IKernelZZZ objKernel, String sAlias, ArrayList<JComponent>listaComponent) throws ExceptionZZZ {
		super(objKernel);
		JComponentGroupNew_(sAlias, listaComponent);
	}
	
	private boolean JComponentGroupNew_(String sAlias, ArrayList<JComponent>listaComponent) {
		boolean bReturn = false;
		main:{
			this.setGroupAlias(sAlias);
			
			if(listaComponent!=null) {							
				for(JComponent componenttemp : listaComponent) {
					if(componenttemp!=null) {												
						this.addComponent(componenttemp);						
					}
				}	
				
			}			
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
				//mach nix, nur 1x hinzufügen;
			}else {
				listaComponent.add(component);
				this.hasAnyComponentAdded(true);
			}
		}
	}
	
	public boolean hasAnyComponentAdded() {
		return this.bAnyComponentAdded;
	}
	private void hasAnyComponentAdded(boolean bValue) {
		this.bAnyComponentAdded=bValue;
	}
	
	// Methoden über die Gruppe auf die Componenten durchzugreifen
	public void setVisible(boolean bVisible) {
		ArrayList<JComponent>listaComponent = this.getComponents();
		for(JComponent component : listaComponent) {
			component.setVisible(bVisible);
		}
	}
	
	//########## INTERFACES
	//+++ IListenerComponentGroupSwitchZZZ
	@Override
	public void doSwitch(EventComponentGroupSwitchZZZ eventComponentGroupSwitchNew) {
		//20210430 hole aus  dem Event die aktiv zu schaltende Gruppe.		
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
		System.out.println("... setze den activeState auf '" + bActiveState + "'");
		
		boolean bActiveStateUsed;
		String sGroupAliasUsed = this.getGroupAlias();
		if(sGroupAlias.equals(sGroupAliasUsed)) {
			bActiveStateUsed = bActiveState;
			
			TODOGOON; //20210513: Hier nicht nur die Sichtbarkeit steuern, sondern auch die Werte für die Komponenten der Gruppe aktualisieren.
            
			
		}else {
			bActiveStateUsed = !bActiveState;
			System.out.println("ABER für Gruppe '" + sGroupAliasUsed + "' gilt...");
			System.out.println("... setze den activeState auf '" + bActiveStateUsed + "'");			
		}		
		this.setVisible(bActiveStateUsed);			
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
