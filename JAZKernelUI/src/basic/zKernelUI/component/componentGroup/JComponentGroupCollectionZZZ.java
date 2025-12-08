package basic.zKernelUI.component.componentGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjektZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;

public class JComponentGroupCollectionZZZ<T>  extends AbstractKernelUseObjectZZZ<T>  implements Iterable<T>,IEventBrokerComponentGroupSwitchUserZZZ {
	private static final long serialVersionUID = 1813161603148465148L;

	//++++++++++ Mehrerer Gruppen zu der HashMap zusammenfassen.
	//Merke: Der Button steuert über den Index die Reihenfolge
	HashMapIndexedObjektZZZ<Integer,JComponentGroupZZZ> hmIndexed = null;
	
	//+++ Den EventBroker DER GRUPPE hinzufügen, damit darueber der Event abgefeuert werden kann
	//Merke: Dem EventBroker ist eine Reihefolge (über den Index) egal
	//KernelSenderComponentGroupSwitchZZZ objEventBroker = null;
	ISenderComponentGroupSwitchZZZ objEventBroker = null;
	
	
	//+++ Das Model, hierin ist u.a. auch das Elternpanel enthalten
	IModelComponentGroupValueZZZ model = null;
	
	public JComponentGroupCollectionZZZ() throws ExceptionZZZ {
		super();
		JComponentGroupCollectionNew_(null, null);
	}
	
