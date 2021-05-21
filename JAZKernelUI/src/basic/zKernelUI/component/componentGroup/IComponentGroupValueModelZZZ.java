package basic.zKernelUI.component.componentGroup;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import debug.zKernelUI.component.buttonSwitchLabelGroup.Row2ModelZZZ;

public interface IComponentGroupValueModelZZZ extends IComponentGroupModelZZZ {
	public IComponentGroupValueModelZZZ createModelForGroup(String sTitle, KernelJPanelCascadedZZZ panelParent, int iIndexInGroupCollection) throws ComponentGroupModelExceptionZZZ, ExceptionZZZ; //Diese Modell wird bei jedem "Click" in dem refresh() aufgerufen.	
	public ArrayList<JComponentGroupZZZ>createComponentGroupArrayList() throws ComponentGroupModelExceptionZZZ, ExceptionZZZ;
}
