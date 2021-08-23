package basic.zKernelUI.component.navigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.abstractList.VectorExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.componentGroup.IEventBrokerComponentGroupSwitchUserZZZ;
import basic.zKernelUI.component.componentGroup.ISenderComponentGroupSwitchZZZ;
import basic.zKernelUI.component.componentGroup.KernelSenderComponentGroupSwitchZZZ;

TODOGOON; //20210822: Wenn die Collection selbst auf einen Click eines NavigatorElements reagieren soll, den Listener implementieren
public class NavigatorElementCollectionZZZ<T>  extends KernelUseObjectZZZ  implements Iterable<T>, IEventBrokerNavigatorElementSwitchUserZZZ, IListenerNavigatorElementSwitchZZZ {

//Wenn die einzelnen Elemente auf einen Click eines NavigatorElements reagieren sollen, nur dort den Listenere implementieren.
//public class NavigatorElementCollectionZZZ<T>  extends KernelUseObjectZZZ  implements Iterable<T>, IEventBrokerNavigatorElementSwitchUserZZZ { 
			
	//++++++++++ Mehrerer Gruppen zu der HashMap zusammenfassen.
	//Merke1: Über den Index wird die Reihenfolge festgelegt.
	//Merke2: Im Nomralfall ist nur 1 Element in der Arraylist... aber schon mal als Erweiterung gedacht.
	HashMapIndexedZZZ<Integer, ArrayList<INavigatorElementZZZ>> hmIndexed = null;
	
	//+++ Den EventBroker DER GRUPPE hinzufügen, damit darueber der Event abgefeuert werden kann
	//Merke: Dem EventBroker ist eine Reihefolge (über den Index) egal
	//ISenderComponentGroupSwitchZZZ objEventBroker = null;
	ISenderNavigatorElementSwitchZZZ objEventBroker = null;
		
	
	//+++ Das Model
	IModelNavigatorValueZZZ model = null;
	
	//+++ Merke: Anders als bei der ComponentGroup ist an dieser Stelle das Elternpanel gespeichert. Für das Model ist das nicht wichtig.
	protected IPanelCascadedZZZ panelParent;
	
	public NavigatorElementCollectionZZZ() throws ExceptionZZZ {
		super();
		NavigatorElementCollectionNew_(null, null, null);
	}
	