	public JComponentGroupCollectionZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
		JComponentGroupCollectionNew_(null, null);
	}
	public JComponentGroupCollectionZZZ(IKernelZZZ objKernel, ArrayList<JComponentGroupZZZ>listaGroup) throws ExceptionZZZ {
		super(objKernel);
		JComponentGroupCollectionNew_(null, listaGroup);
		
	}
	public JComponentGroupCollectionZZZ(IKernelZZZ objKernel, IModelComponentGroupValueZZZ model) throws ExceptionZZZ {
		super(objKernel);
		JComponentGroupCollectionNew_(model, null);
		
	}
	
	private boolean JComponentGroupCollectionNew_(IModelComponentGroupValueZZZ model, ArrayList<JComponentGroupZZZ>listaGroup) throws ExceptionZZZ{
		boolean bReturn = false;		
		main:{			
			if(listaGroup!=null) {	//Für eine GroupCollection OHNE Modell, einfach die Liste der GroupComponents hinzufügen	
				for(JComponentGroupZZZ grouptemp : listaGroup) {
					if(grouptemp!=null) {
						this.add(grouptemp);
					}
				}	
				bReturn = true;
				break main;
			}
			
			this.setModel(model);
			if(model!=null) {       ///Für eine GroupCollection MIT Modell
				HashMapIndexedObjektZZZ<Integer,ArrayList<JComponent>>hmComponent = model.createComponentHashMap();
				
				//+++ Die Labels auf die Gruppen verteilen
				ArrayList<JComponentGroupZZZ>listaGroup2 = new ArrayList<JComponentGroupZZZ>();				
				int iIndex=-1;
				
				String sTitle = model.getTitle();
				IPanelCascadedZZZ panelParent = model.getPanelParent();
				Iterator itListaComponent = hmComponent.iterator();
				while(itListaComponent.hasNext()) {			
					ArrayList<JComponent>listaComponenttemp = (ArrayList<JComponent>) itListaComponent.next();
					if(listaComponenttemp!=null) {
					if(!listaComponenttemp.isEmpty()) {
						iIndex=iIndex+1;
						String sIndexAsAlias = Integer.toString(iIndex);
						
						//Für jede Gruppe ihr eigenes Model erstellen, das auch den Index in der Collection enthält, zwecks Generierung des passenden Wertes.
						//Diese Modell wird bei jedem "Click" in dem refresh() aufgerufen.
						IModelComponentGroupValueZZZ objValueProvider = new ModelPanelDebugZZZ(objKernel, sTitle, panelParent, iIndex); 
						JComponentGroupZZZ grouptemp = new JComponentGroupZZZ(objKernel, sIndexAsAlias, sTitle, panelParent, listaComponenttemp);
						grouptemp.setComponentValueProvider(objValueProvider);
						if(grouptemp.hasAnyComponentAdded()) {
							listaGroup2.add(grouptemp);
						}	
					}
					}
				}	
				
				
				//Die Liste der ComponentsGroup-Elemente, erstellt aus dem Modell der Collection hinzufügen
				for(JComponentGroupZZZ grouptemp : listaGroup2) {
					if(grouptemp!=null) {
						this.add(grouptemp);
					}
				}	
				bReturn = true;
			}					
		}//end main
		return bReturn;
	}
	
	public IModelComponentGroupValueZZZ getModel() {
		return this.model;
	}
	public void setModel(IModelComponentGroupValueZZZ model) {
		this.model = model;
	}
	
	public HashMapIndexedObjektZZZ<Integer,JComponentGroupZZZ>getHashMapIndexed() throws ExceptionZZZ{
		if(this.hmIndexed==null) {
			this.hmIndexed = new HashMapIndexedObjektZZZ<Integer,JComponentGroupZZZ>();
		}
		return this.hmIndexed;		
	}
	private void setHashMapIndexed(HashMapIndexedObjektZZZ<Integer,JComponentGroupZZZ> hmIndexed) {
		this.hmIndexed = hmIndexed;
	}
	private VectorZZZ<Integer>getVectorIndex() throws ExceptionZZZ{
		return this.getHashMapIndexed().getVectorIndex();
	}
	private HashMap<Integer, JComponentGroupZZZ> getHashMap() throws ExceptionZZZ{		
		return this.getHashMapIndexed().getHashMap();		
	}		
	
	public boolean add(JComponentGroupZZZ group) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(group==null) break main;
			
			//+++ Die Gruppe dem EventBroker hinzufügen, der alle registrierten Gruppen über einen Button Click informiert.
			//KernelSenderComponentGroupSwitchZZZ objEventBroker = (KernelSenderComponentGroupSwitchZZZ) this.getSenderUsed();
			ISenderComponentGroupSwitchZZZ objEventBroker = this.getSenderUsed();
			objEventBroker.addListenerComponentGroupSwitch(group);
			
			//+++ Letztendlich die Gruppe der HashMap hinzufügen
			HashMapIndexedObjektZZZ<Integer,JComponentGroupZZZ>hmIndexed = this.getHashMapIndexed();
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
			HashMapIndexedObjektZZZ<Integer,JComponentGroupZZZ>hmIndexed = this.getHashMapIndexed();		
			JComponentGroupZZZ groupToFind = (JComponentGroupZZZ) hmIndexed.getValue(iIndexToFind);
			if(groupToFind==null) break main;			
			groupToFind.setVisible(true);
						
			//+++ Hole aus der HashMap die anderen Gruppen und setze diese unsichtbar
			Iterator it = hmIndexed.iterator();
			while(it.hasNext()) {
				JComponentGroupZZZ group = (JComponentGroupZZZ) it.next();
				if(group!=null) {
					if(group.equals(groupToFind)) {
						//Mache nix
					}else {
						group.setVisible(false);
					}
				}
			}			
			
			//Elternpanel aktualisieren
			if(this.getModel()!=null) {
				if(this.getModel().getPanelParent()!=null) {
					JPanel panel = (JPanel) this.getModel().getPanelParent();
					panel.revalidate();
					panel.repaint();
				}
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
			JComponentGroupZZZ group = (JComponentGroupZZZ) this.getGroupByAlias(sGroupAlias);	
			if(group==null)break main;
			
			//+++ Hole aus der HashMap die anderen Gruppen, ohne dem Alias, setzte sie unsichtbar.
			HashMapIndexedObjektZZZ<Integer,JComponentGroupZZZ>hmIndexed = this.getHashMapIndexed();
			if(hmIndexed!=null) {
				Iterator it = hmIndexed.iterator();
				while(it.hasNext()) {
					JComponentGroupZZZ grouptemp = (JComponentGroupZZZ) it.next();
					if(grouptemp!=null) {
						if(grouptemp.equals(group)){
							//mache nix, schon vorher gemacht
						}else {
							grouptemp.setVisible(false);
						}
					}
				}
			}				
			
			//Elternpanel aktualisieren
			if(this.getModel()!=null) {
				if(this.getModel().getPanelParent()!=null) {
					JPanel panel = (JPanel) this.getModel().getPanelParent();
					panel.revalidate();
					panel.repaint();
				}
			}
			bReturn = true;
		}//end main
		return bReturn;		
	}
	
	/** Gehe alle Gruppen durch und suche nach dem passenden Alias
	 * @param sGroupAlias
	 * @return
	 * @author Fritz Lindhauer, 09.05.2021, 10:20:52
	 */
	public JComponentGroupZZZ getGroupByAlias(String sGroupAlias) {
		JComponentGroupZZZ objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sGroupAlias)) break main;
			
			Iterator it = hmIndexed.iterator();
			while(it.hasNext()) {
				JComponentGroupZZZ group = (JComponentGroupZZZ) it.next();
				if(group!=null) {
					if(group.getGroupAlias().equals(sGroupAlias)) {
						objReturn = group;
						break main;
					}
				}
			}
		}//end main:
		return objReturn;
	}

	//######## INTERFACES
	//+++ aus IEventBrokerComponentGroupSwitchUserZZZ.java
		@Override
		public ISenderComponentGroupSwitchZZZ getSenderUsed() throws ExceptionZZZ {
			if(this.objEventBroker==null) {
				IKernelZZZ objKernel = this.getKernelObject();
				KernelSenderComponentGroupSwitchZZZ objEventBroker = new KernelSenderComponentGroupSwitchZZZ(objKernel);
				this.setSenderUsed(objEventBroker);
			}
			return this.objEventBroker;
		}
		
		@Override
		public void setSenderUsed(ISenderComponentGroupSwitchZZZ objEventBroker) {
			this.objEventBroker = objEventBroker;
		}
	
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
	            	VectorZZZ<Integer> vec = getVectorIndex();
					
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
	                	VectorZZZ<Integer> vec = getVectorIndex();
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
}
