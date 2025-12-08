package basic.zKernelUI.component.navigator;

import java.util.ArrayList;

import javax.swing.JComponent;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjektZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;

public interface IModelNavigatorValueZZZ {			
	
	public INavigatorElementZZZ createNavigatorElement(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ;
	
	public HashMapIndexedObjektZZZ<Integer, ArrayList<INavigatorElementZZZ>> createNavigatorElementHashMap() throws ExceptionZZZ;
	public ArrayListZZZ<INavigatorElementZZZ>createNavigatorElementArrayList() throws ExceptionZZZ;
}
