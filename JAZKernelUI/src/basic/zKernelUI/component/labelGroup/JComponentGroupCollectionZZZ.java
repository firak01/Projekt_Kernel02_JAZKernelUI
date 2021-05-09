package basic.zKernelUI.component.labelGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.abstractList.VectorExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;

public class JComponentGroupCollectionZZZ<T>  extends KernelUseObjectZZZ  implements Iterable<T>,IEventBrokerSwitchComponentUserZZZ {
	
	//++++++++++ Mehrerer Gruppen zu der HashMap zusammenfassen.
	//Merke: Der Button steuert über den Index die Reihenfolge
	HashMapIndexedZZZ<Integer,JComponentGroupZZZ> hmIndexed = null;
	
	//+++ Den EventBroker DER GRUPPE hinzufügen, damit darueber der Event abgefeuert werden kann
	//Merke: Dem EventBroker ist eine Reihefolge (über den Index) egal
	//KernelSenderComponentGroupSwitchZZZ objEventBroker = null;
	ISenderComponentGroupSwitchZZZ objEventBroker = null;

	
	public JComponentGroupCollectionZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
		JComponentGroupCollectionNew_(null);
	}
	public JComponentGroupCollectionZZZ(IKernelZZZ objKernel, ArrayList<JComponentGroupZZZ>listaGroup) throws ExceptionZZZ {
		super(objKernel);
		JComponentGroupCollectionNew_(listaGroup);
		
	}
	
	private boolean JComponentGroupCollectionNew_(ArrayList<JComponentGroupZZZ>listaGroup) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(listaGroup!=null) {				
				for(JComponentGroupZZZ grouptemp : listaGroup) {
					if(grouptemp!=null) {
						this.add(grouptemp);
					}
				}				
			}
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	public HashMapIndexedZZZ<Integer,JComponentGroupZZZ>getHashMapIndexed() throws ExceptionZZZ{
		if(this.hmIndexed==null) {
			this.hmIndexed = new HashMapIndexedZZZ<Integer,JComponentGroupZZZ>();
		}
		return this.hmIndexed;
	}
	private void setHashMapIndexed(HashMapIndexedZZZ<Integer,JComponentGroupZZZ> hmIndexed) {
		this.hmIndexed = hmIndexed;
	}
	private VectorExtendedZZZ<Integer>getVectorIndex() throws ExceptionZZZ{
		return this.getHashMapIndexed().getVectorIndex();
	}
	private HashMap<Integer,Object> getHashMap() throws ExceptionZZZ{		
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
			HashMapIndexedZZZ<Integer,JComponentGroupZZZ>hmIndexed = this.getHashMapIndexed();
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
			HashMapIndexedZZZ<Integer,JComponentGroupZZZ>hmIndexed = this.getHashMapIndexed();		
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
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	public boolean setVisible(String sGroupAlias) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sGroupAlias)) break main;

//			//+++ Hole aus der HashMap die Gruppe mit dem Alias, setzte sie sichtbar.
			HashMapIndexedZZZ<Integer,JComponentGroupZZZ>hmIndexed = this.getHashMapIndexed();
			if(hmIndexed!=null) {
				Iterator it = hmIndexed.iterator();
				while(it.hasNext()) {
					JComponentGroupZZZ group = (JComponentGroupZZZ) it.next();
					if(group!=null) {
						if(group.getGroupAlias().equals(sGroupAlias)) {
							group.setVisible(true);
						}else {
							group.setVisible(false);
						}
					}
				}
				//TODOGOON:
				JComponentGroupZZZ group = (JComponentGroupZZZ) this.getGroupByAlias(sGroupAlias);	
				String stemp = group.toString();
				System.out.println("STRING: " + stemp);
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
	//+++ aus IEventBrokerSwitchComponentUserZZZ.java
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
}
