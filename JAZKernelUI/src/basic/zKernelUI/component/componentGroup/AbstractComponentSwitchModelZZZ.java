package basic.zKernelUI.component.componentGroup;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernelUI.component.IComponentValueModelZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.model.ComponentModelHelperZZZ;
import debug.zKernelUI.component.buttonSwitchLabelGroup.Row2ModelZZZ;

public abstract class AbstractComponentSwitchModelZZZ extends KernelUseObjectZZZ  implements IComponentValueModelZZZ{
	private String sTitle=null;
	private KernelJPanelCascadedZZZ panelParent;
	private int iIndexInCollection=-1;	
	HashMapIndexedZZZ<Integer,ArrayList<JComponent>>hmComponent=null; //Die HashMap mit dem Index für die Gruppe und der ArrayList der Komponenten der Gruppe
		
	public AbstractComponentSwitchModelZZZ() {		
		super();
		AbstractComponentSwitchModelNew_(null, null, -1);
	}
	
	/** Konstruktor für eine GroupCollection, die kein Modell verwendet.
	 *  Die einzelnen Gruppen werden explizit hinzugefügt:
	 *  Z.B.
	 *  JComponentGroupZZZ group02_01 = new JComponentGroupZZZ(objKernel, "ZWEI", "Title: DebugGroup02", this, listaComponent_01);
															
		//### Die Gruppen in einer Collection zusammenfassen
		JComponentGroupCollectionZZZ groupc_01 = new JComponentGroupCollectionZZZ(objKernel);
		groupc_01.add(group01_01);
	 * @param objKernel
	 * @throws ExceptionZZZ
	 * 19.05.2021, 08:52:40, Fritz Lindhauer
	 */
	public AbstractComponentSwitchModelZZZ(IKernelZZZ objKernel) throws ComponentGroupModelExceptionZZZ, ExceptionZZZ {		
		super(objKernel);
		AbstractComponentSwitchModelNew_(null, null, -1);
	}
	
	
	/** Konstruktor für das Model über die gesamte Collection aller ComponentGroups
	 * @param objKernel
	 * @param sTitle
	 * @param panelParent
	 * @throws ExceptionZZZ
	 * 19.05.2021, 08:49:03, Fritz Lindhauer
	 */
	public AbstractComponentSwitchModelZZZ(IKernelZZZ objKernel, String sTitle, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ, ComponentGroupModelExceptionZZZ {
		super(objKernel);
		AbstractComponentSwitchModelNew_(sTitle, panelParent, -1);
	}
	
	/** Konstruktor für das Model einer ComponentGroup, an der per Index vorgegebenen Stelle der Collection
	 * @param objKernel
	 * @param sTitle
	 * @param panelParent
	 * @throws ExceptionZZZ
	 * 19.05.2021, 08:49:03, Fritz Lindhauer
	 */
	public AbstractComponentSwitchModelZZZ(IKernelZZZ objKernel, String sTitle, KernelJPanelCascadedZZZ panelParent, int iIndexInCollection) throws ExceptionZZZ, ComponentGroupModelExceptionZZZ {
		super(objKernel);
		AbstractComponentSwitchModelNew_(sTitle, panelParent, iIndexInCollection);
	}

	private void AbstractComponentSwitchModelNew_(String sTitle, KernelJPanelCascadedZZZ panelParent, int iIndexInCollection) {
		this.sTitle = sTitle;
		this.panelParent = panelParent;
		this.iIndexInCollection = iIndexInCollection;
	}
		
	//GETTER / SETTER
	public String getTitle() {
		return this.sTitle;
	}
	public KernelJPanelCascadedZZZ getPanelParent() {
		return this.panelParent;
	}
	public int getIndexInCollection() {
		return this.iIndexInCollection;
	}
	
	//+++ Interface IComponentValueProviderZZZZ
	@Override
	public HashMapIndexedZZZ<Integer, ArrayList<String>> getComponentValues()  throws ExceptionZZZ{
		String sTitle = this.getTitle();
		KernelJPanelCascadedZZZ panelParent = this.getPanelParent();
		int iIndexInCollection = this.getIndexInCollection();
		return this.createValuesText(sTitle, panelParent, iIndexInCollection);
	}
			
	//##############################
	//Im Fall einer GroupCollection, die auf einem Modell basiert, wird ggfs. die HashMap erst erzeugt.
	//Im Fall einer GroupCollection, die nicht auf einem Modell basiert wird die HashMap beim erstmaligen Hinzufügen einer Gruppe zur Collection erzeugt.
	//                               und dann bei jeder neu hinzugefuegten Gruppe erweitert.
	public HashMapIndexedZZZ<Integer,ArrayList<JComponent>> getComponentHashMap() throws ExceptionZZZ{
		if(this.hmComponent==null) {
			String sTitle = this.getTitle();
			KernelJPanelCascadedZZZ panel = this.getPanelParent();
			this.hmComponent = createComponentHashMap(sTitle, panel);
		}
		return this.hmComponent;
	}
	
	//Im Fall einer GroupCollection, die auf einem Modell basiert...
	public ArrayList<JComponentGroupZZZ>createComponentGroupArrayList() throws ComponentGroupModelExceptionZZZ, ExceptionZZZ{
		ArrayList<JComponentGroupZZZ>listaReturn = null;
		main:{
			HashMapIndexedZZZ<Integer,ArrayList<JComponent>>hmComponent = this.getComponentHashMap();
		
			//+++ Die Components/Labels auf die Gruppen verteilen		 		
			int iIndexInGroupCollection=-1;		
			Iterator itListaComponent = hmComponent.iterator();
			while(itListaComponent.hasNext()) {
				iIndexInGroupCollection=iIndexInGroupCollection+1;			
				if(iIndexInGroupCollection==0) {
					listaReturn = new ArrayList<JComponentGroupZZZ>();	
				}
		
				ArrayList<JComponent>listaComponenttemp = (ArrayList<JComponent>) itListaComponent.next();						
				String sIndexAsAlias = Integer.toString(iIndexInGroupCollection);
				
				//TODOGOON; //20210518 DIES dynamisch machen
				//IComponentValueModelZZZ objValueProvider = new Row2ModelZZZ(this.getKernelObject(),"row2",this.getPanelParent(), iIndex); //Diese Modell wird bei jedem "Click" in dem refresh() aufgerufen.
				IComponentValueModelZZZ objValueProvider = this.createModelForGroup("row2", this.getPanelParent(), iIndexInGroupCollection);
				JComponentGroupZZZ grouptemp = new JComponentGroupZZZ(this.getKernelObject(), sIndexAsAlias, objValueProvider,listaComponenttemp);
				if(grouptemp.hasAnyComponentAdded()) {				
					listaReturn.add(grouptemp);
				}								
			}
		}//end main:
		return listaReturn;
	}
	
	//Im Fall einer GroupCollection, die auf einem Modell basiert, wird hier über alle Groups die jeweilige Liste der Components geholt und an die definierte Indexposition der Collection gestellt.
	public HashMapIndexedZZZ<Integer,ArrayList<JComponent>>createComponentHashMap(String sTitle, KernelJPanelCascadedZZZ panel) throws ExceptionZZZ {
		HashMapIndexedZZZ<Integer,ArrayList<JComponent>> hmReturn = new HashMapIndexedZZZ<Integer,ArrayList<JComponent>>();
		main:{
			int iIndexInCollection=0;
			HashMapIndexedZZZ<Integer,ArrayList<String>> hmValuesText0 = this.createValuesText(sTitle, panel, iIndexInCollection);
			boolean bComponentsForIndexFilled = ComponentModelHelperZZZ.fillComponentHashMap(hmReturn, hmValuesText0, iIndexInCollection);
			while(bComponentsForIndexFilled) {
				iIndexInCollection++;
				HashMapIndexedZZZ<Integer,ArrayList<String>> hmValuesText = this.createValuesText(sTitle, panel, iIndexInCollection);
				bComponentsForIndexFilled = ComponentModelHelperZZZ.fillComponentHashMap(hmReturn, hmValuesText, iIndexInCollection);
			}			
		}//end main
		return hmReturn;
	}	
			
	//Darin ist das Modell enthalten, also welche Componenten/welches Label zu welcher Gruppe gehört.
	public abstract HashMapIndexedZZZ<Integer,ArrayList<String>>createValuesText(String sTitle, KernelJPanelCascadedZZZ panel, int iIndexInCollection) throws ComponentGroupModelExceptionZZZ;			
	
	//Diese Modell wird bei jedem "Click" in dem refresh() aufgerufen.
	public abstract IComponentValueModelZZZ createModelForGroup(String sTitle, KernelJPanelCascadedZZZ panelParent, int iIndexInGroupCollection) throws ComponentGroupModelExceptionZZZ, ExceptionZZZ; 
}
