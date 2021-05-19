package basic.zKernelUI.component;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zKernelUI.component.componentGroup.IComponentSwitchModelZZZ;
import basic.zKernelUI.component.componentGroup.JComponentGroupZZZ;
import debug.zKernelUI.component.buttonSwitchLabelGroup.Row2ModelZZZ;

public interface IComponentValueModelZZZ extends IComponentSwitchModelZZZ {
	public IComponentValueModelZZZ createModelForGroup(String sTitle, KernelJPanelCascadedZZZ panelParent, int iIndexInGroupCollection) throws ExceptionZZZ; //Diese Modell wird bei jedem "Click" in dem refresh() aufgerufen.	
	public ArrayList<JComponentGroupZZZ>createComponentGroupArrayList() throws ExceptionZZZ;
}
