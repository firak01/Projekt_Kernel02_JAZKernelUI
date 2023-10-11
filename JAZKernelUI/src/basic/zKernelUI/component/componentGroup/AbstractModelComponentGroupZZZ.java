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
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.model.ModelComponentHelperZZZ;
import debug.zKernelUI.component.buttonSwitchLabelGroup.ModelRow2ZZZ;

public abstract class AbstractModelComponentGroupZZZ extends AbstractKernelUseObjectZZZ  implements IModelComponentGroupValueZZZ{
	protected String sTitle=null;
	protected IPanelCascadedZZZ panelParent;
	protected int iIndexInCollection=-1;	
	protected HashMapIndexedZZZ<Integer,ArrayList<JComponent>>hmComponent=null; //Die HashMap mit dem Index für die Gruppe und der ArrayList der Komponenten der Gruppe
		
	public AbstractModelComponentGroupZZZ() {		
		super();
		AbstractModelComponentGroupNew_(null, null, -1);
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
	public AbstractModelComponentGroupZZZ(IKernelZZZ objKernel) throws ModelComponentGroupExceptionZZZ, ExceptionZZZ {		
		super(objKernel);
		AbstractModelComponentGroupNew_(null, null, -1);
	}
	
	
	/** Konstruktor für das Model über die gesamte Collection aller ComponentGroups
	 * @param objKernel
	 * @param sTitle
	 * @param panelParent
	 * @throws ExceptionZZZ
	 * 19.05.2021, 08:49:03, Fritz Lindhauer
	 */
	public AbstractModelComponentGroupZZZ(IKernelZZZ objKernel, String sTitle, IPanelCascadedZZZ panelParent) throws ExceptionZZZ, ModelComponentGroupExceptionZZZ {
		super(objKernel);
		AbstractModelComponentGroupNew_(sTitle, panelParent, -1);
	}
	
	/** Konstruktor für das Model einer ComponentGroup, an der per Index vorgegebenen Stelle der Collection
	 * @param objKernel
	 * @param sTitle
	 * @param panelParent
	 * @throws ExceptionZZZ
	 * 19.05.2021, 08:49:03, Fritz Lindhauer
	 */
	public AbstractModelComponentGroupZZZ(IKernelZZZ objKernel, String sTitle, IPanelCascadedZZZ panelParent, int iIndexInCollection) throws ExceptionZZZ, ModelComponentGroupExceptionZZZ {
		super(objKernel);
		AbstractModelComponentGroupNew_(sTitle, panelParent, iIndexInCollection);
	}

	private void AbstractModelComponentGroupNew_(String sTitle, IPanelCascadedZZZ panelParent, int iIndexInCollection) {
		this.sTitle = sTitle;
		this.panelParent = panelParent;
		this.iIndexInCollection = iIndexInCollection;
	}
		
	//GETTER / SETTER	
	public int getIndexInCollection() {
		return this.iIndexInCollection;
	}
	public void setIndexInCollection(int iIndex) {
		this.iIndexInCollection = iIndex;
	}
	
	//+++ Interface IComponentValueProviderZZZZ
	
	@Override
	public String getTitle() {
		return this.sTitle;
	}
	
	@Override
	public IPanelCascadedZZZ getPanelParent() {
		return this.panelParent;
	}
	
	
	@Override
	public HashMapIndexedZZZ<Integer, ArrayList<String>> getComponentValues()  throws ExceptionZZZ{
		String sTitle = this.getTitle();
		IPanelCascadedZZZ panelParent = this.getPanelParent();
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
			IPanelCascadedZZZ panel = this.getPanelParent();
			this.hmComponent = createComponentHashMap(sTitle, panel);
		}
		return this.hmComponent;
	}
	
	//Im Fall einer GroupCollection, die auf einem Modell basiert...
	public ArrayList<JComponentGroupZZZ>createComponentGroupArrayList() throws ModelComponentGroupExceptionZZZ, ExceptionZZZ{
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
				
				//20210518 Das Objekt der verwendeten Modelklasse hier nun jeweils passend erzeugen.
				//IComponentValueModelZZZ objValueProvider = new Row2ModelZZZ(this.getKernelObject(),"row2",this.getPanelParent(), iIndex); //Diese Modell wird bei jedem "Click" in dem refresh() aufgerufen.
				IModelComponentGroupValueZZZ objValueProvider = this.createModelForGroup(this.getTitle(), this.getPanelParent(), iIndexInGroupCollection);
				JComponentGroupZZZ grouptemp = new JComponentGroupZZZ(this.getKernelObject(), sIndexAsAlias, objValueProvider,listaComponenttemp);
				if(grouptemp.hasAnyComponentAdded()) {				
					listaReturn.add(grouptemp);
				}								
			}
		}//end main:
		return listaReturn;
	}
	
	//Im Fall einer GroupCollection, die auf einem Modell basiert, wird hier über alle Groups die jeweilige Liste der Components geholt und an die definierte Indexposition der Collection gestellt.
	public HashMapIndexedZZZ<Integer,ArrayList<JComponent>>createComponentHashMap(String sTitle, IPanelCascadedZZZ panel) throws ExceptionZZZ {
		HashMapIndexedZZZ<Integer,ArrayList<JComponent>> hmReturn = new HashMapIndexedZZZ<Integer,ArrayList<JComponent>>();
		main:{
			int iIndexInCollection=0;
			HashMapIndexedZZZ<Integer,ArrayList<String>> hmValuesText0 = this.createValuesText(sTitle, panel, iIndexInCollection);
			boolean bComponentsForIndexFilled = ModelComponentHelperZZZ.fillComponentHashMap(hmReturn, hmValuesText0, iIndexInCollection);
			while(bComponentsForIndexFilled) {
				iIndexInCollection++;
				HashMapIndexedZZZ<Integer,ArrayList<String>> hmValuesText = this.createValuesText(sTitle, panel, iIndexInCollection);
				bComponentsForIndexFilled = ModelComponentHelperZZZ.fillComponentHashMap(hmReturn, hmValuesText, iIndexInCollection);
			}			
		}//end main
		return hmReturn;
	}	
	
	@Override
	public HashMapIndexedZZZ<Integer,ArrayList<JComponent>>createComponentHashMap() throws ExceptionZZZ {
		return this.createComponentHashMap(this.getTitle(), this.getPanelParent());
	}	
	
//	@Override
//	public void revalidateParent() {
//		if(this.getPanelParent()!=null) {
//			this.getPanelParent().revalidate(); //TODOGOON
//			this.getPanelParent().repaint();
//		}
//	}
			
	//Darin ist das Modell enthalten, also welche Componenten/welches Label zu welcher Gruppe gehört.
	public abstract HashMapIndexedZZZ<Integer,ArrayList<String>>createValuesText(String sTitle, IPanelCascadedZZZ panel, int iIndexInCollection) throws ModelComponentGroupExceptionZZZ;			
	
	//Diese Modell wird bei jedem "Click" in dem refresh() aufgerufen.
	public abstract IModelComponentGroupValueZZZ createModelForGroup(String sTitle, IPanelCascadedZZZ panelParent, int iIndexInGroupCollection) throws ModelComponentGroupExceptionZZZ, ExceptionZZZ; 
}
