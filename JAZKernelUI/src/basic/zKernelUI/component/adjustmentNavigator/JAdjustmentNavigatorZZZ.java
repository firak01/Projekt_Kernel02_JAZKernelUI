package basic.zKernelUI.component.adjustmentNavigator;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;

public class JAdjustmentNavigatorZZZ extends KernelUseObjectZZZ implements IListenerAdjustmentNavigatorSwitchZZZ { //,IEventBrokerSwitchComponentUserZZZ { //, IEventBrokerSwitchComponentUserZZZ{
	private ArrayList<JComponent>listaComponent=null;
	private IPanelCascadedZZZ panelParent=null;
	private String sAlias=null;
	private String sTitle=null;
	private IModelAdjustmentNavigatorValueZZZ objValueProvider=null;
	private EventAdjustmentNavigatorSwitchZZZ eventPrevious=null;
	private boolean bAnyComponentAdded=false;
	
	public JAdjustmentNavigatorZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);		
	}
	public JAdjustmentNavigatorZZZ(IKernelZZZ objKernel,String sAlias, IPanelCascadedZZZ panelParent, String sTitle) throws ExceptionZZZ {
		super(objKernel);
		JComponentGroupNew_(sAlias, null, sTitle, panelParent, null);
	}
	public JAdjustmentNavigatorZZZ(IKernelZZZ objKernel, String sAlias, String sTitle, IPanelCascadedZZZ panelParent, ArrayList<JComponent>listaComponent) throws ExceptionZZZ {
		super(objKernel);
		JComponentGroupNew_(sAlias, null, sTitle, panelParent, listaComponent);
	}
	public JAdjustmentNavigatorZZZ(IKernelZZZ objKernel, String sAlias, IModelAdjustmentNavigatorValueZZZ objValueProvider, ArrayList<JComponent>listaComponent) throws ExceptionZZZ {
		super(objKernel);
		JComponentGroupNew_(sAlias, objValueProvider, null, null, listaComponent);
	}
		
	private boolean JComponentGroupNew_(String sAlias, IModelAdjustmentNavigatorValueZZZ objComponentValueProvider, String sTitle, IPanelCascadedZZZ panelParent, ArrayList<JComponent>listaComponent) {
		boolean bReturn = false;
		main:{
			this.setGroupAlias(sAlias);
			this.setComponentValueProvider(objComponentValueProvider);
			this.setGroupTitle(sTitle);				
			this.setPanelParent(panelParent);
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
	
	@Override
	public String getGroupAlias() {
		return this.sAlias;
	}
	
	@Override
	public void setGroupAlias(String sAlias) {
		this.sAlias = sAlias;
	}
	
	@Override
	public String getGroupTitle() {
		return this.sTitle;
	}
	
	@Override
	public void setGroupTitle(String sTitle) {
		this.sTitle = sTitle;
	}
	
	public IPanelCascadedZZZ getPanelParent() {
		return this.panelParent;
	}
	public void setPanelParent(IPanelCascadedZZZ panelParent) {
		this.panelParent = panelParent;
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
	
	
	
	//##### Methoden über die Gruppe auf die Componenten durchzugreifen
	public void setVisible(boolean bVisible) {
		ArrayList<JComponent>listaComponent = this.getComponents();
		for(JComponent component : listaComponent) {
			component.setVisible(bVisible);
		}
	}
	
	public boolean refreshValues(int iIndexUsedInCollection) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			HashMapIndexedZZZ<Integer,ArrayList<String>> hmValues = this.getComponentValuesCustom(); 
			if(hmValues==null) break main;
			if(hmValues.size()==0) break main;
									
			int iIndex=-1;
			ArrayList<JComponent>listaComponent = this.getComponents();
			for(JComponent component : listaComponent) {
				iIndex++;
				if(component instanceof JLabel) {					
					ArrayList<String> listaText = (ArrayList<String>) hmValues.getValue(iIndex);		
					
					String[]saParent=ArrayListZZZ.toStringArray(listaText);				
					String sHtml = StringArrayZZZ.asHtml(saParent);
					
					JLabel label = (JLabel)component;
					label.setText(sHtml);
				}				
			}
		}
		return bReturn;
	}
	
	//########## INTERFACES
	//+++ IListenerComponentGroupSwitchZZZ
	@Override
	public void doSwitch(EventAdjustmentNavigatorSwitchZZZ eventComponentGroupSwitchNew) throws ExceptionZZZ {
		//20210430 hole aus  dem Event die aktiv zu schaltende Gruppe.		
		//Für alle Komponenten der ArrayList:
		//Wenn der Gruppenalias dem hier verwendeten entspricht
		//schalte aktiv
		//ansonsten
		//schalte inaktiv
					
		System.out.println("XXXXXXXXXXX doSwitch der ComponentGroup '" + this.getGroupAlias() + "'");
		
		IListenerAdjustmentNavigatorSwitchZZZ group4activeState = eventComponentGroupSwitchNew.getGroup();
		boolean bActiveState = eventComponentGroupSwitchNew.getComponentActiveState();
		String sGroupAlias = group4activeState.getGroupAlias();
		System.out.println("für Gruppe '" + sGroupAlias + "' gilt...");
		System.out.println("... setze den activeState auf '" + bActiveState + "'");
		
		boolean bActiveStateUsed;
		String sGroupAliasUsed = this.getGroupAlias();
		if(sGroupAlias.equals(sGroupAliasUsed)) {
			bActiveStateUsed = bActiveState;			
			System.out.println("Das ist die aktuelle Gruppe.");				
		}else {
			bActiveStateUsed = !bActiveState;
			System.out.println("ABER für die aktuelle Gruppe '" + sGroupAliasUsed + "' gilt...");
			System.out.println("... setze den activeState auf '" + bActiveStateUsed + "'");			
		}		
		
		int iIndexUsedInCollection = eventComponentGroupSwitchNew.getIndexInCollection();	
		
		//20210513: Hier nicht nur die Sichtbarkeit steuern, sondern auch die Werte für die Komponenten der Gruppe aktualisieren.
		//z.B. kann es sein, dass seit der initialen Erstellung der Gruppe sich an den Panels etwas geändert hat.		        		
		this.refreshValues(iIndexUsedInCollection);
		this.setVisible(bActiveStateUsed);			
		this.setEventPrevious(eventComponentGroupSwitchNew);
	}
	
	
	
	
	//### Interface IListener	
	@Override
	public EventAdjustmentNavigatorSwitchZZZ getEventPrevious() {
		return this.eventPrevious;
	}
	@Override
	public void setEventPrevious(EventAdjustmentNavigatorSwitchZZZ eventComponentGroupSwitchNew) {
		this.eventPrevious = eventComponentGroupSwitchNew;
	}
	@Override
	public void doSwitchCustom(EventAdjustmentNavigatorSwitchZZZ eventComponentGroupSwitchNew) {
		// TODO Auto-generated method stub		
	}
	@Override
	public HashMapIndexedZZZ<Integer, ArrayList<String>> getComponentValuesCustom() throws ExceptionZZZ {
		IModelAdjustmentNavigatorValueZZZ objValueProvider = this.getComponentValueProvider();
		if(objValueProvider!=null) {
			HashMapIndexedZZZ<Integer, ArrayList<String>> hmValuesCustom = objValueProvider.getNavigatorValues();
			return hmValuesCustom;
		}else {
			return null;
		}
	}
	@Override
	public IModelAdjustmentNavigatorValueZZZ getComponentValueProvider() {
		return this.objValueProvider;
	}
	@Override
	public void setComponentValueProvider(IModelAdjustmentNavigatorValueZZZ objComponentValueProvider) {
		this.objValueProvider = objComponentValueProvider;
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
