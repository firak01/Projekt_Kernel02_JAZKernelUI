package basic.zKernelUI.component.navigator;

import java.util.ArrayList;

import javax.swing.JComponent;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;

public interface IModelNavigatorValueZZZ extends IModelNavigatorZZZ {
	public IModelNavigatorValueZZZ createModelForNavigator(String sTitle, IPanelCascadedZZZ panelParent, int iIndexInGroupCollection) throws ModelAdjustmentNavigatorExceptionZZZ, ExceptionZZZ; //Diese Modell wird bei jedem "Click" in dem refresh() aufgerufen.	
	public ArrayList<JAdjustmentNavigatorZZZ>createAdjustmentNavigatorArrayList() throws ModelAdjustmentNavigatorExceptionZZZ, ExceptionZZZ;
	public HashMapIndexedZZZ<Integer,ArrayList<JComponent>>createComponentHashMap() throws ExceptionZZZ;
	public String getTitle();
	public IPanelCascadedZZZ getPanelParent();
}
