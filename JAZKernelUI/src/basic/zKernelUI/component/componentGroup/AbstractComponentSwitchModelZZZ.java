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
import basic.zKernelUI.component.IComponentValueModelZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.model.ComponentModelHelperZZZ;

public abstract class AbstractComponentSwitchModelZZZ implements IComponentValueModelZZZ{
	private String sTitle=null;
	private KernelJPanelCascadedZZZ panelParent;
	private int iIndexInCollection=-1;
	
	public AbstractComponentSwitchModelZZZ() {		
	}
	
	public AbstractComponentSwitchModelZZZ(String sTitle, KernelJPanelCascadedZZZ panelParent, int iIndexInCollection) {
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
			
	public abstract HashMapIndexedZZZ<Integer,ArrayList<String>>createValuesText(String sTitle, KernelJPanelCascadedZZZ panel, int iIndexInCollection) throws ExceptionZZZ;			
}