	public NavigatorElementCollectionZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
		NavigatorElementCollectionNew_(null, null, null);
	}
	public NavigatorElementCollectionZZZ(IKernelZZZ objKernel, IPanelCascadedZZZ panelParent, ArrayList<INavigatorElementZZZ>listaGroup) throws ExceptionZZZ {
		super(objKernel);
		NavigatorElementCollectionNew_(null, panelParent, listaGroup);
		
	}
	public NavigatorElementCollectionZZZ(IKernelZZZ objKernel, IPanelCascadedZZZ panelParent, IModelNavigatorValueZZZ model) throws ExceptionZZZ {
		super(objKernel);
		NavigatorElementCollectionNew_(model, panelParent, null);
		
	}
	
	private boolean NavigatorElementCollectionNew_(IModelNavigatorValueZZZ model, IPanelCascadedZZZ panelParent, ArrayList<INavigatorElementZZZ>listaGroup) throws ExceptionZZZ{
		boolean bReturn = false;		
		main:{			
			if(listaGroup!=null) {	//Für eine GroupCollection OHNE Modell, einfach die Liste der GroupComponents hinzufügen	
				for(INavigatorElementZZZ grouptemp : listaGroup) {
					if(grouptemp!=null) {
						this.add(grouptemp);
					}
				}	
				bReturn = true;
				break main;
			}
		
			//Merke: Das ist anders als bei der ComponentGroupCollection. Darin wird der Event von einem "externen" Button aus aufgerufen.
			//       Hier wird der Event vom NavigatorElement selbst aufgerufen.
			
			TODOGOON;
		
			//20210822 STRATEGIEFRAGE: Wird nun die Collection hinzugefügt oder jedes einzelne Element?
			//Ich denke: erst einmal die Collection der Elemente. 
	        //In einer zweiten Stufe, dann weitere Objekte, z.B. Panels, die dann sichtbar werden.		
			//objEventBroker.addListenerNavigatorElementSwitch(group);
			objEventBroker.addListenerNavigatorElementSwitch(this);
			
			this.panelParent = panelParent;
			this.setModel(model);
			if(model!=null) {       ///Für eine GroupCollection MIT Modell
				
				HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>>hmElement = model.createNavigatorElementHashMap();
				this.setHashMapIndexed(hmElement);
				
	
			}	
			bReturn = true;
							
		}//end main
		return bReturn;
	}
	
	public IModelNavigatorValueZZZ getModel() {
		return this.model;
	}
	public void setModel(IModelNavigatorValueZZZ model) {
		this.model = model;
	}
	
	public IPanelCascadedZZZ getPanelParent() {
		return this.panelParent;
	}
	
	public HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>>getHashMapIndexed() throws ExceptionZZZ{
		if(this.hmIndexed==null) {
			this.hmIndexed = new HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>>();
		}
		return this.hmIndexed;		
	}
	private void setHashMapIndexed(HashMapIndexedZZZ<Integer, ArrayList<INavigatorElementZZZ>> hmElement) {
		this.hmIndexed = hmElement;
	}
	private VectorExtendedZZZ<Integer>getVectorIndex() throws ExceptionZZZ{
		return this.getHashMapIndexed().getVectorIndex();
	}
	private HashMap<Integer,Object> getHashMap() throws ExceptionZZZ{		
		return this.getHashMapIndexed().getHashMap();		
	}		
	
	public boolean add(INavigatorElementZZZ group) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(group==null) break main;
			
			//+++ Die Gruppe dem EventBroker hinzufügen, der alle registrierten Gruppen über einen Button Click informiert.			
			ISenderNavigatorElementSwitchZZZ objEventBroker = this.getSenderUsed();
			
			TODOGOON; //20210822 STRATEGIEFRAGE: Wird nun die Collection hinzugefügt oder jedes einzelne Element?
			//Ich denke: erst einmal die Collection der Elemente.  
	        //In einer zweiten Stufe, dann weitere Objekte, z.B. Panels, die dann sichtbar werden.		
			//objEventBroker.addListenerNavigatorElementSwitch(group);
			
		
			//+++ Letztendlich die Gruppe der HashMap hinzufügen
			HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>>hmIndexed = this.getHashMapIndexed();
			hmIndexed.put(group);
						
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public boolean setVisible(int iIndexToFind) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(iIndexToFind<0) break main;

//			//+++ Hole aus der HashMap die Gruppe mit dem Index, setzte sie sichtbar.			
			HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>>hmIndexed = this.getHashMapIndexed();		
			ArrayList<INavigatorElementZZZ> groupToFind = (ArrayList<INavigatorElementZZZ>) hmIndexed.getValue(iIndexToFind);
			if(groupToFind==null) break main;	
			if(groupToFind.isEmpty()) break main;
			
			for(INavigatorElementZZZ element : groupToFind) {
				element.setVisible(true);
			}
			
						
			//+++ Hole aus der HashMap die anderen Gruppen und setze diese unsichtbar
			Iterator it = hmIndexed.iterator();
			while(it.hasNext()) {
				ArrayList<INavigatorElementZZZ> group = (ArrayList<INavigatorElementZZZ>) it.next();
				if(group!=null) {
					if(group.equals(groupToFind)) {
						//Mache nix
					}else {
						for(INavigatorElementZZZ element : group) {
							element.setVisible(true);
						}
					}
				}
			}			
			
			//Elternpanel aktualisieren
			if(this.getPanelParent()!=null) {
				JPanel panel = (JPanel) this.getPanelParent();
				panel.revalidate();
				panel.repaint();
			}
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	public boolean setVisible(String sGroupAlias) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sGroupAlias)) break main;

			//+++ Hole die Gruppe mit dem Alias, setzte sie sichtbar
			INavigatorElementZZZ group = (INavigatorElementZZZ) this.getGroupByAlias(sGroupAlias);	
			if(group==null)break main;
			
			int iIndex = group.getPosition();
			this.setVisible(iIndex);
			
			bReturn = true;
		}//end main
		return bReturn;		
	}
	
	/** Gehe alle Gruppen durch und suche nach dem passenden Alias
	 * @param sGroupAlias
	 * @return
	 * @author Fritz Lindhauer, 09.05.2021, 10:20:52
	 */
	public ArrayList<INavigatorElementZZZ>  getGroupByAlias(String sGroupAlias) {
		ArrayList<INavigatorElementZZZ> objaReturn = null;
		main:{
			if(StringZZZ.isEmpty(sGroupAlias)) break main;
			
			Iterator it = hmIndexed.iterator();
			while(it.hasNext()) {
				ArrayList<INavigatorElementZZZ> group = (ArrayList<INavigatorElementZZZ>) it.next();
				if(group!=null) {
					
					//Es reicht jeweils das erste Element abzufragen
					for(INavigatorElementZZZ element : group) {
						if(element!=null) {
							String sAlias = element.getAlias();
							if(sGroupAlias.equals(sAlias)) {
								objaReturn = group;
							}
							break;
						}
					}
				}
				
				if(objaReturn!=null)break main;
			}
		}//end main:
		return objaReturn;
	}

	//######## INTERFACES
	//+++ aus IEventBrokerComponentGroupSwitchUserZZZ.java
