package basic.zKernelUI.component.navigator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.abstractList.VectorExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;

//TODOGOON: 20210826 Mache diese Klasse abstrakt und überschreibe die custom-Methoden....
public class NavigatorElementCollectionZZZ<T>  extends KernelUseObjectZZZ  implements Iterable<T>, IEventBrokerNavigatorElementSwitchUserZZZ, IListenerNavigatorElementSwitchZZZ {

//Wenn die einzelnen Elemente auf einen Click eines NavigatorElements reagieren sollen, nur dort den Listenere implementieren.
//public class NavigatorElementCollectionZZZ<T>  extends KernelUseObjectZZZ  implements Iterable<T>, IEventBrokerNavigatorElementSwitchUserZZZ { 
			
	//++++++++++ Mehrerer Gruppen zu der HashMap zusammenfassen.
	//Merke1: Über den Index wird die Reihenfolge festgelegt.
	//Merke2: Im Nomralfall ist nur 1 Element in der Arraylist... aber schon mal als Erweiterung gedacht.
	HashMapIndexedZZZ<Integer, ArrayList<INavigatorElementZZZ>> hmIndexed = null;
	
	//+++ Den EventBroker DER GRUPPE hinzufügen, damit darueber der Event abgefeuert werden kann
	//Merke: Dem EventBroker ist eine Reihefolge (über den Index) egal
	ISenderNavigatorElementSwitchZZZ objEventBroker = null;
		
	//+++ Der vorher verwendete Event. Damit wird verhindert, dass mehrmals hintereinander das gleiche NavigatorElement angeclickt wird.
	EventNavigatorElementSwitchZZZ eventPrevious = null;
		
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
			this.panelParent = panelParent;
			
			//Dann die NavigatorElemente "Element fuer Element" also "Zeile fuer Zeile" hinzufügen.	       			
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
			
			//20210822 STRATEGIEFRAGE: Wird nun die Collection hinzugefügt oder jedes einzelne Element?
			//Ich denke: erst einmal die Collection der Elemente.  
	        //In einer zweiten Stufe, dann weitere Objekte, z.B. Panels, die dann sichtbar werden.
			//+++ Dem Navigator den EventBroker hinzufügen, der alle registrierten Objekte über einen Button Click informiert.
			ISenderNavigatorElementSwitchZZZ objEventBroker = this.getSenderUsed();
			objEventBroker.addListenerNavigatorElementSwitch(this); //Die Collection soll selbst auf den Click lauschen.... andere Panels können ebenfalls darauf lauschen.
									
			this.setModel(model);
			if(model!=null) {       ///Für eine GroupCollection MIT Modell
				
				HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>>hmElement = model.createNavigatorElementHashMap();
				this.setHashMapIndexed(hmElement);				
	
			}	
			
			//Nun erst die Elemente Clickbar machen, man braucht das panel
			HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>>hmElement = this.getHashMapIndexed();
			if(hmElement!=null) {
				Iterator<ArrayList<INavigatorElementZZZ>> it = hmElement.iterator();
				while(it.hasNext()) {
					ArrayList<INavigatorElementZZZ>lista = it.next();
					for(INavigatorElementZZZ element : lista) {
						INavigatorElementMouseListenerZZZ l = element.createMouseListener(panelParent);
						l.setSenderUsed(objEventBroker); //Damit wird dann der Click an die registrierten Objekte weitergeleitet... auch an die NavigatorElementCollection selbst.
						element.addMouseListener(l);												
					}
				}
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
	
	public boolean add(INavigatorElementZZZ element) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(element==null) break main;
								
			//Das ist der MouseListener, der beim Clicken auf das Element ALS EIGENER THREAD gestartet wird. 
			//Die einzelnen MouseListener haben einen anderen Alias, über den dann identifiziert wird wer geclickt wurde.
			String sAlias = element.getAlias();
			ActionSwitchZZZ<T> actionSwitch = new ActionSwitchZZZ<T>(objKernel, (IPanelCascadedZZZ) panelParent, objEventBroker, sAlias);	       
			element.getLabel().addMouseListener(actionSwitch);
		
			//Merke: Im MouseListener wird dann der Event ".... NavigatorElement mit Alias xyz wurde aktiviert...." geworfen, ausgehend vom EventBroker!
			
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
							element.setVisible(false);
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

	@Override
	public void doSwitch(EventNavigatorElementSwitchZZZ event) throws ExceptionZZZ {
		
		main:{
			//Merke: Theoretisch wäre hier z.B. noch eine queryCustom Abfrage möglich, um zu prüfen, ob umgeschaltet werden soll.
			//Aber jetzt erst einmal nur weiterleiten.
			
			EventNavigatorElementSwitchZZZ eventPrevious = this.getEventPrevious();
			if(eventPrevious!=null) {
				
				//TODOGOON; //20210826 Die Events auf Gleichheit prüfen können.....
				if(eventPrevious.equals(event)) {
					//mache nix
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+"#zuvor schon den gleichen Navigator angeclickt. Ignoriere diesen Click.");
					break main;					
				}else {
					
				}
			}
						
			this.setEventPrevious(event);
			this.doSwitchCustom(event);
		}//end main:
	}

	@Override
	public void doSwitchCustom(EventNavigatorElementSwitchZZZ eventComponentGroupSwitchNew) throws ExceptionZZZ {
		System.out.println(ReflectCodeZZZ.getPositionCurrent()+"#mache nun die Umschaltung.");
		
		//20210827 setzte aktiv und andere inaktiv.... TODOGOON: Den Event aber schon vor dem Aufruf des Threads abprüfen. Also beim Mausclick.
		String sAlias = eventComponentGroupSwitchNew.getNavigatorElementAlias();
		
		
		//Die anderen Elemente alle passiv schalten
		HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>> hmElement = this.getHashMapIndexed();
		Iterator<ArrayList<INavigatorElementZZZ>> itElement = hmElement.iterator();
		
		while(itElement.hasNext()) {
			ArrayList<INavigatorElementZZZ> listaElementTemp = itElement.next();
			listaElementTemp.get(0).setVisible(false);//Merke: Momentan ist das nur 1 Element in der Liste...						
		}
		
		//Das ausgewählte Element aktiv schalten
		ArrayList<INavigatorElementZZZ>listaElement = this.getGroupByAlias(sAlias);				
		listaElement.get(0).setVisible(true);//Merke: Momentan ist das nur 1 Element in der Liste...
		
		TODOGOON; //20211018 Nun das EmptyPanel verbergen und das (noch hinzuzufügenden Panel mit dem Wert anzeigen.)
		
		//Wird schon in ActionSwitchZZZ.updatePanel() gemacht, also hier nicht notwendig...
		//((JPanel)this.getPanelParent()).validate();
	}

	@Override
	public EventNavigatorElementSwitchZZZ getEventPrevious() {
		return this.eventPrevious;
	}

	@Override
	public void setEventPrevious(EventNavigatorElementSwitchZZZ eventComponentGroupSwitchNew) {
		this.eventPrevious = eventComponentGroupSwitchNew;
	}

	
}
