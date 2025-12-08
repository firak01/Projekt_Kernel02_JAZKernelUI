package basic.zKernelUI.component.componentGroup;

import java.util.ArrayList;

import javax.swing.JComponent;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;

public interface IModelComponentGroupValueZZZ extends IModelComponentGroupZZZ {
	public IModelComponentGroupValueZZZ createModelForGroup(String sTitle, IPanelCascadedZZZ panelParent, int iIndexInGroupCollection) throws ModelComponentGroupExceptionZZZ, ExceptionZZZ; //Diese Modell wird bei jedem "Click" in dem refresh() aufgerufen.	
	public ArrayList<JComponentGroupZZZ>createComponentGroupArrayList() throws ModelComponentGroupExceptionZZZ, ExceptionZZZ;
	public HashMapIndexedObjectZZZ<Integer,ArrayList<JComponent>>createComponentHashMap() throws ExceptionZZZ;
	public String getTitle();
	public IPanelCascadedZZZ getPanelParent();
}
