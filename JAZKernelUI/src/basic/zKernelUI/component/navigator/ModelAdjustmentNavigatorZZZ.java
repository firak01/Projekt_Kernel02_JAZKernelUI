package basic.zKernelUI.component.navigator;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.model.ModelComponentHelperZZZ;

public class ModelAdjustmentNavigatorZZZ extends AbstractModelNavigatorZZZ{	
	public ModelAdjustmentNavigatorZZZ() {	
		super();
	}
	
	public ModelAdjustmentNavigatorZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
	}

	@Override
	public ArrayListExtendedZZZ<INavigatorElementZZZ> createNavigatorElementArrayList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public INavigatorElementZZZ createNavigatorElement(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public HashMapIndexedZZZ<Integer, ArrayList<INavigatorElementZZZ>> createNavigatorElementHashMap()
//			throws ExceptionZZZ {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
	

