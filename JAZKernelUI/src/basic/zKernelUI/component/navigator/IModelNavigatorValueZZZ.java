package basic.zKernelUI.component.navigator;

import java.util.ArrayList;

import javax.swing.JComponent;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;

public interface IModelNavigatorValueZZZ {		
	public HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>>createNavigatorElementHashMap() throws ExceptionZZZ;
}
