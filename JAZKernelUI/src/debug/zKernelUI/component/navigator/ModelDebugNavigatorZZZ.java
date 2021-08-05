package debug.zKernelUI.component.navigator;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
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

public class ModelDebugNavigatorZZZ extends AbstractModelNavigatorZZZ{	
	public ModelDebugNavigatorZZZ() {	
		super();
	}
	
	public ModelDebugNavigatorZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
	}

	//##############################
	
	
	@Override
	public HashMapIndexedZZZ<Integer, ArrayList<INavigatorElementZZZ>> createNavigatorElementHashMap()
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
}

