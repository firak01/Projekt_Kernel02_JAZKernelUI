package debug.zKernelUI.component.navigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.componentGroup.AbstractModelComponentGroupZZZ;
import basic.zKernelUI.component.componentGroup.ModelComponentGroupExceptionZZZ;
import basic.zKernelUI.component.componentGroup.IModelComponentGroupValueZZZ;
import basic.zKernelUI.component.model.ModelComponentHelperZZZ;
import basic.zKernelUI.component.navigator.AbstractModelNavigatorZZZ;
import basic.zKernelUI.component.navigator.IModelNavigatorValueZZZ;
import basic.zKernelUI.component.navigator.INavigatorElementZZZ;
import basic.zKernelUI.component.navigator.ModelNavigatorExceptionZZZ;
import basic.zKernelUI.component.navigator.NavigatorElementZZZ;

public class ModelDebugNavigatorZZZ extends AbstractModelNavigatorZZZ{	
	public ModelDebugNavigatorZZZ() {	
		super();
	}
	
	public ModelDebugNavigatorZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
	}

	@Override
	public ArrayListExtendedZZZ<INavigatorElementZZZ> createNavigatorElementArrayList() throws ExceptionZZZ {
		ArrayListExtendedZZZ<INavigatorElementZZZ> alReturn = new ArrayListExtendedZZZ<INavigatorElementZZZ>();
		main:{
			//1. Auslesen des wertes aus der Ini-Konfiguration
			IKernelZZZ objKernel = this.getKernelObject();
			//Das wäre normalerweise mit | getrennt...., jetzt das JSON-Array hinzunehmen
			//String[] saReturn = objKernel.getParameterArrayStringByProgramAlias("DebugNavigator", "PanelWest", "NavigatorContentJson");
			IKernelConfigSectionEntryZZZ[] objaEntry = objKernel.getParameterArrayByProgramAlias("DebugNavigator", "PanelWest", "NavigatorContentJson");
			
			//TODOGOON; ///20210810: Nicht vergessen: Das soll eigentlich aus einer HashMap stammen, wg. Wert für das Label und einen technischen Wert, der für den Zugriff auf das Label verwendet wird.
			
			//2. Aus den Entry-Objekten die Navigator-Objekte machen
			for(IKernelConfigSectionEntryZZZ objEntry : objaEntry) {
				INavigatorElementZZZ objReturn = this.createNavigatorElement(objEntry);		
				if(objReturn!=null) {
					alReturn.add(objReturn);
				}
			}
		}//end main:
		return alReturn;
	}

	@Override
	public INavigatorElementZZZ createNavigatorElement(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ {
		INavigatorElementZZZ objReturn = null;
		main:{			
			
			objReturn = new NavigatorElementZZZ(this.getKernelObject(), this, objEntry, objAction);
		}//end main:
		return objReturn;
	}

	@Override
	public HashMapIndexedZZZ<Integer, ArrayList<INavigatorElementZZZ>> createNavigatorElementHashMap() throws ExceptionZZZ {
		HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>> hmReturn = new HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>>();
		main:{
			//1. Auslesen des wertes aus der Ini-Konfiguration
			IKernelZZZ objKernel = this.getKernelObject();
			//Das wäre normalerweise mit | getrennt...., jetzt das JSON-Array hinzunehmen
			//String[] saReturn = objKernel.getParameterArrayStringByProgramAlias("DebugNavigator", "PanelWest", "NavigatorContentJson");
			//HashMap<String,String>hm= objKernel.getParameterHashMapStringByProgramAlias("DebugNavigator", "PanelWest", "NavigatorContentJson");
			
			//20210811 also wie in ArrayStringByProgramAlias über Clone einzelne Entry-Objekt erstellen und diese über den Index speichern.
			IKernelConfigSectionEntryZZZ[] entrya = objKernel.getParameterArrayByProgramAlias("DebugNavigator", "PanelWest", "NavigatorContentJson");
			
			//2. Aus den Entry-Objekten die Navigator-Objekte machen
			int iIndexInCollection=-1;
			for(IKernelConfigSectionEntryZZZ entry : entrya) {
				
				//Merke: Verwende eine ArrayList, die Momentan nur 1 Label enthalten wird
				ArrayList<INavigatorElementZZZ>listaElement = new ArrayList<INavigatorElementZZZ>();
				INavigatorElementZZZ element = this.createNavigatorElement(entry);
				if(element!=null) {
					listaElement.add(element);
				}
				
				iIndexInCollection++;
				Integer intIndexInCollection = new Integer(iIndexInCollection);
				hmReturn.put(intIndexInCollection, listaElement);
			}
					
		}//end main
		return hmReturn;
	}
}