//		@Override
//		public ISenderComponentGroupSwitchZZZ getSenderUsed() throws ExceptionZZZ {
//			if(this.objEventBroker==null) {
//				IKernelZZZ objKernel = this.getKernelObject();
//				KernelSenderComponentGroupSwitchZZZ objEventBroker = new KernelSenderComponentGroupSwitchZZZ(objKernel);
//				this.setSenderUsed(objEventBroker);
//			}
//			return this.objEventBroker;
//		}
//		
//		@Override
//		public void setSenderUsed(ISenderComponentGroupSwitchZZZ objEventBroker) {
//			this.objEventBroker = objEventBroker;
//		}
	
	//+++ aus Iterable
	@Override
	public Iterator<T> iterator() {
		Iterator<T> it = new Iterator<T>() {
        	private int iIndexIterator=-1; //Der Index des gerade verarbeiteten Keys im Iterator
        	private int iIndexWatched=-1;//Der Index des gerade mit hasNext() betrachteten Keys im Iterator
        	
        	
            @Override
            public boolean hasNext() {
            	boolean bReturn = false;
            	main:{
            		try {
	            	VectorExtendedZZZ<Integer> vec = getVectorIndex();
					
	            	if(vec==null)break main;
	            	if(!vec.hasAnyElement())break main;
	            		            	
	            	Integer intLast = (Integer) vec.lastElement();
	            		            
	            	iIndexWatched = iIndexWatched+1;//das nächste Element halt, ausgehend von -1
	            	Integer intNext = new Integer(iIndexWatched);
	            	bReturn = iIndexWatched <= intLast.intValue() && getHashMap().get(intNext) != null;	 
            		} catch (ExceptionZZZ e) {						
						e.printStackTrace();
					}
            	}//end main:
            	return bReturn;
            }

            @SuppressWarnings("unchecked")
			@Override
            public T next() {
                T objReturn = null;
                main:{
                	try {
	                	VectorExtendedZZZ<Integer> vec = getVectorIndex();
		            	if(vec==null)break main;
		            	if(!vec.hasAnyElement())break main;
		            	
	                	int iIndexCur = this.iIndexIterator;
	                	if(iIndexCur<this.iIndexWatched) {
	                		iIndexCur = this.iIndexWatched;
	                	}else {
	                		iIndexCur = iIndexCur + 1;
	                	}
	                	
		            	Integer intLast = (Integer) vec.lastElement();
		            	boolean bReturn = iIndexCur <= intLast.intValue() && getHashMap().get(iIndexCur) != null;	 
		            	if(bReturn) {
		            		this.iIndexIterator = iIndexCur;
		            		objReturn = (T) getHashMap().get(iIndexCur);
		            	}
                	} catch (ExceptionZZZ e) {						
						e.printStackTrace();
					}
                }//end main:
            	return objReturn;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
	}//end inner class iterator

	//######## INTERFACES
	//+++ aus IEventBrokerNavigatorElementSwitchUserZZZ.java
	@Override
	public ISenderNavigatorElementSwitchZZZ getSenderUsed() throws ExceptionZZZ {
		if(this.objEventBroker==null) {
			IKernelZZZ objKernel = this.getKernelObject();
			KernelSenderNavigatorElementSwitchZZZ objEventBroker = new KernelSenderNavigatorElementSwitchZZZ(objKernel);
			this.setSenderUsed(objEventBroker);
		}
		return this.objEventBroker;
	}

	@Override
	public void setSenderUsed(ISenderNavigatorElementSwitchZZZ objEventSender) {
		this.objEventBroker = objEventSender;
	}

	
}
