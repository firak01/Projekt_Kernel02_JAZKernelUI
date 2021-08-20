package basic.zKernelUI.component.navigator;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;

public class NavigatorElementZZZ extends AbstractNavigatorElementZZZ{
	public NavigatorElementZZZ() {
		super();
	}
	
	public NavigatorElementZZZ(IKernelZZZ objKernel, IModelNavigatorValueZZZ objModelNavigator, String sAlias, int iPositionInModel,String sValue) throws ExceptionZZZ {
		super(objKernel, objModelNavigator, sAlias, iPositionInModel,sValue);
	}
	
	public NavigatorElementZZZ(IKernelZZZ objKernel, IModelNavigatorValueZZZ objModelNavigator, IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ {
		super(objKernel, objModelNavigator, objEntry);
	}

	
}
